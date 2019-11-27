package org.learningredis.chapter.three.datastruct;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class MyStringTest {

    private final JedisWrapper jedis = new JedisWrapper();

    private void test() throws InterruptedException {
        String commonKey = "mykey";

        jedis.set(commonKey, "Hello World");
        System.out.println("1) " + jedis.get(commonKey));

        jedis.append(commonKey, " and this is a bright sunny day");
        System.out.println("2) " + jedis.get(commonKey));

        String substring = jedis.getrange(commonKey, 0, 5);
        System.out.println("3) substring value = " + substring);

        String commonKey1 = "mykey1";
        jedis.set(commonKey1, "Let's learn redis");
        for (String value : jedis.mget(commonKey, commonKey1)) {
            System.out.println("4) - " + value);
        }

        String commonKey2 = "mykey2";
        String commonKey3 = "mykey3";
        jedis.mset(commonKey2, "let's start with string",
                commonKey3, "then we will learn other data types");
        for (String value : jedis.mget(commonKey, commonKey1, commonKey2, commonKey3)) {
            System.out.println("5) -- " + value);
        }

        String commonKey4 = "mykey4";
        jedis.msetnx(commonKey4, "next in line is hashmaps");
        System.out.println("6) " + jedis.get(commonKey4));
        jedis.msetnx(commonKey4, "next in line is sorted sets");
        System.out.println("7) " + jedis.get(commonKey4));

        String commonKey5 = "mykey5";
        jedis.psetex(commonKey5, 1000, "this message will self destruct in 1000 milliseconds");
        System.out.println("8) " + jedis.get(commonKey5));
        Thread.sleep(1200);
        System.out.println("8) " + jedis.get(commonKey5));

        Long length = jedis.strlen(commonKey);
        System.out.println("9) the length of the string '" + commonKey + "' is " + length);

        jedis.destroy();
    }

    public static void main(String[] args) throws InterruptedException {
        new MyStringTest().test();
    }
}

/*
 * output:

1) Hello World
2) Hello World and this is a bright sunny day
3) substring value = Hello
4) - Hello World and this is a bright sunny day
4) - Let's learn redis
5) -- Hello World and this is a bright sunny day
5) -- Let's learn redis
5) -- let's start with string
5) -- then we will learn other data types
6) next in line is hashmaps
7) next in line is hashmaps
8) this message will self destruct in 1000 milliseconds
8) null
9) the length of the string 'mykey' is 42

 *
 */
