package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ShoppingCartDBManager;
import org.learningredis.chapter.six.web.util.UserDBManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class Add2CartCommand extends Commands {

    public Add2CartCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String result = "did not update the shopping cart";
        String sessionID = getArgument().getValue("sessionid");
        String product = getArgument().getValue("product");
        String[] productList = product.split(",");
        Map<String, String> productQtyMap = new HashMap<>();
        for (String _product : productList) {
            String[] nameQty = _product.split("@");
            productQtyMap.put(nameQty[0], nameQty[1]);
        }
        if (UserDBManager.singleton.doesSessionExist(sessionID)) {
            result = ShoppingCartDBManager.singleton.addToShoppingCart(sessionID, productQtyMap);
        }
        return "Result : " + result;
    }
}
