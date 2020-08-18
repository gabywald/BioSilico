package gabywald.biosilico.model.chemicals;

import gabywald.biosilico.interfaces.IChemicals;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class ChemicalsBuilder {

	private static IChemicals basicBuild() {
		return new Chemicals();
	}
	
	public static IChemicals build() {
		return ChemicalsBuilder.basicBuild();
	}
	
}
