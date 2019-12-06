package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.MessageCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.MapListToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The msg command is used to send messages to the nodes in the gossip server
 * ecosystem. The precondition for executing this command is that the master
 * node should be in the start mode.
 * <p>
 * Sequence of flow of data in Message command:
 * Shell => Message Command => Message Command Handler => JedisUtil
 */
public class MessageCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: msg <node name> where command=set,field1=v1,field2=v2,...
     *
     * Master node:
     *
     * msg vinoo where command=set, p=300,z=600
     * command=set, p=300, z=600
     * :->true
     * :->Sent to desired channel
     *
     * Client node:
     *
     * true
     * setting done in vinoo
     */
    public MessageCommand() {
        validator.configureTemplate().add(new StringToken(Commands.MSG.getValue()))
                .add(new StringToken()).add(new StringToken("where"))
                .add(new MapListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new MessageCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
