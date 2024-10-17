package com.example.spelltrainer.persistence;

/**
 * Factory-Klasse zur Erstellung von {@link SpellTrainerDAO} Implementierungen basierend auf dem Persistenztyp.
 * @version 16.10.2024
 * @author Marko Djordjevic
 */
public class SpellTrainerDAOFactory {

    /**
     * Erzeugt eine Instanz von {@link SpellTrainerDAO} basierend auf dem angegebenen Typ.
     * @param type     Der Persistenztyp (z.B. "JSON", "XML").
     * @param filename Der Dateiname, in dem die Daten gespeichert oder geladen werden sollen.
     * @return Eine Instanz von {@link SpellTrainerDAO}.
     * @throws UnsupportedOperationException Wenn der angegebene Persistenztyp nicht unterstützt wird.
     */
    public static SpellTrainerDAO getSpellTrainerDAO(String type, String filename) {
        switch (type.toUpperCase()) {
            case "JSON":
                return new JSONSpellTrainerDAO(filename);
            // Weitere Persistenztypen können hier hinzugefügt werden
            default:
                throw new UnsupportedOperationException("Persistenztyp " + type + " wird nicht unterstützt.");
        }
    }
}
