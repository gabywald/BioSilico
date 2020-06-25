package gabywald.biosilico.tests.text;

import gabywald.biosilico.exceptions.GeneticTreeNodeException;
import gabywald.biosilico.structures.GeneticTreeNode;
import gabywald.biosilico.structures.GeneticTreeNodeListe;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneticTreeNodeTests01 {

	public static void main (String argv[]) {	
		
		String itemsGattaca = new String("abcdefghijklmnopqrstuvwxyz000111222333444555666777888999::''MM**");
		try {
			GeneticTreeNode testRootGattaca = new GeneticTreeNode("ACTG",itemsGattaca,true);
			
			// Terminal.ecrireStringln("root : "+testRootGattaca.getLevel()+":"+testRootGattaca.getLateral()+":"+testRootGattaca.cumulateLateral());
			Terminal.ecrireStringln("root : "+testRootGattaca.getNodes().length()+"\t"+testRootGattaca.getCodeValues());
			Terminal.sautDeLigne();
			Terminal.sautDeLigne();
			
			// GeneticTreeNodeListe level1bis = testRoot.getNodes();
			// for (int i = 0 ; i < level1bis.length() ; i++) { level1bis.getGeneticTreeNode(i).computeNode(); }
			
			GeneticTreeNodeListe level1 = testRootGattaca.getNodes();
			for (int i = 0 ; i < level1.length() ; i++) {
				GeneticTreeNode tmpLeave1 = level1.getGeneticTreeNode(i);
				// Terminal.ecrireStringln("level 1 : "+tmpLeave1.getLevel()+":"+tmpLeave1.getNodes().length());
				// Terminal.ecrireStringln("level 1("+tmpLeave1.getLevel()+") : "+tmpLeave1.getLateral()+"\t\t\t"+tmpLeave1.getCode());
				GeneticTreeNodeListe level2 = tmpLeave1.getNodes();
				for (int j = 0 ; j < level2.length() ; j++) {
					GeneticTreeNode tmpLeave2 = level2.getGeneticTreeNode(j);
					// Terminal.ecrireStringln("\tlevel 2("+tmpLeave2.getLevel()+"):*"+tmpLeave2.getNodes().length());
					// Terminal.ecrireStringln("\tlevel 2("+tmpLeave2.getLevel()+") : "+tmpLeave2.getLateral()+"\t\t"+tmpLeave2.getCode());
					GeneticTreeNodeListe level3 = tmpLeave2.getNodes();
					for (int k = 0 ; k < level3.length() ; k++) {
						GeneticTreeNode tmpLeave3 = level3.getGeneticTreeNode(k);
						// Terminal.ecrireStringln("\t\tlevel 3("+tmpLeave3.getLevel()+"):**"+tmpLeave3.getNodes().length());
						Terminal.ecrireStringln("\t\tlevel 3("+tmpLeave3.getLevel()+") : "+tmpLeave3.getLateral()+"\t"+tmpLeave3.getCode()+"\t"+tmpLeave3.getValue()+"\t"+tmpLeave3.isLeave()+"\t"+tmpLeave3.getOutput());
					}
				}
			}
			Terminal.sautDeLigne();
			
	
			
			GeneticTreeNodeListe testLeaves = testRootGattaca.getLeaves();
			for (int i = 0 ; i < testLeaves.length() ; i++) {
				GeneticTreeNode tmpLeave = testLeaves.getGeneticTreeNode(i);
				Terminal.ecrireString(""+tmpLeave.getValue());
				// Terminal.ecrireStringln("leave "+i+" : "+tmpLeave.getLevel()+":"+tmpLeave.getLateral()+"=> "+tmpLeave.getValue());
				// Terminal.ecrireStringln("leave "+i+" : "+tmpLeave.getNodes().length());
				// Terminal.sautDeLigne();
			}
			Terminal.sautDeLigne();
		} catch (GeneticTreeNodeException e) { e.printStackTrace(); }
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		String itemsPhaseTwo[] = new String[256];
		itemsPhaseTwo[0] = "a";itemsPhaseTwo[1] = "a";
		itemsPhaseTwo[2] = "b";itemsPhaseTwo[3] = "b";
		itemsPhaseTwo[4] = "c";itemsPhaseTwo[5] = "c";
		itemsPhaseTwo[6] = "d";itemsPhaseTwo[7] = "d";
		itemsPhaseTwo[8] = "e";itemsPhaseTwo[9] = "e";
		itemsPhaseTwo[10] = "f";itemsPhaseTwo[11] = "f";
		itemsPhaseTwo[12] = "g";itemsPhaseTwo[13] = "g";
		itemsPhaseTwo[14] = "h";itemsPhaseTwo[15] = "h";
		itemsPhaseTwo[16] = "i";itemsPhaseTwo[17] = "i";
		itemsPhaseTwo[18] = "j";itemsPhaseTwo[19] = "j";
		itemsPhaseTwo[20] = "k";itemsPhaseTwo[21] = "k";
		itemsPhaseTwo[22] = "l";itemsPhaseTwo[23] = "l";
		itemsPhaseTwo[24] = "m";itemsPhaseTwo[25] = "m";
		itemsPhaseTwo[26] = "n";itemsPhaseTwo[27] = "n";
		itemsPhaseTwo[28] = "o";itemsPhaseTwo[29] = "o";
		itemsPhaseTwo[30] = "p";itemsPhaseTwo[31] = "p";
		itemsPhaseTwo[32] = "q";itemsPhaseTwo[33] = "q";
		itemsPhaseTwo[34] = "r";itemsPhaseTwo[35] = "r";
		itemsPhaseTwo[36] = "s";itemsPhaseTwo[37] = "s";
		itemsPhaseTwo[38] = "t";itemsPhaseTwo[39] = "t";
		itemsPhaseTwo[40] = "u";itemsPhaseTwo[41] = "u";
		itemsPhaseTwo[42] = "v";itemsPhaseTwo[43] = "v";
		itemsPhaseTwo[44] = "w";itemsPhaseTwo[45] = "w";
		itemsPhaseTwo[46] = "x";itemsPhaseTwo[47] = "x";
		itemsPhaseTwo[48] = "y";itemsPhaseTwo[49] = "y";
		itemsPhaseTwo[50] = "z";itemsPhaseTwo[51] = "z";
		itemsPhaseTwo[52] = "A";itemsPhaseTwo[53] = "A";
		itemsPhaseTwo[54] = "B";itemsPhaseTwo[55] = "B";
		itemsPhaseTwo[56] = "C";itemsPhaseTwo[57] = "C";
		itemsPhaseTwo[58] = "D";itemsPhaseTwo[59] = "D";
		itemsPhaseTwo[60] = "E";itemsPhaseTwo[61] = "E";
		itemsPhaseTwo[62] = "F";itemsPhaseTwo[63] = "F";
		itemsPhaseTwo[64] = "G";itemsPhaseTwo[65] = "G";
		itemsPhaseTwo[66] = "H";itemsPhaseTwo[67] = "H";
		itemsPhaseTwo[68] = "I";itemsPhaseTwo[69] = "I";
		itemsPhaseTwo[70] = "J";itemsPhaseTwo[71] = "J";
		itemsPhaseTwo[72] = "K";itemsPhaseTwo[73] = "K";
		itemsPhaseTwo[74] = "L";itemsPhaseTwo[75] = "L";
		itemsPhaseTwo[76] = "M";itemsPhaseTwo[77] = "M";
		itemsPhaseTwo[78] = "N";itemsPhaseTwo[79] = "N";
		itemsPhaseTwo[80] = "O";itemsPhaseTwo[81] = "O";
		itemsPhaseTwo[82] = "P";itemsPhaseTwo[83] = "P";
		itemsPhaseTwo[84] = "Q";itemsPhaseTwo[85] = "Q";
		itemsPhaseTwo[86] = "R";itemsPhaseTwo[87] = "R";
		itemsPhaseTwo[88] = "S";itemsPhaseTwo[89] = "S";
		itemsPhaseTwo[90] = "T";itemsPhaseTwo[91] = "T";
		itemsPhaseTwo[92] = "U";itemsPhaseTwo[93] = "U";
		itemsPhaseTwo[94] = "V";itemsPhaseTwo[95] = "V";
		itemsPhaseTwo[96] = "W";itemsPhaseTwo[97] = "W";
		itemsPhaseTwo[98] = "X";itemsPhaseTwo[99] = "X";
		itemsPhaseTwo[100] = "Y";itemsPhaseTwo[101] = "Y";
		itemsPhaseTwo[102] = "Z";itemsPhaseTwo[103] = "Z";
		itemsPhaseTwo[104] = "0";itemsPhaseTwo[105] = "0";
		itemsPhaseTwo[106] = "0";itemsPhaseTwo[107] = "0";
		itemsPhaseTwo[108] = "1";itemsPhaseTwo[109] = "1";
		itemsPhaseTwo[110] = "1";itemsPhaseTwo[111] = "1";
		itemsPhaseTwo[112] = "2";itemsPhaseTwo[113] = "2";
		itemsPhaseTwo[114] = "2";itemsPhaseTwo[115] = "2";
		itemsPhaseTwo[116] = "3";itemsPhaseTwo[117] = "3";
		itemsPhaseTwo[118] = "3";itemsPhaseTwo[119] = "3";
		itemsPhaseTwo[120] = "4";itemsPhaseTwo[121] = "4";
		itemsPhaseTwo[122] = "4";itemsPhaseTwo[123] = "4";
		itemsPhaseTwo[124] = "5";itemsPhaseTwo[125] = "5";
		itemsPhaseTwo[126] = "5";itemsPhaseTwo[127] = "5";
		itemsPhaseTwo[128] = "6";itemsPhaseTwo[129] = "6";
		itemsPhaseTwo[130] = "6";itemsPhaseTwo[131] = "6";
		itemsPhaseTwo[132] = "7";itemsPhaseTwo[133] = "7";
		itemsPhaseTwo[134] = "7";itemsPhaseTwo[135] = "7";
		itemsPhaseTwo[136] = "8";itemsPhaseTwo[137] = "8";
		itemsPhaseTwo[138] = "8";itemsPhaseTwo[139] = "8";
		itemsPhaseTwo[140] = "9";itemsPhaseTwo[141] = "9";
		itemsPhaseTwo[142] = "9";itemsPhaseTwo[143] = "9";
		itemsPhaseTwo[144] = "=";itemsPhaseTwo[145] = "=";
		itemsPhaseTwo[146] = "!";itemsPhaseTwo[147] = "!";
		itemsPhaseTwo[148] = ".";itemsPhaseTwo[149] = ".";
		itemsPhaseTwo[150] = "-";itemsPhaseTwo[151] = "-";
		itemsPhaseTwo[152] = "+";itemsPhaseTwo[153] = "+";
		itemsPhaseTwo[154] = "/";itemsPhaseTwo[155] = "/";
		itemsPhaseTwo[156] = "*";itemsPhaseTwo[157] = "*";
		itemsPhaseTwo[158] = "\"";itemsPhaseTwo[159] = "\"";
		itemsPhaseTwo[160] = "'";itemsPhaseTwo[161] = "'";
		itemsPhaseTwo[162] = ":";itemsPhaseTwo[163] = ":";
		itemsPhaseTwo[164] = "++";itemsPhaseTwo[165] = "++";
		itemsPhaseTwo[166] = "--";itemsPhaseTwo[167] = "--";
		itemsPhaseTwo[168] = ";";itemsPhaseTwo[169] = ";";
		itemsPhaseTwo[170] = "\n";itemsPhaseTwo[171] = "\n";
		itemsPhaseTwo[172] = "\t";itemsPhaseTwo[173] = "\t";
		itemsPhaseTwo[174] = " ";itemsPhaseTwo[175] = " ";
		itemsPhaseTwo[176] = "(";itemsPhaseTwo[177] = "(";
		itemsPhaseTwo[178] = ")";itemsPhaseTwo[179] = ")";
		itemsPhaseTwo[180] = "[";itemsPhaseTwo[181] = "[";
		itemsPhaseTwo[182] = "]";itemsPhaseTwo[183] = "]";
		itemsPhaseTwo[184] = "{";itemsPhaseTwo[185] = "{";
		itemsPhaseTwo[186] = "}";itemsPhaseTwo[187] = "}";
		itemsPhaseTwo[188] = "<";itemsPhaseTwo[189] = "<";
		itemsPhaseTwo[190] = ">";itemsPhaseTwo[191] = ">";
		itemsPhaseTwo[192] = "class";itemsPhaseTwo[193] = "class";
		itemsPhaseTwo[194] = "class";itemsPhaseTwo[195] = "class";
		itemsPhaseTwo[196] = "interface";itemsPhaseTwo[197] = "interface";
		itemsPhaseTwo[198] = "interface";itemsPhaseTwo[199] = "interface";
		itemsPhaseTwo[200] = "static";itemsPhaseTwo[201] = "static";
		itemsPhaseTwo[202] = "static";itemsPhaseTwo[203] = "static";
		itemsPhaseTwo[204] = "final";itemsPhaseTwo[205] = "final";
		itemsPhaseTwo[206] = "final";itemsPhaseTwo[207] = "final";
		itemsPhaseTwo[208] = "private";itemsPhaseTwo[209] = "private";
		itemsPhaseTwo[210] = "private";itemsPhaseTwo[211] = "private";
		itemsPhaseTwo[212] = "public";itemsPhaseTwo[213] = "public";
		itemsPhaseTwo[214] = "public";itemsPhaseTwo[215] = "public";
		itemsPhaseTwo[216] = "void";itemsPhaseTwo[217] = "void";
		itemsPhaseTwo[218] = "void";itemsPhaseTwo[219] = "void";
		itemsPhaseTwo[220] = "int";itemsPhaseTwo[221] = "int";
		itemsPhaseTwo[222] = "int";itemsPhaseTwo[223] = "int";
		itemsPhaseTwo[224] = "double";itemsPhaseTwo[225] = "double";
		itemsPhaseTwo[226] = "double";itemsPhaseTwo[227] = "double";
		itemsPhaseTwo[228] = "true";itemsPhaseTwo[229] = "true";
		itemsPhaseTwo[230] = "true";itemsPhaseTwo[231] = "true";
		itemsPhaseTwo[232] = "false";itemsPhaseTwo[233] = "false";
		itemsPhaseTwo[234] = "false";itemsPhaseTwo[235] = "false";
		itemsPhaseTwo[236] = "if";itemsPhaseTwo[237] = "if";
		itemsPhaseTwo[238] = "else";itemsPhaseTwo[239] = "else";
		itemsPhaseTwo[240] = "while";itemsPhaseTwo[241] = "while";
		itemsPhaseTwo[242] = "while";itemsPhaseTwo[243] = "while";
		itemsPhaseTwo[244] = "&&";itemsPhaseTwo[245] = "&&";
		itemsPhaseTwo[246] = "||";itemsPhaseTwo[247] = "||";
		itemsPhaseTwo[248] = "return";itemsPhaseTwo[249] = "return";
		itemsPhaseTwo[250] = "return";itemsPhaseTwo[251] = "return";
		itemsPhaseTwo[252] = "[STOP]";itemsPhaseTwo[253] = "[STOP]";
		itemsPhaseTwo[254] = "[STOP]";itemsPhaseTwo[255] = "[STOP]";

		/** Terminal.ecrireStringln(itemsPhaseTwo.length+""); */
		try {
			GeneticTreeNode testRootPhaseTwo = new GeneticTreeNode("UBVP",itemsPhaseTwo,false);
			
			GeneticTreeNodeListe lvl1 = testRootPhaseTwo.getNodes();
			for (int i = 0 ; i < lvl1.length() ; i++) {
				GeneticTreeNode tmpLeave1 = lvl1.getGeneticTreeNode(i);
				// Terminal.ecrireStringln("level 1 : "+tmpLeave1.getLevel()+":"+tmpLeave1.getNodes().length());
				// Terminal.ecrireStringln("level 1("+tmpLeave1.getLevel()+") : "+tmpLeave1.getLateral()+"\t\t\t"+tmpLeave1.getCode()+"\t"+tmpLeave1.getLastCode());
				GeneticTreeNodeListe lvl2 = tmpLeave1.getNodes();
				for (int j = 0 ; j < lvl2.length() ; j++) {
					GeneticTreeNode tmpLeave2 = lvl2.getGeneticTreeNode(j);
					// Terminal.ecrireStringln("\tlevel 2("+tmpLeave2.getLevel()+"):*"+tmpLeave2.getNodes().length());
					// Terminal.ecrireStringln("\tlevel 2("+tmpLeave2.getLevel()+") : "+tmpLeave2.getLateral()+"\t\t"+tmpLeave2.getCode()+"\t"+tmpLeave2.getLastCode());
					GeneticTreeNodeListe lvl3 = tmpLeave2.getNodes();
					for (int k = 0 ; k < lvl3.length() ; k++) {
						GeneticTreeNode tmpLeave3 = lvl3.getGeneticTreeNode(k);
						// Terminal.ecrireStringln("\t\tlevel 3("+tmpLeave3.getLevel()+"):**"+tmpLeave3.getNodes().length());
						// Terminal.ecrireStringln("\t\tlevel 3("+tmpLeave3.getLevel()+") : "+tmpLeave3.getLateral()+"\t"+tmpLeave3.getCode()+"\t"+tmpLeave3.getLastCode());
						GeneticTreeNodeListe lvl4 = tmpLeave3.getNodes();
						for (int l = 0 ; l < lvl4.length() ; l++) {
							GeneticTreeNode tmpLeave4 = lvl4.getGeneticTreeNode(l);
							// Terminal.ecrireStringln("\t\t\tlevel 4("+tmpLeave4.getLevel()+"):***"+tmpLeave4.getNodes().length());
							Terminal.ecrireStringln("\t\t\tlevel 4("+tmpLeave4.getLevel()+") : "+tmpLeave4.getLateral()+"\t"+tmpLeave4.getCode()+"\t"+tmpLeave4.getValue()+"\t"+tmpLeave4.isLeave()+"\t"+tmpLeave4.getOutput()+"\t"+tmpLeave4.getLastCode());
						}
					}
				}
			}
			Terminal.sautDeLigne();
			
			GeneticTreeNodeListe testFeuilles = testRootPhaseTwo.getLeaves();
			Terminal.ecrireStringln(testFeuilles.length()+"");
			for (int i = 0 ; i < testFeuilles.length() ; i++) {
				GeneticTreeNode tmpFeuille = testFeuilles.getGeneticTreeNode(i);
				if (i%64 == 0) { Terminal.sautDeLigne();Terminal.ecrireString(i+"\t"); }
				Terminal.ecrireString(tmpFeuille.getValue());
				// Terminal.ecrireStringln("leave "+i+" : "+tmpFeuille.getLevel()+":"+tmpFeuille.getLateral()+"=> "+tmpFeuille.getValue());
				// Terminal.ecrireStringln("leave "+i+" : "+tmpFeuille.getNodes().length());
				// Terminal.sautDeLigne();
			}
			Terminal.sautDeLigne();
		} catch (GeneticTreeNodeException e) { e.printStackTrace(); }
		
		/** 
		System.out.println("(false && false) == "+(false && false));
		System.out.println("(true && false) == "+(true && false));
		System.out.println("(true && true) == "+(true && true));
		 */
		
	}
}
