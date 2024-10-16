package com.example.spelltrainer.persistence;

import com.example.spelltrainer.model.SpellTrainer;

/**
 * Implementierung des SpellTrainerDAO mit JSON als Speicherformat.
 */
public class JSONSpellTrainerDAO implements SpellTrainerDAO {

    private final String filename;

    public JSONSpellTrainerDAO(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(SpellTrainer spellTrainer) throws Exception {
        // Vorbereitung für die JSON-Speicherung
        // Hier wird später die Logik zur Speicherung implementiert
    }

    @Override
    public SpellTrainer load() throws Exception {
        // Vorbereitung für das Laden von JSON
        // Hier wird später die Logik zum Laden implementiert
        return null;
    }
}
