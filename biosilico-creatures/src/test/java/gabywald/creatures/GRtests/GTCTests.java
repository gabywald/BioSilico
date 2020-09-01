package gabywald.creatures.GRtests;

//import java.util.ArrayList;
//import java.util.List;
//
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.geneticReader.GeneticFileContentOld;
import gabywald.creatures.genetics.CreatureGene;
import gabywald.creatures.genetics.CreatureGeneFactory;
import gabywald.creatures.model.UnsignedByte;
//import gabywald.creatures.geneticReader.UnsignedByte;
//import gabywald.creatures.genetics.AppearanceGene;
//import gabywald.creatures.genetics.ChemicalReactionGene;
//import gabywald.creatures.genetics.CreatureGene;
//import gabywald.creatures.genetics.CreatureGeneFactory;
//import gabywald.creatures.genetics.EmitterGene;
//import gabywald.creatures.genetics.GaitGene;
//import gabywald.creatures.genetics.GenusGene;
//import gabywald.creatures.genetics.HalfLivesGene;
//import gabywald.creatures.genetics.InitialConcentrationGene;
//import gabywald.creatures.genetics.InstinctGene;
//import gabywald.creatures.genetics.PigmentBleedingGene;
//import gabywald.creatures.genetics.PigmentGene;
//import gabywald.creatures.genetics.PoseGene;
//import gabywald.creatures.genetics.ReceptorGene;
//import gabywald.creatures.genetics.StimulusGene;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 * TODO review and replace "System.out.println(" with "Logger.printlnLog(LoggerLevel.LL_NONE, "
 */
public class GTCTests {
	
	@Test
	public void testGTC001() {
		// final String dir = System.getProperty("user.dir");
        // System.out.println("current dir = " + dir);
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC002() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/dad1.gen");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				// int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("."); }
			} /** END "for (int i = 0 ; i < gtc.length() ; i++)" */
			System.out.println();
		} /** END "if (gtc.isReaded())" */
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC003() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/mum1.gen");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				// int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("."); }
			} /** END "for (int i = 0 ; i < gtc.length() ; i++)" */
			System.out.println();
		} /** END "if (gtc.isReaded())" */
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC004() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/Gren.gen");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				// int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("."); }
			} /** END "for (int i = 0 ; i < gtc.length() ; i++)" */
			System.out.println();
		} /** END "if (gtc.isReaded())" */
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
//	public void testGTC005() {
//		GeneticFileContent gtc = new GeneticFileContent("resources/creaturesOriginals/dad1.gen");
//		if (gtc.isReaded()) {
//			
//			List<CreatureGene> genome = new ArrayList<CreatureGene>();
//			
//			String dataPrevious = new String("");
//			String dataCurrents = new String("");
//			
//			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
//			for (int i = 0 ; i < gtc.length() ; i++) {
//				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
//				char charAsCC = gtc.charAt(i);
//				dataCurrents += charAsCC;
//				/** NOTE Creatures 1 GEN files begins directly, 
//				 * C2 / C3 begin with 'dna[23]'. */
//				if ( (dataCurrents.endsWith("gene")) || (dataCurrents.endsWith("gend")) ) { 
//					dataPrevious = dataCurrents.substring(0, dataCurrents.length() - 4); 
//					dataCurrents = dataCurrents.substring(dataCurrents.length() - 4, dataCurrents.length());
//					if ( ( ! dataPrevious.equals("") ) && ( ! dataPrevious.startsWith("gend") ) ) { 
//						// System.out.println("[" + dataPrevious + "]" ); 
//						int index = 4;
//						int type  = dataPrevious.charAt(index++); // 4
//						int subt  = dataPrevious.charAt(index++); // 5
//						int iden  = dataPrevious.charAt(index++); // 6
//						int numg  = dataPrevious.charAt(index++); // 7
//						int agee  = dataPrevious.charAt(index++); // 8
//						int flags = dataPrevious.charAt(index++); // 9
//						/** For Creatures 2 and Creatures 3. */
//						// int muta  = dataPrevious.charAt(index++);  // 10 // mutability
//						/** For Creature 3 / Village. */
//						// int varia = dataPrevious.charAt(index++); // 11 // variant (turn on / off among species)
//						
////						System.out.println("\t[" + type + "]\t[" + subt + "]\t[" 
////												+ iden + "]\t[" + numg + "]\t[" 
////												+ agee + "]\t[" + flags + "]" );
//						
//						// System.out.println("\t[" + muta + "]" );
//						
//						String toTest = new String("");
////						for (int j = 0 ; j < 10 ; j++) 
////							{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
////						System.out.println( "\t" + toTest );
//						
//						int currentSize			= genome.size();
//						
//						switch(type) {
//						case(0):
//							switch(subt) {
//							case(0):System.out.println("\t Brain Lobe Gene");	
//								System.out.println("\t\t(" + dataPrevious.length() + ") - (" + index + ")");
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
//								System.out.println( "\t\t" + toTest );
//								break;
//							case(1):System.out.println("\t Brain Organ Gene");	
//								System.out.println("\t\t(" + dataPrevious.length() + ") - (" + index + ")");
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
//								System.out.println( "\t\t" + toTest );
//								break;
//							case(2):System.out.println("\t Brain Tract Gene");
//								System.out.println("\t\t(" + dataPrevious.length() + ") - (" + index + ")");
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
//								System.out.println( "\t\t" + toTest );
//								break;
//							default:System.out.println("\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
//							} /** END "switch(subt)" */
//							break;
//						case(1):
//							switch(subt) {
//							case(0):// System.out.println("\t Receptor Gene");	
//								List<UnsignedByte> recDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ recDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								ReceptorGene rec = new ReceptorGene(recDatas);
//								CreatureGeneFactory.setHeaderInformations(rec, iden, numg, agee, flags);
//								genome.add(rec);
//								break;
//							case(1):// System.out.println("\t Emitter Gene");		
//								List<UnsignedByte> emiDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ emiDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								EmitterGene emi = new EmitterGene(emiDatas);
//								CreatureGeneFactory.setHeaderInformations(emi, iden, numg, agee, flags);
//								genome.add(emi);
//								break;
//							case(2):// System.out.println("\t Chemical R. Gene");	
//								List<UnsignedByte> chrDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ chrDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								ChemicalReactionGene chr = new ChemicalReactionGene(chrDatas);
//								CreatureGeneFactory.setHeaderInformations(chr, iden, numg, agee, flags);
//								genome.add(chr);
//								break;
//							case(3):// System.out.println("\t Half-Lives Gene");	
//								List<UnsignedByte> halfLives = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ halfLives.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								// for (int k = 0 ; k < halfLives.size() ; k++) 
//								// 	{ System.out.println( "\t\t" + k + " / " + halfLives.size() + " :: [" + halfLives.get(k).getValue() + "]" ); }
//								HalfLivesGene haf = new HalfLivesGene(halfLives);
//								CreatureGeneFactory.setHeaderInformations(haf, iden, numg, agee, flags);
//								genome.add(haf);
//							break;
//							case(4):// System.out.println("\t Init. Conc. Gene");	
//								if (dataPrevious.length() < 12) {
//									System.out.println("\t Init. Conc. Gene ?????");	
//									System.out.println("\t\t(" + dataPrevious.length() + ") - (" + index + ")");
//									for (int j = index ; j < dataPrevious.length() ; j++) 
//										{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
//									System.out.println( "\t\t" + toTest );
//								} else {
//									int chemic = (int)dataPrevious.charAt(index++);
//									int amount = (int)dataPrevious.charAt(index++);
//									InitialConcentrationGene icg = new InitialConcentrationGene(chemic, amount);
//									CreatureGeneFactory.setHeaderInformations(icg, iden, numg, agee, flags);
//									genome.add(icg);
//								} /** END "if (dataPrevious.length() < 12)" */
//								break;
//							default:System.out.println("\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
//							} /** END "switch(subt)" */
//							break;
//						case(2):
//							switch(subt) {
//							case(0):// System.out.println("\t Stimulus Gene");		
//								List<UnsignedByte> stgDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ stgDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
////								for (int k = 0 ; k < stgDatas.size() ; k++) 
////									{ System.out.println( "\t\t" + (k+1) + " / " + stgDatas.size() + " :: [" + stgDatas.get(k).getValue() + "]" ); }
//								StimulusGene stg = new StimulusGene(stgDatas);
//								CreatureGeneFactory.setHeaderInformations(stg, iden, numg, agee, flags);
//								genome.add(stg);
//								break;
//							case(1):// System.out.println("\t Genus Gene");		
//								int speci = (int)dataPrevious.charAt(index++);
//								String moth = dataPrevious.substring(index + 0, index + 4);
//								String fath = dataPrevious.substring(index + 4, index + 8);
//								// System.out.println("\t{" + moth + "}\t{" + fath + "}");
//								GenusGene geg = new GenusGene(speci, moth, fath);
//								CreatureGeneFactory.setHeaderInformations(geg, iden, numg, agee, flags);
//								genome.add(geg);
//							break;
//							case(2):// System.out.println("\t Appearance Gene");	
//								int body = (int)dataPrevious.charAt(index++);
//								int spri = (int)dataPrevious.charAt(index++);
//								AppearanceGene apg = new AppearanceGene(body, spri);
//								CreatureGeneFactory.setHeaderInformations(apg, iden, numg, agee, flags);
//								genome.add(apg);
//								break;
//							case(3):// System.out.println("\t Pose Gene");	
//								int kind = (int)dataPrevious.charAt(index++);
//								String info = new String(""); 
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ info += dataPrevious.charAt(j); }
//								// System.out.println( "\t\t pose Gene info [" + info + "]" );
//								PoseGene pog = new PoseGene(kind, info);
//								CreatureGeneFactory.setHeaderInformations(pog, iden, numg, agee, flags);
//								genome.add(pog);
//								break;
//							case(4):// System.out.println("\t Gait Gene");			
//								List<UnsignedByte> gagDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ gagDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								GaitGene gag = new GaitGene(gagDatas);
//								CreatureGeneFactory.setHeaderInformations(gag, iden, numg, agee, flags);
//								genome.add(gag);
//								break;
//							case(5):// System.out.println("\t Instinct Gene");
//								List<UnsignedByte> itgDatas = new ArrayList<UnsignedByte>();
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ itgDatas.add(new UnsignedByte((int)dataPrevious.charAt(j))); }
//								InstinctGene itg = new InstinctGene(itgDatas);
//								CreatureGeneFactory.setHeaderInformations(itg, iden, numg, agee, flags);
//								genome.add(itg);
//								break;
//							case(6):// System.out.println("\t Pigment Gene");		
//								int colo = (int)dataPrevious.charAt(index++);
//								int amou = (int)dataPrevious.charAt(index++);
//								PigmentGene pig = new PigmentGene(colo, amou);
//								CreatureGeneFactory.setHeaderInformations(pig, iden, numg, agee, flags);
//								genome.add(pig);
//								break;
//							case(7):// System.out.println("\t Pigment bl. Gene");	
//								int rota = (int)dataPrevious.charAt(index++);
//								int swap = (int)dataPrevious.charAt(index++);
//								PigmentBleedingGene pbg = new PigmentBleedingGene(rota, swap);
//								CreatureGeneFactory.setHeaderInformations(pbg, iden, numg, agee, flags);
//								genome.add(pbg);
//								break;
//							default:System.out.println("\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
//							} /** END "switch(subt)" */
//							break;
//						case(3):
//							switch(subt) {
//							case(0):System.out.println("\t Organ Gene");		
//								System.out.println("\t\t(" + dataPrevious.length() + ") - (" + index + ")");
//								for (int j = index ; j < dataPrevious.length() ; j++) 
//									{ toTest += "[" + (int)dataPrevious.charAt(j) + "]\t"; }
//								System.out.println( "\t\t" + toTest );
//								break;
//							default:System.out.println("\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
//							} /** END "switch(subt)" */
//							break;
//						default:System.out.println("\t UNKNOWN TYPE ! [" + type + "]\t[" + subt + "]");
//						} /** END "switch(type)" */
//						
//						if (currentSize == genome.size()) { 
//							System.out.println("[" + dataPrevious + "]" );
//							System.out.println("\t[" + type + "]\t[" + subt + "]\t[" 
//									+ iden + "]\t[" + numg + "]\t[" 
//									+ agee + "]\t[" + flags + "]" );
//							System.out.println("-- -- -- -- --\n");
//						} /** END "if (currentSize != genome.size())" */
//						
//					} /** END "if ( ! dataPrevious.equals("") )" */
//				}
//			} /** END "for (int i = 0 ; i < gtc.length() ; i++)" */
//			
//			System.out.println("Genome has [" + genome.size() + "] genes !");
//			
//			System.out.println();
//		} /** END "if (gtc.isReaded())" */
//		Assertions.assertEquals(true, gtc.isReaded());
//	}
	
	
//	public void testGTC042() {
//		String zero = Integer.toBinaryString( 0);System.out.println(" 0 => '" + zero + "'");
//		String ones = Integer.toBinaryString( 1);System.out.println(" 1 => '" + ones + "'");
//		String thre = Integer.toBinaryString( 3);System.out.println(" 3 => '" + thre + "'");
//		String four = Integer.toBinaryString( 4);System.out.println(" 4 => '" + four + "'");
//		String seve = Integer.toBinaryString( 7);System.out.println(" 7 => '" + seve + "'");
//		String fift = Integer.toBinaryString(15);System.out.println("15 => '" + fift + "'");
//		String tthr = Integer.toBinaryString(23);System.out.println("23 => '" + tthr + "'");
//		Assertions.assertEquals(true, true);
//	}
	
//	public void testGTC043() {
//		for (int i = 0 ; i < 256 ; i++) 
//			{ System.out.println(CreatureGene.binaryCompleted(i, 8, '0')); }
//	}
	
	@Test
	public void testGTC001bis() {
		// final String dir = System.getProperty("user.dir");
        // System.out.println("current dir = " + dir);
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertEquals(true, gtc.isReadable());
	}
	
	@Test
	public void testGTC002bis() {
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		while (gtc.isReadable()) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gtc.isReadable()))" */
		Assertions.assertEquals(true, !gtc.isReadable());
	}
	
	@Test
	public void testGTC003bis() {
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		while (gtc.isReadable()) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gtc.isReadable()))" 
		Assertions.assertEquals(true, !gtc.isReadable());
	}
	
	@Test
	public void testGTC004bis() {
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/Gren.gen");
		while (gtc.isReadable()) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gtc.isReadable()))" 
		Assertions.assertEquals(true, !gtc.isReadable());
	}
	
	@Test
	public void testGTC005bis() {
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		String previous = new String("");
		String nextNext = new String("");
		while ( (gtc.isReadable()) && ( ! nextNext.equals("gend")) ) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
				nextNext += tmpChar;
				if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) ) {
					previous = new String("");
					previous = nextNext.substring(0, nextNext.length()-4);
					nextNext = nextNext.substring(nextNext.length()-4, nextNext.length());
					if ( ( ! previous.equals("")) && ( ! previous.startsWith("gend")) ) 
						{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- {{" + previous + "}} :: {{" + nextNext + "}}"); } 
					else 
						{ Logger.printlnLog(LoggerLevel.LL_WARNING, "-- {" + previous + "} :: {" + nextNext + "}"); }
				}
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} // END "while (gtc.isReadable()))" 
		Assertions.assertEquals(true, (!gtc.isReadable()) || (gtc.isReadable()));
	}
	
	@Test
	public void testGTC006() {
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		String previous = new String("");
		String nextNext = new String("");
		while ( (gtc.isReadable()) && (!previous.startsWith("gend")) ) {
			char tmpChar = gtc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
				nextNext += tmpChar;
				if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) ) {
					previous = nextNext.substring(0, nextNext.length()-4);
					nextNext = nextNext.substring(nextNext.length()-4, nextNext.length());
					if ( ( ! previous.equals("")) && ( ! previous.startsWith("gend")) ) {
						// Logger.printlnLog(LoggerLevel.LL_INFO, "-- {{" + previous + "}}");
						CreatureGene toRecord = CreatureGeneFactory.readGene(previous, new UnsignedByte(1));
						// if (toRecord != null) 
						// 	{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- CreatureGene OK ! [" + previous + "]"); }
						// else
						// 	{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
						if (toRecord == null) 
							{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
					} else 
						{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- {" + nextNext + "}"); }
				}
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} // END "while (gtc.isReadable()))" 
		Assertions.assertEquals(true, (!gtc.isReadable()) || (gtc.isReadable()));
	}
	
	@Test
	public void testGTC007() {
		String genomeFile = "creatures/creaturesOriginals/dad1.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
	
	@Test
	public void testGTC008() {
		String genomeFile = "creatures/creaturesOriginals/mum1.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
	
	@Test
	public void testGTC009() {
		String genomeFile = "creatures/creaturesOriginals/Gren.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
}

