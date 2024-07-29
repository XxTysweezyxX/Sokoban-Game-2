package SokobanGame2;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure the creation of the GUI is done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create and display the game menu
            Menu menu = new Menu();
            menu.setVisible(true); // Make the menu visible
        });
    }
}
