package org.learningredis.ch07.gossipserver.commandhandlers;

import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public abstract class AbstractCommandHandler {

    private String nodeName;

    public AbstractCommandHandler(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public abstract CheckResult process(List<Token> tokenList);

}
