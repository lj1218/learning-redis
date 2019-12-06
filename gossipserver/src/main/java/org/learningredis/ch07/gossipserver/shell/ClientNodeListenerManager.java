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

    public ClientNodeListenerManager(ClientNode clientNode) {
        nodeName = clientNode.getNodeName();
        privateEventMessageSubscriber = new ClientEventMessageListener(clientNode);
    }

    @Override
    public void start() {
        System.out.println(" start the client node manager ..");
        privateEventThread = new Thread(privateEventMessageSubscriber);
//        commonEventThread.start();
        privateEventThread.start();
    }

    @Override
    public void stop() {
        System.out.println(" stop the client node manager ..");
        privateEventMessageSubscriber.unSubscribe();
//        commonEventThread.interrupt();
        privateEventThread.interrupt();
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
