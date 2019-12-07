package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.commands.AbstractCommand;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public interface NodeMessageListenerManager {

    void start();

    void stop();

    void passCommand(AbstractCommand command);
}
