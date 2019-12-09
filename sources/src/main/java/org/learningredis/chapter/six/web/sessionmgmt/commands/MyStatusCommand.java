package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.UserDBManager;

import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class MyStatusCommand extends Commands {

    public MyStatusCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        if (UserDBManager.singleton.doesSessionExist(sessionID)) {
            Set<String> browsingHistory = AnalyticsDBManager.singleton.getBrowsingHistory(sessionID);
            StringBuilder sb = new StringBuilder();
            sb.append(" View your browsing history where the one on top is the least visited product\n")
                    .append(" and the product at the bottom is the most frequented product\n");
            int i = 1;
            for (String history : browsingHistory) {
                sb.append("[").append(i++).append("] ").append(history).append("\n");
            }
            System.out.println(getClass().getSimpleName() + ": Printing the result for execute function");
            System.out.println("Result = " + sb.toString());
            return sb.toString();
        }
        return "history is not available";
    }
}
