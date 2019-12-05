package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.MessageCommandHandler;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.MapListToken;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MessageCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public MessageCommand() {
        // Master node
        // msg vinoo where command=set, p=300,z=600
        // command=set, p=300, z=600
        // :->true
        // :->Sent to desired channel

        // Client node
        // true
        // setting done in vinoo
        validator.configureTemplate().add(new StringToken("msg"))
                .add(new StringToken()).add(new StringToken("where"))
                .add(new MapListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new MessageCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
