package org.learningredis.chapter.three.datastruct;

import org.learningredis.common.JedisWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class MyHashesTest {

    private final JedisWrapper jedis = new JedisWrapper();

    private void test() {
        String commonKey = "learning redis";

        jedis.del(commonKey); // clear map
//        jedis.hclear(commonKey); // clear map

        jedis.hset(commonKey, "publisher", "Pack Publisher");
        jedis.hset(commonKey, "author", "Vinoo Das");
        System.out.println(jedis.hgetAll(commonKey));

        Map<String, String> attributes = new HashMap<>();
        attributes.put("ISBN", "XX-XX-XX-XX");
        attributes.put("tags", "Redis,NoSQL");
        attributes.put("pages", "250");
        attributes.put("weight-in-gms", "200.56");
        jedis.hmset(commonKey, attributes);
        System.out.println(jedis.hgetAll(commonKey));
        System.out.println(jedis.hget(commonKey, "publisher"));
        System.out.println(jedis.hmget(commonKey, "publisher", "author"));
        System.out.println(jedis.hvals(commonKey));
        System.out.println(jedis.hkeys(commonKey));
        System.out.println(jedis.hexists(commonKey, "cost"));
        System.out.println(jedis.hlen(commonKey));
        System.out.println(jedis.hincrBy(commonKey, "pages", 10));
        System.out.println(jedis.hincrByFloat(commonKey, "weight-in-gms", 1.1) + " gms");
        System.out.println(jedis.hdel(commonKey, "weight-in-gms"));
        System.out.println(jedis.hgetAll(commonKey));

        jedis.del(commonKey); // clear map
        jedis.destroy();
    }

    public static void main(String[] args) {
        new MyHashesTest().test();
    }
}

/*
 * output:

{author=Vinoo Das, publisher=Pack Publisher}
{publisher=Pack Publisher, pages=250, ISBN=XX-XX-XX-XX, weight-in-gms=200.56, author=Vinoo Das, tags=Redis,NoSQL}
Pack Publisher
[Pack Publisher, Vinoo Das]
[Pack Publisher, Vinoo Das, XX-XX-XX-XX, 250, 200.56, Redis,NoSQL]
[publisher, pages, ISBN, weight-in-gms, author, tags]
false
6
260
201.66 gms
1
{publisher=Pack Publisher, pages=260, ISBN=XX-XX-XX-XX, author=Vinoo Das, tags=Redis,NoSQL}

 *
 */
