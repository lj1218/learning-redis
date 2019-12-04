package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class VisitTodayCommand extends Commands {

    public VisitTodayCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String productName = getArgument().getValue("productname");
        int visitCount = AnalyticsDBManager.singleton.getVisitToday(productName);
        System.out.println(this.getClass().getSimpleName() + ": Printing the result for execute function");
        System.out.println("Result = Total Unique Visitors are: " + visitCount);
        return "Total Unique Visitors are: " + visitCount;
    }
}
