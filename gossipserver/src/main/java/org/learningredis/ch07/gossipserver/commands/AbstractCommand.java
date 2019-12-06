package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public abstract class AbstractCommand {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract CheckResult execute(CommandTokens commandTokens);

}
