package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.commands.*;
import org.learningredis.ch07.gossipserver.node.ClientNode;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ClientNodeListenerManager implements NodeMessageListenerManager {

    private String nodeName;
    private ClientEventMessageListener privateEventMessageSubscriber;
    private Thread commonEventThread;
    private Thread privateEventThread;
    private boolean started;

    public ClientNodeListenerManager(ClientNode clientNode) {
        nodeName = clientNode.getNodeName();
        privateEventMessageSubscriber = new ClientEventMessageListener(clientNode);
    }

    @Override
    public synchronized void start() {
        if (started) {
            System.out.println(" the client node manager already started");
            return;
        }
        System.out.println(" start the client node manager ..");
        privateEventThread = new Thread(privateEventMessageSubscriber);
//        commonEventThread.start();
        privateEventThread.start();
        started = true;
    }

    @Override
    public synchronized void stop() {
        if (!started) {
            System.out.println(" the client node manager not started");
            return;
        }
        System.out.println(" stop the client node manager ..");
        privateEventMessageSubscriber.unSubscribe();
//        commonEventThread.interrupt();
        privateEventThread.interrupt();
        started = false;
    }

    @Override
    public void passCommand(AbstractCommand command) {
        if (command instanceof ActivateCommand || command instanceof ReactivateCommand) {
            this.start();
        } else if (command instanceof PassivateCommand || command instanceof KillNodeCommand) {
            this.stop();
        }
    }
}
