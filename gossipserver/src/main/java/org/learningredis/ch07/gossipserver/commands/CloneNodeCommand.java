package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.CloneNodeCommandHandler;
import org.learningredis.ch07.gossipserver.token.CommandTokens;
import org.learningredis.ch07.gossipserver.token.StringToken;
import org.learningredis.ch07.gossipserver.token.Token;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.Validator;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class CloneNodeCommand extends AbstractCommand {

    private Validator validator = new Validator();

    public CloneNodeCommand() {
        // The syntax for this command is: msg <node name> where command=clone, target=<node name>, source=<node name>
        // Master node
        // msg loki where command=clone,target=loki,source=vinoo
        // command=clone, target=loki, source=vinoo
        // :->true
        // :->Sent to desired channel

        // Client node(loki)
        // clone loki target=loki, source=vinoo
        // true
        // OK
        validator.configureTemplate().add(new StringToken("clone"))
                .add(new StringToken()).add(new StringToken("from"))
                .add(new StringToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new CloneNodeCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
