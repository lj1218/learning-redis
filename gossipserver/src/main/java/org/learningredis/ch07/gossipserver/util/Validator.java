package org.learningredis.ch07.gossipserver.util;

import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class Validator {

    private Template template = new Template();

    public Template configureTemplate() {
        return template;
    }

    public void setInput(String message) {
    }

    public void setInput(CommandTokens tokens) {
    }

    public CheckResult validate() {
        return null;
    }

    public Token getToken(int index) {
        return template.get(index);
    }

    public List<Token> getAllTokens() {
        return null;
    }

}
