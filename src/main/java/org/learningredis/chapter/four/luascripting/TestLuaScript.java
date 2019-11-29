package org.learningredis.chapter.four.luascripting;

import org.learningredis.common.JedisWrapper;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class TestLuaScript {

    private static final String SCRIPT_DIR = "src/main/java/org/learningredis/chapter/four/luascripting/";
    private String luaScript = Reader.read(SCRIPT_DIR + "LuaScript.lua");
    private JedisWrapper jedis = new JedisWrapper();

    private void luaScript() {
        String result = (String) jedis.eval(luaScript,
                Collections.singletonList("msg"),
                Arrays.asList("Learning Redis",
                        "Now I am learning Lua for Redis",
                        "prepare for the test again"));
        System.out.println(result);
        jedis.destroy();
    }

    public static void main(String[] args) {
        new TestLuaScript().luaScript();
    }

}
