package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.StatusCommandHandler;
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
 * This command is used to get the current status of a node. The precondition for
 * executing this command is that the node should be in some state. The command
 * in the client focuses on the data of the client node.
 * <p>
 * Sequence of flow of data in Status command:
 * Shell => Status Command => Status Command Handler => JedisUtil
 */
public class StatusCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: status
     *
     * Client node:
     *
     * status
     * :->true
     * :->REGISTRATION-HOLDER = true
     * ACTIVATION-HOLDER = true
     * PASSIVATION-HOLDER = false
     * {createTime=8/19/14 1:46 PM, lastAccessTime=8/19/14 1:46 PM, y=500, x=200}
     *
     * Master node:
     *
     * status
     * :->true
     * :->The following nodes are registered
     * [loki, vinoo]
     * The following nodes are activated
     * [loki, vinoo]
     * The following nodes are passivated
     * []
     * The following nodes are in consistent state
     * []
     */
    public StatusCommand() {
        validator.configureTemplate().add(new StringToken(Commands.STATUS.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new StatusCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
