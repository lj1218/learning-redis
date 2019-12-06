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
 * The stop command will stop the master node in the gossip server ecosystem.
 * The precondition for executing this command is that the node should be in
 * the start mode.
 * <p>
 * Sequence of flow of data in Stop command:
 * Shell => Stop Master Command => JedisUtil
 */
public class StopMasterCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: stop
     *
     * Master node:
     *
     * stop
     *  stop the master node manager ..
     * :->true
     * :->master stopped..
     */
    public StopMasterCommand() {
        validator.configureTemplate().add(new StringToken(Commands.STOP.getValue()));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        return new CheckResult().setTrue().appendReason("master stopped..");
    }
}
