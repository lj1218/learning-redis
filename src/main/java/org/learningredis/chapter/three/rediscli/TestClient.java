package org.learningredis.chapter.three.rediscli;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class TestClient {

    public static void main(String[] args) {
        Client client = new Client();
        client.set("MSG", "Hello world : simple test client");
        client.get("MSG");
        client.get("Wrong-key");
        client.destroy();
    }
}
