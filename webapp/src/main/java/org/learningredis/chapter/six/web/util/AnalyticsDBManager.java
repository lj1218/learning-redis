package org.learningredis.chapter.six.web.util;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This class is responsible for analytics-related functional calls to the data base.
 */
public class AnalyticsDBManager extends RedisDBManager {

    public static final AnalyticsDBManager singleton = new AnalyticsDBManager();

    private AnalyticsDBManager() {
        super();
    }

    public void updateProductVisit(String sessionID, String productName) {
        Jedis jedis = getConnection();
        jedis.setbit(getKeyOfVisit(productName), new Long(sessionID), true);
        returnConnection(jedis);
    }

    public void updateProductPurchase(String sessionID, String productName) {
        Jedis jedis = getConnection();
        jedis.setbit(getKeyOfPurchase(productName), new Long(sessionID), true);
        returnConnection(jedis);
    }

    public void updateRatingInTag(String productName, double rating) {
        Jedis jedis = getConnection();
        String string = jedis.hget(productName, "tags");
        String[] tags = string.split(",");
        List<String> tagList = new ArrayList<>();
        for (String tag : tags) {
            String[] tagAndRating = tag.split("@");
            tagList.add(tagAndRating[0]);
        }
        for (String tag : tagList) {
            jedis.zincrby(tag.toLowerCase(), rating, productName);
        }
        returnConnection(jedis);
    }

//    public int getVisitToday(String productName) {
//        Jedis jedis = getConnection();
//        // Redis client 会把返回的 byte 数组转换为 String，这个转换过程会将无法用 UTF-8 表示的字符转换为 '?' - 65533，
//        // 从而导致信息失真，因此统计结果是错误的。另外，我们只需获取总数，没必要获取整个 bitset 数据。改进办法为使用方法为 bitcount()。
//        String val = jedis.get(getKeyOfVisit(productName));
//        returnConnection(jedis);
//        if (val != null) {
//            BitSet users = BitSet.valueOf(val.getBytes());
//            return users.cardinality();
//        }
//        return 0;
//    }

    public int getVisitToday(String productName) {
        Jedis jedis = getConnection();
        // Redis client 会把返回的 byte 数组转换为 String，这个转换过程会将无法用 UTF-8 表示的字符转换为 '?' - 65533，
        // 从而导致信息失真，因此统计结果是错误的。另外，我们只需获取总数，没必要获取整个 bitset 数据。改进办法为使用方法为 bitcount()。
        long count = jedis.bitcount(getKeyOfVisit(productName));
        returnConnection(jedis);
        return (int) count;
    }

    public void registerInSessionTracker(String sessionID) {
        Jedis jedis = getConnection();
        Long sessionValue = new Long(sessionID);
        jedis.setbit(getKeyOfSessionIdTracker(), sessionValue, true);
        returnConnection(jedis);
    }

    public void updateBrowsingHistory(String sessionID, String productName) {
        Jedis jedis = getConnection();
        jedis.zincrby(getKeyOfBrowsingHistory(sessionID), 1.0, productName);
        returnConnection(jedis);
    }

    public Set<String> getBrowsingHistory(String sessionID) {
        Jedis jedis = getConnection();
        Set<String> range = jedis.zrange(getKeyOfBrowsingHistory(sessionID), 0, 1000000);
        returnConnection(jedis);
        return range;
    }

    public List<String> getMyPurchaseHistory(String sessionID) {
        Jedis jedis = getConnection();
        String name = jedis.hget(getKeyOfSessionData(sessionID), "name");
        List<String> purchaseHistory = jedis.lrange(getKeyOfPurchaseHistory(name), 0, 100);
        returnConnection(jedis);
        return purchaseHistory;
    }

    public String getTagHistory(String tagName) {
        Jedis jedis = getConnection();
        Set<String> sortedPProductList = jedis.zrange(tagName.toLowerCase(), 0, 10000);
        StringBuilder sb = new StringBuilder();
        sb.append("The following products are listed as per the hit rate\n");
        int i = 1;
        for (String productName : sortedPProductList) {
            sb.append(" [").append(i++).append("] ")
                    .append(productName).append(" and the score is ")
                    .append(jedis.zscore(tagName.toLowerCase(), productName)).append("\n");
        }
        returnConnection(jedis);
        return sb.toString();
    }

    public List<String> getTopProducts(int slotForTag, String tag) {
        Jedis jedis = getConnection();
        Set<String> sortedProductList = jedis.zrevrange(tag.toLowerCase(), 0, 100000000);
        List<String> topProducts = new ArrayList<>();
        int index = 0;
        for (String product : sortedProductList) {
            if (index++ > slotForTag) {
                break;
            }
            topProducts.add(product);
        }
        returnConnection(jedis);
        return topProducts;
    }

}
