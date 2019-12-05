package org.learningredis.ch07.gossipserver.util;

import org.learningredis.ch07.gossipserver.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class Template {

    private List<Token> tokens = new ArrayList<>();

    public Template add(Token token) {
        tokens.add(token);
        return this;
    }

    public Token get(int index) {
        return tokens.get(index);
    }
}
