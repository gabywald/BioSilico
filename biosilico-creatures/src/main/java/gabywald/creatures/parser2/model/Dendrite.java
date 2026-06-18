package gabywald.creatures.parser2.model;

/**
 * Représente une connexion (dendrite) entre deux lobes cérébraux.
 */
public class Dendrite {
    public int sourceLobe;
    public int sourceCell;
    public int targetLobe;
    public int targetCell;
    public int strength;
    public int type; // 0 = D0, 1 = D1

    public Dendrite(int sourceLobe, int sourceCell, int targetLobe, int targetCell, int strength, int type) {
        this.sourceLobe = sourceLobe;
        this.sourceCell = sourceCell;
        this.targetLobe = targetLobe;
        this.targetCell = targetCell;
        this.strength = strength;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
            "Dendrite: %d[%d] -> %d[%d], Strength=%d, Type=D%d",
            sourceLobe, sourceCell, targetLobe, targetCell, strength, type
        );
    }
}
