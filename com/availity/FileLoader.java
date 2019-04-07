package com.availity;

import java.io.File;

public class FileLoader {
    /**
     * Method to load test files.
     * The parameter passed indicates whether we should load LISP or CSV test files.
     */
    public File[] getFiles(String fileType) {
        // TODO: implement properties files instead of using absolute paths.
        StringBuilder path = new StringBuilder("TestFiles");

        if(fileType.equalsIgnoreCase("LISP")) {
            return getLISPFiles(path);
        }
        else if(fileType.equalsIgnoreCase("CSV")) {
            return getCSVFiles(path);
        }
        else return new File(path.toString()).listFiles(); // TODO: don't return parent directory that could have other files.
    }

    private File[] getLISPFiles(StringBuilder path) {
        path.append("\\LISP");
        return new File(path.toString()).listFiles();
    }

    private File[] getCSVFiles(StringBuilder path) {
        path.append("\\CSV");
        return new File(path.toString()).listFiles((d,s) -> {
            return s.contains(".csv");
        });
    }
}
