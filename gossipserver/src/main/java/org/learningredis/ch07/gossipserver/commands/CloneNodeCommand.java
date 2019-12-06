package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.CloneNodeCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The clone command is used to make a clone of a node in the gossip server
 * ecosystem. The precondition for executing this command is that the master
 * node should not be in the start mode and a minimum of two client nodes
 * should be in the activated mode.
 * <p>
 * Sequence of flow of data in Clone command:
 * Shell => Clone Node Command => Clone Node Command Handler => JedisUtil
 */
public class CloneNodeCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: msg <node name> where command=clone, target=<node name>, source=<node name>
     *
     * Master node:
     *
     * msg loki where command=clone,target=loki,source=vinoo
     * command=clone, target=loki, source=vinoo
     * :->true
     * :->Sent to desired channel
     *
     * Client node(loki):
     *
     * clone loki target=loki, source=vinoo
     * true
     * OK
     */
    public CloneNodeCommand() {
        validator.configureTemplate().add(new StringToken(Commands.CLONE.getValue()))
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
