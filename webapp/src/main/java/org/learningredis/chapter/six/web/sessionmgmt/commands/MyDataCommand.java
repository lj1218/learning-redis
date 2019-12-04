package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class MyDataCommand extends Commands {

    public MyDataCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        String name = UserDBManager.singleton.getUserName(sessionID);
        Map<String, String> map = UserDBManager.singleton.getRegistrationMap(name);
        return map.toString();
    }
}
