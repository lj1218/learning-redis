package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class DisplayTagCommand extends Commands {

    public DisplayTagCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        String tagName = getArgument().getValue("tagname");
        return ProductDBManager.singleton.getTagValues(tagName);
    }
}
