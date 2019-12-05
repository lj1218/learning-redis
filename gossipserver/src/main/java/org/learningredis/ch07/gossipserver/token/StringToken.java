package org.learningredis.ch07.gossipserver.token;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class StringToken extends Token {

    private String value;

    public StringToken() {
    }

    public StringToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
