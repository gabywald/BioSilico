package gabywald.biosilico.anthill.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.biosilico.anthill.helpers.AntHillExampleHelper;
import gabywald.biosilico.anthill.view.AntHillGraphicalFrame;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * Launcher of AntHill (Graphical) View. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalLauncher {
	/** Unique instance of this launcher. */
	private static AntHillGraphicalLauncher instance = null;
	/** View of the controller. */
	private AntHillGraphicalFrame localView;
	
	private World2D currentWSimulation	= null;
	private World2DCase currentCase		= null;
	private Plant currentPlant			= null;
	private Ant currentAnt				= null;
	private DataCollector dcExportData	= null;
	private StringBuilder sbExportData	= null;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (AntHillGraphicalLauncher)
	 */
	public static AntHillGraphicalLauncher getInstance() {
		if (AntHillGraphicalLauncher.instance == null) 
			{ AntHillGraphicalLauncher.instance = new AntHillGraphicalLauncher(); }
		return AntHillGraphicalLauncher.instance;
	}
	
	public void setView(AntHillGraphicalFrame view) { this.localView = view; }
	public AntHillGraphicalFrame getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		
		/* ***** Loading Data to start simulation ***** */
		Ant testAnt = new Ant();

		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");

		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );

		Plant testPlant = new Plant();

		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");

		testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );

		// ***** test with a World and WorldCase
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);

		testAnt.setCurrentWorldCase( wc );
		testPlant.setCurrentWorldCase( wc );
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		wc.addAgent( new EnergySource() );
		wc.addAgent( new BlackHole() );

		/* ***** Loading View ***** */
		AntHillGraphicalLauncher controller = AntHillGraphicalLauncher.getInstance();
		controller.currentWSimulation	= w; // Put the current instance of Simulation World here !!
		controller.currentCase			= wc;
		controller.currentPlant			= testPlant;
		controller.currentAnt			= testAnt;
		controller.dcExportData = new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		controller.sbExportData = new StringBuilder();
		controller.setView(AntHillGraphicalFrame.getInstance( controller.dcExportData ));
	}
	
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
					dcExportData.addValue(	this.currentCase.getChemicals().getVariable( chem.getIndex() ), 
							"case" + chem.getName(), steps + "");				
				});

				sbExportData.append( "STEP [")
				.append( this.currentAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
				.append("][")
				.append( this.currentPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
				.append("]\n");
				sbExportData.append( this.currentAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( this.currentPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( this.currentCase.getChemicals().toString() ).append( "*****\n" );

				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
			});
			// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
			this.currentCase.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
			this.currentCase.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 	100);

			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		});
	}

}
