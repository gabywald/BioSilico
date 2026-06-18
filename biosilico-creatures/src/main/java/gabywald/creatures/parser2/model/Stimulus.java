package gabywald.creatures.parser2.model;

import java.util.List;

/**
 * Représente un stimulus de Creatures.
 */
public class Stimulus {
    public int stimulusType;
    public int intensity;
    public List<Integer> locus;

    public Stimulus(int stimulusType, int intensity, List<Integer> locus) {
        this.stimulusType = stimulusType;
        this.intensity = intensity;
        this.locus = locus;
    }

    @Override
    public String toString() {
        return String.format(
            "Stimulus: Type=%d, Intensity=%d, LOC(%d,%d,%d)",
            stimulusType, intensity, locus.get(0), locus.get(1), locus.get(2)
        );
    }
}
