package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;
import org.learningredis.chapter.six.web.util.UserDBManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class LoginCommand extends Commands {

    public LoginCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String name = getArgument().getValue("name");
        String password = getArgument().getValue("password");
        if (UserDBManager.singleton.doesUserExist(name)) {
            if (UserDBManager.singleton.getUserPassword(name).equals(password)
                    && UserDBManager.singleton.getUserSessionId(name).equals("null")) {
                String sessionID = ProductDBManager.getRandomSessionID();
                UserDBManager.singleton.login(sessionID, name);
                Map<String, String> map = new HashMap<>();
                map.put("sessionID", sessionID);
                UserDBManager.singleton.setRegistrationMap(name, map);
                System.out.println("login map : " + map);
                AnalyticsDBManager.singleton.registerInSessionTracker(sessionID);
                return "Login successful \n" + name + " \n use the following session id : " + sessionID;
            } else if (UserDBManager.singleton.getUserPassword(name).equals(password)
                    && !UserDBManager.singleton.getUserSessionId(name).equals("null")) {
                return " Login failed ...u r already logged in\n please logout to login again\n or try relogin comand";
            } else {
                return " Login failed ...invalid password";
            }
        }
        return "please register before executing command for login";
    }
}
