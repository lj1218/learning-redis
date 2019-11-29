package org.learningredis.chapter.four.pubsub.reliable;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class SimpleMsgSubscriber {

    public static void main(String[] args) {
        SimpleMsgSubscriber source = new SimpleMsgSubscriber();
        Thread msgWorker = new Thread(source.new MsgProcessor());
        Thread lostMsgWorker = new Thread(source.new LostMsgProcessor());
        msgWorker.start();
        lostMsgWorker.start();
    }

    class MsgProcessor extends JedisPubSub implements Runnable {

        private JedisWrapper jedis = new JedisWrapper();

        @Override
        public void run() {
            jedis.subscribe(this, "client1");
        }

        @Override
        public void onMessage(String channel, String message) {
            System.out.println("processing the msg = " + message);
        }
    }

    class LostMsgProcessor implements Runnable {
        @Override
        public void run() {
            JedisWrapper jedis = new JedisWrapper();
            String msg;
            while ((msg = jedis.spop("MSGBOX")) != null) {
                MessageHandler.push(msg);
            }
            jedis.destroy();
            System.out.println("LostMsgProcessor exit");
        }
    }

    static class MessageHandler {

        private static final JedisWrapper jedis = new JedisWrapper();

        public static void push(String msg) {
            String luaScript = "";
            try {
                luaScript = LuaScript.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = (String) jedis.eval(luaScript, Collections.singletonList(""),
                    Collections.singletonList("{type='channel',publishto='client1',msg='"
                            + msg + "'}"));
            System.out.println(result);
        }

    }

}
