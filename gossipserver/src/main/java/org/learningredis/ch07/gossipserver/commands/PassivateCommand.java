package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.PassivateCommandHandler;
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
 * This command will passivate the node into the gossip server ecosystem. The
 * precondition for executing this command is that the node should be in the
 * activated state. On passivation, the client's event listeners will be shutdown
 * and will not be in a position to take events from the master. Since the node
 * is passivated, the data in the node's Config store will be taken and pushed
 * into the archive file of the node.
 * <p>
 * Sequence of flow of data in Passivate command:
 * Shell => Passivate Command => Passivate Command Handler => JedisUtil
 */
public class PassivateCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: passivate
     *
     * Client node:
     *
     * passivate
     * :->true
     * :->Passivation Successful
     */
    public PassivateCommand() {
        validator.configureTemplate().add(new StringToken(Commands.PASSIVATE.getValue()));
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
