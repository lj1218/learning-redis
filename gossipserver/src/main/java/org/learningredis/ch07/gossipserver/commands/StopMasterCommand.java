package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class StopMasterCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public StopMasterCommand() {
        validator.configureTemplate().add(new StringToken("stop"));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        return new CheckResult().setTrue().appendReason("master stopped..");
    }
}
