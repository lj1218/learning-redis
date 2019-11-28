package org.learningredis.chapter.three.rediscli;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class Client {

    private Connection connection;

    public Client() {
        this.connection = new Connection();
    }

    public void set(String key, String value) {
        execute(new SetCommand(connection, key, value));
    }

    public void get(String key) {
        execute(new GetCommand(connection, key));
    }

    private void execute(Command command) {
        try {
            // Connects to server
            command.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        connection.close();
    }

}
