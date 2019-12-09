package org.learningredis.chapter.three.rediscli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class SetCommand extends Command {

    private Connection connection;
    private String key;
    private String value;

    public SetCommand(Connection connection, String key, String value) {
        this.connection = connection;
        this.key = key;
        this.value = value;
    }

    @Override
    protected String createPayload() {
        ArrayList<String> messageList = new ArrayList<>(3);
        messageList.add("SET");
        messageList.add(key);
        messageList.add(value);
        return super.createPayload(messageList);
    }

    @Override
    public void execute() {
        PrintWriter out = connection.getWriter();
        BufferedReader in = connection.getReader();
        try {
            out.print(this.createPayload());
            out.flush();
            // Reads from Redis server
            // This is going to be a single line reply
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
