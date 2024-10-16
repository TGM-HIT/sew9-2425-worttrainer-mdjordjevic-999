package com.example.spelltrainer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rechtschreibtrainer-Klasse, die die Trainingslogik enthält.
 */
public class SpellTrainer {
    private List<WordImagePair> wordImagePairs;
    private WordImagePair currentPair;
    private int totalAttempts;
    private int correctAttempts;
    private int incorrectAttempts;

    public SpellTrainer(List<WordImagePair> wordImagePairs) {
        if (wordImagePairs == null || wordImagePairs.isEmpty()) {
            throw new IllegalArgumentException("Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.");
        }
        this.wordImagePairs = new ArrayList<>(wordImagePairs);
    }
}
