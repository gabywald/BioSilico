package gabywald.creatures.parser2.model;

import java.util.List;
import java.util.Map;

/**
 * Représente un instinct de Creatures.
 */
public class Instinct {
    public List<Map<String, Object>> conditions;
    public String action;
    public String rewardPunish;
    public int amount;

    public Instinct(List<Map<String, Object>> conditions, String action, String rewardPunish, int amount) {
        this.conditions = conditions;
        this.action = action;
        this.rewardPunish = rewardPunish;
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder conditionsStr = new StringBuilder();
        for (Map<String, Object> condition : conditions) {
            conditionsStr.append(String.format("%s[%d], ",
                condition.get("lobe"), condition.get("cell")));
        }
        if (conditionsStr.length() > 0) {
            conditionsStr.setLength(conditionsStr.length() - 2); // Supprimer la dernière virgule
        }
        return String.format(
            "Instinct: IF %s THEN %s (%s: %d)",
            conditionsStr.toString(), action, rewardPunish, amount
        );
    }
}