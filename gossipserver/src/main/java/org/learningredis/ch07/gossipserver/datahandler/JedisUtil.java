package org.learningredis.ch07.gossipserver.datahandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.MapListToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.util.*;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * This class is instrumental in conversing with the data store. All the logic around
 * managing the account, state, and data of a node is managed here.
 */
public class JedisUtil extends JedisPool {

    public CheckResult setValuesInNode(String nodeName, Map<String, String> map) {
        Jedis jedis = getResource();
        jedis.hset(ConstUtil.getConfigurationStore(nodeName), map);
        returnResource(jedis);
        return new CheckResult().appendReason("setting done in " + nodeName);
    }

    public CheckResult getValuesFromNode(String nodeName, List<String> values) {
        Jedis jedis = getResource();
        Pipeline pipeline = jedis.pipelined();
        String configStore = ConstUtil.getConfigurationStore(nodeName);
        for (String value : values) {
            pipeline.hget(configStore, value);
        }
        List<Object> result = pipeline.syncAndReturnAll();
        returnResource(jedis);
        return new CheckResult().appendReason(result.toString());
    }

    public CheckResult deleteValuesFromNode(String nodeName, List<String> values) {
        Jedis jedis = getResource();
        Pipeline pipeline = jedis.pipelined();
        String configStore = ConstUtil.getConfigurationStore(nodeName);
        for (String value : values) {
            pipeline.hdel(configStore, value);
        }
        pipeline.sync();
        returnResource(jedis);
        return new CheckResult().appendReason("values deleted");
    }

    public List<Boolean> doesExist(String nodeName, List<String> holders) {
        if (holders == null) {
            return new ArrayList<>(0);
        }
        List<Boolean> result = new ArrayList<>(holders.size());
        for (String holder : holders) {
            result.add(doesExist(nodeName, holder));
        }
        return result;
    }

    public Boolean doesExist(String nodeName, String holder) {
        Jedis jedis = getResource();
        boolean result = jedis.sismember(holder, nodeName);
        returnResource(jedis);
        return result;
    }

    public CheckResult registerNode(String nodeName) {
        Jedis jedis = getResource();
        jedis.sadd(ConstUtil.registrationHolder, nodeName);
        returnResource(jedis);
        return new CheckResult();
    }

    public CheckResult activateNode(String nodeName) {
        Jedis jedis = getResource();
        jedis.sadd(ConstUtil.activationHolder, nodeName);
        returnResource(jedis);
        return new CheckResult().appendReason("Activation Successful");
    }

    public List<String> getAllNodesFromRegistrationHolder() {
        return getSetMembers(ConstUtil.registrationHolder);
    }

    public List<String> getAllNodesFromActivatedHolder() {
        return getSetMembers(ConstUtil.activationHolder);
    }

    public List<String> getAllNodesFromPassivatedHolder() {
        return getSetMembers(ConstUtil.passivationHolder);
    }

    public List<String> getAllNodesInInconsistentState() {
        return getSetsInter(ConstUtil.activationHolder, ConstUtil.passivationHolder);
    }

    private List<String> getSetMembers(String holder) {
        Jedis jedis = getResource();
        Set<String> members = jedis.smembers(holder);
        returnResource(jedis);
        List<String> result = new ArrayList<>(members.size());
        result.addAll(members);
        return result;
    }

    private List<String> getSetsInter(String... holders) {
        Jedis jedis = getResource();
        Set<String> members = jedis.sinter(holders);
        returnResource(jedis);
        List<String> result = new ArrayList<>(members.size());
        result.addAll(members);
        return result;
    }

    public CheckResult getStatus(String nodeName) {
        return new CheckResult()
                .appendReason(ConstUtil.registrationHolder + " = "
                        + doesExist(nodeName, ConstUtil.registrationHolder))
                .appendReason(ConstUtil.activationHolder + " = "
                        + doesExist(nodeName, ConstUtil.activationHolder))
                .appendReason(ConstUtil.passivationHolder + " = "
                        + doesExist(nodeName, ConstUtil.passivationHolder))
                .appendReason(JSON.toJSONString(getConfigFromConfigStore(nodeName)));
    }

    public CheckResult passivateNode(String nodeName) {
        CheckResult checkResult = archiveNode(nodeName);
        if (!checkResult.getResult()) {
            return checkResult;
        }
        passivateNode0(nodeName);
        return new CheckResult().appendReason("Passivation Successful");
    }

    // save the config into the archive file
    private CheckResult dumpConfig(String fileName, Map<String, String> config) {
        CheckResult checkResult = new CheckResult();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(JSON.toJSONString(config));
            bw.write(System.lineSeparator());
        } catch (IOException e) {
            checkResult.setFalse(e.getMessage());
        }
        return checkResult;
    }

    public CheckResult reactiveNode(String nodeName) {
        CheckResult checkResult = pumpConfig(nodeName);
        if (!checkResult.getResult()) {
            return checkResult;
        }
        Jedis jedis = getResource();
        Pipeline pipeline = jedis.pipelined();
        pipeline.sadd(ConstUtil.activationHolder, nodeName);
        pipeline.srem(ConstUtil.passivationHolder, nodeName);
        pipeline.sync();
        returnResource(jedis);
        return checkResult.appendReason("Reactivate success ..");
    }

    // pumped config back again to the node's Config store
    private CheckResult pumpConfig(String nodeName) {
        CheckResult checkResult = new CheckResult();
        try (InputStream is = new FileInputStream(ConstUtil.getArchiveStore(nodeName))) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            Map<String, String> config = new HashMap<>();
            for (Map.Entry<String, Object> entry : JSONObject.parseObject(new String(bytes)).entrySet()) {
                config.put(entry.getKey(), entry.getValue().toString());
            }
            Jedis jedis = getResource();
            Pipeline pipeline = jedis.pipelined();
            String configStore = ConstUtil.getConfigurationStore(nodeName);
            pipeline.del(configStore);
            pipeline.hset(configStore, config);
            pipeline.sync();
            returnResource(jedis);
        } catch (IOException e) {
            checkResult.setFalse(e.getMessage());
            return checkResult;
        }
        return checkResult;
    }

    public CheckResult archiveNode(String nodeName) {
        return dumpConfig(ConstUtil.getArchiveStore(nodeName), getConfigFromConfigStore(nodeName));
    }

    private Map<String, String> getConfigFromConfigStore(String nodeName) {
        Jedis jedis = getResource();
        Map<String, String> config = jedis.hgetAll(ConstUtil.getConfigurationStore(nodeName));
        returnResource(jedis);
        return config;
    }

    public CheckResult syncNode(String nodeName) {
        return pumpConfig(nodeName);
    }

    public CheckResult reconnectNode(String nodeName) {
        return new CheckResult();
    }

    public CheckResult publish(String channel, Map<String, String> command) {
        Jedis jedis = getResource();
        jedis.publish(channel, MapListToken.map2SanitizedString(command));
        returnResource(jedis);
        return new CheckResult().appendReason("Send to desired channel: " + channel);
    }

    public CheckResult killNode(String nodeName) {
        destroyNode(nodeName);
        return new CheckResult();
    }

    public CheckResult clone(String target, String source) {
        return null;
    }

    public CheckResult stopNode(String nodeName) {
        CheckResult checkResult = archiveNode(nodeName);
        if (!checkResult.getResult()) {
            return checkResult;
        }
        passivateNode0(nodeName);
        Jedis jedis = getResource();
        jedis.sadd(ConstUtil.shutdownHolder, nodeName);
        returnResource(jedis);
        return checkResult;
    }

    private void passivateNode0(String nodeName) {
        Jedis jedis = getResource();
        Pipeline pipeline = jedis.pipelined();
        pipeline.srem(ConstUtil.activationHolder, nodeName);
        pipeline.sadd(ConstUtil.passivationHolder, nodeName);
        pipeline.del(ConstUtil.getConfigurationStore(nodeName));
        pipeline.sync();
        returnResource(jedis);
    }

    private void destroyNode(String nodeName) {
        Jedis jedis = getResource();
        Pipeline pipeline = jedis.pipelined();
        pipeline.srem(ConstUtil.registrationHolder, nodeName);
        pipeline.srem(ConstUtil.activationHolder, nodeName);
        pipeline.srem(ConstUtil.passivationHolder, nodeName);
        pipeline.srem(ConstUtil.shutdownHolder, nodeName);
        pipeline.del(ConstUtil.getConfigurationStore(nodeName));
        pipeline.sync();
        returnResource(jedis);
    }

}
