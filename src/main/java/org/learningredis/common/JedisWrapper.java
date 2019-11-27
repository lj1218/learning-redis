package org.learningredis.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ListPosition;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/11/27
 */
public class JedisWrapper extends JedisPool {

    public JedisWrapper() {
        super(new JedisPoolConfig(), "localhost");
    }

    // efficient
    public Long del(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.del(key);
        returnResource(jedis);
        return ret;
    }


    // Operate String

    public void set(String key, String value) {
        Jedis jedis = getResource();
        jedis.set(key, value);
        returnResource(jedis);
    }

    public String get(String key) {
        Jedis jedis = getResource();
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    public Long append(String key, String value) {
        Jedis jedis = getResource();
        Long ret = jedis.append(key, value);
        returnResource(jedis);
        return ret;
    }

    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = getResource();
        String ret = jedis.getrange(key, startOffset, endOffset);
        returnResource(jedis);
        return ret;
    }

    public List<String> mget(String... strings) {
        Jedis jedis = getResource();
        List<String> ret = jedis.mget(strings);
        returnResource(jedis);
        return ret;
    }

    public String mset(String... strings) {
        Jedis jedis = getResource();
        String ret = jedis.mset(strings);
        returnResource(jedis);
        return ret;
    }

    public Long msetnx(String... strings) {
        Jedis jedis = getResource();
        Long ret = jedis.msetnx(strings);
        returnResource(jedis);
        return ret;
    }

    public String psetex(String key, long milliseconds, String value) {
        Jedis jedis = getResource();
        String ret = jedis.psetex(key, milliseconds, value);
        returnResource(jedis);
        return ret;
    }

    public Long strlen(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.strlen(key);
        returnResource(jedis);
        return ret;
    }


    // Operate Hashes

    public Long hset(String key, String field, String value) {
        Jedis jedis = getResource();
        Long ret = jedis.hset(key, field, value);
        returnResource(jedis);
        return ret;
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = getResource();
        String ret = jedis.hmset(key, hash);
        returnResource(jedis);
        return ret;
    }

    public String hget(String key, String field) {
        Jedis jedis = getResource();
        String ret = jedis.hget(key, field);
        returnResource(jedis);
        return ret;
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = getResource();
        List<String> ret = jedis.hmget(key, fields);
        returnResource(jedis);
        return ret;
    }

    public List<String> hvals(String key) {
        Jedis jedis = getResource();
        List<String> ret = jedis.hvals(key);
        returnResource(jedis);
        return ret;
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.hkeys(key);
        returnResource(jedis);
        return ret;
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = getResource();
        boolean ret = jedis.hexists(key, field);
        returnResource(jedis);
        return ret;
    }

    public Long hlen(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.hlen(key);
        returnResource(jedis);
        return ret;
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis jedis = getResource();
        Long ret = jedis.hincrBy(key, field, value);
        returnResource(jedis);
        return ret;
    }

    public Double hincrByFloat(String key, String field, double value) {
        Jedis jedis = getResource();
        Double ret = jedis.hincrByFloat(key, field, value);
        returnResource(jedis);
        return ret;
    }

    public Long hdel(String key, String... fields) {
        Jedis jedis = getResource();
        Long ret = jedis.hdel(key, fields);
        returnResource(jedis);
        return ret;
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = getResource();
        Map<String, String> ret = jedis.hgetAll(key);
        returnResource(jedis);
        return ret;
    }

    // inefficient
    public Long hclear(String key) {
        Set<String> keySet = hkeys(key);
        if (keySet.isEmpty()) {
            return 0L;
        }

        String[] keys = new String[keySet.size()];
        int i = 0;
        for (String k : keySet) {
            keys[i++] = k;
        }
        return hdel(key, keys);
    }


    // Operate List

    public Long lpush(String key, String... strings) {
        Jedis jedis = getResource();
        Long ret = jedis.lpush(key, strings);
        returnResource(jedis);
        return ret;
    }

    public Long lpushx(String key, String... strings) {
        Jedis jedis = getResource();
        Long ret = jedis.lpushx(key, strings);
        returnResource(jedis);
        return ret;
    }

    public Long rpush(String key, String... strings) {
        Jedis jedis = getResource();
        Long ret = jedis.rpush(key, strings);
        returnResource(jedis);
        return ret;
    }

    public Long linsert(String key, ListPosition where, String pivot, String value) {
        Jedis jedis = getResource();
        Long ret = jedis.linsert(key, where, pivot, value);
        returnResource(jedis);
        return ret;
    }

    public String lpop(String key) {
        Jedis jedis = getResource();
        String ret = jedis.lpop(key);
        returnResource(jedis);
        return ret;
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = getResource();
        Long ret = jedis.lrem(key, count, value);
        returnResource(jedis);
        return ret;
    }

    public String lset(String key, long index, String value) {
        Jedis jedis = getResource();
        String ret = jedis.lset(key, index, value);
        returnResource(jedis);
        return ret;
    }

    public String ltrim(String key, long start, long stop) {
        Jedis jedis = getResource();
        String ret = jedis.ltrim(key, start, stop);
        returnResource(jedis);
        return ret;
    }

    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = getResource();
        String ret = jedis.rpoplpush(srckey, dstkey);
        returnResource(jedis);
        return ret;
    }

    public List<String> lrange(String key, long start, long stop) {
        Jedis jedis = getResource();
        List<String> ret = jedis.lrange(key, start, stop);
        returnResource(jedis);
        return ret;
    }

    public String lindex(String key, long index) {
        Jedis jedis = getResource();
        String ret = jedis.lindex(key, index);
        returnResource(jedis);
        return ret;
    }

    public Long llen(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.llen(key);
        returnResource(jedis);
        return ret;
    }


    // Operate Sets

    public Long sadd(String key, String... members) {
        Jedis jedis = getResource();
        Long ret = jedis.sadd(key, members);
        returnResource(jedis);
        return ret;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.smembers(key);
        returnResource(jedis);
        return ret;
    }

    public String srandmember(String key) {
        Jedis jedis = getResource();
        String ret = jedis.srandmember(key);
        returnResource(jedis);
        return ret;
    }

    public String spop(String key) {
        Jedis jedis = getResource();
        String ret = jedis.spop(key);
        returnResource(jedis);
        return ret;
    }

    public Long scard(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.scard(key);
        returnResource(jedis);
        return ret;
    }

    public Set<String> sinter(String... keys) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.sinter(keys);
        returnResource(jedis);
        return ret;
    }

    public Long sinterstore(String dstKey, String... keys) {
        Jedis jedis = getResource();
        Long ret = jedis.sinterstore(dstKey, keys);
        returnResource(jedis);
        return ret;
    }

    public boolean sismember(String key, String member) {
        Jedis jedis = getResource();
        boolean ret = jedis.sismember(key, member);
        returnResource(jedis);
        return ret;
    }

    public Long smove(String srcKey, String dstKey, String member) {
        Jedis jedis = getResource();
        Long ret = jedis.smove(srcKey, dstKey, member);
        returnResource(jedis);
        return ret;
    }

    public Long srem(String key, String... members) {
        Jedis jedis = getResource();
        Long ret = jedis.srem(key, members);
        returnResource(jedis);
        return ret;
    }

    public Set<String> sunion(String... keys) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.sunion(keys);
        returnResource(jedis);
        return ret;
    }

    public Long sunionstore(String dstKey, String... keys) {
        Jedis jedis = getResource();
        Long ret = jedis.sunionstore(dstKey, keys);
        returnResource(jedis);
        return ret;
    }

    public Set<String> sdiff(String... keys) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.sdiff(keys);
        returnResource(jedis);
        return ret;
    }


    // Operate Sorted Sets

    public Long zadd(String key, double score, String member) {
        Jedis jedis = getResource();
        Long ret = jedis.zadd(key, score, member);
        returnResource(jedis);
        return ret;
    }

    public Long zcard(String key) {
        Jedis jedis = getResource();
        Long ret = jedis.zcard(key);
        returnResource(jedis);
        return ret;
    }

    public Double zincrby(String key, double increment, String member) {
        Jedis jedis = getResource();
        Double ret = jedis.zincrby(key, increment, member);
        returnResource(jedis);
        return ret;
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = getResource();
        Long ret = jedis.zcount(key, min, max);
        returnResource(jedis);
        return ret;
    }

    public Set<String> zrange(String key, long start, long stop) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.zrange(key, start, stop);
        returnResource(jedis);
        return ret;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = getResource();
        Set<String> ret = jedis.zrangeByScore(key, min, max);
        returnResource(jedis);
        return ret;
    }

    public Long zrank(String key, String member) {
        Jedis jedis = getResource();
        Long ret = jedis.zrank(key, member);
        returnResource(jedis);
        return ret;
    }

    public Long zrevrank(String key, String member) {
        Jedis jedis = getResource();
        Long ret = jedis.zrevrank(key, member);
        returnResource(jedis);
        return ret;
    }

    public Double zscore(String key, String member) {
        Jedis jedis = getResource();
        Double ret = jedis.zscore(key, member);
        returnResource(jedis);
        return ret;
    }

    public Long zunionstore(String dstKey, String... keys) {
        Jedis jedis = getResource();
        Long ret = jedis.zunionstore(dstKey, keys);
        returnResource(jedis);
        return ret;
    }

    public Long zrem(String key, String... members) {
        Jedis jedis = getResource();
        Long ret = jedis.zrem(key, members);
        returnResource(jedis);
        return ret;
    }

    public Long zremrangeByRank(String key, long start, long stop) {
        Jedis jedis = getResource();
        Long ret = jedis.zremrangeByRank(key, start, stop);
        returnResource(jedis);
        return ret;
    }

    public Long zremrangeByScore(String key, double min, double max) {
        Jedis jedis = getResource();
        Long ret = jedis.zremrangeByScore(key, min, max);
        returnResource(jedis);
        return ret;
    }

    public void destroy() {
        super.destroy();
    }

}
