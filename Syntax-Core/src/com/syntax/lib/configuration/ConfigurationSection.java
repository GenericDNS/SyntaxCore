package com.syntax.lib.configuration;

public interface ConfigurationSection {

    public String getString(String path);

    public int getInt(String path);

    public Object getObject(String path);

}
