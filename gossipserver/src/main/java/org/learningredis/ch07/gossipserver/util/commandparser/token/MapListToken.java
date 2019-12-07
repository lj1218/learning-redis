package org.learningredis.ch07.gossipserver.util.commandparser.token;

import org.learningredis.ch07.gossipserver.util.CheckResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MapListToken extends Token {

    private String value = "";

    private Map<String, String> map = new HashMap<>();

    public String getValue() {
        return value;
    }

    public CheckResult setValue(String value) {
        this.value = value;
        for (String keyVal : value.split(",")) {
            String[] kv = keyVal.split("=");
            if (kv.length != 2) {
                return new CheckResult().setFalse("(" + keyVal + ") kv.length != 2");
            }
            map.put(kv[0], kv[1]);
        }
        return new CheckResult();
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public String getNValue(String elem) {
        return map.get(elem);
    }

    public String getValueAsSantizedString() {
        return map2SanitizedString(map);
    }

    public static String map2SanitizedString(Map<String, String> hash) {
        if (hash == null || hash.isEmpty()) {
            return "";
        }

        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : hash.entrySet()) {
            if (!isFirst) {
                sb.append(",");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            isFirst = false;
        }
        return sb.toString();
    }

    public MapListToken removeElement(String elem) {
        map.remove(elem);
        return this;
    }

    public Map<String, String> getValueAsMap() {
        return map;
    }

    @Override
    public String toString() {
        return "MapListToken{" +
                "value='" + value + '\'' +
                ", map=" + map +
                '}';
    }
}
