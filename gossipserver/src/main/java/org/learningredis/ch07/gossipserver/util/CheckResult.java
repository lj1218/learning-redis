package org.learningredis.ch07.gossipserver.util;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class CheckResult {

    private StringBuffer reason = new StringBuffer();
    private boolean result;

    public CheckResult setTrue() {
        result = true;
        return this;
    }

    public CheckResult setFalse(String message) {
        result = false;
        return appendReason(message);
    }

    public CheckResult appendReason(String message) {
        reason.append(message).append(System.lineSeparator());
        return this;
    }

    public String getReason() {
        return reason.toString();
    }

    public boolean getResult() {
        return result;
    }

    public Object getValue() {
        return null;
    }

}
