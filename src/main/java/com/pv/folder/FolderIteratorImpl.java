package com.pv.folder;

import java.io.File;
import java.util.NoSuchElementException;

public class FolderIteratorImpl implements FolderIterator {

    private final File[] files;
    private int currentPosition;

    public FolderIteratorImpl(File[] files) {
        this.files = files;
        currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        return currentPosition < files.length -1;
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more files to iterate.");
        }
        return files[++currentPosition];
    }

    @Override
    public boolean hasPrev() {
        return currentPosition > 0;
    }

    @Override
    public File prev() {
        if (!hasPrev()) {
            throw new NoSuchElementException("No previous file to return.");
        }
        return files[--currentPosition];
    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}
