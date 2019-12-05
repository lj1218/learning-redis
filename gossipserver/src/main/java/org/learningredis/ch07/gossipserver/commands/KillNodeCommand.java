package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.KillNodeCommandHandler;
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
public class KillNodeCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public KillNodeCommand() {
        // The syntax for this command is: msg <node name> where command = kill
        // Master node
        // msg vinoo where command=kill
        // command=kill
        // :->true
        // :->Sent to desired channel

        // Client node
        // Archiving the node..
        validator.configureTemplate().add(new StringToken("kill"))
                .add(new StringToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new KillNodeCommandHandler(getName()).process(tokenList);
            if (checkResult.getResult()) {
                String path = System.getProperty("user.home" + "/archive/"
                        + getName() + ".json");
                File file = new File(path);
                if (file.exists()) {
                    if (file.delete()) {
                        System.exit(0);
                    } else {
                        checkResult.appendReason("Archive file for "
                                + getName() + ".json could not get deleted!");
                    }
                }
            }
        }
        return checkResult;
    }
}
