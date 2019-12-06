package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.ReconnectCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The reconnect command will reconnect a node in the gossip server ecosystem. The
 * precondition for executing this command is that the node should be in the activated
 * state and the node should have undergone a shut down. So, when the node comes up
 * after the shut down and this command is fired, then the listeners for the client
 * node will get spawned and the node will be back in the activated state.
 * <p>
 * Sequence of flow of data in Reconnect command:
 * Shell => Reconnect Command => Reconnect Command Handler => JedisUtil
 */
public class ReconnectCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: reconnect
     *
     * Client node:
     *
     * reconnect
     * :->true
     * :->
     */
    public ReconnectCommand() {
        validator.configureTemplate().add(new StringToken(Commands.RECONNECT.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new ReconnectCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
