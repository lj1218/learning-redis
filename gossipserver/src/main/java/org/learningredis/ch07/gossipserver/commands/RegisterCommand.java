package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.RegisterCommandHandler;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

import java.io.File;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class RegisterCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public RegisterCommand() {
        validator.configureTemplate().add(new StringToken("register"));
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new RegisterCommandHandler(getName()).process(tokenList);
        }
        if (checkResult.getResult()) {
            String path = System.getProperty("user.home") + "/archive/";
            File file = new File(path);
            if (!file.exists()) {
                if (file.mkdir()) {
                    checkResult.appendReason("Archive folder created!");
                } else {
                    checkResult.appendReason("Archive folder exists!");
                }
            }
        }
        return checkResult;
    }
}
