package org.learningredis.chapter.five.sharding;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 */
public class MyShards extends ShardedJedisPool {

    public static void main(String[] args) {
        List<JedisShardInfo> shards = new ArrayList<>();
        setup(shards);
        new MyShards(new GenericObjectPoolConfig(), shards).putData();
    }

    public MyShards(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards) {
        super(poolConfig, shards);
    }

    private static void setup(List<JedisShardInfo> shards) {
        JedisShardInfo master0 = new JedisShardInfo("localhost", 6379);
        JedisShardInfo master1 = new JedisShardInfo("localhost", 6369);
        shards.add(master1);
        shards.add(master0);
    }

    private void putData() {
        for (int index = 0; index < 10; ++index) {
            ShardedJedis jedis = getResource();
            jedis.set("mykey" + index, "my value is " + index);
            returnResource(jedis);
        }
        for (int index = 0; index < 10; ++index) {
            ShardedJedis jedis = getResource();
            System.out.println("The following information is from master running on port : "
                    + jedis.getShardInfo("mykey" + index).getPort());
            System.out.println("The value for the key is " + jedis.get("mykey" + index));
            returnResource(jedis);
        }
    }
}
