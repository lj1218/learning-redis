package org.learningredis.chapter.three.rediscli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class SetCommand extends Command {

    private String key;
    private String value;

    public SetCommand(String key, String value) {
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
    public void execute() throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(super.socket.getOutputStream(), true);
            out.println(this.createPayload());
            // Reads from Redis server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // This is going to be a single line reply
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            in.close();
            socket.close();
        }
    }
}