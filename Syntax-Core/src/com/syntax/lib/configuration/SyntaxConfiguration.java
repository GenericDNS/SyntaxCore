package com.syntax.lib.configuration;

import com.syntax.lib.data.DefaultsMaps;

public interface SyntaxConfiguration {

    public void addDefault(String path, Object o);

    public void addDefaults(DefaultsMaps<String, Object> defaultsMap);

    public void load();

    public String read();

}
