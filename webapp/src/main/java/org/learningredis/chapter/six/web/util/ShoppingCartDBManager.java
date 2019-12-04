package org.learningredis.chapter.six.web.util;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This class is responsible for shopping cart-related functional calls to the data base.
 */
public class ShoppingCartDBManager extends RedisDBManager {

    public static final ShoppingCartDBManager singleton = new ShoppingCartDBManager();

    private ShoppingCartDBManager() {
        super();
    }

    public String addToShoppingCart(String sessionID, Map<String, String> productQtyMap) {
        Jedis jedis = getConnection();
        String result = jedis.hmset(getKeyOfShoppingCart(sessionID), productQtyMap);
        returnConnection(jedis);
        return result;
    }

    public Map<String, String> myCartInfo(String sessionID) {
        Jedis jedis = getConnection();
        Map<String, String> shoppingCart = jedis.hgetAll(getKeyOfShoppingCart(sessionID));
        returnConnection(jedis);
        return shoppingCart;
    }

    public String editMyCart(String sessionID, Map<String, String> productQtyMap) {
        Jedis jedis = getConnection();
        String result = "";
        String key = getKeyOfShoppingCart(sessionID);
        if (jedis.exists(key)) {
            Set<String> keySet = productQtyMap.keySet();
            for (String field : keySet) {
                if (jedis.hexists(key, field)) {
                    int intValue = Integer.parseInt(productQtyMap.get(field));
                    if (intValue == 0) {
                        jedis.hdel(key, field);
                    } else if (intValue > 0) {
                        jedis.hset(key, field, productQtyMap.get(field));
                    }
                }
            }
            result = "Updated the shopping cart for user";
        } else {
            result = "Could not update the shopping cart for the user!!";
        }
        returnConnection(jedis);
        return result;
    }

    public String buyItemsInTheShoppingCart(String sessionID) {
        Jedis jedis = getConnection();
        if (!jedis.exists(getKeyOfSessionData(sessionID))) {
            returnConnection(jedis);
            return "error#Invalid session";
        }
        if (!jedis.exists(getKeyOfShoppingCart(sessionID))) {
            returnConnection(jedis);
            return "error#Shopping Cart is empty!";
        }

        String key = getKeyOfShoppingCart(sessionID);
        Map<String, String> cartInfo = jedis.hgetAll(key);
        Set<String> productNameList = cartInfo.keySet();
        StringBuilder sb = new StringBuilder();
        sb.append("RECEIPT: You have purchased the following\n")
                .append("------------------------------------\n");
        int i = 1;
        for (String productName : productNameList) {
            String unitCost = jedis.hget(productName, "cost");
            int unitCostValue = Integer.parseInt(unitCost);
            String quantity = cartInfo.get(productName);
            int quantityValue = Integer.parseInt(quantity);
            sb.append("[").append(i++).append("] Name of item : ")
                    .append(productName).append(" and quantity was : ")
                    .append(quantity).append(" the total cost is = ")
                    .append(quantityValue * unitCostValue).append("\n");
        }
        sb.append("------------------------------------#");
        for (String productName : productNameList) {
            sb.append(productName).append(",");
        }

        // Update the user purchase history:
        String name = jedis.hget(getKeyOfSessionData(sessionID), "name");
        for (String productName : productNameList) {
            jedis.lpush(getKeyOfPurchaseHistory(name), productName + " on " + getDate());
        }

        // remove shopping cart
        jedis.del(key);

        returnConnection(jedis);
        return sb.toString();
    }

}
