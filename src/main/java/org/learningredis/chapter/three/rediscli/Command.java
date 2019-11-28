package org.learningredis.chapter.three.rediscli;

import java.util.ArrayList;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public abstract class Command {

    protected String createPayload(ArrayList<String> messageList) {
        int argumentSize = messageList.size();
        StringBuilder payload = new StringBuilder();
        payload.append('*');
        payload.append(argumentSize);
        payload.append("\r\n");
        for (String msg : messageList) {
            payload.append('$');
            payload.append(msg.length());
            payload.append("\r\n");
            payload.append(msg);
            payload.append("\r\n");
        }
        return payload.toString();
    }

    protected abstract String createPayload();

    public abstract void execute();
}
