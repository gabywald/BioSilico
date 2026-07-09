package gabywald.creatures.parser2.model;

/**
 * Représente un lobe cérébral de Creatures.
 */
public class BrainLobe {
    public int number;
    public String name;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean perceptionLink;

    public BrainLobe(int number, String name, int x, int y, int width, int height, boolean perceptionLink) {
        this.number = number;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.perceptionLink = perceptionLink;
    }

    @Override
    public String toString() {
        return String.format(
            "Lobe %d (%s): Position=(%d,%d), Size=%dx%d, PerceptionLink=%s",
            number, name, x, y, width, height, perceptionLink ? "Yes" : "No"
        );
    }
}
