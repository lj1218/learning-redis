package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.KillNodeCommandHandler;
import org.learningredis.ch07.gossipserver.datahandler.ConstUtil;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.io.File;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The kill command is used to kill a node in the gossip server ecosystem. The
 * precondition for executing this command is that the master node should be in
 * the start mode. Here we'll do it via the msg command.
 * <p>
 * Sequence of flow of data in Kill command:
 * Shell => Kill Node Command => Kill Node Command Handler => JedisUtil
 */
public class KillNodeCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: msg <node name> where command = kill
     *
     * Master node:
     *
     * msg vinoo where command=kill
     * command=kill
     * :->true
     * :->Sent to desired channel
     *
     *
     * Client node:
     *
     * Archiving the node..
     * Archived the node..
     */
    public KillNodeCommand() {
        validator.configureTemplate().add(new StringToken(Commands.KILL.getValue()))
                .add(new StringToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new KillNodeCommandHandler(getName()).process(tokenList);
            if (checkResult.getResult()) {
                String path = ConstUtil.getArchiveStore(getName());
                File file = new File(path);
                if (file.exists()) {
                    if (file.delete()) {
                        System.exit(0);
                    } else {
                        checkResult.appendReason("Archive file for "
                                + getName() + ".json could not get deleted!");
                    }
                }
            }
        }
        return checkResult;
    }
}
