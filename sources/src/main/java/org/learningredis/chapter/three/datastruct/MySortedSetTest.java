package org.learningredis.chapter.three.datastruct;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class MySortedSetTest {

    private final JedisWrapper jedis = new JedisWrapper();

    private void test() {
        String key1 = "purchase";
        jedis.zadd(key1, 0, "learning-redis");
        jedis.zadd(key1, 0, "cassandra");
        jedis.zadd(key1, 0, "hadoop");
        System.out.println(jedis.zcard(key1));

        // purchase a 4 books on redis
        jedis.zincrby(key1, 1, "learning-redis");
        jedis.zincrby(key1, 1, "learning-redis");
        jedis.zincrby(key1, 1, "learning-redis");
        jedis.zincrby(key1, 1, "learning-redis");

        // purchase a 2 books on cassandra
        jedis.zincrby(key1, 1, "cassandra");
        jedis.zincrby(key1, 1, "cassandra");

        // purchase a 1 books on hadoop
        jedis.zincrby(key1, 1, "hadoop");

        System.out.println(jedis.zcount(key1, 3, 4));
        System.out.println(jedis.zrange(key1, 0, 2));
        System.out.println(jedis.zrangeByScore(key1, 0, 2));

        System.out.println(jedis.zrank(key1, "learning-redis"));
        System.out.println(jedis.zrank(key1, "cassandra"));
        System.out.println(jedis.zrank(key1, "hadoop"));

        System.out.println(jedis.zrevrank(key1, "learning-redis"));
        System.out.println(jedis.zrevrank(key1, "cassandra"));
        System.out.println(jedis.zrevrank(key1, "hadoop"));

        System.out.println(jedis.zscore(key1, "learning-redis"));
        System.out.println(jedis.zscore(key1, "cassandra"));
        System.out.println(jedis.zscore(key1, "hadoop"));

        String key2 = key1 + ":nosql";
        jedis.zunionstore(key2, key1);
        System.out.println("-- " + jedis.zrange(key2, 0, -1));
        System.out.println("-- " + jedis.zrank(key2, "learning-redis"));

        jedis.zrem(key2, "hadoop");
        System.out.println("-- " + jedis.zrange(key2, 0, -1));

        jedis.zremrangeByRank(key2, 0, 0);
        System.out.println("-- " + jedis.zrange(key2, 0, -1));

        jedis.zremrangeByScore(key2, 3, 4);
        System.out.println("-- " + jedis.zrange(key2, 0, -1));

        jedis.del(key1);
        jedis.del(key2);
        jedis.destroy();
    }

    public static void main(String[] args) {
        new MySortedSetTest().test();
    }

}

/*
 * output:

3
1
[hadoop, cassandra, learning-redis]
[hadoop, cassandra]
2
1
0
0
1
2
4.0
2.0
1.0
-- [hadoop, cassandra, learning-redis]
-- 2
-- [cassandra, learning-redis]
-- [learning-redis]
-- []

 *
 */
