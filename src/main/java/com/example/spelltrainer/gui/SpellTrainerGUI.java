package com.example.spelltrainer.gui;

import com.example.spelltrainer.model.SpellTrainer;
import com.example.spelltrainer.persistence.JSONSpellTrainerDAO;
import com.example.spelltrainer.persistence.SpellTrainerDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Hauptklasse der grafischen Benutzeroberfläche für den Rechtschreibtrainer.
 */
public class SpellTrainerGUI {

    private static final String DATA_FILE = "spelltrainer.json";

    private SpellTrainer spellTrainer;
    private SpellTrainerDAO dao;

    private JFrame frame;
    private JLabel welcomeLabel;
    private JButton startButton;
    private JLabel statusLabel;

    public SpellTrainerGUI() {
        dao = new JSONSpellTrainerDAO(DATA_FILE);
        loadSpellTrainer();
        initializeGUI();
    }

    private void loadSpellTrainer() {
        try {
            spellTrainer = dao.load();
            if (spellTrainer == null) {
                spellTrainer = createNewSpellTrainer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            spellTrainer = createNewSpellTrainer();
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Rechtschreibtrainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Willkommen zum Rechtschreibtrainer!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        startButton = new JButton("Starten");
        startButton.addActionListener(e -> startTraining());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Lade Wort-Bild-Paare...", SwingConstants.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // Zentriert das Fenster
        frame.setVisible(true);
    }

    private void startTraining() {
        statusLabel.setText("Training wird gestartet...");
        // Platzhalter für die Trainingsschleife
        // Hier kann die Trainingslogik integriert werden
        JOptionPane.showMessageDialog(frame, "Training startet bald!", "Info", JOptionPane.INFORMATION_MESSAGE);
        statusLabel.setText("Training läuft...");
    }

    private SpellTrainer createNewSpellTrainer() {
        // Implementiere die Erstellung eines neuen SpellTrainers
        // Beispielweise mit vorgefertigten Wort-Bild-Paaren
        return new SpellTrainer(List.of());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpellTrainerGUI::new);
    }
}
