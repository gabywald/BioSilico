package gabywald.cellmodel.model;

import java.util.Random;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Chromosome extends SequencableEntity {
	private static int MAX_LENGTH = 1000;
	
	public Chromosome() {
		Random len = new Random();
		Random gen = new Random();
		this.sequence = "";
		for (int i = 0 ; i < len.nextInt(Chromosome.MAX_LENGTH) ; i++) {
			int choice = gen.nextInt(4);choice++;
			switch(choice) {
			case(1):sequence += "a";break;
			case(2):sequence += "c";break;
			case(3):sequence += "g";break;
			case(4):sequence += "t";break;
			}
		}
	}
	
	
}
