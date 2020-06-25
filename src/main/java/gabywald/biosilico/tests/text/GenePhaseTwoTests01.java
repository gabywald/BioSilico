package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.GenePhaseTwo;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GenePhaseTwoTests01 {

	public static void main (String argv[]) {
		String test = "Ceci est un test !";
		
		GenePhaseTwo test01 = new GenePhaseTwo(test);
		String transcription = test01.getTranscription();
		String sample = test01.getSampleTranscription();
		
		System.out.println(test);
		System.out.println(transcription);
		System.out.println(sample);
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(transcription,0));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(transcription,1));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(transcription,2));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(transcription,3));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(transcription,4));
		System.out.println("==========");
		
		
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(sample,0));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(sample,1));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(sample,2));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(sample,3));
		System.out.println("==========");
		System.out.println(GenePhaseTwo.translation(sample,4));
		System.out.println("==========");
	}
}
