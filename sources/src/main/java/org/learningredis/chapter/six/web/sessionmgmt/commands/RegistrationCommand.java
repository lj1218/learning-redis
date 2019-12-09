package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class RegistrationCommand extends Commands {

    public RegistrationCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String name = getArgument().getValue("name");
        if (!UserDBManager.singleton.doesUserExist(name)) {
            UserDBManager.singleton.createUser(getArgument().getAttributes());
            return "successful registration  -> " + name;
        }
        return "user already registered in";
    }
}
