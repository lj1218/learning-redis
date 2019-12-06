package org.learningredis.ch07.gossipserver.util.commandparser.token;

import org.learningredis.ch07.gossipserver.util.CheckResult;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class StringToken extends Token {

    private String value = "";

    public StringToken() {
    }

    public StringToken(String value) {
        this.value = value;
    }

    public CheckResult setValue(String value) {
        this.value = value;
        return new CheckResult();
    }

    public String getValue() {
        return value;
    }
}
