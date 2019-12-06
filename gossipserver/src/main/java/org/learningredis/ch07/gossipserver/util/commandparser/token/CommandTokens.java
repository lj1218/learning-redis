package org.learningredis.ch07.gossipserver.util.commandparser.token;

import java.util.Arrays;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class CommandTokens {

    private String value;

    private String[] tokens;

    public CommandTokens(String value) {
        this.value = value;
        tokens = value.split(" ");
    }

    public String getValue() {
        return value;
    }

    public String[] getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return "CommandTokens{" +
                "value='" + value + '\'' +
                ", tokens=" + Arrays.toString(tokens) +
                '}';
    }
}
