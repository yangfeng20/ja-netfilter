package com.janetfilter.plugins.dns;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;

import java.util.ArrayList;
import java.util.List;

public class DNSFilterPlugin implements PluginEntry {
    private static final String PLUGIN_NAME = "DNS";
    private final List<MyTransformer> transformers = new ArrayList<>();

    @Override
    public void init(Environment environment, PluginConfig config) {
        transformers.add(new InetAddressTransformer(config.getBySection(PLUGIN_NAME)));
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getAuthor() {
        return "neo";
    }

    @Override
    public String getVersion() {
        return "v1.1.0";
    }

    @Override
    public String getDescription() {
        return "A plugin for the ja-netfilter, it can block dns resolution.";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return transformers;
    }
}
