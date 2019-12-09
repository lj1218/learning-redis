package org.learningredis.chapter.four.pubsub;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class Subscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("on message : " + channel + " value = " + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("on pattern message : " + pattern + " channel = "
                + channel + " value = " + message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("on subscribe : " + channel + " value = " + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("on un-subscribe : " + channel + " value = " + subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("on pattern unsubscribe : " + pattern + " value = " + subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("on pattern subscribe : " + pattern + " value = " + subscribedChannels);
    }
}
