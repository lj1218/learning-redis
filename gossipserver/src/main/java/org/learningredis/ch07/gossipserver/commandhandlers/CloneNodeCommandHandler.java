package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.token.MapListToken;
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
public class CloneNodeCommandHandler extends AbstractCommandHandler {

    public CloneNodeCommandHandler(String nodeName) {
        super(nodeName);
    }

    @Override
    public CheckResult process(List<Token> tokenList) {
        CheckResult checkResult = new CheckResult();
        MapListToken mapTokens = (MapListToken) tokenList.get(1);
        String target = mapTokens.getNValue("target");
        String source = mapTokens.getNValue("source");
        JedisUtil jedisUtil = new JedisUtil();
        List<Boolean> targetValidityResult = jedisUtil.doesExist(target,
                Arrays.asList(ConstUtil.registrationHolder,
                        ConstUtil.activationHolder,
                        ConstUtil.passivationHolder,
                        ConstUtil.shutdownHolder));
        List<Boolean> sourceValidityResult = jedisUtil.doesExist(source,
                Arrays.asList(ConstUtil.registrationHolder,
                        ConstUtil.activationHolder,
                        ConstUtil.passivationHolder,
                        ConstUtil.shutdownHolder));
        if (targetValidityResult.get(0) && targetValidityResult.get(1)
                && !targetValidityResult.get(2) && !targetValidityResult.get(3)) {
            checkResult = jedisUtil.killNode(getNodeName());
            if (sourceValidityResult.get(0) && sourceValidityResult.get(1)
                    && !sourceValidityResult.get(2) && !sourceValidityResult.get(3)) {
                checkResult = jedisUtil.clone(target, source);
            } else {
                checkResult.setFalse("The source =" + source
                        + " is not in a proper state to clone");
            }
        } else {
            checkResult.setFalse("The target =" + target
                    + " is not in a proper state to clone");
        }
        return checkResult;
    }
}
