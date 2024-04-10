package com.janetfilter.plugins.power;

import com.janetfilter.core.commons.DebugInfo;
import com.janetfilter.core.enums.RuleType;
import com.janetfilter.core.models.FilterRule;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class ArgsFilter {
    private static Set<String> l1cached;
    private static Map<String, BigInteger[]> l2cached;

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

            if (-1 == sections[1].indexOf(',')) {
                DebugInfo.output("Invalid record: " + rule + ", skipped.");
                continue;
            }

            l1cached.add(Arrays.stream(sections[0].split(",")).map(s -> String.valueOf(new BigInteger(s).intValue())).collect(Collectors.joining(",")));

            String[] tmp = sections[1].split(",");
            l2cached.put(sections[0], new BigInteger[]{new BigInteger(tmp[0]), new BigInteger(tmp[1])});
        }
    }

    public static BigInteger[] testFilter(BigInteger x, BigInteger y, BigInteger z) {
        if (l1cached.contains(y.intValue() + "," + z.intValue())) {
            return l2cached.getOrDefault(y + "," + z, null);
        }

        return null;
    }
}
