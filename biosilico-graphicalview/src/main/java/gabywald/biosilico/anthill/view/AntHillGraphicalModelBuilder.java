package gabywald.biosilico.anthill.view;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.helpers.AntHillExampleHelper;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IBuilder;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * Builder of {@link AntHillGraphicalModel}. 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalModelBuilder implements IBuilder<AntHillGraphicalModel> {
	/** World2D definition, dimension are default (1, 1). */
	private int height = 1, width = 1;
	/** List of Organism instances. */
	private List<Organism> organismsList = new ArrayList<Organism>();

	
	public AntHillGraphicalModelBuilder() { ; }
	
	@Override
	public AntHillGraphicalModel build() {
		World2D currentWSimulation	= new World2D( this.height, this.width );
		World2DCase startingWCase	= currentWSimulation.getWorldCase(0,  0);
		for (Agent agt : this.organismsList) 
			{ startingWCase.addAgent(agt); }
		
		// ***** Some initialisations in Starting World2DCase
		startingWCase.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		startingWCase.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		startingWCase.addAgent( new EnergySource() );
		startingWCase.addAgent( new BlackHole() );
		
		AntHillGraphicalModel toReturn = new AntHillGraphicalModel();
		toReturn.setWorld( currentWSimulation );
		toReturn.setOrganisms( this.organismsList );
		
		return toReturn;
	}
	
	/**
	 * Take 2D dimensions of expected world. 
	 * <br/>If needed recompute height and width between 1 and 10. 
	 * @param height (int) 
	 * @param width (int)
	 * @return Current instance of AntHillGraphicalModelBuilder. 
	 */
	public AntHillGraphicalModelBuilder setWorldDimension(int height, int width) {
		this.height	= Gene.obtainValue(1, 10, height);
		this.width	= Gene.obtainValue(1, 10, width);
		return this;
	}
	
	/**
	 * 
	 * @return Current instance of AntHillGraphicalModelBuilder. 
	 */
	public AntHillGraphicalModelBuilder addPlant(String name) {
		Plant currentPlant = new Plant();
		currentPlant.setRank( "Rank Test" );
		currentPlant.setNameCommon( "{" + name + "}PlantStartTest" );
		currentPlant.setNameBiosilico( "{" + name + "} AntHill Plant Example" );
		currentPlant.setDivision( "TESTS") ;
		currentPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		this.organismsList.add( currentPlant );
		return this;
	}
	
	/**
	 * 
	 * @return Current instance of AntHillGraphicalModelBuilder. 
	 */
	public AntHillGraphicalModelBuilder addAnt(String name) {
		Ant currentAnt = new Ant();
		currentAnt.setRank( "Rank Test" );
		currentAnt.setNameCommon( "{" + name + "}AntStartTest" );
		currentAnt.setNameBiosilico( "{" + name + "} AntHill Ant Example" );
		currentAnt.setDivision( "TESTS") ;
		currentAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		this.organismsList.add( currentAnt );
		return this;
	}
}
