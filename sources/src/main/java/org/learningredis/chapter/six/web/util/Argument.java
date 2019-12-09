package org.learningredis.chapter.six.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * The primary goal of this class is to wrap all the name value
 * attributes coming in the request and to put it in a map which
 * can be used in the program later on.
 */
public class Argument {
    private Map<String, String> argumentMap = new HashMap<>();

    public Argument(String args) {
        String[] arguments = args.split(":");
        for (String argument : arguments) {
            String key = argument.split("=")[0];
            String value = argument.split("=")[1];
            argumentMap.put(key, value);
        }
    }

    public String getValue(String key) {
        return argumentMap.get(key);
    }

    public Map<String, String> getAttributes() {
        return argumentMap;
    }
}
