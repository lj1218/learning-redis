package org.learningredis.chapter.four.pubsub;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class SubscriberProcessor implements Runnable {

    private JedisWrapper jedis = new JedisWrapper();
    private Subscriber subscriber = new Subscriber();
    private Thread simpleThread;
    private boolean isSubscribed;
    private boolean isPSubscribed;

    private void unsubscribe() {
        simpleThread.interrupt();
        if (subscriber.isSubscribed()) {
            if (isSubscribed) {
                subscriber.unsubscribe();
            }
            if (isPSubscribed) {
                subscriber.punsubscribe();
            }
        }
    }

    private void subscriberProcessor() {
        simpleThread = new Thread(this);
        simpleThread.start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            subscribe(); // comment out to publish the message to a pattern
//            psubscribe(); // uncomment out to publish the message to a pattern
        }
    }

    private void subscribe() {
        isSubscribed = true;
        jedis.subscribe(subscriber, "news"); // blocked
    }

    private void psubscribe() {
        isPSubscribed = true;
        jedis.psubscribe(subscriber, "news*"); // blocked
    }

    public void destroy() {
        jedis.destroy();
    }

    public static void main(String[] args) {
        SubscriberProcessor test = new SubscriberProcessor();
        test.subscriberProcessor();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test.unsubscribe();
        test.destroy();
    }
}
