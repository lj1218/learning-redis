package org.learningredis.chapter.six.web;

import org.learningredis.chapter.six.web.util.Argument;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This is default command which will get into action if the
 * command passed in the URL is not recognized by the application.
 */
public class DefaultCommand extends Commands {

    public DefaultCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        return "Command Not Recognized!!";
    }
}
