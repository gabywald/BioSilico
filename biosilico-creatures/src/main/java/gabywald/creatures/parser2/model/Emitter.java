package gabywald.creatures.parser2.model;

import java.util.List;

/**
 * Représente un émetteur chimique de Creatures.
 */
public class Emitter {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int sampleRate;
    public int gain;

    public Emitter(List<Integer> locus, String chemical, int threshold, int sampleRate, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.sampleRate = sampleRate;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return String.format(
            "Emitter: LOC(%d,%d,%d) -> %s (Threshold=%d, SampleRate=%d, Gain=%d)",
            locus.get(0), locus.get(1), locus.get(2), chemical, threshold, sampleRate, gain
        );
    }
}
