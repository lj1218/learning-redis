package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class CommissionProductCommand extends Commands {

    public CommissionProductCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        Map<String, String> productAttributes = getArgument().getAttributes();
        boolean commissionResult = ProductDBManager.singleton.commissionProduct(productAttributes);
        boolean taggingResult = ProductDBManager.singleton.enterTagEntries(
                productAttributes.get("name"), productAttributes.get("tags"));
        if (commissionResult && taggingResult) {
            return "commissioning successful";
        }
        return "commissioning not successful";
    }
}
