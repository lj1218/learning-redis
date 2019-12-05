package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.GetNodeDataCommandHandler;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.StringListToken;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class GetNodeDataCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public GetNodeDataCommand() {
        // Master node
        // get x,y where nodes are loki,vinoo
        // :->true
        // :->The results for loki :
        // [300, 600]
        //
        // The results for vinoo :
        // [200, 500]
        validator.configureTemplate().add(new StringToken("get"))
                .add(new StringListToken()).add(new StringToken("where"))
                .add(new StringToken("nodes")).add(new StringToken("are"))
                .add(new StringListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new GetNodeDataCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
