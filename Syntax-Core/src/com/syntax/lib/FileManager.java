package com.syntax.lib;

import java.io.File;

public class FileManager {

    private final String dir;
    private final String[] roots;

    public FileManager(String dir, String... roots) {
        this.dir = dir;
        this.roots = roots;
        create();
    }

    public FileManager create() {
        File dirFile = new File(dir);
        if (!dirFile.exists()) dirFile.mkdirs();
        for (String root : roots) {
            File file = new File(dir + "/" + root);
            if(!file.exists()) {
                file.mkdirs();

            }
        }

        return this;
    }

}
