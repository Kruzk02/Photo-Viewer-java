package com.pv;

import com.formdev.flatlaf.FlatLightLaf;
import com.pv.gui.MainGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(new MainGUI());
    }

}
