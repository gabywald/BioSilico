package gabywald.biosilico.tests.text;

import gabywald.biosilico.data.FileGene;
import gabywald.biosilico.structures.GeneAnnotationListe;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneAnnotationTests {
	
	public static void main(String[] args) {
		FileGene test			= new FileGene("biosilico/fileModelsDocs/GattacaGeneModel.txt");
		GeneAnnotationListe tmp	= test.getGenesAnnotated();
		System.out.println(tmp.toString());
	}
	
}
