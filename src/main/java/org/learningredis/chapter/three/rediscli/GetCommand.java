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
public class GetCommand extends Command {

    private String key;

    public GetCommand(String key) {
        this.key = key;
    }

    @Override
    protected String createPayload() {
        ArrayList<String> messageList = new ArrayList<>(2);
        messageList.add("GET");
        messageList.add(key);
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
            String msg = in.readLine();
            if (!msg.contains("-1")) {
                System.out.println(msg);
                System.out.println(in.readLine());
            } else {
                // This will show the error message since the server has returned '-1'
                System.out.println("This Key does not exist!");
            }
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
