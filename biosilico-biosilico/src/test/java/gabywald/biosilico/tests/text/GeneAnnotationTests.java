package gabywald.biosilico.tests.text;

import java.util.List;

import gabywald.biosilico.data.FileGene;
import gabywald.biosilico.genetics.GeneAnnotation;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneAnnotationTests {
	
	public static void main(String[] args) {
		FileGene test				= new FileGene("biosilico/fileModelsDocs/GattacaGeneModel.txt");
		List<GeneAnnotation> tmp	= test.getGenesAnnotated();
		System.out.println(tmp.toString());
	}
	
}
