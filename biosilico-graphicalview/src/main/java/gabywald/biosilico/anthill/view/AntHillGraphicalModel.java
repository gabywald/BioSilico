package gabywald.biosilico.anthill.view;

import java.util.List;

import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalModel {

	private World2D currentWSimulation		= null;
	private List<Organism> organismsList	= null;
	private DataCollector dcExportData		= null;
	private StringBuilder sbExportData		= null;
	
	private int stepsCounter				= 0;
	
	public AntHillGraphicalModel() {
		// ***** To visualise / export data of simulation. 
		this.dcExportData = new DataCollector("Generic AntHill Analysis", "Steps", "Values of Chemicals");
		this.sbExportData = new StringBuilder();
	}
	
	public List<Organism> getOrganisms()	
		{ return this.organismsList; }
	
	public List<World2DCase> getLocations() 
		{ return this.currentWSimulation.getWorldCases(); }
	
	public DataCollector getDataCollector()	
		{ return this.dcExportData; }
	
	public void oneStep() {
		this.execution();
		this.stepsCounter++;
		
		// ***** Set measures in Plot *****
		// ***** Set measures in Plot for Organism's
		this.organismsList.stream().forEach( orga -> {
			AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.stream().forEach( chem -> {
				dcExportData.addValue(	orga.getChemicals().getVariable( chem.getIndex() ), 
										orga.getCommonName() + chem.getName(), this.stepsCounter + "");
			});
		});
		// ***** Set measures in Plot for WorldCase
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.stream().forEach( chem -> {
			dcExportData.addValue(this.currentWSimulation.getWorldCase(0, 0).getChemicals().getVariable( chem.getIndex() ), 
					"case" + chem.getName(), this.stepsCounter + "");				
		});

		// ***** To Export Measures in data !!!
		sbExportData.append( "STEP [").append( this.stepsCounter ).append("]")
					.append("(").append( this.organismsList.size() ).append(")")
					.append("\n");
		for (Agent agt : this.organismsList) 
			{ sbExportData.append( agt.getChemicals().toString() ).append( "*****\n" ); }
		sbExportData.append( this.currentWSimulation.getWorldCase(0, 0).getChemicals().toString() ).append( "*****\n" );
	}
	
	public int getStepsCounter() 
		{ return this.stepsCounter; }
	
	public void execution() { 
		this.currentWSimulation.execution();
		// TODO NOTE create some "aging gene", for some specific periods (0, 1...) for example of Brain Lobe creations !
		this.organismsList.stream().forEach( orga -> {
			orga.cyclePlusPlus();	// Aging organism
		});
	}

	public void setWorld(World2D wSimulation) 
		{ this.currentWSimulation = wSimulation; }

	public void setOrganisms(List<Organism> organisms) 
		{ this.organismsList = organisms; }
	
}
