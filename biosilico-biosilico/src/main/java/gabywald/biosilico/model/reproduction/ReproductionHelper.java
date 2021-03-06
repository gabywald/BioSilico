package gabywald.biosilico.model.reproduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import gabywald.biosilico.exceptions.ReproductionException;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

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
		
		// NOTE : accept that it could be the same (same ID) !
		
		// ***** Same Type of Organism. 
		if (orga1.getAgentType().compareTo(orga2.getAgentType()) != 0) 
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
		
		// ***** XXX NOTE : Is it pertinent to reject for a number of genes of 0 ?!
		if ( (sumGenesOrga1 == 0) || (sumGenesOrga2 == 0) ) { return false; }
		
		// ***** tolerance of 10% about number of genes (speciation, virus...)
		if ( ((sumDiffNumber / sumGenesOrga1)*100 > 10)  
			&& ((sumDiffNumber / sumGenesOrga2)*100 > 10) ) { return false; }
		
		// XXX Notes other rules to exclude reproduction compatibility ?
		
		if ( (orga1.getSex() > 0) && (orga2.getSex() > 0) ) {
			// ***** If sex applies (different of 0) : sexes have to be different ??
			// Parthenogenysis ?!
			// XXX NOTE ...
		}
		
		return true;
	}
	
	public static int hasGamet(Organism orga) {
		return orga.hasAgentStatus(StatusType.GAMET);
	}
	
	public static Organism makeGamet(Organism orga) {
		Organism gametToReturn = new Organism();
		ReproductionHelper.copySomeData(orga, gametToReturn);
		// gametToReturn.addExtendedLineageItem(orga.getUniqueID(), orga.getScientificName(), orga.getRank());
		gametToReturn.addExtendedLineageItem(orga.getUniqueID(), orga.getBioSilicoName(), orga.getRank());
		
		// ***** Create genome of GAMET. 
		List<Chromosome> genomeOfGamet = new ArrayList<Chromosome>();
		orga.getGenome().stream().forEach( chr -> {
			Chromosome currentCHR = new Chromosome();
			currentCHR.setName( chr.getName() );
			chr.streamGene().forEach( g -> {
				// NOTE more or less, depending of number of sexes ?!
				// NOTE depending of each sex gives to descendant ?!
				
				// XXX Haploïd Gamet !
				// ***** Copy gene and mutate / duplicate / delete ?
				Gene clonedGene = g.clone();
				if ( (clonedGene.canMutate()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
					{ clonedGene.mutationChanges(); }
				if ( (clonedGene.canDuplicate()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
					{ currentCHR.addGene( g.clone() ); }
				if ( (clonedGene.canDelete()) && (IGeneMutation.mutate(clonedGene.getMutationRate())) ) 
					{ ; } else { currentCHR.addGene( clonedGene ); }
			});
			genomeOfGamet.add(currentCHR);
		});
		gametToReturn.setGenome(genomeOfGamet);
		
		// ***** Put some Chemicals get from Organism. UseFul for 'Fruits' !!
		// Here limited to 'Strictly chemicals to avoid changes of "positioning and concepts" used otherwise !!
		IntStream.range(0, ChemicalsHelper.CHEMICAL_STRICT_CHEM).forEach( c -> {
			int initValue = orga.getChemicals().getVariable(c);
			if (initValue > 0) {
				int setupValue = initValue / 5; // 5 % XXX to be configured ?!
				orga.getChemicals().setVariable(c, initValue - setupValue);
				gametToReturn.getChemicals().setVariable(c, setupValue);
			}
		});
		
		return gametToReturn;
	}
	
	public static int sizeOfGenome(Organism orga) {
		return orga.getGenome().stream().map( chr -> chr.length()).reduce(0, Integer::sum);
	}
	
	public static List<Chromosome> gametGenomeFusion(Organism... gamets) throws ReproductionException {
		
		if (gamets.length == 0) 
			{ throw new ReproductionException("No gamets !"); }
		
		// *** Check all are gamets !
		if ( ! Arrays.asList( gamets ).stream().allMatch( g -> (g.getOrganismStatus() == StatusType.GAMET) )) 
			{ throw new ReproductionException("Not all are gamets !"); }
		
		// *** XXX Haploïd : check same size (or similarity) ?? 
		int refSize 		= ReproductionHelper.sizeOfGenome( gamets[0] );
		
		if (refSize == 0) { return new ArrayList<Chromosome>(); }
		
		Organism longest	= gamets[0];
		for (Organism gamet : gamets) {
			int otherSize = ReproductionHelper.sizeOfGenome( gamet );
			if ( ( Math.abs(otherSize / refSize) ) > 10 ) // more than 10% difference
				{ throw new ReproductionException("Gamet size : more than 10% difference !"); }
			if (ReproductionHelper.sizeOfGenome( gamet ) > ReproductionHelper.sizeOfGenome( gamets[0] )) 
				{ longest = gamet; }
		}
		final Organism longestGenomeOrg = longest;
		
		List<Chromosome> genome2return = new ArrayList<Chromosome>();
		IntStream.range(0, longest.getGenome().size()).forEach( i -> {
			Chromosome chr		= longestGenomeOrg.getGenome().get( i );
			Chromosome newCHR	= new Chromosome();
			newCHR.setName( chr.getName() );
			
			// NOTE for each chr (index i ) => for each gene (index j) => peek a gene between the gamets !
			Random rand = new Random();
			IntStream.range(0, chr.length()).forEach( j -> {
				List<Gene> genes = Arrays.asList( gamets ).stream().map( g -> g.getGenome().get( i ).getGene( j )).filter( g -> (g != null) ).collect(Collectors.toList());
				// NOTE : filter above because a gene could be deleted !
				newCHR.addGene( genes.get( rand.nextInt( genes.size() ) ).clone() );
			});
			
			// NOTE complete with the longest gamet (longest genome)
			// NOTE genes could be also duplicated... 
			for (int j = newCHR.length() ; j < chr.length() ; j++) 
				{ newCHR.addGene( chr.getGene( j ) ); }
			
			genome2return.add( newCHR );
			
		});
		
		return genome2return;
	}
	
	public static void copySomeData(Organism currentOrga, Organism nextOrga) {
		nextOrga.setRank(currentOrga.getRank() + " copy" );
		nextOrga.setDivision(currentOrga.getDivision() + " copy" );
		nextOrga.setNameBiosilico(currentOrga.getBioSilicoName() + " copy" );
		nextOrga.setNameScientific(currentOrga.getScientificName() + " copy" );
		nextOrga.setNameCommon(currentOrga.getScientificName() + " copy" );
		// nextOrga.setNameIncluded(includedName);
		nextOrga.setSex(currentOrga.getSex());
		nextOrga.setObjectType(currentOrga.getObjectType());
		nextOrga.setAgentType(currentOrga.getAgentType());
		// nextOrga.setOrganismStatus(StatusType.EGG);
		nextOrga.setOrganismStatus(currentOrga.getOrganismStatus());
		
		// Copy List (and not copy reference). 
		nextOrga.setExtendedLineage(currentOrga.getExtendedLineage().stream().collect(Collectors.toList()));
	}
	
	// ===== ===== ===== ===== ===== REPRODUCTION TYPES ===== ===== ===== ===== ===== 
	// ***** Put / drop it in current WorldCase When Laying Egg !
	
	/**
	 * Cloning reproduction. 
	 * <br/>No check for gamets !
	 * <br/><i>DAEMON</i>
	 * @param orga1
	 * @return
	 * @throws ReproductionException
	 */
	public static Organism cloneReproduction(Organism orga1) 
			throws ReproductionException {
		Organism nextOrga		= new Organism(orga1.getGenome());
		ReproductionHelper.copySomeData(orga1, nextOrga);
		nextOrga.setOrganismStatus(StatusType.EGG);
		nextOrga.addExtendedLineageItem(orga1.getUniqueID(), orga1.getScientificName(), orga1.getRank());
		return nextOrga;
	}
	
	/**
	 * Reproduction from one Organism instance. 
	 * <br/>Check for gamets. 
	 * <Br/><i>BACTA</i> ; <i>VIRIDITA</i>
	 * @param orga1
	 * @return Organism's new instance (EGG status). 
	 * @throws ReproductionException if not enough gamets. 
	 */
	public static Organism unaryReproduction(Organism orga1) 
			throws ReproductionException {
		
		if (ReproductionHelper.hasGamet(orga1) > 1) {
			// ***** take gamets one by one !
			Agent gamet1AsAgent = orga1.getAgentStatus(StatusType.GAMET);
			orga1.remAgent( gamet1AsAgent );
			Agent gamet2AsAgent = orga1.getAgentStatus(StatusType.GAMET);
			orga1.remAgent( gamet2AsAgent );
			
			List<Chromosome> genomeNextOrga = ReproductionHelper.gametGenomeFusion((Organism)gamet1AsAgent, (Organism)gamet2AsAgent);
			
			Organism nextOrga		= new Organism( genomeNextOrga );
			ReproductionHelper.copySomeData(orga1, nextOrga);
			nextOrga.setOrganismStatus(StatusType.EGG);
			nextOrga.addExtendedLineageItem(orga1.getUniqueID(), orga1.getScientificName(), orga1.getRank());
			
			return nextOrga;
		} else {
			throw new ReproductionException("Not enough gamets. {" + orga1.getUniqueID() + "} has (" + ReproductionHelper.hasGamet(orga1) + "). ");
		}
		
	}
	
	/**
	 * Reproduction from two Organism instance (accepted it could be the same). 
	 * <br/>Check for gamets for each ! 
	 * <br/><i>ANIMA</i>
	 * XXX NOTE could be generalized as more !
	 * @param orga1
	 * @param orga2
	 * @return Organism's new instance (EGG status). 
	 * @throws ReproductionException if not enough gamets. 
	 */
	public static Organism binaryReproduction(Organism orga1, Organism orga2) 
			throws ReproductionException {
		if ( ! ReproductionHelper.checkCompatibility(orga1, orga2)) { 
			throw new ReproductionException("Incompatible organisms. {" + orga1.getUniqueID() + "}-{" + orga2.getUniqueID() + "}");
		}
		
		if (orga1.getUniqueID().equals(orga2.getUniqueID())) {
			Logger.printlnLog(LoggerLevel.LL_WARNING, "RH.bR {" + orga1.getUniqueID() + "}-{" + orga2.getUniqueID() + "}");
			throw new ReproductionException( "Same Organism {" + orga1.getUniqueID() + "}" ); 
		}
		
		if ( (ReproductionHelper.hasGamet(orga1) > 0) && (ReproductionHelper.hasGamet(orga2) > 0)) {
			// ***** take gamets one by one !
			Agent gamet1AsAgent = orga1.getAgentStatus(StatusType.GAMET);
			orga1.remAgent( gamet1AsAgent );
			Agent gamet2AsAgent = orga2.getAgentStatus(StatusType.GAMET);
			orga2.remAgent( gamet2AsAgent );
			
			if (gamet2AsAgent == null) 
				{ throw new ReproductionException( "No second gamet !"); }
			
			List<Chromosome> genomeNextOrga = ReproductionHelper.gametGenomeFusion((Organism)gamet1AsAgent, (Organism)gamet2AsAgent);
			
			Organism nextOrga		= new Organism( genomeNextOrga );
			ReproductionHelper.copySomeData(orga1, nextOrga);
			nextOrga.setOrganismStatus(StatusType.EGG);
			orga2.getExtendedLineage().stream().forEach( eli -> nextOrga.addExtendedLineageItem(eli) );

			nextOrga.addExtendedLineageItem(orga1.getUniqueID(), orga1.getScientificName(), orga1.getRank());
			nextOrga.addExtendedLineageItem(orga2.getUniqueID(), orga2.getScientificName(), orga2.getRank());
			
			return nextOrga;
			
			// XXX NOTE 20200825 interest of differences for non-haploïd : diploïd / polyploïd ... to be made for future release !!
			
		} else {
			throw new ReproductionException("Not enough gamets. {" + orga1.getUniqueID() + "} has (" + ReproductionHelper.hasGamet(orga1) 
														 + ") ; {" + orga2.getUniqueID() + "} has (" + ReproductionHelper.hasGamet(orga2) + ") . ");
		}
	}
	
	// ===== ===== ===== ===== ===== ACTUALIZATION ===== ===== ===== ===== ===== 
	
	/**
	 * SetUp of the corresponding Chemicals Variables. 
	 * @param orga Instance to acxtualize. 
	 */
	static void actualizeReproduction(Organism orga) {
		// ***** Decrease gamets signal
		orga.getChemicals().setVariable(StatusType.GAMET.getIndex(), orga.hasAgentStatus(StatusType.GAMET));
		// ***** Indicates that it is pregnant (or not) ! (has EGGs)
		orga.getChemicals().setVariable(StateType.PREGNANT.getIndex(), orga.hasAgentStatus(StatusType.EGG));
	}
	
}
