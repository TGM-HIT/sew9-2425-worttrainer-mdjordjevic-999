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
        // Leere Methode
    }

    @Override
    public SpellTrainer load() throws Exception {
        // Leere Methode
        return null;
    }
}
