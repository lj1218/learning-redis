package org.learningredis.chapter.four.pipelineandtx;

import org.learningredis.common.JedisWrapper;

import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class SingleCommand implements Runnable {

    private JedisWrapper jedis = new JedisWrapper();

    @Override
    public void run() {
        Set<String> data = jedis.smembers("keys-1");
        System.out.println("The return value of nv1 is [ "
                + data.size() + " ]");
        jedis.destroy();
    }
}
