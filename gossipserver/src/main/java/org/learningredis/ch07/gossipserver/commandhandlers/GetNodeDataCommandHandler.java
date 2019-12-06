package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.datahandler.ConstUtil;
import org.learningredis.ch07.gossipserver.datahandler.JedisUtil;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringListToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class GetNodeDataCommandHandler extends AbstractCommandHandler {

    public GetNodeDataCommandHandler(String nodeName) {
        super(nodeName);
    }

    @Override
    public CheckResult process(List<Token> tokenList) {
        CheckResult checkResult = new CheckResult();
        StringListToken gettersStringListToken = (StringListToken) tokenList.get(1);
        StringListToken nodesStringListToken = (StringListToken) tokenList.get(5);
        List<String> nodeList = nodesStringListToken.getValueAsList();
        JedisUtil jedisUtil = new JedisUtil();
        for (String nodeName : nodeList) {
            List<Boolean> result = jedisUtil.doesExist(getNodeName(),
                    Arrays.asList(ConstUtil.registrationHolder,
                            ConstUtil.activationHolder,
                            ConstUtil.passivationHolder,
                            ConstUtil.shutdownHolder));
            if (result.get(0) && result.get(1)
                    && !result.get(2) && !result.get(3)) {
                CheckResult chkResult = jedisUtil.getValuesFromNode(nodeName,
                        gettersStringListToken.getValueAsList());
                checkResult.setTrue()
                        .appendReason("The results for " + nodeName + " :")
                        .appendReason(chkResult.getReason());
            } else {
                checkResult.appendReason("The node where the GET didn't work is as follows: ")
                        .setFalse("Activation Validation for " + nodeName + " :")
                        .appendReason(ConstUtil.registrationHolder + " = " + result.get(0))
                        .appendReason(ConstUtil.activationHolder + " = " + result.get(1))
                        .appendReason(ConstUtil.passivationHolder + " = " + result.get(2));
            }
        }
        return checkResult;
    }
}
