package org.learningredis.chapter.six.web.util;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This class is responsible for product related functional calls to the data base.
 */
public class ProductDBManager extends RedisDBManager {

    public static final ProductDBManager singleton = new ProductDBManager();

    private ProductDBManager() {
        super();
    }

    public boolean commissionProduct(Map<String, String> productAttributes) {
        Jedis jedis = getConnection();
        String productCreationResult = jedis.hmset(productAttributes.get("name"), productAttributes);
        returnConnection(jedis);
        return productCreationResult.toLowerCase().equals("ok");
    }

    public boolean enterTagEntries(String name, String string) {
        Jedis jedis = getConnection();
        String[] tags = string.split(",");
        boolean boolResult = false;
        List<String> tagList = new ArrayList<>();
        for (String tag : tags) {
            String[] tagAndRating = tag.split("@");
            tagList.add(tagAndRating[0]);
        }
        for (String tag : tagList) {
            long result = jedis.zadd(tag.toLowerCase(), 0, name);
            if (result == 0) {
                break;
            } else {
                boolResult = true;
            }
        }
        returnConnection(jedis);
        return boolResult;
    }

    public String getProductInfo(String name) {
        Jedis jedis = getConnection();
        Map<String, String> map = jedis.hgetAll(name);
        StringBuilder sb = new StringBuilder();
        sb.append("Following are the product attributes for ").append(name).append("\n");
        Set<String> keys = map.keySet();
        int i = 1;
        for (String key : keys) {
            sb.append("[").append(i++).append("] . ").append(key)
                    .append(" value : ").append(map.get(key)).append("\n");
        }
        returnConnection(jedis);
        return sb.toString();
    }

    public String getTagValues(String tagName) {
        Jedis jedis = getConnection();
        StringBuilder sb = new StringBuilder();
        Set<String> sortedTagList = jedis.zrange(tagName.toLowerCase(), 0, 10000);
        sb.append("The following products are listed as per the hit rate\n");
        int i = 1;
        for (String tagname : sortedTagList) {
            sb.append(" [").append(i++).append("] ").append(tagname).append("\n");
        }
        returnConnection(jedis);
        return sb.toString();
    }

    public boolean keyExist(String keyName) {
        Jedis jedis = getConnection();
        boolean result = jedis.exists(keyName);
        returnConnection(jedis);
        return result;
    }

    public int getPurchaseToday(String productName) {
        Jedis jedis = getConnection();
        String key = productName + "@purchase:" + getDate();
        String val = jedis.get(key);
        returnConnection(jedis);
        if (val != null) {
            BitSet users = BitSet.valueOf(val.getBytes());
            return users.cardinality();
        }
        return 0;
    }

    public Map<String, Integer> getProductTags(String productName) {
        Jedis jedis = getConnection();
        String productTags = jedis.hget(productName, "tags");
        returnConnection(jedis);
        Map<String, Integer> map = new HashMap<>();
        String[] tagAndWeights = productTags.split(",");
        for (String tagAndWeight : tagAndWeights) {
            String[] _tagAndWeight = tagAndWeight.split("@");
            map.put(_tagAndWeight[0], new Integer(_tagAndWeight[1]));
        }
        return map;
    }
}
