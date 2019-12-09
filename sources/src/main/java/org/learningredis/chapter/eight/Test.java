package org.learningredis.chapter.eight;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;

/**
 * Created by lj1218.
 * Date: 2019/12/9
 */
public class Test {

    public static void main(String[] args) {
        new Test().evaluateShard();
    }

    private void evaluateShard() {
        // Configure Jedis sharded connection pool.
        JedisShardInfo shard1 = new JedisShardInfo("localhost", 6379);
        JedisShardInfo shard2 = new JedisShardInfo("localhost", 6380);
        JedisShardInfo shard3 = new JedisShardInfo("localhost", 6381);
        ShardedJedis shardedJedis = new ShardedJedis(Arrays.asList(shard1, shard2, shard3));

        // Looping to set values in the shard we have created..
        for (int i = 0; i < 10; i++) {
            shardedJedis.set("KEY-" + i, "myValue-" + i);
        }

        // Lets try to read all the values from SHARD-1
        readValuesFromShard("localhost", 6379, "SHARD-1", 10);

        // Lets try to read all the values from SHARD-2
        readValuesFromShard("localhost", 6380, "SHARD-2", 10);

        // Lets try to read all the values from SHARD-3
        readValuesFromShard("localhost", 6381, "SHARD-3", 10);

        // Lets try to read data from the sharded jedis
        for (int i = 0; i < 10; i++) {
            if (shardedJedis.get("KEY-" + i) != null) {
                System.out.println(shardedJedis.get("KEY-" + i));
            }
        }
        shardedJedis.close();
    }

    private void readValuesFromShard(String host, int port, String shardName, int valueCount) {
        Jedis jedis = new Jedis(host, port);
        for (int i = 0; i < valueCount; i++) {
            if (jedis.get("KEY-" + i) != null) {
                System.out.println(jedis.get("KEY-" + i) + " : this is stored in " + shardName);
            }
        }
        jedis.close();
    }

}
/* output:
myValue-1 : this is stored in SHARD-1
myValue-2 : this is stored in SHARD-1
myValue-0 : this is stored in SHARD-2
myValue-3 : this is stored in SHARD-2
myValue-5 : this is stored in SHARD-2
myValue-4 : this is stored in SHARD-3
myValue-6 : this is stored in SHARD-3
myValue-7 : this is stored in SHARD-3
myValue-8 : this is stored in SHARD-3
myValue-9 : this is stored in SHARD-3
myValue-0
myValue-1
myValue-2
myValue-3
myValue-4
myValue-5
myValue-6
myValue-7
myValue-8
myValue-9
 */
