package org.learningredis.ch07.gossipserver.datahandler;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ConstUtil {

    /**
     * Registration holder: This is going to be implemented as a Set in the Redis data store.
     * This will hold all the nodes that are going to be registered in the system.
     */
    public static final String registrationHolder = "REGISTRATION-HOLDER";

    /**
     * Activation holder: This is going to be implemented as a Set in the Redis data store.
     * This will hold all the nodes that are going to be in the Active state.
     */
    public static final String activationHolder = "ACTIVATION-HOLDER";

    /**
     * Passivation holder: This is going to be implemented as a Set in the Redis data store.
     * This will hold all the nodes that are going to be in the Passive state.
     */
    public static final String passivationHolder = "PASSIVATION-HOLDER";

    /**
     * Shutdown holder: This is going to be implemented as a Set in the Redis data store.
     * This will hold all the nodes that are going to be in the shutdown state.
     */
    public static final String shutdownHolder = "SHUTDOWN-HOLDER";

    /**
     * Configuration store: This is going to be implemented as a Map in the Redis data store.
     * This will hold all the configuration data pertaining to a node in the name-value format.
     */
    public static final String configurationStore = "CONFIGURATION-STORE";

    public static String getConfigurationStore(String nodeName) {
        return configurationStore + "@" + nodeName;
    }

    /**
     * Archive store: This is going to be implemented as a File store in the local file system
     * of the client node. This will hold all the configuration data pertaining to a node in
     * the name-value format that is going to be archived in the JSON format.
     */
    public static final String archiveStore = "/archive/";

    public static String getArchiveStoreDir() {
        return System.getProperty("user.home") + archiveStore;
    }

    public static String getArchiveStore(String nodeName) {
        return System.getProperty("user.home") + archiveStore
                + nodeName + ".json";
    }
}
