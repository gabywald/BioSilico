package gabywald.biosilico.data;

import gabywald.global.data.filters.FilterUtils.FilterGroupType;
import gabywald.global.data.filters.GenericFileFilter;

/**
 * This class defines filters for BioSilico Files (Gattaca and Phase II)
 * @author Gabriel Chandesris (2010, 2020, 2022)
 */
public class FilterBioSilico extends GenericFileFilter {
	/** BioSilico Phase II files extension. '.ph2' */
	public final static String ph2 = FilePhaseTwo.DEFAULT_EXTENSION;
	/** BioSilico Gattaca files extension. '.gat' */
	public final static String gat = FileGattaca.DEFAULT_EXTENSION;
	/** BioSilico Gattaca files extension (organism). '.gatorg' */
	public final static String gatorg = FileOrganism.DEFAULT_EXTENSION;
	/** BioSilico Gattaca files extension (gene (?) ). '.gatgen' */
	public final static String gatgen = "gatgen";
	
	public static final FilterBioSilico FILTER_ORGANISM_ONLY = 
			new FilterBioSilico("BioSilico Organism Files", FilterBioSilico.gatorg);
	public static final FilterBioSilico FILTER_GENE_ONLY = 
			new FilterBioSilico("BioSilico Organism Files", FilterBioSilico.gatgen);

	/** Default Constructor. (All extensions. )*/
	public FilterBioSilico() 
		{ this("BioSilico Files", 	FilterBioSilico.ph2, 
									FilterBioSilico.gat, 
									FilterBioSilico.gatorg, 
									FilterBioSilico.gatgen ); }
	
	/**
	 * Extended constructor. 
	 * @param name (String) Filter Name. 
	 * @param extensions (String[]) extensions. 
	 */
	public FilterBioSilico(String name, String... extensions) {
		super(FilterGroupType.NONE.extensions());
		for (int i = 0 ; i < extensions.length ; i++) 
			{ this.addExtension( extensions[i]); }
	}
	
	/**
	 * To get a GenericFileFilter for alignments. 
	 * @return (GenericFileFilter)
	 */
	public static GenericFileFilter getAlignmentsFilter()
		{ return new GenericFileFilter(FilterGroupType.ALIGNMENTS.extensions()); }
	
	/**
	 * To get a GenericFileFilter for sequences. 
	 * @return (GenericFileFilter)
	 */
	public static GenericFileFilter getSequencesFilter()
		{ return new GenericFileFilter(FilterGroupType.SEQUENCES.extensions()); }
	
}
