package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.ReactivateCommandHandler;
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
 * This command will reactive the node. The precondition for executing this
 * command is that the node should be in the passive mode. Upon reactivation,
 * the client's event listener will be spawned once again. The data in the
 * archive file will be pumped back again to the node's Config store.
 * <p>
 * Sequence of flow of data in Reactive command:
 * Shell => Reactive Command => Reactive Command Handler => JedisUtil
 */
public class ReactivateCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: reactivate
     *
     * Client node:
     *
     * reactivate
     * :->true
     * :->Reactivate success ..
     */
    public ReactivateCommand() {
        validator.configureTemplate().add(new StringToken(Commands.REACTIVATE.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new ReactivateCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
