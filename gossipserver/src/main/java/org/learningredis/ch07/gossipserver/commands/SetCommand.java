package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.SetCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.MapListToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * This command will set the data in the nodes. The precondition for executing this
 * command is that the node should be in the activated state. The command is going
 * to insert the name values into the node's Config-store. The Config store is
 * implemented as the Hashes data structure in Redis. As evident, multiple name-value
 * pairs can be inserted in the Config store.
 * <p>
 * Sequence of flow of data in Set command:
 * Shell => Set Command => Set Command Handler => JedisUtil
 */
public class SetCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: set <name=value>,<name=value>,...
     *
     * Client node:
     *
     * set x=200,y=500
     * :->true
     * :->setting done in vinoo
     */
    public SetCommand() {
        validator.configureTemplate().add(new StringToken(Commands.SET.getValue()))
                .add(new MapListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new SetCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
