package org.learningredis.ch07.gossipserver.commands;

import org.learningredis.ch07.gossipserver.commandhandlers.RegisterCommandHandler;
import org.learningredis.ch07.gossipserver.datahandler.ConstUtil;
import org.learningredis.ch07.gossipserver.util.CheckResult;
import org.learningredis.ch07.gossipserver.util.commandparser.Commands;
import org.learningredis.ch07.gossipserver.util.commandparser.Validator;
import org.learningredis.ch07.gossipserver.util.commandparser.token.CommandTokens;
import org.learningredis.ch07.gossipserver.util.commandparser.token.StringToken;
import org.learningredis.ch07.gossipserver.util.commandparser.token.Token;

import java.io.File;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 * <p>
 * This command will register the node into the gossip server ecosystem. The
 * precondition for executing this command is that the node name should be unique;
 * otherwise, a response of failure will be sent to the Shell. The node name will
 * be stored in the Registration holder, which is implemented as a Set data structure
 * in Redis. Apart from this, when the registration process takes place, an archive
 * file is created in the local machine of the node.
 * <p>
 * Sequence of flow of data in a Register command:
 * Shell => Register Command => Register Command Handler => JedisUtil
 */
public class RegisterCommand extends AbstractCommand {

    private Validator validator = new Validator();

    /*
     * The syntax for this command is: register
     *
     * Client node:
     *
     * register
     * :->true
     * :->Registration Successful
     */
    public RegisterCommand() {
        validator.configureTemplate().add(new StringToken(Commands.REGISTER.getValue()));
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
            String path = ConstUtil.getArchiveStoreDir();
            System.out.println("path=" + path);
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
