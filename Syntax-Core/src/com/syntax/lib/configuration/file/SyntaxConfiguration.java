package com.syntax.lib.configuration.file;

import com.syntax.lib.configuration.ConfigurationSection;
import com.syntax.lib.configuration.SyntaxScaffold;
import com.syntax.lib.data.DefaultsMaps;
import com.syntax.lib.mysql.Adapter;
import com.syntax.lib.thread.Scheduler;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SyntaxConfiguration implements
        com.syntax.lib.configuration.SyntaxConfiguration,
        SyntaxScaffold,
        ConfigurationSection {

    DefaultsMaps<String, Object> defaultCollections = new DefaultsMaps<>();

    protected final File remoteFile;

    public SyntaxConfiguration(File remoteFile) {
        this.remoteFile = remoteFile;
    }

    public SyntaxConfiguration(String name) {
        this.remoteFile = new File(name);
    }

    @Override
    public void addDefault(String path, Object o) {
        defaultCollections.add(path, o);
    }

    @Override
    public void addDefaults(DefaultsMaps<String, Object> defaultsMap) {
    }

    private void addInput(String path, Object o) {
        try {
            String line = read();
            FileWriter fileWriter = new FileWriter(remoteFile);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            if (line == null){ bw.write(path + ": " + o); return;}
            bw.write(line + "\n" + path + ": " + o);
            bw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void load() {
        int index = 0;
        List<String> strings = defaultCollections.getPathCollection();
        List<Object> objects = defaultCollections.getValueCollection();
        for (String count : defaultCollections.getPathCollection()) {
            addInput(strings.get(index), objects.get(index));
            index++;
        } this.defaultCollections = new DefaultsMaps<>();
    }

    @Override
    public String read() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = Files.newBufferedReader(remoteFile.toPath());
            bufferedReader.lines().forEach(line -> stringBuilder.append(line + "\n"));
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        } return null;
    }

    @Override
    public void toDataBase(Adapter adapter, String... path) {
        if (!(path == null)) {
            class run implements Runnable {
                @Override
                public void run() {
                    List<String> objects = new ArrayList<>();
                    for (String paths : path) {
                        objects.add(getObject(paths).toString());
                    }
                    Executor executor = Executors.newCachedThreadPool();
                    executor.execute(() -> {
                        adapter.createTable(remoteFile.getName(), path);
                        adapter.addMoreInTable(remoteFile.getName(), new ArrayList<>(Arrays.asList(path)), objects);
                    });
                };
            }
        }
    }

    @Override
    public String getString(String path) {
        String line = read();
        for (String element : line.split("\n")) {
            String[] keyAndValue = element.split(": ",2);
            if (keyAndValue.length == 2 && keyAndValue[0].equals(path)) {
                return keyAndValue[1];
            }
        } return "";
    }

    @Override
    public int getInt(String path) {
        String line = read();
        for (String element : line.split("\n")) {
            String[] keyAndValue = element.split(": ",2);
            if (keyAndValue.length == 2 && keyAndValue[0].equals(path)) {
                return Integer.parseInt(keyAndValue[1]);
            }
        } return -1;
    }

    @Override
    public Object getObject(String path) {
        String line = read();
        for (String element : line.split("\n")) {
            String[] keyAndValue = element.split(": ",2);
            if (keyAndValue.length == 2 && keyAndValue[0].equals(path)) {
                return keyAndValue[1];
            }
        } return "";
    }
}
