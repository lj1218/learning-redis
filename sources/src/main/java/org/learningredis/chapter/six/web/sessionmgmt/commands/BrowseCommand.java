package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class BrowseCommand extends Commands {

    public BrowseCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String productName = getArgument().getValue("browse");
        if (ProductDBManager.singleton.keyExist(productName)) {
            AnalyticsDBManager.singleton.updateBrowsingHistory(
                    getArgument().getValue("sessionid"), productName);
            return "You are browsing the following product = " +
                    productName + "\n" +
                    ProductDBManager.singleton.getProductInfo(productName);
        }
        return "Error: The product you are trying to browse does not exist i.e. " + productName;
    }
}
