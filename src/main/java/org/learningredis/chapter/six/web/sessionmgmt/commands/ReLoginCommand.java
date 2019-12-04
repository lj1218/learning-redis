package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class ReLoginCommand extends Commands {

    public ReLoginCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String name = getArgument().getValue("name");
        String password = getArgument().getValue("password");
        if (UserDBManager.singleton.doesUserExist(name)) {
            if (UserDBManager.singleton.getUserPassword(name).equals(password)) {
                String sessionID = UserDBManager.singleton.getUserSessionId(name);
                return "ReLogin successful \n" + name
                        + " \n use the following session id : " + sessionID;
            }
        }
        return " please register before executing command for login";
    }
}
