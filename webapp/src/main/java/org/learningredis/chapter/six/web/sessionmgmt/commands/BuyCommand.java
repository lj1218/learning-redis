package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ShoppingCartDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class BuyCommand extends Commands {

    public BuyCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String sessionID = getArgument().getValue("sessionid");
        return ShoppingCartDBManager.singleton.buyItemsInTheShoppingCart(sessionID);
    }
}
