package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.GetCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringListToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * This command will get the data from the nodes. The precondition for executing this
 * command is that the node should be in the activated state. The input will be a list
 * of variables and the data needs to be picked up from the Config store. Every node
 * will have its own Config store.
 * <p>
 * Sequence of flow of data in Get command:
 * Shell => Get Command => Get Command Handler => JedisUtil
 */
public class GetCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: get v1,v2,...
     *
     * Client node:
     *
     * get x,y
     * :->true
     * :->[200, 500]
     */
    public GetCommand() {
        validator.configureTemplate().add(new StringToken(Commands.GET.getValue()))
                .add(new StringListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new GetCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
