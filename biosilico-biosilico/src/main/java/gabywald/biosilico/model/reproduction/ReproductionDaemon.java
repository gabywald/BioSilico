package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2020)
 */
public class ReproductionDaemon implements IReproduction {
	
	private static ReproductionDaemon instance = null;
	
	private ReproductionDaemon() { ; }
	
	public static ReproductionDaemon getInstance() {
		if (ReproductionDaemon.instance == null) 
			{ ReproductionDaemon.instance = new ReproductionDaemon(); }
		return ReproductionDaemon.instance;
	}

	@Override
	public void action(Organism... organisms) {
		if (organisms.length == 0) { return; }

		// ***** at least one : cloning the first one given
		Organism currentOrga	= organisms[0];
		
		Organism nextOrga		= new Organism(currentOrga.getGenome());
		nextOrga.setRank(currentOrga.getRank());
		nextOrga.setDivision(currentOrga.getDivision());
		nextOrga.setExtendedLineage(currentOrga.getExtendedLineage());
		nextOrga.setOrganismType(currentOrga.getOrganismTypeAsType());
		nextOrga.setNameBiosilico(currentOrga.getBioSilicoName());
		nextOrga.setNameScientific(currentOrga.getScientificName());
		nextOrga.setNameCommon(currentOrga.getScientificName());
		// nextOrga.setNameIncluded(includedName);
		nextOrga.setSex(currentOrga.getSex());
		nextOrga.setOrganismType(AgentType.BIOSILICO_DAEMON);
		nextOrga.setOrganismStatus(StatusType.EGG);
		
		// ***** Put / drop it in current WorldCase When Laying Egg !
		// nextOrga.setCurrentWorldCase(currentOrga.getCurrentWorldCase());
		currentOrga.addAgent( nextOrga );
		
		// ***** Decrease gamets signal => divide by 2 !!
		currentOrga.getVariables().setVarLess(SomeChemicals.GAMET.getIndex(), currentOrga.getVariables().getVariable(SomeChemicals.GAMET.getIndex()) / 2);
		// ***** Indicates that it is pregnant !
		currentOrga.getVariables().setVarPlusPlus(SomeChemicals.EGG.getIndex());
		currentOrga.getVariables().setVarPlusPlus(StateType.PREGNANT.getIndex());
	}

}
