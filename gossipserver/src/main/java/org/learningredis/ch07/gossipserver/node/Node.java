package org.learningredis.ch07.gossipserver.node;

import org.learningredis.ch07.gossipserver.util.CheckResult;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public abstract class Node {
    private String nodeName;

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public abstract CheckResult process(String commands);
}
