package gabywald.biosilico.model.reproduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import gabywald.biosilico.exceptions.ReproductionException;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class ReproductionHelper {

	/**
	 * To check if the two given Organism instances are compatible to reproduce. 
	 * <br/>Set of rules : 
	 * <ul>
	 * 		<li>Same Type</li>
	 * 		<li>Same number of chromosomes</li>
	 * 		<li>Comparable number of genes (to determine ? +/- 10 % ?)</li>
	 * 		<li>...</li>
	 * 		<li>...</li>
	 * </ul>
	 * @param orga1
	 * @param orga2
	 * @return
	 */
	public static boolean checkCompatibility(Organism orga1, Organism orga2) {
		
		// ***** Same Type of Organism. 
		if (orga1.getOrganismTypeAsType().compareTo(orga2.getOrganismTypeAsType()) != 0) 
			{ return false; }
		
		// ***** Same number of chromosomes. 
		if (orga1.getGenome().size() != orga2.getGenome().size()) 
			{ return false; }
		
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
		
		if ( (orga1.getSex() > 0) && (orga2.getSex() > 0) ) {
			// ***** If sex applies (different of 0) : sexes have to be different ??
			// Parthenogenysis ?!
		}
		
		return true;
	}
	
	public static int hasGamet(Organism orga) {
		return orga.hasAgentStatus(StatusType.GAMET);
	}
	
	public static Organism makeGamet(Organism orga) {
		Organism gametToReturn = new Organism();
		ReproductionHelper.copySomeData(orga, gametToReturn);
		gametToReturn.addExtendedLineageItem(orga.getUniqueID(), orga.getScientificName(), orga.getRank());		
		
		// ***** Create genome of GAMET. 
		Random selection = new Random();
		List<Chromosome> genomeOfGamet = new ArrayList<Chromosome>();
		orga.getGenome().stream().forEach( c -> {
			Chromosome currentCHR = new Chromosome();
			c.streamGene().forEach( g -> {
				// XXX NOTE : here select randomly one gene of of two !! 
				// NOTE more or less, depending of number of sexes ?!
				// NOTE depending of each sex gives to descendant ?!
				if (selection.nextInt() % 2 == 0) {
					// ***** Copy gene and mutate / duplicate / delete ?
					Gene clonedGene = g.clone();
					if ( (clonedGene.canMutate()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
						{ clonedGene.mutationChanges(); }
					if ( (clonedGene.canDuplicate()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
						{ currentCHR.addGene( g.clone() ); }
					if ( (clonedGene.canDelete()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
						{ ; } else { currentCHR.addGene( clonedGene ); }
				}
			});
			genomeOfGamet.add(currentCHR);
		});
		gametToReturn.setGenome(genomeOfGamet);
		
		// ***** Put some Chemicals get from Organism. UseFul for 'Fruits' !!
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( c -> {
			int initValue = orga.getChemicals().getVariable(c);
			if (initValue > 0) {
				int setupValue = initValue / 10; // 10 % XXX to be configured ?!
				orga.getChemicals().setVariable(c, initValue - setupValue);
				gametToReturn.getChemicals().setVariable(c, setupValue);
			}
		});
		
		return gametToReturn;
	}
	
	public static void copySomeData(Organism currentOrga, Organism nextOrga) {
		nextOrga.setRank(currentOrga.getRank());
		nextOrga.setDivision(currentOrga.getDivision());
		nextOrga.setOrganismType(currentOrga.getOrganismTypeAsType());
		nextOrga.setNameBiosilico(currentOrga.getBioSilicoName());
		nextOrga.setNameScientific(currentOrga.getScientificName());
		nextOrga.setNameCommon(currentOrga.getScientificName());
		// nextOrga.setNameIncluded(includedName);
		nextOrga.setSex(currentOrga.getSex());
		nextOrga.setOrganismType(currentOrga.getOrganismTypeAsType());
		nextOrga.setOrganismStatus(StatusType.EGG);
		
		nextOrga.setExtendedLineage(currentOrga.getExtendedLineage());
	}
	
	public static Organism binaryReproduction(Organism orga1, Organism orga2) 
			throws ReproductionException {
		if ( ! ReproductionHelper.checkCompatibility(orga1, orga2)) { 
			throw new ReproductionException("Incompatible organisms. ");
		}
		
		if ( (ReproductionHelper.hasGamet(orga1) > 0) && (ReproductionHelper.hasGamet(orga2) > 0)) {
			// TODO making gamets separately !?
			// TODO use of gamets ?!
			
			Agent gamet1AsAgent = orga1.getAgentStatus(StatusType.GAMET);
			Agent gamet2AsAgent = orga2.getAgentStatus(StatusType.GAMET);
			
			// TODO binary reproduction
			
			// XXX NOTE 20200825 interest of differences for non-haploïd : diploïd / polyploïd ... 
			
		} else {
			throw new ReproductionException("Not enough gamets. ");
		}
		
		return null;
	}
	
	
}
