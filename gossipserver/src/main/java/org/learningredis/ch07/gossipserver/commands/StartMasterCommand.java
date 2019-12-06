package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The start command will start the master node in the gossip server ecosystem. The
 * precondition for executing this command is that the node name should be unique.
 * <p>
 * Sequence of flow of data in Start command:
 * Shell => Start Master Command => JedisUtil
 */
public class StartMasterCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: start
     *
     * Master node:
     *
     * start
     *  start the master node manager ..
     * :->true
     * :->master started..
     */
    public StartMasterCommand() {
        validator.configureTemplate().add(new StringToken(Commands.START.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        return new CheckResult().setTrue().appendReason("master started..");
    }
}
