package com.example.spelltrainer;

import com.example.spelltrainer.model.SpellTrainer;
import com.example.spelltrainer.model.WordImagePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Testklasse für die SpellTrainer-Klasse.
 * version 17.10.2024
 * @author Marko Djordjevic
 */
public class SpellTrainerTest {

    private SpellTrainer spellTrainer;
    private WordImagePair pair1;
    private WordImagePair pair2;
    private WordImagePair pair3;

    @BeforeEach
    @DisplayName("Setup vor jedem Test")
    public void setUp() throws MalformedURLException {
        // Initialisiere Wort-Bild-Paare
        pair1 = new WordImagePair("Apfel", "https://example.com/apfel.jpg");
        pair2 = new WordImagePair("Banane", "https://example.com/banane.jpg");
        pair3 = new WordImagePair("Kirsche", "https://example.com/kirsche.jpg");

        // Erstelle eine Liste von Wort-Bild-Paaren
        List<WordImagePair> pairs = Arrays.asList(pair1, pair2, pair3);

        // Initialisiere SpellTrainer mit den Paaren
        spellTrainer = new SpellTrainer(pairs);
    }

    @Test
    @DisplayName("Test Konstruktor mit gültigen Paaren")
    public void testConstructorWithValidPairs() {
        assertNotNull(spellTrainer, "SpellTrainer sollte nicht null sein");
        assertEquals(3, spellTrainer.getWordImagePairs().size(), "Anzahl der Wort-Bild-Paare sollte 3 sein");
        assertNull(spellTrainer.getCurrentPair(), "Aktuelles Paar sollte null sein");
        assertEquals(0, spellTrainer.getTotalAttempts(), "TotalAttempts sollte 0 sein");
        assertEquals(0, spellTrainer.getCorrectAttempts(), "CorrectAttempts sollte 0 sein");
        assertEquals(0, spellTrainer.getIncorrectAttempts(), "IncorrectAttempts sollte 0 sein");
    }

    @Test
    @DisplayName("Test Konstruktor mit leerer Paar-Liste")
    public void testConstructorWithEmptyPairs() {
        List<WordImagePair> emptyPairs = Arrays.asList();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new SpellTrainer(emptyPairs);
        });
        String expectedMessage = "Die Wort-Bild-Paar-Liste darf nicht null oder leer sein.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception Message sollte enthalten: " + expectedMessage);
    }

    @Test
    @DisplayName("Test Auswahl eines zufälligen Wort-Bild-Paars")
    public void testSelectRandomPair() {
        spellTrainer.selectRandomPair();
        WordImagePair currentPair = spellTrainer.getCurrentPair();
        assertNotNull(currentPair, "Aktuelles Paar sollte nicht null sein nach Auswahl");
        List<WordImagePair> expectedPairs = Arrays.asList(pair1, pair2, pair3);
        assertTrue(expectedPairs.contains(currentPair), "Aktuelles Paar sollte in der ursprünglichen Liste enthalten sein");
    }

    @Test
    @DisplayName("Test korrektes Raten eines Wortes")
    public void testGuessWordCorrect() {
        spellTrainer.selectRandomPair();
        WordImagePair currentPair = spellTrainer.getCurrentPair();
        String correctGuess = currentPair.getWord();

        boolean result = spellTrainer.guessWord(correctGuess);
        assertTrue(result, "guessWord sollte true zurückgeben für korrekte Eingabe");
        assertEquals(1, spellTrainer.getCorrectAttempts(), "CorrectAttempts sollte um 1 erhöht sein");
        assertEquals(1, spellTrainer.getTotalAttempts(), "TotalAttempts sollte um 1 erhöht sein");
        assertNull(spellTrainer.getCurrentPair(), "Aktuelles Paar sollte nach korrekter Eingabe null sein");
    }

    @Test
    @DisplayName("Test falsches Raten eines Wortes")
    public void testGuessWordIncorrect() {
        spellTrainer.selectRandomPair();
        WordImagePair currentPair = spellTrainer.getCurrentPair();
        String incorrectGuess = "FalschesWort";

        boolean result = spellTrainer.guessWord(incorrectGuess);
        assertFalse(result, "guessWord sollte false zurückgeben für falsche Eingabe");
        assertEquals(1, spellTrainer.getIncorrectAttempts(), "IncorrectAttempts sollte um 1 erhöht sein");
        assertEquals(1, spellTrainer.getTotalAttempts(), "TotalAttempts sollte um 1 erhöht sein");
        assertNotNull(spellTrainer.getCurrentPair(), "Aktuelles Paar sollte nach falscher Eingabe nicht null sein");
    }
}
