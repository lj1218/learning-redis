package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.GetNodeDataCommandHandler;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringListToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * The get command will display the state of all the nodes that are registered in the
 * gossip server ecosystem.
 * <p>
 * Sequence of flow of data in GetNodeData command:
 * Shell => GetNodeData Command => GetNodeData Command Handler => JedisUtil
 */
public class GetNodeDataCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this node is: get <field1>,<field2>,... where nodes are <nodeName1>,<nodeName2>,...
     *
     * Master node:
     *
     * get x,y where nodes are loki,vinoo
     * :->true
     * :->The results for loki :
     * [300, 600]
     *
     * The results for vinoo :
     * [200, 500]
     */
    public GetNodeDataCommand() {
        validator.configureTemplate().add(new StringToken(Commands.GET.getValue()))
                .add(new StringListToken()).add(new StringToken("where"))
                .add(new StringToken("nodes")).add(new StringToken("are"))
                .add(new StringListToken());
    }

    @Override
    public CheckResult execute(CommandTokens commandTokens) {
        validator.setInput(commandTokens);
        CheckResult checkResult = validator.validate();
        if (checkResult.getResult()) {
            List<Token> tokenList = validator.getAllTokens();
            checkResult = new GetNodeDataCommandHandler(getName()).process(tokenList);
        }
        return checkResult;
    }
}
