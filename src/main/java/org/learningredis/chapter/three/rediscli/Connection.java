package org.learningredis.chapter.three.rediscli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class Connection {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Connection() {
        try {
            socket = new Socket(ConnectionProperties.HOST, ConnectionProperties.PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
