package com.pv.folder;

import java.io.File;

public interface FolderIterator {
    boolean hasNext();
    File next();
    boolean hasPrev();
    File prev();
    void reset();
}
