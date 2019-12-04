package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class UpdateTagCommand extends Commands {

    public UpdateTagCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        String productName = getArgument().getValue("productname");
        String details = getArgument().getValue("details");
        String actionType = getArgument().getValue("action");
        switch (actionType.toLowerCase()) {
            case "browse":
                if (productName != null && ProductDBManager.singleton.keyExist(productName)) {
                    AnalyticsDBManager.singleton.updateRatingInTag(productName, 1);
                    AnalyticsDBManager.singleton.updateProductVisit(sessionID, productName);
                }
                break;
            case "buy":
                System.out.println("Buying the products in the shopping cart!!");
                String[] products = details.split(",");
                for (String product : products) {
                    if (product != null && !product.trim().equals("")) {
                        AnalyticsDBManager.singleton.updateRatingInTag(product, 10);
                        AnalyticsDBManager.singleton.updateProductPurchase(sessionID, product);
                    }
                }
                break;
            default:
                System.out.println("The URL cannot be acted upon");
                break;
        }
        return "";
    }
}
