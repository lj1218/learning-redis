package org.learningredis.chapter.four.pubsub;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class Publisher {

    private JedisWrapper jedis = new JedisWrapper();

    private void publisher() {
        publish();
        jedis.destroy();
    }

    private void publish() {
        String msg = "Houstan calling texas... message published!!";
        jedis.publish("news", "[1-1] " + msg);
        jedis.publish("news", "[1-2] " + msg);
        jedis.publish("news.sport", "[2-1] " + msg);
        jedis.publish("news.sport", "[2-2] " + msg);
    }

    public static void main(String[] args) {
        new Publisher().publisher();
    }
}
