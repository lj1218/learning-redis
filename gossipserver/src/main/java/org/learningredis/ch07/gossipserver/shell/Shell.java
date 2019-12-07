package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.node.ClientNode;
import org.learningredis.ch07.gossipserver.node.MasterNode;
import org.learningredis.ch07.gossipserver.node.Node;
import org.learningredis.ch07.gossipserver.util.CheckResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * Shell is a program that acts like a standalone gateway to the gossip server as well as
 * a plugin to an application that wants to use the gossip server. The shell activates the
 * node, which in turn prepares the listeners and the command library for the node. There
 * are two types of nodes: client nodes and the master node.
 */
public class Shell {

    private static Shell singleton = new Shell();

    private Node node = null;

    private Shell() {
    }

    public static Shell instance() {
        return singleton;
    }

    // : as an shell API mode.
    public Shell asClient(String nodeName) {
        if (node != null && nodeName != null && !nodeName.trim().isEmpty()) {
            node = new ClientNode(nodeName);
            return this;
        }
        return null;
    }

    public Shell asMaster() {
        if (node != null) {
            node = new MasterNode();
            return this;
        }
        return null;
    }

    public CheckResult execute(String commands) {
        CheckResult checkResult = new CheckResult();
        if (commands != null && !commands.trim().isEmpty()) {
            checkResult = node.process(commands);
        }
        return checkResult;
    }

    private void startInteracting() throws IOException {
        System.out.println("Please enter the name of the node..");
        BufferedReader nodeNameReader = new BufferedReader(new InputStreamReader(System.in));
        String nodeName = nodeNameReader.readLine();
        if (nodeName.equals("master")) {
            node = new MasterNode();
        } else {
            node = new ClientNode(nodeName);
        }
        while (true) {
            System.out.printf("[%s] > ", node.getNodeName());
            BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
            String commands = commandReader.readLine();
            if (commands == null) {
                System.out.println("Ctrl + C");
                break;
            }
            if ((commands = commands.trim()).isEmpty()) {
                continue;
            }
            CheckResult checkResult = node.process(commands);
            System.out.println(":->" + checkResult.getResult());
            System.out.println(":->" + checkResult.getReason());
            System.out.println(":->" + checkResult.getValue());
        }
        System.exit(0);
    }

    // : as a shell standalone mode.
    public static void main(String[] args) throws IOException {
        Shell shell = Shell.instance();
        shell.startInteracting();
    }
}
