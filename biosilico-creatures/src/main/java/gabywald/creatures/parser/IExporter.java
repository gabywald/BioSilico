package gabywald.creatures.parser;

import java.io.IOException;
import java.util.List;

public interface IExporter {
	public void exportTo(List<Gene> genes, String filename) throws IOException;
}
