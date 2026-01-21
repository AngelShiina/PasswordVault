package de.angelshiina;

import javax.swing.*;

import de.angelshiina.screens.MasterScreen;
import de.angelshiina.vault.VaultStore;

public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            if (VaultStore.exists()) {
                new MasterScreen(false);
            } else {
                new MasterScreen(true);
            }
        });
    }
}