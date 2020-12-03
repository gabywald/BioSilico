package gabywald.creatures.genetics.simple;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class CreaturesGenome {
	private String name;
	private String pathOfFile;
	private List<ICreaturesGene> genome;
	
	public CreaturesGenome(String name, String path, List<ICreaturesGene> genome) {
		this.name		= name;
		this.pathOfFile	= path;
		this.genome		= genome;
	}

	public String getName()
		{ return this.name; }

	public String getPathOfFile() 
		{ return this.pathOfFile; }

	public List<ICreaturesGene> getGenome() 
		{ return this.genome; }
	
}
