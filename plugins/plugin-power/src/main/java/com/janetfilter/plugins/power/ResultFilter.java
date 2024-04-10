package com.janetfilter.plugins.power;

import com.janetfilter.core.commons.DebugInfo;
import com.janetfilter.core.enums.RuleType;
import com.janetfilter.core.models.FilterRule;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class ResultFilter {
    private static Set<String> l1cached;
    private static Map<String, BigInteger> l2cached;

    public static void setRules(List<FilterRule> rules) {
        l1cached = new HashSet<>();
        l2cached = new HashMap<>();

        for (FilterRule rule : rules) {
            if (rule.getType() != RuleType.EQUAL) {
                continue;
            }

            String[] sections = rule.getRule().split("->", 2);
            if (2 != sections.length) {
                DebugInfo.output("Invalid record: " + rule + ", skipped.");
                continue;
            }

            l1cached.add(Arrays.stream(sections[0].split(",")).map(s -> String.valueOf(new BigInteger(s).intValue())).collect(Collectors.joining(",")));

            l2cached.put(sections[0], new BigInteger(sections[1]));
        }
    }

    public static BigInteger testFilter(BigInteger x, BigInteger y, BigInteger z) {
        if (l1cached.contains(x.intValue() + "," + y.intValue() + "," + z.intValue())) {
            return l2cached.getOrDefault(x + "," + y + "," + z, null);
        }

        return null;
    }
}
