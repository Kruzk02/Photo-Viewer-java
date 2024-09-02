package controller;

public class FileSelector {
    private FileSelection selection;

    public FileSelector(FileSelection selection) {
        this.selection = selection;
    }

    public void set(FileSelection selection) {
        this.selection = selection;
    }

    public void select() {
        selection.select();
    }
}
