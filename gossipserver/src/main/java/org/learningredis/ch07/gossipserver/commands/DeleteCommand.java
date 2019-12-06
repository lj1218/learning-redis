package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.DeleteCommandHandler;
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
 * This command will delete the data in the nodes. The precondition for executing this
 * command is that the node should be activated. The command will be executed by passing
 * the name of the variables that needs to be deleted.
 * <p>
 * Sequence of flow of data in Delete command:
 * Shell => Delete Command => Delete Command Handler => JedisUtil
 */
public class DeleteCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: del v1,v2,...
     *
     * Client node:
     *
     * del x,y
     * :->true
     * :->values deleted
     */
    public DeleteCommand() {
        validator.configureTemplate().add(new StringToken(Commands.DEL.getValue()))
                .add(new StringListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new DeleteCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
