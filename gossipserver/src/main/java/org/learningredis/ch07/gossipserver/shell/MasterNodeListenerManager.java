package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.commands.AbstractCommand;
import org.learningredis.ch07.gossipserver.commands.StartMasterCommand;
import org.learningredis.ch07.gossipserver.commands.StopMasterCommand;
import org.learningredis.ch07.gossipserver.node.MasterNode;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MasterNodeListenerManager implements NodeMessageListenerManager {

    private MasterEventMessageListener masterEventMessageSubscriber;
    private Thread privateEventThread;
    private MasterNode masterNode;

    public MasterNodeListenerManager(MasterNode masterNode) {
        this.masterNode = masterNode;
        masterEventMessageSubscriber = new MasterEventMessageListener(masterNode);
    }

    @Override
    public void start() {
        System.out.println(" start the master node manager ..");
        privateEventThread = new Thread(masterEventMessageSubscriber);
        privateEventThread.start();
    }

    @Override
    public void stop() {
        System.out.println(" stop the master node manager ..");
        privateEventThread.interrupt();
        masterEventMessageSubscriber.unsubscribe();
    }

    @Override
    public void passCommand(AbstractCommand command) {
        if (command instanceof StartMasterCommand) {
            this.start();
        } else if (command instanceof StopMasterCommand) {
            this.stop();
        }
    }
}
