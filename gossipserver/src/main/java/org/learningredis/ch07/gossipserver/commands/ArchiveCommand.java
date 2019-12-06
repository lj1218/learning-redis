package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.ArchiveCommandHandler;
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
 * This command will archive the data of a node in the gossip server ecosystem. The
 * precondition for executing this command is that the node should be in the registered
 * mode. When this command is issued, the data in the node's Config store will be
 * flushed and put into an archive file in the filesystem of the client node's machine.
 * <p>
 * Sequence of flow of data in Archive command:
 * Shell => Archive Command => Archive Command Handler => JedisUtil
 */
public class ArchiveCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: archive
     *
     * Client node:
     *
     * archive
     * :->true
     * :->
     */
    public ArchiveCommand() {
        validator.configureTemplate().add(new StringToken(Commands.ARCHIVE.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new ArchiveCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
