package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class LogoutCommand extends Commands {

    public LogoutCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        if (UserDBManager.singleton.expireSession(sessionID)) {
            return "logout was clean";
        }
        return "logout was not clean";
    }
}
