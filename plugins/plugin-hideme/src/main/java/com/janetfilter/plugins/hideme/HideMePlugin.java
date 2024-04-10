package com.janetfilter.plugins.hideme;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;

import java.util.ArrayList;
import java.util.List;

public class HideMePlugin implements PluginEntry {
    private final List<MyTransformer> transformers = new ArrayList<>();

    @Override
    public void init(Environment environment, PluginConfig config) {
        transformers.add(new VMTransformer(environment));
        transformers.add(new ClassNameTransformer());
    }

    @Override
    public String getName() {
        return "HideMe";
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
        return "A plugin for the ja-netfilter, it can prevent detection against javaagent.";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return transformers;
    }
}
