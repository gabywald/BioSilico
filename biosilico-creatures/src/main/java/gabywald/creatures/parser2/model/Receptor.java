package gabywald.creatures.parser2.model;

import java.util.List;

/**
 * Représente un récepteur chimique de Creatures.
 */
public class Receptor {
    public List<Integer> locus;
    public String chemical;
    public int threshold;
    public int nominal;
    public int gain;

    public Receptor(List<Integer> locus, String chemical, int threshold, int nominal, int gain) {
        this.locus = locus;
        this.chemical = chemical;
        this.threshold = threshold;
        this.nominal = nominal;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return String.format(
            "Receptor: LOC(%d,%d,%d) -> %s (Threshold=%d, Nominal=%d, Gain=%d)",
            locus.get(0), locus.get(1), locus.get(2), chemical, threshold, nominal, gain
        );
    }
}
