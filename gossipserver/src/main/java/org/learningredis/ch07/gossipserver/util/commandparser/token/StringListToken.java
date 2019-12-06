package org.learningredis.ch07.gossipserver.util.commandparser.token;

import org.learningredis.ch07.gossipserver.util.CheckResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class StringListToken extends Token {

    private String value;
    private List<String> list = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public CheckResult setValue(String value) {
        this.value = value;
        list.clear();
        list.addAll(Arrays.asList(value.split(",")));
        return new CheckResult();
    }

    public List<String> getValueAsList() {
        return list;
    }

    @Override
    public String toString() {
        return "StringListToken{" +
                "value='" + value + '\'' +
                ", list=" + list +
                '}';
    }
}
