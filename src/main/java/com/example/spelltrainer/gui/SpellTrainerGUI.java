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
 * @version 16.10.2024
 * @author
 */
public class SpellTrainerGUI {

    private static final String DATA_FILE = "spelltrainer.json";

    private SpellTrainer spellTrainer;
    private SpellTrainerDAO dao;
    private boolean lastAttemptCorrect = false;

    /**
     * Konstruktor für die {@code SpellTrainerGUI}-Klasse.
     * Initialisiert das DAO (Data Access Object) und lädt den gespeicherten Zustand
     * des {@link SpellTrainer}. Falls kein gespeicherter Zustand vorhanden ist oder
     * ein Fehler beim Laden auftritt, wird ein neuer {@code SpellTrainer} erstellt.
     */
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

    /**
     * Startet die grafische Benutzeroberfläche und das Trainingsprogramm.
     * Diese Methode öffnet ein Fenster, zeigt Bilder an und fordert den Benutzer auf,
     * das entsprechende Wort einzugeben. Die Trainingsstatistiken werden aktualisiert
     * und am Ende des Trainings angezeigt.
     */
    public void start() {
        boolean continueTraining = true;

        while (continueTraining) {
            // Wenn kein aktuelles Paar ausgewählt ist, wähle ein zufälliges Paar aus
            if (spellTrainer.getCurrentPair() == null) {
                spellTrainer.selectRandomPair();
            }

            WordImagePair currentPair = spellTrainer.getCurrentPair();
            ImageIcon imageIcon = loadImageIcon(currentPair.getImageUrl().toString());

            String message = buildMessage();

            // Erstelle ein unsichtbares JFrame als Elternkomponente für das JOptionPane
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Zentriert das Fenster

            // Zeige das Eingabedialogfeld mit dem Bild und der Nachricht an
            String guess = (String) JOptionPane.showInputDialog(
                    frame,
                    message,
                    "Rechtschreibtrainer",
                    JOptionPane.PLAIN_MESSAGE,
                    imageIcon,
                    null,
                    ""
            );

            frame.dispose(); // Schließe das JFrame nach der Eingabe

            if (guess == null || guess.trim().isEmpty()) {
                continueTraining = false; // Beende das Training, wenn keine Eingabe erfolgt
            } else {
                lastAttemptCorrect = spellTrainer.guessWord(guess);
            }
        }

        // Speichere den aktuellen Zustand des SpellTrainers
        try {
            dao.save(spellTrainer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Zeige die finalen Statistiken nach Beendigung des Trainings an
        JOptionPane.showMessageDialog(
                null,
                "Training beendet!\n" + buildFinalStatistics(),
                "Rechtschreibtrainer",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Baut die Nachricht zusammen, die im Eingabedialogfeld angezeigt wird.
     * @return Die zusammengebaute Nachricht als {@code String}.
     */
    private String buildMessage() {
        StringBuilder message = new StringBuilder();

        // Füge die aktuellen Trainingsstatistiken hinzu
        message.append("Statistik:\n")
                .append("Gesamtversuche: ").append(spellTrainer.getTotalAttempts()).append("\n")
                .append("Korrekt: ").append(spellTrainer.getCorrectAttempts()).append("\n")
                .append("Falsch: ").append(spellTrainer.getIncorrectAttempts()).append("\n\n");

        // Füge Informationen über den letzten Versuch hinzu, falls vorhanden
        if (spellTrainer.getTotalAttempts() > 0) {
            message.append("Letzter Versuch war ")
                    .append(lastAttemptCorrect ? "richtig" : "falsch")
                    .append(".\n\n");
        }

        message.append("Bitte gib das Wort ein:");

        return message.toString();
    }

    /**
     * Baut die finalen Statistiken zusammen, die nach Beendigung des Trainings angezeigt werden.
     * @return Die finalen Statistiken als {@code String}.
     */
    private String buildFinalStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append("Gesamtversuche: ").append(spellTrainer.getTotalAttempts()).append("\n")
                .append("Korrekte Antworten: ").append(spellTrainer.getCorrectAttempts()).append("\n")
                .append("Falsche Antworten: ").append(spellTrainer.getIncorrectAttempts()).append("\n");

        return stats.toString();
    }

    /**
     * Erstellt einen neuen {@link SpellTrainer} mit einer vordefinierten Liste von {@link WordImagePair}.
     * @return Ein neuer {@code SpellTrainer} mit den initialen Wort-Bild-Paaren.
     */
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

    /**
     * Erstellt ein {@link WordImagePair} Objekt.
     * @param word     Das Wort des Paares.
     * @param imageUrl Die URL des zugehörigen Bildes.
     * @return Ein neues {@code WordImagePair} Objekt oder {@code null}, wenn ein Fehler auftritt.
     */
    private WordImagePair createWordImagePair(String word, String imageUrl) {
        try {
            return new WordImagePair(word, imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lädt ein Bild von der angegebenen URL und skaliert es auf die gewünschte Größe.
     * @param imageUrl Die URL des Bildes als {@code String}.
     * @return Ein {@code ImageIcon} mit dem geladenen und skalierten Bild oder ein leeres {@code ImageIcon}, wenn ein Fehler auftritt.
     */
    private ImageIcon loadImageIcon(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
            if (image != null) {
                // Skaliere das Bild auf 300x200 Pixel für die Anzeige
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

    /**
     * Die Hauptmethode, die die Anwendung startet.
     * @param args Die Befehlszeilenargumente (werden nicht verwendet).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpellTrainerGUI gui = new SpellTrainerGUI();
            gui.start();
        });
    }
}
