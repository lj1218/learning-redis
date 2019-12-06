package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.SyncCommandHandler;
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
 * The sync command will synchronize the data of a node in the gossip server ecosystem.
 * The precondition for executing this command is that the node should be in the
 * registered mode. When this command is issued, the data in the archive file is pumped
 * back into the Config store of the user.
 * <p>
 * Sequence of flow of data in Sync command:
 * Shell => Sync Command => Sync Command Handler => JedisUtil
 */
public class SyncCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: sync
     *
     * Client node
     *
     * sync
     * :->true
     * :->
     */
    public SyncCommand() {
        validator.configureTemplate().add(new StringToken(Commands.SYNC.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new SyncCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
