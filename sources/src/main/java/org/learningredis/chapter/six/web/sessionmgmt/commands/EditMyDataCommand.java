package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class EditMyDataCommand extends Commands {

    public EditMyDataCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        Map<String, String> editMap = getArgument().getAttributes();
        boolean result = UserDBManager.singleton.editRegistrationMap(editMap);
        if (result) {
            return "Edit is Done....";
        }
        return "Edit not Done.... please check sessionid and name combination";
    }
}
