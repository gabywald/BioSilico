package gabywald.creatures.parser2.model;

import java.util.List;
import java.util.Map;

/**
 * Représente une réaction chimique de Creatures.
 */
public class Reaction {
    public List<Map<String, Object>> reactants;
    public List<Map<String, Object>> products;
    public int rate;

    public Reaction(List<Map<String, Object>> reactants, List<Map<String, Object>> products, int rate) {
        this.reactants = reactants;
        this.products = products;
        this.rate = rate;
    }

    @Override
    public String toString() {
        StringBuilder reactantsStr = new StringBuilder();
        for (Map<String, Object> reactant : reactants) {
            reactantsStr.append(String.format("%d %s + ",
                reactant.get("proportion"), reactant.get("chem_name")));
        }
        if (reactantsStr.length() > 0) {
            reactantsStr.setLength(reactantsStr.length() - 3); // Supprimer le dernier "+ "
        }

        StringBuilder productsStr = new StringBuilder();
        for (Map<String, Object> product : products) {
            productsStr.append(String.format("%d %s + ",
                product.get("proportion"), product.get("chem_name")));
        }
        if (productsStr.length() > 0) {
            productsStr.setLength(productsStr.length() - 3); // Supprimer le dernier "+ "
        }

        return String.format(
            "Reaction: %s → %s (Rate=%d)",
            reactantsStr.toString(), productsStr.toString(), rate
        );
    }
}
