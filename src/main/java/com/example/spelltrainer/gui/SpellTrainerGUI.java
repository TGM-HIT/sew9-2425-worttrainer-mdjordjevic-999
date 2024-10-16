package com.example.spelltrainer.gui;

import com.example.spelltrainer.model.SpellTrainer;
import com.example.spelltrainer.model.WordImagePair;
import com.example.spelltrainer.persistence.JSONSpellTrainerDAO;
import com.example.spelltrainer.persistence.SpellTrainerDAO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Hauptklasse der grafischen Benutzeroberfläche für den Rechtschreibtrainer.
 */
public class SpellTrainerGUI {

    private static final String DATA_FILE = "spelltrainer.json";

    private SpellTrainer spellTrainer;
    private SpellTrainerDAO dao;
    private boolean lastAttemptCorrect = false;

    public SpellTrainerGUI() {
        dao = new JSONSpellTrainerDAO(DATA_FILE);
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

    public void start() {
        boolean continueTraining = true;

        while (continueTraining) {
            if (spellTrainer.getCurrentPair() == null) {
                spellTrainer.selectRandomPair();
            }

            WordImagePair currentPair = spellTrainer.getCurrentPair();
            ImageIcon imageIcon = loadImageIcon(currentPair.getImageUrl().toString());

            String message = buildMessage();

            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Zentriert das Fenster

            String guess = (String) JOptionPane.showInputDialog(
                    frame,
                    message,
                    "Rechtschreibtrainer",
                    JOptionPane.PLAIN_MESSAGE,
                    imageIcon,
                    null,
                    ""
            );

            frame.dispose();

            if (guess == null || guess.trim().isEmpty()) {
                continueTraining = false;
            } else {
                lastAttemptCorrect = spellTrainer.guessWord(guess);
            }
        }

        try {
            dao.save(spellTrainer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(
                null,
                "Training beendet!\n" + buildFinalStatistics(),
                "Rechtschreibtrainer",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String buildMessage() {
        StringBuilder message = new StringBuilder();

        message.append("Statistik:\n")
                .append("Gesamtversuche: ").append(spellTrainer.getTotalAttempts()).append("\n")
                .append("Korrekt: ").append(spellTrainer.getCorrectAttempts()).append("\n")
                .append("Falsch: ").append(spellTrainer.getIncorrectAttempts()).append("\n\n");

        if (spellTrainer.getTotalAttempts() > 0) {
            message.append("Letzter Versuch war ")
                    .append(lastAttemptCorrect ? "richtig" : "falsch")
                    .append(".\n\n");
        }

        message.append("Bitte gib das Wort ein:");

        return message.toString();
    }

    private String buildFinalStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append("Gesamtversuche: ").append(spellTrainer.getTotalAttempts()).append("\n")
                .append("Korrekte Antworten: ").append(spellTrainer.getCorrectAttempts()).append("\n")
                .append("Falsche Antworten: ").append(spellTrainer.getIncorrectAttempts()).append("\n");

        return stats.toString();
    }

    private SpellTrainer createNewSpellTrainer() {
        List<WordImagePair> pairs = Arrays.asList(
                createWordImagePair("koala", "https://img.freepik.com/premium-photo/closeup-funny-portrait-surprised-koala-with-huge-eyes-solid-white-background-wide-angle-shot-ai-generative_955712-9739.jpg"),
                createWordImagePair("affe", "https://img.freepik.com/premium-photo/closeup-funny-portrait-surprised-chimpanzee-with-huge-eyes-solid-white-background-wide-angle-shot-ai-generative_955712-9934.jpg"),
                createWordImagePair("hase", "https://img.freepik.com/premium-photo/closeup-funny-portrait-surprised-hare-with-huge-eyes-wide-angle-shot-ai-generative_955712-10785.jpg"),
                createWordImagePair("schaf", "https://img.freepik.com/premium-photo/closeup-funny-portrait-surprised-sheep-with-huge-eyes-solid-white-background-wide-angle-shot-ai-generative_955712-9917.jpg"),
                createWordImagePair("hund", "https://img.freepik.com/fotos-premium/closeup-funny-portrait-von-ueberraschtem-jack-russell-hund-mit-riesigen-augen-weitwinkel-schuss-ai-generative_955712-9745.jpg")
        );

        // Entferne null-Werte aus der Liste
        pairs.removeIf(pair -> pair == null);

        return new SpellTrainer(pairs);
    }

    private WordImagePair createWordImagePair(String word, String imageUrl) {
        try {
            return new WordImagePair(word, imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageIcon loadImageIcon(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                return new ImageIcon();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpellTrainerGUI gui = new SpellTrainerGUI();
            gui.start();
        });
    }
}
