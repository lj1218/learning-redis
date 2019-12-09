package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class MyPurchaseHistoryCommand extends Commands {

    public MyPurchaseHistoryCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        StringBuilder report = new StringBuilder();
        List<String> purchaseHistory = AnalyticsDBManager.singleton.getMyPurchaseHistory(sessionID);
        report.append("Your purchase history is as follows : \n");
        int i = 0;
        for (String purchase : purchaseHistory) {
            report.append("[").append(i++).append("] You purchased ")
                    .append(purchase).append("\n");
        }
        return report.toString();
    }
}
