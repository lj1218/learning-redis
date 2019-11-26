package org.learningredis.chapter.two;

/**
 * Created by lj1218.
 * Date: 2019/11/26
 */
public class HelloWorld2 {

    private final JedisWrapper jedisWrapper;

    public HelloWorld2() {
        jedisWrapper = new JedisWrapper();
    }

    private void test() {
        jedisWrapper.set("MSG", "Hello World");
        String result = jedisWrapper.get("MSG");
        System.out.println(" MSG : " + result);
        jedisWrapper.destroy();
    }

    public static void main(String[] args) {
        HelloWorld2 helloWorld2 = new HelloWorld2();
        helloWorld2.test();
    }

}
