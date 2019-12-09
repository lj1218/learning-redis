package org.learningredis.chapter.four.pubsub.reliable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class LuaScript {

    static final String PATH = "src/main/java/org/learningredis/" +
            "chapter/four/pubsub/reliable/ReliableMessaging.lua";

    public static String read(String script) throws IOException {
        Path file = Paths.get(script);
        BufferedReader br = Files.newBufferedReader(file, Charset.defaultCharset());
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        String contentString = content.toString();
        System.out.println("Content: " + contentString);
        return contentString;
    }

    public static String read() throws IOException {
        return read(PATH);
    }
}
