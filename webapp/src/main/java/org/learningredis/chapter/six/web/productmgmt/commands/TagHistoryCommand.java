package org.learningredis.chapter.six.web.productmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class TagHistoryCommand extends Commands {

    public TagHistoryCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        String tagName = getArgument().getValue("tagname");
        return AnalyticsDBManager.singleton.getTagHistory(tagName);
    }
}
