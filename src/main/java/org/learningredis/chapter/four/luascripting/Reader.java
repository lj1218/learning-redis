package org.learningredis.chapter.four.luascripting;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class Reader {

    public static String read(String script) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(script));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine).append(System.lineSeparator());
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
