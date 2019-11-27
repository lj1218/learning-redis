package org.learningredis.chapter.three.datastruct;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class MySetTest {

    private final JedisWrapper jedis = new JedisWrapper();

    private void test() {
        String key1 = "follow:cricket";
        jedis.sadd(key1, "vinoo.das@junk-mail.com", "vinoo.das3@junk-mail.com");
        System.out.println(jedis.smembers(key1));
        System.out.println(jedis.scard(key1));

        String key2 = "follow:redis";
        jedis.sadd(key2, "vinoo.das1@junk-mail.com", "vinoo.das2@junk-mail.com");
        System.out.println(jedis.smembers(key2));
        System.out.println(jedis.scard(key2));

        // intersect the above sets to give name who is interested in cricket and redis
        String key3 = "follow:redis&cricket";
        System.out.println(jedis.sinter(key1, key2));
        jedis.sinterstore(key3, key1, key2);
        System.out.println(jedis.smembers(key3));
        System.out.println(jedis.sismember(key3, "vinoo.das@junk-mail.com"));
        System.out.println(jedis.sismember(key3, "vinoo.das1@junk-mail.com"));

        jedis.smove(key1, key2, "vinoo.das3@junk-mail.com");
        System.out.println(jedis.smembers(key2));

        System.out.println(jedis.srandmember(key1));
        System.out.println(jedis.spop(key1));
        System.out.println(jedis.smembers(key1));

        System.out.println(jedis.sadd(key1, "wrong-data@junk-mail.com"));
        System.out.println(jedis.smembers(key1));

        System.out.println(jedis.srem(key1, "wrong-data@junk-mail.com"));
        System.out.println(jedis.smembers(key1));

        String key4 = "follow:redis|cricket";
        System.out.println(jedis.sunion(key1, key2));
        System.out.println(jedis.sunionstore(key4, key1, key2));
        System.out.println(jedis.smembers(key4));

        System.out.println(jedis.sdiff(key1, key2));

        jedis.del(key1);
        jedis.del(key2);
        jedis.del(key3);
        jedis.del(key4);
        jedis.destroy();
    }

    public static void main(String[] args) {
        new MySetTest().test();
    }
}

/*
 * output:

[vinoo.das@junk-mail.com, vinoo.das3@junk-mail.com]
2
[vinoo.das2@junk-mail.com, vinoo.das1@junk-mail.com, vinoo.das3@junk-mail.com]
3
[vinoo.das3@junk-mail.com]
[vinoo.das3@junk-mail.com]
false
false
[vinoo.das2@junk-mail.com, vinoo.das1@junk-mail.com, vinoo.das3@junk-mail.com]
vinoo.das@junk-mail.com
vinoo.das@junk-mail.com
[]
1
[wrong-data@junk-mail.com]
1
[]
[vinoo.das2@junk-mail.com, vinoo.das1@junk-mail.com, vinoo.das3@junk-mail.com]
3
[vinoo.das2@junk-mail.com, vinoo.das1@junk-mail.com, vinoo.das3@junk-mail.com]
[]

 *
 */
