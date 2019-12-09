package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class DisplayCommand extends Commands {

    public DisplayCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        return ProductDBManager.singleton.getProductInfo(
                getArgument().getValue("name")
        );
    }
}
