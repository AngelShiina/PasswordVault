package de.angelshiina.screens;

import javax.swing.*;

import de.angelshiina.passwords.PasswordGenerator;
import de.angelshiina.vault.VaultStore;

import java.awt.*;
import java.util.Map;

public class VaultUI extends JFrame {

    private final Map<String, String> vault;
    private final char[] master;
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list;

    public VaultUI(char[] master) throws Exception {
        this.master = master;
        this.vault = VaultStore.load(master);

        setTitle("Password Vault");
        setSize(460, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        vault.keySet().stream().sorted().forEach(model::addElement);

        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Double click -> copy password
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String key = list.getSelectedValue();
                    if (key != null) {
                        copy(vault.get(key));
                        JOptionPane.showMessageDialog(VaultUI.this, "Copied to clipboard");
                    }
                }
            }
        });

        JButton add = appleButton("Add");
        JButton gen = appleButton("Generate");
        JButton remove = appleButton("Remove");

        add.addActionListener(e -> showAdd(null));
        gen.addActionListener(e -> showGenerator());
        remove.addActionListener(e -> removeSelected());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottom.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        bottom.add(add);
        bottom.add(gen);
        bottom.add(remove);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void showAdd(String preset) {
        JTextField name = new JTextField();
        JPasswordField pass = new JPasswordField();

        if (preset != null) pass.setText(preset);

        JPanel p = new JPanel(new GridLayout(0, 1, 6, 6));
        p.add(new JLabel("Name"));
        p.add(name);
        p.add(new JLabel("Password"));
        p.add(pass);

        int res = JOptionPane.showConfirmDialog(this, p, "Add Entry",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;

        String key = name.getText().trim();
        String pw = new String(pass.getPassword());

        if (key.isEmpty() || pw.isEmpty()) return;

        // overwrite if exists (optional)
        if (!vault.containsKey(key)) {
            model.addElement(key);
            sortModel();
        }

        vault.put(key, pw);
        save();
    }

    private void showGenerator() {
        JSpinner len = new JSpinner(new SpinnerNumberModel(20, 8, 64, 1));
        JCheckBox auto = new JCheckBox("Save directly");

        JPanel p = new JPanel(new GridLayout(0, 1, 6, 6));
        p.add(new JLabel("Length"));
        p.add(len);
        p.add(auto);

        int res = JOptionPane.showConfirmDialog(this, p, "Generate",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;

        String pw = PasswordGenerator.generate((int) len.getValue());

        if (auto.isSelected()) showAdd(pw);
        else {
            copy(pw);
            JOptionPane.showMessageDialog(this, "Copied");
        }
    }

    private void removeSelected() {
        String key = list.getSelectedValue();
        if (key == null) {
            JOptionPane.showMessageDialog(this, "Select an entry first.");
            return;
        }

        int res = JOptionPane.showConfirmDialog(
                this,
                "Remove \"" + key + "\"?",
                "Confirm Remove",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (res != JOptionPane.OK_OPTION) return;

        vault.remove(key);
        model.removeElement(key);
        save();
    }

    private void sortModel() {
        java.util.List<String> items = java.util.Collections.list(model.elements());
        items.sort(String::compareToIgnoreCase);
        model.clear();
        for (String s : items) model.addElement(s);
    }

    private void save() {
        try {
            VaultStore.save(vault, master);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save vault.");
        }
    }

    private void copy(String s) {
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(s), null);
    }

    private JButton appleButton(String t) {
        JButton b = new JButton(t);
        b.setFocusPainted(false);
        b.setBackground(new Color(245, 245, 245));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        return b;
    }
}