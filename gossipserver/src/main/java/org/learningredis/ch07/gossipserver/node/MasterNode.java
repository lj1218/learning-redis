package org.learningredis.ch07.gossipserver.node;

import org.learningredis.ch07.gossipserver.util.CheckResult;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MasterNode extends Node {
    public MasterNode() {
        super("master");
    }

    @Override
    public CheckResult process(String commands) {
        return null;
    }
}