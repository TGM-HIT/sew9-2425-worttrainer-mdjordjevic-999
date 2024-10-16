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
        this.word = word;
        this.imageUrl = new URL(imageUrl);
    }
}
