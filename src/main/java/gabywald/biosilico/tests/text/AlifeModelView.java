package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.global.structures.ObservableObject;
import gabywald.global.view.text.Terminal;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class AlifeModelView implements Observer {
	private static AlifeModelView instance = null;
	
	private AlifeModelView(Organism orga) 
		{ orga.addObserver(this); }
	
	private static AlifeModelView getAlifeModelView(Organism orga) {
		if (AlifeModelView.instance == null) 
			{ AlifeModelView.instance = new AlifeModelView(orga); }
		return AlifeModelView.instance;
	}
	
	public static void main(String[] args) {
		Terminal.ecrireStringln("Launch ALIFE Model Text View");
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, 
										 false, 
										 false, 
										 false, 
										 0, 0, 0, 0,
										 1, 
										 100));
		basic_genome.addGene(
				new InitialConcentration(false, 
										 false, 
										 false, 
										 true, 
										 0, 0, 0, 0,
										 2, 
										 100));
		basic_genome.addGene(
				new InitialConcentration(false, 
										 false, 
										 false, 
										 true, 
										 0, 0, 0, 0,
										 3, 
										 100));
		basic_genome.addGene(
				new InitialConcentration(false, 
										 false, 
										 false, 
										 false, 
										 0, 0, 0, 0,
										 4, 
										 100));
		basic_genome.addGene(
				new InitialConcentration(false, 
										 false, 
										 false, 
										 true, 
										 0, 0, 0, 0,
										 5, 
										 100));
		
		basic_genome.addGene(new BiochemicalReaction(false, 
				false, 
				false, 
				true, 
				0, 10, 0, 0,
				1, 10, 
				2, 10, 
				6, 5, 
				7, 5, 
				3));
		basic_genome.addGene(new BiochemicalReaction(false, 
													false, 
													false, 
													true, 
													0, 5, 0, 0,
													2, 10, 
													3, 10, 
													6, 5, 
													7, 5, 
													3));
		basic_genome.addGene(new BiochemicalReaction(false, 
				false, 
				false, 
				true, 
				1, 3, 0, 0,
				6, 10, 
				0, 0, 
				8, 5, 
				0, 0, 
				3));
		basic_genome.addGene(new BiochemicalReaction(false, 
				false, 
				false, 
				true, 
				2, 5, 0, 0,
				2, 1, 
				0, 0, 
				4, 5, 
				0, 0, 
				3));
		
		basic_genome.addGene(new BiochemicalReaction(false, 
				false, 
				false, 
				true, 
				0, 1000, 0, 0,
				8, 1, 
				4, 1, 
				10, 5, 
				11, 5, 
				10));
		
		basic_genome.addGene(new BiochemicalReaction(false, 
				false, 
				false, 
				true, 
				0, 2000, 0, 0,
				10, 2, 
				11, 2, 
				8, 1, 
				4, 1, 
				20));
		
		Organism test = new Organism(basic_genome);
		AlifeModelView.getAlifeModelView(test);
		Thread testThread = new Thread(test);
		testThread.start();
	}

	public void update(Observable arg0, Object arg1) {
		ObservableObject obj_change = (ObservableObject)arg0;
		Terminal.ecrireStringln(obj_change.getState());
	}
}
