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
            BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
            String readLine = commandReader.readLine();
            if (readLine == null) {
                System.out.println("Ctrl + C");
                break;
            }
            CheckResult checkResult = node.process(readLine);
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
