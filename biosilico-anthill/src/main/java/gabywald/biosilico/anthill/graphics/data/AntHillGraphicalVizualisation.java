package gabywald.biosilico.anthill.graphics.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalVizualisation {

	public static final int BASE_COMPUTATION = 50;
	public static final List<SomeChemicals> TO_FILTER_IN_INT = new ArrayList<SomeChemicals>();
	static {
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_HEAT);
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_SOLAR);
		TO_FILTER_IN_INT.add(SomeChemicals.DIOXYGEN);
		TO_FILTER_IN_INT.add(SomeChemicals.CARBON_DIOXYDE);
		TO_FILTER_IN_INT.add(SomeChemicals.WATER);
		TO_FILTER_IN_INT.add(SomeChemicals.GLUCOSE);
		TO_FILTER_IN_INT.add(SomeChemicals.STARCH);
		// TO_FILTER_IN_INT.add(StateType.AGING);
	}

	public static void main(String[] args) {

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

		// HERE NEW GENES ARE ALREADY ADDED (see above)

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

		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();

		// sbExportData.showJFrameWithChartPanel();
		sbExportData.showChartFrame();

		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus();	// Aging organism
				testAnt.cyclePlusPlus();	// Aging organism
				int steps = i;
				int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
							"plant" + chem.getName(), steps + "");
					sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
							"ant" + chem.getName(), steps + "");
					sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
							"wc" + chem.getName(), steps + "");				
				});

				sbExportDataSTR.append( "STEP [")
				.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
				.append("][")
				.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
				.append("]\n");
				sbExportDataSTR.append( testAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( testPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( wc.getChemicals().toString() ).append( "*****\n" );

				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
			});
			// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
			wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
			wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);

			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		});

	}

}
