package org.learningredis.ch07.gossipserver.util.commandparser.token;

import org.learningredis.ch07.gossipserver.util.CheckResult;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public abstract class Token {

    public abstract String getValue();

    public abstract CheckResult setValue(String value);

}
