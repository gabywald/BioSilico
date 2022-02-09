package gabywald.biosilico.anthill.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.biosilico.anthill.helpers.AntHillExampleHelper;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * To regroup model for graphical representation. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalModel {
	/** Unique instance of this model. */
	private static AntHillGraphicalModel instance = null;

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
	public static AntHillGraphicalModel getInstance() {
		if (AntHillGraphicalModel.instance == null) 
			{ AntHillGraphicalModel.instance = new AntHillGraphicalModel(); }
		return AntHillGraphicalModel.instance;
	}
	
	private AntHillGraphicalModel() {
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
		
		// this.currentWCase.addAgent( new Condensator() );
		
		/* ***** To visualise / export data of simulation. ***** */
		this.dcExportData = new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		this.sbExportData = new StringBuilder();
	}
	
	
	public List<Organism> getOrganisms()	{ 
		List<Organism> orgs = new ArrayList<>();
		orgs.add(this.currentAnt);
		orgs.add(this.currentPlant);
		return orgs;
	}
	
	public List<World2DCase> getLocations() 
		{ return this.currentWSimulation.getWorldCases(); }
	
	public DataCollector getDataCollector()	
		{ return this.dcExportData; }
	
	public static final int BASE_COMPUTATION = 50;
	
	public static final List<SomeChemicals> TO_FILTER_IN_INT = new ArrayList<SomeChemicals>();
	static { // TODO review / remake this List (not for all elts). 
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_HEAT);
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_SOLAR);
		TO_FILTER_IN_INT.add(SomeChemicals.DIOXYGEN);
		TO_FILTER_IN_INT.add(SomeChemicals.CARBON_DIOXYDE);
		TO_FILTER_IN_INT.add(SomeChemicals.WATER);
		TO_FILTER_IN_INT.add(SomeChemicals.GLUCOSE);
		TO_FILTER_IN_INT.add(SomeChemicals.STARCH);
		// TO_FILTER_IN_INT.add(StateType.AGING);
	}
	
	public void oneStep() {
		this.currentWSimulation.execution();
		this.currentPlant.cyclePlusPlus();	// Aging organism
		this.currentAnt.cyclePlusPlus();	// Aging organism
		this.stepsCounter++;
		int aging = this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex());
		// int aging = this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex());
		dcExportData.addValue(	aging, StateType.AGING.name(), this.stepsCounter + "" );
		TO_FILTER_IN_INT.stream().forEach( chem -> {
			dcExportData.addValue(	this.currentPlant.getChemicals().getVariable( chem.getIndex() ), 
					"plant" + chem.getName(), this.stepsCounter + "");
			dcExportData.addValue(	this.currentAnt.getChemicals().getVariable( chem.getIndex() ), 
					"ant" + chem.getName(), this.stepsCounter + "");
			dcExportData.addValue(	this.currentWCase.getChemicals().getVariable( chem.getIndex() ), 
					"case" + chem.getName(), this.stepsCounter + "");				
		});

		sbExportData.append( "STEP [")
		.append( this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex()) )
		.append("][")
		.append( this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
		.append("]\n");
		sbExportData.append( this.currentAnt.getChemicals().toString() ).append( "*****\n" );
		sbExportData.append( this.currentPlant.getChemicals().toString() ).append( "*****\n" );
		sbExportData.append( this.currentWCase.getChemicals().toString() ).append( "*****\n" );
	}
	
	// TODO use this in controller (or externalize simulation part !)
	public void justArun() {
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				this.currentWSimulation.execution();
				this.currentPlant.cyclePlusPlus();	// Aging organism
				this.currentAnt.cyclePlusPlus();	// Aging organism
				int steps = i;
				int aging = this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				// int aging = this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				dcExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					dcExportData.addValue(	this.currentPlant.getChemicals().getVariable( chem.getIndex() ), 
							"plant" + chem.getName(), steps + "");
					dcExportData.addValue(	this.currentAnt.getChemicals().getVariable( chem.getIndex() ), 
							"ant" + chem.getName(), steps + "");
					dcExportData.addValue(	this.currentWCase.getChemicals().getVariable( chem.getIndex() ), 
							"case" + chem.getName(), steps + "");				
				});

				sbExportData.append( "STEP [")
				.append( this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex()) )
				.append("][")
				.append( this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
				.append("]\n");
				sbExportData.append( this.currentAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( this.currentPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( this.currentWCase.getChemicals().toString() ).append( "*****\n" );

				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
			});
			// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
			this.currentWCase.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
			this.currentWCase.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 	100);

			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		});
	}

	public int getStepsCounter() 
		{ return this.stepsCounter; }
}
