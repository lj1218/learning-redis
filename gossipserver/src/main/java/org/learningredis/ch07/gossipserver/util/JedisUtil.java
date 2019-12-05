package org.learningredis.ch07.gossipserver.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class JedisUtil {

    public CheckResult setValuesInNode(String nodeName, Map<String, String> map) {
        return null;
    }

    public CheckResult getValuesFromNode(String nodeName, List<String> values) {
        return null;
    }

    public CheckResult deleteValuesFromNode(String nodeName, List<String> values) {
        return null;
    }

    public List<Boolean> doesExist(String nodeName, List<String> holders) {
        return new ArrayList<>();
    }

    public CheckResult registerNode(String nodeName) {
        return null;
    }

    public CheckResult activateNode(String nodeName) {
        return null;
    }

    public List<String> getAllNodesFromRegistrationHolder() {
        return null;
    }

    public List<String> getAllNodesFromActivatedHolder() {
        return null;
    }

    public List<String> getAllNodesFromPassivatedHolder() {
        return null;
    }

    public List<String> getAllNodesInInconsistentState() {
        return null;
    }

    public CheckResult getStatus(String nodeName) {
        return null;
    }

    public CheckResult passivateNode(String nodeName) {
        return null;
    }

    public CheckResult reactiveNode(String nodeName) {
        return null;
    }

    public CheckResult archiveNode(String nodeName) {
        return null;
    }

    public CheckResult syncNode(String nodeName) {
        return null;
    }

    public CheckResult reconnectNode(String nodeName) {
        return null;
    }

    public CheckResult publish(String channel, Map<String, String> map) {
        return null;
    }

    public CheckResult killNode(String nodeName) {
        return null;
    }

    public CheckResult clone(String target, String source) {
        return null;
    }
}
