package gabywald.creatures.parser2.model;

import java.util.Map;

/**
 * Représente les demi-vies des chimies de Creatures.
 */
public class HalfLife {
    public Map<String, Integer> chemicalHalfLives;

    public HalfLife(Map<String, Integer> chemicalHalfLives) {
        this.chemicalHalfLives = chemicalHalfLives;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : chemicalHalfLives.entrySet()) {
            sb.append(String.format("%s=%d, ", entry.getKey(), entry.getValue()));
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Supprimer la dernière virgule
        }
        return "Half-Lives: " + sb.toString();
    }
}
