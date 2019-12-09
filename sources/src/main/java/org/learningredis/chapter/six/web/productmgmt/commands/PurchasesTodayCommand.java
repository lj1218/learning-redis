package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class PurchasesTodayCommand extends Commands {

    public PurchasesTodayCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String productName = getArgument().getValue("productname");
        int purchaseCount = ProductDBManager.singleton.getPurchaseToday(productName);
        System.out.println(this.getClass().getSimpleName() + ": Printing the result for execute function");
        System.out.println("Result = Total Unique Customers are: " + purchaseCount);
        return "Total Unique Customers are: " + purchaseCount;
    }
}
