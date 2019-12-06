package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.ActivateCommandHandler;
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
 * This command will activate the node into the gossip server ecosystem. The
 * precondition for executing this command is that the node should be registered.
 * When the node is activated, an entry is added to the Activation holder, which
 * is implemented as Set in Redis. Apart from this, on activation, the client node
 * will spawn listeners, which will be up and ready to listen to any event that
 * can come from the master. The listeners will be basically listening for events
 * on a separate thread.
 * <p>
 * Sequence of flow of data in Activate command:
 * Shell => Activate Command => Activate Command Handler => JedisUtil
 */
public class ActivateCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: activate
     *
     * Client node:
     *
     * activate
     * :->true
     * :->Activation Successful
     */
    public ActivateCommand() {
        validator.configureTemplate().add(new StringToken(Commands.ACTIVATE.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new ActivateCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
