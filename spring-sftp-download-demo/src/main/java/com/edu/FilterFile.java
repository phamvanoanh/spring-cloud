package com.edu;

import org.springframework.integration.file.filters.AcceptOnceFileListFilter;

import java.io.File;

public class FilterFile extends AcceptOnceFileListFilter<File> {

    public boolean accept(File file) {
        return file.isFile() && file.getName().endsWith(".csv");
    }


}
