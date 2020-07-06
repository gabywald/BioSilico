package gabywald.biosilico.utils;

import java.util.List;

import gabywald.biosilico.genetics.GeneAnnotation;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class GeneAnnotationUtils {
	
	public GeneAnnotation getLast(List<GeneAnnotation> lga) {
		return (lga.size() > 0) ? lga.get(lga.size() - 1) : null;
	}
	
}
