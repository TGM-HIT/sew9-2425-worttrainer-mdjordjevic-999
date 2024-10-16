package com.example.spelltrainer.persistence;

import com.example.spelltrainer.model.SpellTrainer;
import com.example.spelltrainer.model.WordImagePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung des {@link SpellTrainerDAO} mit JSON als Speicherformat.
 * @version 16.10.2024
 * @author Marko Djordjevic
 */
public class JSONSpellTrainerDAO implements SpellTrainerDAO {

    private final String filename;

    /**
     * Konstruktor für die {@code JSONSpellTrainerDAO}-Klasse.
     * @param filename Der Name der Datei, in der die Daten gespeichert werden sollen.
     */
    public JSONSpellTrainerDAO(String filename) {
        this.filename = filename;
    }

    /**
     * Speichert den aktuellen Zustand des {@link SpellTrainer} in einer JSON-Datei.
     * @param spellTrainer Der {@code SpellTrainer}, dessen Zustand gespeichert werden soll.
     * @throws IOException   Wenn ein Fehler beim Schreiben in die Datei auftritt.
     * @throws JSONException Wenn ein Fehler beim Erstellen des JSON-Objekts auftritt.
     */
    @Override
    public void save(SpellTrainer spellTrainer) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        JSONArray pairsArray = new JSONArray();

        // Iteriere über alle Wort-Bild-Paare und füge sie dem JSON-Array hinzu
        for (WordImagePair pair : spellTrainer.getWordImagePairs()) {
            JSONObject pairJson = new JSONObject();
            pairJson.put("word", pair.getWord());
            pairJson.put("imageUrl", pair.getImageUrl().toString());
            pairsArray.put(pairJson);
        }

        json.put("wordImagePairs", pairsArray);
        json.put("totalAttempts", spellTrainer.getTotalAttempts());
        json.put("correctAttempts", spellTrainer.getCorrectAttempts());
        json.put("incorrectAttempts", spellTrainer.getIncorrectAttempts());

        // Wenn ein aktuelles Paar ausgewählt ist, füge es ebenfalls hinzu
        if (spellTrainer.getCurrentPair() != null) {
            JSONObject currentPairJson = new JSONObject();
            currentPairJson.put("word", spellTrainer.getCurrentPair().getWord());
            currentPairJson.put("imageUrl", spellTrainer.getCurrentPair().getImageUrl().toString());
            json.put("currentPair", currentPairJson);
        }

        // Schreibe das JSON-Objekt in die Datei mit einer Einrückung von 4 Leerzeichen
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString(4));
        }
    }

    /**
     * Lädt den Zustand des {@link SpellTrainer} aus einer JSON-Datei.
     * @return Ein {@code SpellTrainer} Objekt mit dem geladenen Zustand oder {@code null}, wenn die Datei nicht existiert.
     * @throws IOException             Wenn ein Fehler beim Lesen der Datei auftritt.
     * @throws MalformedURLException   Wenn eine der Bild-URLs ungültig ist.
     * @throws JSONException           Wenn ein Fehler beim Parsen des JSON-Inhalts auftritt.
     */
    @Override
    public SpellTrainer load() throws IOException, MalformedURLException, JSONException {
        File file = new File(filename);
        if (!file.exists()) {
            return null; // Keine Daten vorhanden
        }

        // Lese den gesamten Inhalt der Datei als String
        String content = new String(Files.readAllBytes(file.toPath()));
        JSONObject json = new JSONObject(content);

        JSONArray pairsArray = json.getJSONArray("wordImagePairs");
        List<WordImagePair> wordImagePairs = new ArrayList<>();

        // Erstelle die Liste der Wort-Bild-Paare aus dem JSON-Array
        for (int i = 0; i < pairsArray.length(); i++) {
            JSONObject pairJson = pairsArray.getJSONObject(i);
            WordImagePair pair = new WordImagePair(
                    pairJson.getString("word"),
                    pairJson.getString("imageUrl")
            );
            wordImagePairs.add(pair);
        }

        // Erstelle einen neuen SpellTrainer mit den geladenen Wort-Bild-Paaren
        SpellTrainer spellTrainer = new SpellTrainer(wordImagePairs);
        spellTrainer.setTotalAttempts(json.getInt("totalAttempts"));
        spellTrainer.setCorrectAttempts(json.getInt("correctAttempts"));
        spellTrainer.setIncorrectAttempts(json.getInt("incorrectAttempts"));

        // Wenn ein aktuelles Paar gespeichert ist, setze es
        if (json.has("currentPair")) {
            JSONObject currentPairJson = json.getJSONObject("currentPair");
            WordImagePair currentPair = new WordImagePair(
                    currentPairJson.getString("word"),
                    currentPairJson.getString("imageUrl")
            );
            spellTrainer.setCurrentPair(currentPair);
        }

        return spellTrainer;
    }
}
