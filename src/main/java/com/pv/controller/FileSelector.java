package com.pv.controller;

public class FileSelector {
    private FileSelection selection;

    public FileSelector() {
    }

    public FileSelector(FileSelection selection) {
        this.selection = selection;
    }

    public void set(FileSelection selection) {
        this.selection = selection;
    }

    public void select() {
        if (selection != null) {
            selection.select();
        }else {
            throw new NullPointerException();
        }
    }
}
