package com.example.spelltrainer.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Repräsentiert ein Paar aus Wort und zugehöriger Bild-URL.
 */
public class WordImagePair {
    private String word;
    private URL imageUrl;

    public WordImagePair(String word, String imageUrl) throws MalformedURLException {
        setWord(word);
        setImageUrl(imageUrl);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Das Wort darf nicht null oder leer sein.");
        }
        this.word = word.trim();
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) throws MalformedURLException {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Die Bild-URL darf nicht null oder leer sein.");
        }
        this.imageUrl = new URL(imageUrl.trim());
    }
}
