package org.learningredis.ch07.gossipserver.shell;

import org.learningredis.ch07.gossipserver.commands.AbstractCommand;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public interface NodeMessageListenerManager {

    public void start();

    public void stop();

    public void passCommand(AbstractCommand command);
}
