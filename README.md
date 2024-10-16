# 9a.1: Worttrainer Reloaded

**Marko Djordjevic 5CHIT**

---


## Über das Projekt

**Worttrainer Reloaded** ist eine Java-basierte Desktop-Anwendung, die Benutzer dabei unterstützt, ihre Rechtschreibfähigkeiten zu verbessern. Die Anwendung zeigt ein Bild an und fordert den Benutzer auf, das entsprechende Wort einzugeben. Fortschritte werden in einer JSON-Datei gespeichert, sodass der Trainingsfortschritt nach dem Neustart der Anwendung fortgesetzt werden kann.

---

## Funktionen

- **Grafische Benutzeroberfläche (GUI):** Intuitive Bedienung mit `JOptionPane`.
- **Zufällige Wort-Bild-Paare:** Dynamische Auswahl der Trainingsaufgaben.
- **Statistiken:** Verfolgung von Gesamtversuchen, korrekten und falschen Antworten.
- **Persistenz:** Speicherung des Trainingsfortschritts in einer JSON-Datei.
- **Fehlerbehandlung:** Robust gegen ungültige Eingaben und Ladeprobleme.


---

## Installation

### Voraussetzungen

- **Java Development Kit (JDK) 17
- **Gradle** (optional, da das Projekt den Gradle Wrapper verwendet)

### Schritte

1. **Repository klonen:**

"git clone https://github.com/dein-username/worttrainer-reloaded.git
cd worttrainer-reloaded"

2. **Projekt bauen:**

Mit dem Gradle Wrapper:
"./gradlew build"

Oder mit lokal installiertem Gradle:
"gradle build"

3. **Anwendung ausführen:**

Mit dem Gradle Wrapper:
"./gradlew run"

Oder mit lokal installiertem Gradle:
"gradle run"

### Verwendung

1.  Führe die Anwendung wie oben beschrieben aus. Ein Eingabedialog wird angezeigt, der ein Bild und eine Aufforderung zur Eingabe des entsprechenden Wortes enthält.

2.  Gib das Wort ein, das zum angezeigten Bild passt, und bestätige die Eingabe.

3.  Nach jeder Eingabe werden die aktuellen Statistiken angezeigt, einschließlich Gesamtversuche, korrekte und falsche Antworten.

4.  Schließe das Eingabefenster oder lasse das Eingabefeld leer, um das Training zu beenden. Eine Abschlussstatistik wird angezeigt und der Fortschritt wird in der spelltrainer.json-Datei gespeichert.