package de.angelshiina.screens;

import javax.swing.*;

import de.angelshiina.vault.VaultStore;

import java.awt.*;

public class MasterScreen extends JFrame {

    public MasterScreen(boolean create) {
        setTitle(create ? "Create Master Password" : "Unlock Vault");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPasswordField pass = new JPasswordField();
        JButton action = appleButton(create ? "Create Vault" : "Unlock");

        JLabel info = new JLabel(
                create ? "Create a master password" : "Enter your master password"
        );
        info.setForeground(Color.DARK_GRAY);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        p.add(info);
        p.add(Box.createVerticalStrut(10));
        p.add(pass);
        p.add(Box.createVerticalStrut(20));
        p.add(action);

        action.addActionListener(e -> {
            char[] master = pass.getPassword();
            try {
                if (create) {
                    VaultStore.init(master);
                } else {
                    VaultStore.load(master);
                }
                dispose();
                new VaultUI(master);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Wrong password");
            }
        });

        add(p);
        setVisible(true);
    }

    private JButton appleButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(240, 240, 240));
        b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return b;
    }
}