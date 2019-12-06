package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.datahandler.ConstUtil;
import org.learningredis.ch07.gossipserver.datahandler.JedisUtil;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ArchiveCommandHandler extends AbstractCommandHandler {

    public ArchiveCommandHandler(String nodeName) {
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
        if (result.get(0) && !result.get(3)
                && (result.get(1) || result.get(2))) {
            checkResult = jedisUtil.archiveNode(getNodeName());
        } else {
            checkResult.setFalse("Archive Validation :")
                    .appendReason(ConstUtil.registrationHolder + " = " + result.get(0))
                    .appendReason(ConstUtil.activationHolder + " = " + result.get(1))
                    .appendReason(ConstUtil.passivationHolder + " = " + result.get(2));
        }
        return checkResult;
    }
}
