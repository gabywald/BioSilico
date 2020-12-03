package gabywald.creatures.genetics.simple;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class Creatures1Genome {
	private String name;
	private String pathOfFile;
	private List<Creatures1Gene> genome;
	
	public Creatures1Genome(String name, String path, List<Creatures1Gene> genome) {
		this.name = name;
		this.pathOfFile = path;
		this.genome = genome;
	}

	public String getName()
		{ return this.name; }

	public String getPathOfFile() 
		{ return this.pathOfFile; }

	public List<Creatures1Gene> getGenome() 
		{ return this.genome; }
	
}
