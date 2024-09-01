import com.formdev.flatlaf.FlatLightLaf;
import gui.MainGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(new MainGUI());
    }

}
