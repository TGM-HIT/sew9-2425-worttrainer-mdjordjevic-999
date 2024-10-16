package com.example.spelltrainer.persistence;

import com.example.spelltrainer.model.SpellTrainer;

/**
 * Schnittstelle für die Persistenz des SpellTrainers.
 */
public interface SpellTrainerDAO {
    void save(SpellTrainer spellTrainer) throws Exception;
    SpellTrainer load() throws Exception;
}
