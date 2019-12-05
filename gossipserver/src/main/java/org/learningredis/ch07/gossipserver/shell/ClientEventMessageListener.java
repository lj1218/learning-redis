package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.commands.CloneNodeCommand;
import org.learningredis.ch07.gossipserver.commands.KillNodeCommand;
import org.learningredis.ch07.gossipserver.commands.MessageCommand;
import org.learningredis.ch07.gossipserver.commands.SetCommand;
import org.learningredis.ch07.gossipserver.node.Node;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.MapListToken;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.ConnectionManager;
import org.learningredis.ch07.gossipserver.util.Validator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ClientEventMessageListener implements Runnable {

    private Subscriber subscriber;
    private Node node;
    private Jedis jedis = ConnectionManager.get();
    private Validator validator;

    public ClientEventMessageListener(Node node) {
        this.node = node;
        subscriber = new Subscriber(node);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            jedis.subscribe(subscriber, node.getNodeName());
        }
    }

    public void unSubscribe() {
        subscriber.unsubscribe(node.getNodeName());
    }

    public class Subscriber extends JedisPubSub {
        public Subscriber(Node clientNode) {
        }

        @Override
        public void onMessage(String nodeName, String message) {
            validator = new Validator();
            validator.configureTemplate().add(new MapListToken());
            validator.setInput(message);
            CheckResult checkResult = validator.validate();
            if (checkResult.getResult()) {
                MapListToken mapListToken = (MapListToken) validator.getToken(0);
                if (mapListToken.containsKey("command")) {
                    String commandValue = mapListToken.getNValue("command");
                    if (commandValue.equals("set")) {
                        MapListToken newMapListToken = mapListToken.removeElement("command");
                        SetCommand command = new SetCommand();
                        command.setName(node.getNodeName());
                        CheckResult result = command.execute(new CommandTokens(
                                "set " + newMapListToken.getValueAsSantizedString()));
                        System.out.println(result.getResult());
                        System.out.println(result.getReason());
                    } else if (commandValue.equals("kill")) {
                        KillNodeCommand command = new KillNodeCommand();
                        command.setName(node.getNodeName());
                        MapListToken newMapListToken = mapListToken.removeElement("command");
                        CheckResult result = command.execute(new CommandTokens(
                                "kill " + node.getNodeName()));
                        System.out.println(result.getResult());
                        System.out.println(result.getReason());
                    } else if (commandValue.equals("clone")) {
                        CloneNodeCommand command = new CloneNodeCommand();
                        command.setName(node.getNodeName());
                        MapListToken newMapListToken = mapListToken.removeElement("command");
                        CheckResult result = command.execute(new CommandTokens(
                                "clone " + newMapListToken.getValueAsSantizedString()));
                        System.out.println(result.getResult());
                        System.out.println(result.getReason());
                    } else {
                        MessageCommand messageCommand = new MessageCommand();
                        messageCommand.setName(nodeName);
                        CommandTokens commandTokens = new CommandTokens("msg master where msg=inllegal_command");
                        messageCommand.execute(commandTokens);
                    }
                } else {
                    System.out.println(":->" + checkResult.appendReason(
                            "The command sent from publisher does not contain 'command' token"));
                }
            } else {
                System.out.println(":->" + checkResult.getReason());
            }
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println(channel);
            System.out.println(message);
        }
    }
}
