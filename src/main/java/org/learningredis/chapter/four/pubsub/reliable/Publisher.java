package org.learningredis.chapter.four.pubsub.reliable;

import org.learningredis.chapter.four.luascripting.Reader;
import org.learningredis.common.JedisWrapper;

import java.util.Collections;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class Publisher {

    private String luaScript = Reader.read(LuaScript.PATH);
    private JedisWrapper jedis = new JedisWrapper();

    private void sendingReliableMessages() {
        String result = (String) jedis.eval(luaScript, Collections.singletonList(""),
                Collections.singletonList("{type='channel',publishto='client1',msg='"
                        + System.currentTimeMillis() + "'}"));
        System.out.println(result);
        jedis.destroy();
    }

    public static void main(String[] args) {
        new Publisher().sendingReliableMessages();
    }
}
