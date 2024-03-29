package gabywald.biosilico.anthill.view;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.model.enums.SomeChemicals;

/**
 * Runner implementation for AntHill Modelisation. 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalRunner implements Runnable {
	
	private boolean isActive						= false;
	
	private AntHillGraphicalFrame localController	= null;
	
	public AntHillGraphicalRunner(AntHillGraphicalFrame ahgf) {
		this.localController = ahgf;
	}

	@Override
	public void run() {
		while (this.isActive) {
			this.standardRun();
		}
	}
	
	public void setActive(boolean isActive) { this.isActive = isActive; }

	public static final int BASE_COMPUTATION = 50;
	
	public static final List<SomeChemicals> TO_FILTER_IN_INT_Organism = new ArrayList<SomeChemicals>();
	static { // TODO review / remake this List (not for all elts). 
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.ENERGY_HEAT);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.ENERGY_SOLAR);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.DIOXYGEN);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.CARBON_DIOXYDE);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.WATER);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.GLUCOSE);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_Organism.add(SomeChemicals.STARCH);
		// AntHillGraphicalRunner.TO_FILTER_IN_INT.add(StateType.AGING);
	}
	
	public static final List<SomeChemicals> TO_FILTER_IN_INT_WorldCase = new ArrayList<SomeChemicals>();
	static { // TODO review / remake this List (not for all elts). 
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.add(SomeChemicals.ENERGY_HEAT);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.add(SomeChemicals.ENERGY_SOLAR);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.add(SomeChemicals.DIOXYGEN);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.add(SomeChemicals.CARBON_DIOXYDE);
		AntHillGraphicalRunner.TO_FILTER_IN_INT_WorldCase.add(SomeChemicals.WATER);
		// AntHillGraphicalRunner.TO_FILTER_IN_INT.add(StateType.AGING);
	}
	
	public void standardRun() {
		for (int j = 0 ; (this.isActive) && (j < 5) ; j++) {
			for (int i = j*AntHillGraphicalRunner.BASE_COMPUTATION ; 
					(this.isActive) 
					&& 
					(i < (j*AntHillGraphicalRunner.BASE_COMPUTATION + AntHillGraphicalRunner.BASE_COMPUTATION + 1) ) ; 
					j++) {
				this.localController.getLocalModel().oneStep(); 
				this.localController.setSteps( this.localController.getLocalModel().getStepsCounter() );
				
				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
}
