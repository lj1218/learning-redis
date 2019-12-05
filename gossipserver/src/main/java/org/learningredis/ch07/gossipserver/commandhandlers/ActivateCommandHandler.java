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
public class ActivateCommandHandler extends AbstractCommandHandler {

    public ActivateCommandHandler(String nodeName) {
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
        if (result.get(0) && !result.get(1)
                && !result.get(2) && !result.get(3)) {
            checkResult = jedisUtil.activateNode(getNodeName());
        } else {
            checkResult.setFalse("Activation Failed :")
                    .appendReason(ConstUtil.registrationHolder + " = " + result.get(0))
                    .appendReason(ConstUtil.activationHolder + " = " + result.get(1))
                    .appendReason(ConstUtil.passivationHolder + " = " + result.get(2))
                    .appendReason(ConstUtil.shutdownHolder + " = " + result.get(3));
        }
        return checkResult;
    }
}
