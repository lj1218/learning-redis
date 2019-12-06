package org.learningredis.ch07.gossipserver.node;

import org.learningredis.ch07.gossipserver.commands.*;
import org.learningredis.ch07.gossipserver.shell.MasterNodeListenerManager;
import org.learningredis.ch07.gossipserver.shell.NodeMessageListenerManager;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MasterNode extends Node {

    private NodeMessageListenerManager manager;

    public MasterNode() {
        super("master");
        manager = new MasterNodeListenerManager(this);
    }

    @Override
    public CheckResult process(String commands) {
        AbstractCommand command;
        if (commands.startsWith(Commands.START.getValue())) {
            command = new StartMasterCommand();
        } else if (commands.startsWith(Commands.STOP.getValue())) {
            command = new StopMasterCommand();
        } else if (commands.startsWith(Commands.STATUS.getValue())) {
            command = new StatusCommand();
        } else if (commands.startsWith(Commands.GET.getValue())) {
            command = new GetNodeDataCommand();
        } else if (commands.startsWith(Commands.MSG.getValue())) {
            command = new MessageCommand();
        } else if (commands.startsWith(Commands.KILL.getValue())) {
            command = new KillNodeCommand();
        } else if (commands.startsWith(Commands.CLONE.getValue())) {
            command = new CloneNodeCommand();
        } else {
            return new CheckResult().setFalse(Commands.ILLEGAL.getValue());
        }

        command.setName(getNodeName());
        CheckResult checkResult = command.execute(new CommandTokens(commands));
        if (commands.startsWith(Commands.START.getValue())) {
            if (checkResult.getResult()) {
                manager.start();
            }
        } else if (commands.startsWith(Commands.STOP.getValue())) {
            if (checkResult.getResult()) {
                manager.stop();
            }
        }
        return checkResult;
    }
}
