package com.janetfilter.plugins.url;

import com.janetfilter.core.commons.DebugInfo;
import com.janetfilter.core.models.FilterRule;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

public class URLFilter {
    private static List<FilterRule> ruleList;

    public static void setRules(List<FilterRule> rules) {
        ruleList = rules;
    }

    public static URL testURL(URL url) throws IOException {
        if (null == url || null == ruleList) {
            return null;
        }

        for (FilterRule rule : ruleList) {
            if (!rule.test(url.toString())) {
                continue;
            }

            DebugInfo.output("Reject url: " + url + ", rule: " + rule);
            throw new SocketTimeoutException("connect timed out");
        }

        return url;
    }
}
