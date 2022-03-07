package gabywald.biosilico.anthill.tests;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;

/**
 * To centralize some data and choices about Ant Genome. 
 * @author gabroiel Chandesris (2022)
 */
public abstract class AntBuildingGenomeDataHelper {
	
	/** At 2020-2022, is Brain.MAX_HEIGHT (99). */
	private static final int BRAIN_HEIGHT	= 99;
	/** At 2020-2022, is Brain.MAX_WIDTH (99). */
	private static final int BRAIN_WIDTH	= 99;
	/** At 2020-2022, is (1). */
	private static final int BRAIN_DEPTH	=  1;

	public static final int BRAIN_LOBE_OUTPUT	= 10;
	
	/**
	 * To build "Basic" Brain ... 
	 * @return (BrainGene) "99*99*1*0"
	 */
	public static BrainGene buildBasicBrainGene() {
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		StringBuilder sbBrainGene = new StringBuilder();
		sbBrainGene	.append("Brain Gene ")	.append( AntBuildingGenomeDataHelper.BRAIN_HEIGHT ).append("*")
											.append( AntBuildingGenomeDataHelper.BRAIN_WIDTH ).append("*")
											.append( AntBuildingGenomeDataHelper.BRAIN_DEPTH ).append("*").append( 0 );
		return bgb	.heigth( AntBuildingGenomeDataHelper.BRAIN_HEIGHT )
					.width( AntBuildingGenomeDataHelper.BRAIN_WIDTH )
					.depth( AntBuildingGenomeDataHelper.BRAIN_DEPTH )
					.more( 0 ).name( sbBrainGene.toString() )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build();
	}
}
