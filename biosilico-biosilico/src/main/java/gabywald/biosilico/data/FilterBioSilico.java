package gabywald.biosilico.data;

import gabywald.global.data.FilterUtils;

/**
 * This class defines filters for BioSilico Files (Gattaca and Phase II)
 * @author Gabriel Chandesris (2010, 2020)
 */
public class FilterBioSilico extends FilterUtils {
	/** BioSilico Phase II files extension. */
	public final static String ph2 = "ph2";
	/** BioSilico Gattaca files extension. */
	public final static String gat = "gat";
	/** BioSilico Gattaca files extension (organism). */
	public final static String gatorg = "gatorg";
	/** BioSilico Gattaca files extension (organism). */
	public final static String gatgen = "gatgen";

	/** Default Constructor. */
	public FilterBioSilico() {
		super(FilterGroupType.NONE, "BioSilico Files");
		this.addExtension(FilterBioSilico.ph2);
		this.addExtension(FilterBioSilico.gat);
		this.addExtension(FilterBioSilico.gatorg);
		this.addExtension(FilterBioSilico.gatgen);
	}
}
