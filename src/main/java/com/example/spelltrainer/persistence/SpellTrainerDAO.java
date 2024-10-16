package com.example.spelltrainer.persistence;

import com.example.spelltrainer.model.SpellTrainer;

/**
 * Schnittstelle für die Persistenz des {@link SpellTrainer}.
 * @version 16.10.2024
 * @author Marko Djordjevic
 */
public interface SpellTrainerDAO {

    /**
     * Speichert den aktuellen Zustand des {@link SpellTrainer}.
     * @param spellTrainer Der {@code SpellTrainer}, dessen Zustand gespeichert werden soll.
     * @throws Exception Wenn ein Fehler beim Speichern auftritt.
     */
    void save(SpellTrainer spellTrainer) throws Exception;

    /**
     * Lädt den gespeicherten Zustand des {@link SpellTrainer}.
     * @return Ein {@code SpellTrainer} Objekt mit dem geladenen Zustand oder {@code null}, wenn kein gespeicherter Zustand vorhanden ist.
     * @throws Exception Wenn ein Fehler beim Laden auftritt.
     */
    SpellTrainer load() throws Exception;
}
