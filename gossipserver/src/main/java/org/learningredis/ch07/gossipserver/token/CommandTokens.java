package org.learningredis.ch07.gossipserver.token;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class CommandTokens {

    private String value;

    public CommandTokens(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
