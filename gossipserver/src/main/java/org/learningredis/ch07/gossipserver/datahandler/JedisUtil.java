package org.learningredis.ch07.gossipserver.datahandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        System.out.println("holder=" + holder + ", nodeName=" + nodeName);
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
        return null;
    }

    public List<String> getAllNodesFromActivatedHolder() {
        return null;
    }

    public List<String> getAllNodesFromPassivatedHolder() {
        return null;
    }

    public List<String> getAllNodesInInconsistentState() {
        return null;
    }

    public CheckResult getStatus(String nodeName) {
        return null;
    }

    public CheckResult passivateNode(String nodeName) {
        Jedis jedis = getResource();
        String configStore = ConstUtil.getConfigurationStore(nodeName);
        Map<String, String> config = jedis.hgetAll(configStore);
        CheckResult checkResult = dumpConfig(ConstUtil.getArchiveStore(nodeName), config);
        if (!checkResult.getResult()) {
            returnResource(jedis);
            return checkResult;
        }
        Pipeline pipeline = jedis.pipelined();
        pipeline.srem(ConstUtil.activationHolder, nodeName);
        pipeline.sadd(ConstUtil.passivationHolder, nodeName);
        pipeline.del(configStore);
        pipeline.sync();
        returnResource(jedis);
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
            jedis.hset(ConstUtil.getConfigurationStore(nodeName), config);
            returnResource(jedis);
        } catch (IOException e) {
            checkResult.setFalse(e.getMessage());
            return checkResult;
        }
        return checkResult;
    }

    public CheckResult archiveNode(String nodeName) {
        return null;
    }

    public CheckResult syncNode(String nodeName) {
        return null;
    }

    public CheckResult reconnectNode(String nodeName) {
        return null;
    }

    public CheckResult publish(String channel, Map<String, String> map) {
        return null;
    }

    public CheckResult killNode(String nodeName) {
        return null;
    }

    public CheckResult clone(String target, String source) {
        return null;
    }
}
