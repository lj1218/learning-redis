package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.PassivateCommandHandler;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class PassivateCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public PassivateCommand() {
        validator.configureTemplate().add(new StringToken("passivate"));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new PassivateCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
