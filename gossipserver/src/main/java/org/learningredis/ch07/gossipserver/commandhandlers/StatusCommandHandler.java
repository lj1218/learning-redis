package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.datahandler.JedisUtil;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class StatusCommandHandler extends AbstractCommandHandler {

    public StatusCommandHandler(String nodeName) {
        super(nodeName);
    }

    @Override
    public CheckResult process(List<Token> tokenList) {
        CheckResult checkResult = new CheckResult();
        JedisUtil jedisUtil = new JedisUtil();
        if (getNodeName().equals("master")) {
            List<String> registeredNames = jedisUtil.getAllNodesFromRegistrationHolder();
            checkResult.setTrue().appendReason("The following nodes are registered")
                    .appendReason(registeredNames.toString());
            List<String> activeNodeNames = jedisUtil.getAllNodesFromActivatedHolder();
            checkResult.appendReason("The following nodes are activated")
                    .appendReason(activeNodeNames.toString());
            List<String> passiveNodeNames = jedisUtil.getAllNodesFromPassivatedHolder();
            checkResult.appendReason("The following nodes are passivated")
                    .appendReason(passiveNodeNames.toString());
            List<String> inconsistentState = jedisUtil.getAllNodesInInconsistentState();
            checkResult.appendReason("The following nodes are in inconsistent state")
                    .appendReason(inconsistentState.toString());
        } else {
            checkResult = jedisUtil.getStatus(getNodeName());
        }
        return checkResult;
    }
}
