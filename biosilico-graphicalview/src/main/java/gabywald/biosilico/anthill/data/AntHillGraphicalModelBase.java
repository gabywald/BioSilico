package gabywald.biosilico.anthill.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.helpers.AntHillExampleHelper;
import gabywald.biosilico.anthill.view.AntHillGraphicalRunner;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * To regroup model for graphical representation. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2022)
 * @deprecated replace by {@link AntHillGraphicalModel} created by {@link AntHillGraphicalModelBuilder}. 
 */
public class AntHillGraphicalModelBase {
	/** Unique instance of this model. */
	private static AntHillGraphicalModelBase instance = null;

	private World2D currentWSimulation	= null;
	private World2DCase currentWCase	= null;
	private Plant currentPlant			= null;
	private Ant currentAnt				= null;
	private DataCollector dcExportData	= null;
	private StringBuilder sbExportData	= null;
	
	private int stepsCounter			= 0;
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (AntHillGraphicalModel)
	 */
	public static AntHillGraphicalModelBase getInstance() {
		if (AntHillGraphicalModelBase.instance == null) 
			{ AntHillGraphicalModelBase.instance = new AntHillGraphicalModelBase(); }
		return AntHillGraphicalModelBase.instance;
	}
	
	private AntHillGraphicalModelBase() {
		/* ***** Loading Data to start simulation ***** */
		this.currentAnt = new Ant();

		this.currentAnt.setRank("Rank Test");
		this.currentAnt.setNameCommon("Test Starting Ant");
		this.currentAnt.setNameBiosilico("AntHill Ant Example");
		this.currentAnt.setDivision("TESTS");

		this.currentAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );

		this.currentPlant = new Plant();

		this.currentPlant.setRank("Rank Test");
		this.currentPlant.setNameCommon("Test Starting Plant");
		this.currentPlant.setNameBiosilico("AntHill Plant Example");
		this.currentPlant.setDivision("TESTS");

		this.currentPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );

		// ***** test with a World and WorldCase
		this.currentWSimulation	= new World2D(1, 1);
		this.currentWCase		= this.currentWSimulation.getWorldCase(0,  0);

		this.currentAnt.setCurrentWorldCase( this.currentWCase );
		this.currentPlant.setCurrentWorldCase( this.currentWCase );
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		this.currentWCase.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		this.currentWCase.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		this.currentWCase.addAgent( new EnergySource() );
		this.currentWCase.addAgent( new BlackHole() );
		
		/* ***** To visualise / export data of simulation. ***** */
		this.dcExportData = new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		this.sbExportData = new StringBuilder();
	}
	
	public List<Organism> getOrganisms()	{ 
		List<Organism> orgs = new ArrayList<>();
		orgs.add(this.currentAnt);
		orgs.add(this.currentPlant);
		
		// this.currentWCase.getAgentType(type)
		
		return orgs;
	}
	
	public List<World2DCase> getLocations() 
		{ return this.currentWSimulation.getWorldCases(); }
	
	public DataCollector getDataCollector()	
		{ return this.dcExportData; }
	
	public void oneStep() {
		this.execution();
		this.stepsCounter++;
		int aging = this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex());
		// int aging = this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex());
		dcExportData.addValue(	aging, StateType.AGING.name(), this.stepsCounter + "" );
		
		// ***** Set measures in Plot *****
		// ***** Set measures in Plot for Organism's
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.stream().forEach( chem -> {
			dcExportData.addValue(	this.currentPlant.getChemicals().getVariable( chem.getIndex() ), 
					"plant" + chem.getName(), this.stepsCounter + "");
			dcExportData.addValue(	this.currentAnt.getChemicals().getVariable( chem.getIndex() ), 
					"ant--" + chem.getName(), this.stepsCounter + "");
		});
		// ***** Set measures in Plot for WorldCase
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.stream().forEach( chem -> {
			dcExportData.addValue(	this.currentWCase.getChemicals().getVariable( chem.getIndex() ), 
					"case" + chem.getName(), this.stepsCounter + "");				
		});

		// ***** To Export Measures in data !!!
		sbExportData.append( "STEP [").append( this.stepsCounter ).append("]\n");
		sbExportData.append( this.currentAnt.getChemicals().toString() ).append( "*****\n" );
		sbExportData.append( this.currentPlant.getChemicals().toString() ).append( "*****\n" );
		sbExportData.append( this.currentWCase.getChemicals().toString() ).append( "*****\n" );
	}
	
	public int getStepsCounter() 
		{ return this.stepsCounter; }
	
	public void execution() {
		this.currentWSimulation.execution();
		this.currentPlant.cyclePlusPlus();	// Aging organism
		this.currentAnt.cyclePlusPlus();	// Aging organism
	}

}
