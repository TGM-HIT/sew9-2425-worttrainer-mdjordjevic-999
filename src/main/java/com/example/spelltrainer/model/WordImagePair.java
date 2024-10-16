package com.example.spelltrainer.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Repräsentiert ein Paar aus einem Wort und der zugehörigen Bild-URL.
 * @version 16.10.2024
 * @autor Marko Djordjevic
 */
public class WordImagePair {
    private String word;
    private URL imageUrl;

    /**
     * Konstruktor für die WordImagePair-Klasse.
     * @param word      Das Wort des Paares.
     * @param imageUrl  Die URL des zugehörigen Bildes als String.
     * @throws MalformedURLException Wenn die übergebene Bild-URL nicht gültig ist.
     * @throws IllegalArgumentException Wenn das Wort oder die Bild-URL null oder leer ist.
     */
    public WordImagePair(String word, String imageUrl) throws MalformedURLException {
        setWord(word);
        setImageUrl(imageUrl);
    }

    /**
     * Gibt das Wort des Paares zurück.
     * @return Das Wort als {@code String}.
     */
    public String getWord() {
        return word;
    }

    /**
     * Setzt das Wort des Paares.
     * @param word Das neue Wort.
     * @throws IllegalArgumentException Wenn das Wort null oder leer ist.
     */
    public void setWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Das Wort darf nicht null oder leer sein.");
        }
        this.word = word.trim();
    }

    /**
     * Gibt die Bild-URL des Paares zurück.
     * @return Die Bild-URL als {@code URL}.
     */
    public URL getImageUrl() {
        return imageUrl;
    }

    /**
     * Setzt die Bild-URL des Paares.
     * @param imageUrl Die neue Bild-URL als {@code String}.
     * @throws MalformedURLException      Wenn die übergebene Bild-URL nicht gültig ist.
     * @throws IllegalArgumentException   Wenn die Bild-URL null oder leer ist.
     */
    public void setImageUrl(String imageUrl) throws MalformedURLException {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Die Bild-URL darf nicht null oder leer sein.");
        }
        // Erstelle ein URL-Objekt aus dem übergebenen String nach dem Entfernen von Leerzeichen
        this.imageUrl = new URL(imageUrl.trim());
    }
}
