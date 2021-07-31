package com.syntax.lib.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DefaultsMaps<P extends String, V> {

    List<P> pathCollection;
    List<V> valueCollection;

    public DefaultsMaps() {
        this.pathCollection = new ArrayList<>();
        this.valueCollection = new ArrayList<>();
    }

    public void put(List<P> paths, V... values) {
        this.pathCollection = paths;
        this.valueCollection = new ArrayList<>(Arrays.asList(values));
    }

    public void add(P path, V value) {
        this.pathCollection.add(path);
        this.valueCollection.add(value);
    }

    public List<P> getPathCollection() {
        return pathCollection;
    }

    public List<V> getValueCollection() {
        return valueCollection;
    }
}
