package org.learningredis.chapter.six.web.sessionmgmt.commands;

import org.learningredis.chapter.six.web.Commands;
import org.learningredis.chapter.six.web.util.AnalyticsDBManager;
import org.learningredis.chapter.six.web.util.Argument;
import org.learningredis.chapter.six.web.util.ProductDBManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class RecommendByProductCommand extends Commands {

    private static final int totalRecommendations = 10;

    public RecommendByProductCommand(Argument argument) {
        super(argument);
    }

    @Override
    public String execute() {
        System.out.println(this.getClass().getSimpleName() + ": Entering the execute function");
        StringBuilder sb = new StringBuilder();
        String productName = getArgument().getValue("productname");
        sb.append("If you are looking into " + productName
                + " you might also find the following\n");
        sb.append("products interesting...\n");
        Map<String, Integer> tags = ProductDBManager.singleton.getProductTags(productName);
        // Lets get total sum of weights
        int totalWeight = 0;
        Set<String> keys = tags.keySet();
        for (String key : keys) {
            totalWeight += tags.get(key);
        }
        for (String key : keys) {
            int slotForTag = Math.round(totalRecommendations * tags.get(key) / (float) totalWeight);
            List<String> productNames = AnalyticsDBManager.singleton.getTopProducts(slotForTag, key);
            for (String product : productNames) {
                if (!product.equals(productName)) {
                    sb.append("For tag = ").append(key).append(" the recommended product is ")
                            .append(product).append("\n");
                }
            }
        }
        System.out.println(getClass().getSimpleName() + ": Printing the result for execute function");
        System.out.println("Result = " + sb.toString());
        return sb.toString();
    }
}
