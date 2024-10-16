package com.example.spelltrainer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Die Rechtschreibtrainer-Klasse, die die Trainingslogik für das Rechtschreibtraining enthält.
 * @version 16.10.2024
 * @author Marko Djordjevic
 */
public class SpellTrainer {
    private List<WordImagePair> wordImagePairs;
    private WordImagePair currentPair;
    private int totalAttempts;
    private int correctAttempts;
    private int incorrectAttempts;

    private Random random;

    /**
     * Konstruktor für die SpellTrainer-Klasse.
     * @param wordImagePairs Eine Liste von Wort-Bild-Paaren für das Training.
     * @throws IllegalArgumentException Wenn die übergebene Liste null oder leer ist.
     */
    public SpellTrainer(List<WordImagePair> wordImagePairs) {
        if (wordImagePairs == null || wordImagePairs.isEmpty()) {
            throw new IllegalArgumentException("Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.");
        }
        // Erstelle eine Kopie der übergebenen Liste, um die Originaldaten zu schützen
        this.wordImagePairs = new ArrayList<>(wordImagePairs);
        this.random = new Random();
    }

    /**
     * Gibt die Liste der Wort-Bild-Paare zurück.
     * @return Die Liste der Wort-Bild-Paare.
     */
    public List<WordImagePair> getWordImagePairs() {
        return wordImagePairs;
    }

    /**
     * Setzt die Liste der Wort-Bild-Paare.
     * @param wordImagePairs Die neue Liste der Wort-Bild-Paare.
     * @throws IllegalArgumentException Wenn die übergebene Liste null oder leer ist.
     */
    public void setWordImagePairs(List<WordImagePair> wordImagePairs) {
        if (wordImagePairs == null || wordImagePairs.isEmpty()) {
            throw new IllegalArgumentException("Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.");
        }
        this.wordImagePairs = wordImagePairs;
    }

    /**
     * Gibt das aktuell ausgewählte Wort-Bild-Paar zurück.
     * @return Das aktuelle Wort-Bild-Paar oder null, wenn keines ausgewählt ist.
     */
    public WordImagePair getCurrentPair() {
        return currentPair;
    }

    /**
     * Setzt das aktuell ausgewählte Wort-Bild-Paar.
     * @param currentPair Das Wort-Bild-Paar, das aktuell ausgewählt werden soll.
     */
    public void setCurrentPair(WordImagePair currentPair) {
        this.currentPair = currentPair;
    }

    /**
     * Wählt ein Wort-Bild-Paar basierend auf dem angegebenen Index aus.
     * @param index Der Index des zu wählenden Wort-Bild-Paars.
     * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt.
     */
    public void selectPairByIndex(int index) {
        if (index < 0 || index >= wordImagePairs.size()) {
            throw new IndexOutOfBoundsException("Ungültiger Index für die Wort-Bild-Paar-Liste.");
        }
        currentPair = wordImagePairs.get(index);
    }

    /**
     * Wählt zufällig ein Wort-Bild-Paar aus der Liste aus.
     */
    public void selectRandomPair() {
        int index = random.nextInt(wordImagePairs.size());
        currentPair = wordImagePairs.get(index);
    }

    /**
     * Überprüft die Benutzereingabe gegen das aktuell ausgewählte Wort.
     * @param guess Die vom Benutzer eingegebene Vermutung.
     * @return {@code true}, wenn die Vermutung korrekt ist; {@code false} andernfalls.
     * @throws IllegalStateException Wenn kein aktuelles Wort-Bild-Paar ausgewählt ist.
     */
    public boolean guessWord(String guess) {
        if (currentPair == null) {
            throw new IllegalStateException("Kein aktuelles Wort-Bild-Paar ausgewählt.");
        }
        totalAttempts++;
        // Vergleiche die Vermutung unabhängig von Groß-/Kleinschreibung und führenden/trailenden Leerzeichen
        if (currentPair.getWord().equalsIgnoreCase(guess.trim())) {
            correctAttempts++;
            currentPair = null; // Das Paar wird deselektiert, da es korrekt geraten wurde
            return true;
        } else {
            incorrectAttempts++;
            return false;
        }
    }

    /**
     * Gibt die Gesamtanzahl der Versuche zurück.
     * @return Die Gesamtanzahl der Versuche.
     */
    public int getTotalAttempts() {
        return totalAttempts;
    }

    /**
     * Setzt die Gesamtanzahl der Versuche.
     * @param totalAttempts Die neue Gesamtanzahl der Versuche.
     */
    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    /**
     * Gibt die Anzahl der korrekten Versuche zurück.
     * @return Die Anzahl der korrekten Versuche.
     */
    public int getCorrectAttempts() {
        return correctAttempts;
    }

    /**
     * Setzt die Anzahl der korrekten Versuche.
     * @param correctAttempts Die neue Anzahl der korrekten Versuche.
     */
    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    /**
     * Gibt die Anzahl der inkorrekten Versuche zurück.
     * @return Die Anzahl der inkorrekten Versuche.
     */
    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    /**
     * Setzt die Anzahl der inkorrekten Versuche.
     * @param incorrectAttempts Die neue Anzahl der inkorrekten Versuche.
     */
    public void setIncorrectAttempts(int incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }
}
