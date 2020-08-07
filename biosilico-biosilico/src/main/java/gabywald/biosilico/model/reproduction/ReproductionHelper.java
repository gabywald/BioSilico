package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class ReproductionHelper {

	/**
	 * To check if the two given Organism instances are compatible to reproduce. 
	 * <br/>Set of rules : 
	 * <ul>
	 * 		<li>Same number of chromosomes</li>
	 * 		<li>Comparable number of genes (to determine ? +/- 10 % ?)</li>
	 * 		<li>...</li>
	 * </ul>
	 * @param orga1
	 * @param orga2
	 * @return
	 */
	public static boolean checkCompatibility(Organism orga1, Organism orga2) {
		// ***** Same number of chromosomes. 
		if (orga1.getGenome().size() != orga2.getGenome().size()) { return false; }
		
		// ***** Comparing sizes / number of genes. 
		int sumGenesOrga1 = 0;
		int sumGenesOrga2 = 0;
		int sumDiffNumber = 0;
		for (int i = 0 ; i < orga1.getGenome().size() ; i++) {
			sumGenesOrga1	+= orga1.getGenome().get(0).length();
			sumGenesOrga2	+= orga2.getGenome().get(0).length();
			sumDiffNumber	+= Math.abs(orga1.getGenome().get(0).length() - orga2.getGenome().get(0).length());
		}
		if ( (sumGenesOrga1 == 0) || (sumGenesOrga2 == 0) ) { return false; }
		if ( ((sumDiffNumber / sumGenesOrga1)*100 > 10)  
			&& ((sumDiffNumber / sumGenesOrga2)*100 > 10) ) { return false; }
		
		// XXX Notes other rules to exclude reproduction compatibility ?
		
		return true;
	}
	
	public static void binaryReproduction(Organism orga1, Organism orga2) {
		if ( ! ReproductionHelper.checkCompatibility(orga1, orga2)) { return; }
		
		// TODO binary reproduction
		// TODO making gamets separately !?
		// TODO use of gamets ?!
	}
	
	
}
