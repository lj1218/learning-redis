package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.node.Node;
import org.learningredis.ch07.gossipserver.token.MapListToken;
import org.learningredis.ch07.gossipserver.util.ConnectionManager;
import org.learningredis.ch07.gossipserver.util.Validator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MasterEventMessageListener implements Runnable {
    private Subscriber subscriber;
    private Node node;
    private Jedis jedis = ConnectionManager.get();
    private Validator validator = new Validator();

    public MasterEventMessageListener(Node node) {
        this.node = node;
        subscriber = new Subscriber(node);
        validator.configureTemplate().add(new MapListToken());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            jedis.subscribe(subscriber, node.getNodeName());
        }
    }

    public void unsubscribe() {
        subscriber.unsubscribe(node.getNodeName());
    }

    public class Subscriber extends JedisPubSub {
        public Subscriber(Node node) {
        }

        @Override
        public void onMessage(String nodeName, String message) {
            System.out.println("msg: " + message);
            System.out.println("Not processed further in the current implementation");
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println(channel);
            System.out.println(message);
        }
    }
}
