package org.learningredis.chapter.three.datastruct;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.ListPosition;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class MyListTest {

    private JedisWrapper jedis = new JedisWrapper();

    private void test() {
        String commonKey = "mykey4list";
        String commonKey1 = "mykey4list1";
        System.out.println(jedis.del(commonKey));
        System.out.println(jedis.del(commonKey1));
        for (int index = 0; index < 3; index++) {
            jedis.lpush(commonKey, "Message - " + index);
        }
        System.out.println(jedis.lrange(commonKey, 0, -1));

        for (int index = 3; index < 6; index++) {
            jedis.rpush(commonKey, "Message - " + index);
        }
        System.out.println(jedis.lrange(commonKey, 0, -1));
        System.out.println(jedis.lindex(commonKey, 0));

        System.out.println(jedis.linsert(commonKey, ListPosition.AFTER,
                "Message - 5", "Message - 7"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.linsert(commonKey, ListPosition.BEFORE,
                "Message - 7", "Message - 6"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.llen(commonKey));
        System.out.println(jedis.lpop(commonKey));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.lpush(commonKey, "Message - 2", "Message - 1.9"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.lpushx(commonKey, "Message - 1.8"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.lrem(commonKey, 0, "Message - 1.8"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.lrem(commonKey, -1, "Message - 7"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.lset(commonKey, 7, "Message - 7"));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        System.out.println(jedis.ltrim(commonKey, 2, -4));
        System.out.println(jedis.lrange(commonKey, 0, -1));

        jedis.rpoplpush(commonKey, commonKey1);
        System.out.println(jedis.lrange(commonKey, 0, -1));
        System.out.println(jedis.lrange(commonKey1, 0, -1));

        jedis.del(commonKey);
        jedis.del(commonKey1);
        jedis.destroy();
    }

    public static void main(String[] args) {
        new MyListTest().test();
    }

}

/*
 * output:

0
0
[Message - 2, Message - 1, Message - 0]
[Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5]
Message - 2
7
[Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 7]
8
[Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6, Message - 7]
8
Message - 2
[Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6, Message - 7]
9
[Message - 1.9, Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6, Message - 7]
10
[Message - 1.8, Message - 1.9, Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6, Message - 7]
1
[Message - 1.9, Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6, Message - 7]
1
[Message - 1.9, Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 6]
OK
[Message - 1.9, Message - 2, Message - 1, Message - 0, Message - 3, Message - 4, Message - 5, Message - 7]
OK
[Message - 1, Message - 0, Message - 3]
[Message - 1, Message - 0]
[Message - 3]

 *
 */
