package org.learningredis.chapter.six.web;

import org.learningredis.chapter.six.web.util.Argument;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This is the parent and abstract class for all the commands that
 * will be implemented in the application.
 */
public abstract class Commands {
    private Argument argument;

    public Commands(Argument argument) {
        this.argument = argument;
    }

    public abstract String execute();

    public Argument getArgument() {
        return argument;
    }
}
