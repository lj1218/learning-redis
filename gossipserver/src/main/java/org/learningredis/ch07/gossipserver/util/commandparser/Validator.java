package org.learningredis.ch07.gossipserver.util.commandparser;

import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

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

    public void setInput(String input) {
        setInput(new CommandTokens(input));
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
            if (!(checkResult = templateToken.setValue(cmdTokens[i++])).getResult()) {
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
