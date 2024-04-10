package com.janetfilter.plugins.dns;

import com.janetfilter.core.commons.DebugInfo;
import com.janetfilter.core.models.FilterRule;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class DNSFilter {
    private static List<FilterRule> ruleList;

    public static void setRules(List<FilterRule> rules) {
        ruleList = rules;
    }

    public static String testQuery(String host) throws IOException {
        if (null == host || null == ruleList) {
            return null;
        }

        for (FilterRule rule : ruleList) {
            if (!rule.test(host)) {
                continue;
            }

            DebugInfo.output("Reject dns query: " + host + ", rule: " + rule);
            throw new java.net.UnknownHostException();
        }

        return host;
    }

    public static Object testReachable(InetAddress n) throws IOException {
        if (null == n || null == ruleList) {
            return null;
        }

        for (FilterRule rule : ruleList) {
            if (!rule.test(n.getHostName())) {
                continue;
            }

            DebugInfo.output("Reject dns reachable test: " + n.getHostName() + ", rule: " + rule);
            return false;
        }

        return null;
    }
}
