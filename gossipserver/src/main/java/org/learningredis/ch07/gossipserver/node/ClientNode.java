package org.learningredis.ch07.gossipserver.node;

import org.learningredis.ch07.gossipserver.commands.*;
import org.learningredis.ch07.gossipserver.shell.ClientNodeListenerManager;
import org.learningredis.ch07.gossipserver.shell.NodeMessageListenerManager;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ClientNode extends Node {

    private NodeMessageListenerManager manager;

    public ClientNode(String nodeName) {
        super(nodeName);
        manager = new ClientNodeListenerManager(this);
    }

    @Override
    public CheckResult process(String commands) {
        AbstractCommand command;
        if (commands.startsWith(Commands.REGISTER.getValue())) {
            command = new RegisterCommand();
        } else if (commands.startsWith(Commands.ACTIVATE.getValue())) {
            command = new ActivateCommand();
        } else if (commands.startsWith(Commands.SET.getValue())) {
            command = new SetCommand();
        } else if (commands.startsWith(Commands.GET.getValue())) {
            command = new GetCommand();
        } else if (commands.startsWith(Commands.DEL.getValue())) {
            command = new DeleteCommand();
        } else if (commands.startsWith(Commands.PASSIVATE.getValue())) {
            command = new PassivateCommand();
        } else if (commands.startsWith(Commands.REACTIVATE.getValue())) {
            command = new ReactivateCommand();
        } else if (commands.startsWith(Commands.ARCHIVE.getValue())) {
            command = new ArchiveCommand();
        } else if (commands.startsWith(Commands.SYNC.getValue())) {
            command = new SyncCommand();
        } else if (commands.startsWith(Commands.RECONNECT.getValue())) {
            command = new ReconnectCommand();
        } else {
            return new CheckResult().setFalse(Commands.ILLEGAL.getValue());
        }

        command.setName(getNodeName());
        CheckResult checkResult = command.execute(new CommandTokens(commands));
        if (commands.startsWith(Commands.ACTIVATE.getValue())
                || commands.startsWith(Commands.REACTIVATE.getValue())
                || commands.startsWith(Commands.RECONNECT.getValue())) {
            if (checkResult.getResult()) {
                manager.start();
            }
        } else if (commands.startsWith(Commands.PASSIVATE.getValue())) {
            if (checkResult.getResult()) {
                manager.stop();
            }
        }
        return checkResult;
    }
}
