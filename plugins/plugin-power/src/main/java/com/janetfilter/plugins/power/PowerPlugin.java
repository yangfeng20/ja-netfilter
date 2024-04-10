package com.janetfilter.plugins.power;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;

import java.util.ArrayList;
import java.util.List;

public class PowerPlugin implements PluginEntry {
    private final List<MyTransformer> transformers = new ArrayList<>();

    @Override
    public void init(Environment environment, PluginConfig config) {
        transformers.add(new ArgsTransformer(config.getBySection("Args")));
        transformers.add(new ResultTransformer(config.getBySection("Result")));
    }

    @Override
    public String getName() {
        return "Power";
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
        return "A plugin for the ja-netfilter, it is a dragon slayer for asymmetric encryption.";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return transformers;
    }
}
