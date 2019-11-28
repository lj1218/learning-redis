package org.learningredis.chapter.three.rediscli;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class TestClient {

    public void execute(Command command) {
        try {
            // Connects to server
            command.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestClient client = new TestClient();
        SetCommand set = new SetCommand("MSG", "Hello world : simple test client");
        client.execute(set);

        GetCommand get = new GetCommand("MSG");
        client.execute(get);

        get = new GetCommand("Wrong-key");
        client.execute(get);
    }
}
