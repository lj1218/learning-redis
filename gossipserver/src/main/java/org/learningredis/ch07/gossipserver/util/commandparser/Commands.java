package org.learningredis.ch07.gossipserver.util.commandparser;

/**
 * Created by lj1218.
 * Date: 2019/12/6
 */
public enum Commands {

    START("start"), // master node
    STOP("stop"), // master node
    STATUS("status"), // master node
    GET("get"), // master/client node
    MSG("msg"), // master node
    KILL("kill"), // master node
    CLONE("clone"), // master node

    REGISTER("register"), // client node
    ACTIVATE("activate"), // client node
    SET("set"), // client node
    DEL("del"), // client node
    PASSIVATE("passivate"), // client node
    REACTIVATE("reactivate"), // client node
    ARCHIVE("archive"), // client node
    SYNC("sync"), // client node
    RECONNECT("reconnect"), // client node

    ILLEGAL("illegal command");

    private final String value;

    Commands(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Commands fromValue(String value) {
        for (Commands command : values()) {
            if (command.getValue().equals(value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Value '" + value
                + "' could not be found in Commands");
    }
}
