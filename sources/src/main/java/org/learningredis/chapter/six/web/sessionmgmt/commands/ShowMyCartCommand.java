package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ShoppingCartDBManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class ShowMyCartCommand extends Commands {

    public ShowMyCartCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        Map<String, String> productMap = ShoppingCartDBManager.singleton.myCartInfo(sessionID);
        StringBuilder sb = new StringBuilder();
        if (!productMap.isEmpty()) {
            sb.append("Your shopping cart contains the following : \n");
            Set<String> set = productMap.keySet();
            int i = 1;
            for (String str : set) {
                sb.append("[").append(i++).append("] product name = ").append(str)
                        .append(" Qty = ").append(productMap.get(str)).append("\n");
            }
            return sb.toString();
        }
        return " your shopping cart is empty.";
    }
}
