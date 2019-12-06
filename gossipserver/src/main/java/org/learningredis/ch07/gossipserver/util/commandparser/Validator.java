package org.learningredis.ch07.gossipserver.util.commandparser;

import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.*;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class Validator {

    private Template template = new Template();

    private CommandTokens commandTokens;

    public Template configureTemplate() {
        return template;
    }

    public void setInput(String message) {
    }

    public void setInput(CommandTokens tokens) {
        this.commandTokens = tokens;
    }

    public CheckResult validate() {
        System.out.println(commandTokens);
        List<Token> templateTokens = getAllTokens();
        String[] cmdTokens = commandTokens.getTokens();
        if (templateTokens.size() > cmdTokens.length) {
            return new CheckResult().setFalse("command args not enough!");
        }
        int i = 0;
        CheckResult checkResult = new CheckResult();
        for (Token templateToken : templateTokens) {
            String cmdToken = cmdTokens[i++];
            if (templateToken instanceof StringToken) {
                StringToken st = (StringToken) templateToken;
                if (!st.getValue().isEmpty()) {
                    if (!st.getValue().equals(cmdToken)) {
                        return new CheckResult().setFalse("expect " + st.getValue()
                                + ", but got " + cmdToken);
                    }
                    continue;
                }
            }
            if (!(checkResult = templateToken.setValue(cmdToken)).getResult()) {
                return checkResult;
            }
        }
        return checkResult;
    }

    public Token getToken(int index) {
        return template.get(index);
    }

    public List<Token> getAllTokens() {
        return template.getTokens();
    }

}
