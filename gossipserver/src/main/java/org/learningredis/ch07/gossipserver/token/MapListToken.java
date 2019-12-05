package org.learningredis.ch07.gossipserver.token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class MapListToken extends Token {

    public boolean containsKey(String key) {
        return false;
    }

    public String getNValue(String elem) {
        return "";
    }

    public String getValueAsSantizedString() {
        return "";
    }

    public MapListToken removeElement(String elem) {
        return this;
    }

    public Map<String, String> getValueAsMap() {
        return new HashMap<>();
    }
}
