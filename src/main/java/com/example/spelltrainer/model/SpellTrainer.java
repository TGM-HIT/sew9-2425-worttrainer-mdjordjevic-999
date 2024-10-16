package com.example.spelltrainer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Rechtschreibtrainer-Klasse, die die Trainingslogik enthält.
 */
public class SpellTrainer {
    private List<WordImagePair> wordImagePairs;
    private WordImagePair currentPair;
    private int totalAttempts;
    private int correctAttempts;
    private int incorrectAttempts;

    private Random random;

    public SpellTrainer(List<WordImagePair> wordImagePairs) {
        if (wordImagePairs == null || wordImagePairs.isEmpty()) {
            throw new IllegalArgumentException("Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.");
        }
        this.wordImagePairs = new ArrayList<>(wordImagePairs);
        this.random = new Random();
    }

    public List<WordImagePair> getWordImagePairs() {
        return wordImagePairs;
    }

    public void setWordImagePairs(List<WordImagePair> wordImagePairs) {
        if (wordImagePairs == null || wordImagePairs.isEmpty()) {
            throw new IllegalArgumentException("Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.");
        }
        this.wordImagePairs = wordImagePairs;
    }

    public WordImagePair getCurrentPair() {
        return currentPair;
    }

    public void setCurrentPair(WordImagePair currentPair) {
        this.currentPair = currentPair;
    }

    public void selectPairByIndex(int index) {
        if (index < 0 || index >= wordImagePairs.size()) {
            throw new IndexOutOfBoundsException("Ungültiger Index für die Wort-Bild-Paar-Liste.");
        }
        currentPair = wordImagePairs.get(index);
    }

    public void selectRandomPair() {
        int index = random.nextInt(wordImagePairs.size());
        currentPair = wordImagePairs.get(index);
    }

    public boolean guessWord(String guess) {
        if (currentPair == null) {
            throw new IllegalStateException("Kein aktuelles Wort-Bild-Paar ausgewählt.");
        }
        totalAttempts++;
        if (currentPair.getWord().equalsIgnoreCase(guess.trim())) {
            correctAttempts++;
            currentPair = null; // Paar wird deselektiert
            return true;
        } else {
            incorrectAttempts++;
            return false;
        }
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public int getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(int correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public int getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(int incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }
}
