package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.ConstUtil;
import org.learningredis.ch07.gossipserver.util.JedisUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class SyncCommandHandler extends AbstractCommandHandler {

    public SyncCommandHandler(String nodeName) {
        super(nodeName);
    }

    @Override
    public CheckResult process(List<Token> tokenList) {
        CheckResult checkResult = new CheckResult();
        JedisUtil jedisUtil = new JedisUtil();
        List<Boolean> result = jedisUtil.doesExist(getNodeName(),
                Arrays.asList(ConstUtil.registrationHolder,
                        ConstUtil.activationHolder,
                        ConstUtil.passivationHolder,
                        ConstUtil.shutdownHolder));
        if (result.get(0) && result.get(1) && !result.get(3)) {
            checkResult = jedisUtil.syncNode(getNodeName());
        } else {
            checkResult.setFalse("Sync Failed");
        }
        return checkResult;
    }
}
