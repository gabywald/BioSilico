package gabywald.creatures.parser;

import java.io.IOException;
import java.util.List;

/**
 * Interface for Exportation. 
 * @author Gabriel Chandesris (2026)
 * @deprecated (tests of reimplementation)
 */
public interface IExporter {
	public void exportTo(List<Gene> genes, String filename) throws IOException;
}
