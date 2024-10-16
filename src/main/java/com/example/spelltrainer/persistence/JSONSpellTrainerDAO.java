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
 * Implementierung des SpellTrainerDAO mit JSON als Speicherformat.
 */
public class JSONSpellTrainerDAO implements SpellTrainerDAO {

    private final String filename;

    public JSONSpellTrainerDAO(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(SpellTrainer spellTrainer) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        JSONArray pairsArray = new JSONArray();

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

        if (spellTrainer.getCurrentPair() != null) {
            JSONObject currentPairJson = new JSONObject();
            currentPairJson.put("word", spellTrainer.getCurrentPair().getWord());
            currentPairJson.put("imageUrl", spellTrainer.getCurrentPair().getImageUrl().toString());
            json.put("currentPair", currentPairJson);
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString(4));
        }
    }

    @Override
    public SpellTrainer load() throws IOException, MalformedURLException, JSONException {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }

        String content = new String(Files.readAllBytes(file.toPath()));
        JSONObject json = new JSONObject(content);

        JSONArray pairsArray = json.getJSONArray("wordImagePairs");
        List<WordImagePair> wordImagePairs = new ArrayList<>();

        for (int i = 0; i < pairsArray.length(); i++) {
            JSONObject pairJson = pairsArray.getJSONObject(i);
            WordImagePair pair = new WordImagePair(
                    pairJson.getString("word"),
                    pairJson.getString("imageUrl")
            );
            wordImagePairs.add(pair);
        }

        SpellTrainer spellTrainer = new SpellTrainer(wordImagePairs);
        spellTrainer.setTotalAttempts(json.getInt("totalAttempts"));
        spellTrainer.setCorrectAttempts(json.getInt("correctAttempts"));
        spellTrainer.setIncorrectAttempts(json.getInt("incorrectAttempts"));

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
