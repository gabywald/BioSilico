package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.structures.GeneMoreListe;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneGattacaTests05 {

	public static void main (String argv[]) {

		GeneMoreListe liste = new GeneMoreListe("initialGenes.txt", true);
		liste.readFile();
		for (int i = 0 ; i < liste.length() ; i++)
			{ liste.removeChamps(0); }

		BiochemicalReaction BR000 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				169, 1, 150, 1, 151, 1, 171, 1, 1);
		BR000.setName("BR000");
		BiochemicalReaction BR001 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				170, 1, 150, 1, 151, 1, 172, 1, 1);
		BR001.setName("BR001");
		BiochemicalReaction BR002 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				171, 1, 0, 0, 172, 1, 0, 0, 1);
		BR002.setName("BR002");
		BiochemicalReaction BR003 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				172, 1, 0, 0, 171, 1, 0, 0, 1);
		BR003.setName("BR003");
		BiochemicalReaction BR004 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				172, 1, 150, 1, 173, 1, 151, 1, 1);
		BR004.setName("BR004");
		BiochemicalReaction BR005 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				173, 1, 0, 0, 174, 1, 175, 1, 1);
		BR005.setName("BR005");
		BiochemicalReaction BR006 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				174, 1, 175, 1, 173, 1, 0, 0, 1);
		BR006.setName("BR006");
		BiochemicalReaction BR007 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				174, 1, 0, 0, 175, 1, 0, 0, 1);
		BR007.setName("BR007");
		BiochemicalReaction BR008 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				175, 1, 0, 0, 174, 1, 0, 0, 1);
		BR008.setName("BR008");
		BiochemicalReaction BR009 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				175, 1, 165, 1, 176, 1, 166, 1, 2);
		BR009.setName("BR009");
		BiochemicalReaction BR010 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				176, 1, 166, 1, 175, 1, 165, 1, 1);
		BR010.setName("BR010");
		BiochemicalReaction BR011 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				176, 1, 151, 1, 177, 1, 150, 1, 2);
		BR011.setName("BR011");
		BiochemicalReaction BR012 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				177, 1, 150, 1, 176, 1, 151, 1, 1);
		BR012.setName("BR012");
		BiochemicalReaction BR013 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				177, 1, 0, 0, 178, 1, 0, 0, 2);
		BR013.setName("BR013");
		BiochemicalReaction BR014 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				178, 1, 0, 0, 177, 1, 0, 0, 1);
		BR014.setName("BR014");
		BiochemicalReaction BR015 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				178, 1, 0, 0, 179, 1, 182, 1, 2);
		BR015.setName("BR015");
		BiochemicalReaction BR016 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				179, 1, 182, 1, 178, 1, 0, 0, 1);
		BR016.setName("BR016");
		BiochemicalReaction BR017 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				179, 1, 151, 1, 198, 1, 150, 1, 2);
		BR017.setName("BR017");
		BiochemicalReaction BR018 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				198, 1, 187, 1, 181, 1, 184, 1, 2);
		BR018.setName("BR018");
		BiochemicalReaction BR019 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				181, 1, 165, 1, 181, 1, 166, 1, 2);
		BR019.setName("BR019");
		BiochemicalReaction BR020 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				278, 1, 240, 1, 279, 1, 181, 1, 1);
		BR020.setName("BR020");
		BiochemicalReaction BR021 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				279, 1, 150, 1, 319, 1, 152, 1, 1);
		BR021.setName("BR021");
		BiochemicalReaction BR022 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 187, 2, 319, 1, 15, 2, 1);
		BR022.setName("BR022");
		BiochemicalReaction BR023 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 167, 1, 319, 1, 168, 1, 1);
		BR023.setName("BR023");
		BiochemicalReaction BR024 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 165, 1, 319, 1, 166, 1, 1);
		BR024.setName("BR024");
		BiochemicalReaction BR025 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 182, 1, 317, 1, 184, 1, 1);
		BR025.setName("BR025");
		BiochemicalReaction BR026 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				276, 1, 240, 1, 277, 1, 181, 1, 1);
		BR026.setName("BR026");
		BiochemicalReaction BR027 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				277, 1, 150, 1, 317, 1, 152, 1, 1);
		BR027.setName("BR027");
		BiochemicalReaction BR028 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 187, 2, 317, 1, 15, 2, 1);
		BR028.setName("BR028");
		BiochemicalReaction BR029 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 167, 1, 317, 1, 168, 1, 1);
		BR029.setName("BR029");
		BiochemicalReaction BR030 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 165, 1, 317, 1, 166, 1, 1);
		BR030.setName("BR030");
		BiochemicalReaction BR031 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 182, 1, 315, 1, 184, 1, 1);
		BR031.setName("BR031");
		BiochemicalReaction BR032 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				274, 1, 240, 1, 275, 1, 181, 1, 1);
		BR032.setName("BR032");
		BiochemicalReaction BR033 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				275, 1, 150, 1, 315, 1, 152, 1, 1);
		BR033.setName("BR033");
		BiochemicalReaction BR034 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 187, 2, 315, 1, 15, 2, 1);
		BR034.setName("BR034");
		BiochemicalReaction BR035 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 167, 1, 315, 1, 168, 1, 1);
		BR035.setName("BR035");
		BiochemicalReaction BR036 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 165, 1, 315, 1, 166, 1, 1);
		BR036.setName("BR036");
		BiochemicalReaction BR037 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 182, 1, 313, 1, 184, 1, 1);
		BR037.setName("BR037");
		BiochemicalReaction BR038 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				272, 1, 240, 1, 273, 1, 181, 1, 1);
		BR038.setName("BR038");
		BiochemicalReaction BR039 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				273, 1, 150, 1, 313, 1, 152, 1, 1);
		BR039.setName("BR039");
		BiochemicalReaction BR040 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 187, 2, 313, 1, 15, 2, 1);
		BR040.setName("BR040");
		BiochemicalReaction BR041 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 167, 1, 313, 1, 168, 1, 1);
		BR041.setName("BR041");
		BiochemicalReaction BR042 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 165, 1, 313, 1, 166, 1, 1);
		BR042.setName("BR042");
		BiochemicalReaction BR043 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 182, 1, 311, 1, 184, 1, 1);
		BR043.setName("BR043");
		BiochemicalReaction BR044 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				270, 1, 240, 1, 271, 1, 181, 1, 1);
		BR044.setName("BR044");
		BiochemicalReaction BR045 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				271, 1, 150, 1, 311, 1, 152, 1, 1);
		BR045.setName("BR045");
		BiochemicalReaction BR046 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 187, 2, 311, 1, 15, 2, 1);
		BR046.setName("BR046");
		BiochemicalReaction BR047 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 167, 1, 311, 1, 168, 1, 1);
		BR047.setName("BR047");
		BiochemicalReaction BR048 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 165, 1, 311, 1, 166, 1, 1);
		BR048.setName("BR048");
		BiochemicalReaction BR049 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 182, 1, 309, 1, 184, 1, 1);
		BR049.setName("BR049");
		BiochemicalReaction BR050 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				268, 1, 240, 1, 269, 1, 181, 1, 1);
		BR050.setName("BR050");
		BiochemicalReaction BR051 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				269, 1, 150, 1, 309, 1, 152, 1, 1);
		BR051.setName("BR051");
		BiochemicalReaction BR052 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 187, 2, 309, 1, 15, 2, 1);
		BR052.setName("BR052");
		BiochemicalReaction BR053 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 167, 1, 309, 1, 168, 1, 1);
		BR053.setName("BR053");
		BiochemicalReaction BR054 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 165, 1, 309, 1, 166, 1, 1);
		BR054.setName("BR054");
		BiochemicalReaction BR055 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 182, 1, 307, 1, 184, 1, 1);
		BR055.setName("BR055");
		BiochemicalReaction BR056 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				266, 1, 240, 1, 267, 1, 181, 1, 1);
		BR056.setName("BR056");
		BiochemicalReaction BR057 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				267, 1, 150, 1, 307, 1, 152, 1, 1);
		BR057.setName("BR057");
		BiochemicalReaction BR058 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 187, 2, 307, 1, 15, 2, 1);
		BR058.setName("BR058");
		BiochemicalReaction BR059 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 167, 1, 307, 1, 168, 1, 1);
		BR059.setName("BR059");
		BiochemicalReaction BR060 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 165, 1, 307, 1, 166, 1, 1);
		BR060.setName("BR060");
		BiochemicalReaction BR061 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 182, 1, 305, 1, 184, 1, 1);
		BR061.setName("BR061");
		BiochemicalReaction BR062 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				264, 1, 240, 1, 265, 1, 181, 1, 1);
		BR062.setName("BR062");
		BiochemicalReaction BR063 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				265, 1, 150, 1, 305, 1, 152, 1, 1);
		BR063.setName("BR063");
		BiochemicalReaction BR064 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 187, 2, 305, 1, 15, 2, 1);
		BR064.setName("BR064");
		BiochemicalReaction BR065 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 167, 1, 305, 1, 168, 1, 1);
		BR065.setName("BR065");
		BiochemicalReaction BR066 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 165, 1, 305, 1, 166, 1, 1);
		BR066.setName("BR066");
		BiochemicalReaction BR067 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 182, 1, 303, 1, 184, 1, 1);
		BR067.setName("BR067");
		BiochemicalReaction BR068 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				262, 1, 240, 1, 263, 1, 181, 1, 1);
		BR068.setName("BR068");
		BiochemicalReaction BR069 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				263, 1, 150, 1, 303, 1, 152, 1, 1);
		BR069.setName("BR069");
		BiochemicalReaction BR070 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 187, 2, 303, 1, 15, 2, 1);
		BR070.setName("BR070");
		BiochemicalReaction BR071 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 167, 1, 303, 1, 168, 1, 1);
		BR071.setName("BR071");
		BiochemicalReaction BR072 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 165, 1, 303, 1, 166, 1, 1);
		BR072.setName("BR072");
		BiochemicalReaction BR073 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 182, 1, 301, 1, 184, 1, 1);
		BR073.setName("BR073");
		BiochemicalReaction BR074 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				260, 1, 240, 1, 261, 1, 181, 1, 1);
		BR074.setName("BR074");
		BiochemicalReaction BR075 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				261, 1, 150, 1, 301, 1, 152, 1, 1);
		BR075.setName("BR075");
		BiochemicalReaction BR076 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 187, 2, 301, 1, 15, 2, 1);
		BR076.setName("BR076");
		BiochemicalReaction BR077 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 167, 1, 301, 1, 168, 1, 1);
		BR077.setName("BR077");
		BiochemicalReaction BR078 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 165, 1, 301, 1, 166, 1, 1);
		BR078.setName("BR078");
		BiochemicalReaction BR079 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 182, 1, 299, 1, 184, 1, 1);
		BR079.setName("BR079");
		BiochemicalReaction BR080 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				258, 1, 240, 1, 259, 1, 181, 1, 1);
		BR080.setName("BR080");
		BiochemicalReaction BR081 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				259, 1, 150, 1, 299, 1, 152, 1, 1);
		BR081.setName("BR081");
		BiochemicalReaction BR082 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 187, 2, 299, 1, 15, 2, 1);
		BR082.setName("BR082");
		BiochemicalReaction BR083 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 167, 1, 299, 1, 168, 1, 1);
		BR083.setName("BR083");
		BiochemicalReaction BR084 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 165, 1, 299, 1, 166, 1, 1);
		BR084.setName("BR084");
		BiochemicalReaction BR085 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 182, 1, 297, 1, 184, 1, 1);
		BR085.setName("BR085");
		BiochemicalReaction BR086 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				256, 1, 240, 1, 257, 1, 181, 1, 1);
		BR086.setName("BR086");
		BiochemicalReaction BR087 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				257, 1, 150, 1, 297, 1, 152, 1, 1);
		BR087.setName("BR087");
		BiochemicalReaction BR088 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 187, 2, 297, 1, 15, 2, 1);
		BR088.setName("BR088");
		BiochemicalReaction BR089 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 167, 1, 297, 1, 168, 1, 1);
		BR089.setName("BR089");
		BiochemicalReaction BR090 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 165, 1, 297, 1, 166, 1, 1);
		BR090.setName("BR090");
		BiochemicalReaction BR091 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 182, 1, 295, 1, 184, 1, 1);
		BR091.setName("BR091");
		BiochemicalReaction BR092 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				254, 1, 240, 1, 255, 1, 181, 1, 1);
		BR092.setName("BR092");
		BiochemicalReaction BR093 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				255, 1, 150, 1, 295, 1, 152, 1, 1);
		BR093.setName("BR093");
		BiochemicalReaction BR094 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 187, 2, 295, 1, 15, 2, 1);
		BR094.setName("BR094");
		BiochemicalReaction BR095 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 167, 1, 295, 1, 168, 1, 1);
		BR095.setName("BR095");
		BiochemicalReaction BR096 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 165, 1, 295, 1, 166, 1, 1);
		BR096.setName("BR096");
		BiochemicalReaction BR097 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 182, 1, 293, 1, 184, 1, 1);
		BR097.setName("BR097");
		BiochemicalReaction BR098 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				252, 1, 240, 1, 253, 1, 181, 1, 1);
		BR098.setName("BR098");
		BiochemicalReaction BR099 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				253, 1, 150, 1, 293, 1, 152, 1, 1);
		BR099.setName("BR099");
		BiochemicalReaction BR100 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 187, 2, 293, 1, 15, 2, 1);
		BR100.setName("BR100");
		BiochemicalReaction BR101 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 167, 1, 293, 1, 168, 1, 1);
		BR101.setName("BR101");
		BiochemicalReaction BR102 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 165, 1, 293, 1, 166, 1, 1);
		BR102.setName("BR102");
		BiochemicalReaction BR103 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 182, 1, 291, 1, 184, 1, 1);
		BR103.setName("BR103");
		BiochemicalReaction BR104 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				250, 1, 240, 1, 251, 1, 181, 1, 1);
		BR104.setName("BR104");
		BiochemicalReaction BR105 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				251, 1, 150, 1, 291, 1, 152, 1, 1);
		BR105.setName("BR105");
		BiochemicalReaction BR106 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 187, 2, 291, 1, 15, 2, 1);
		BR106.setName("BR106");
		BiochemicalReaction BR107 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 167, 1, 291, 1, 168, 1, 1);
		BR107.setName("BR107");
		BiochemicalReaction BR108 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 165, 1, 291, 1, 166, 1, 1);
		BR108.setName("BR108");
		BiochemicalReaction BR109 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 182, 1, 289, 1, 184, 1, 1);
		BR109.setName("BR109");
		BiochemicalReaction BR110 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				248, 1, 240, 1, 249, 1, 181, 1, 1);
		BR110.setName("BR110");
		BiochemicalReaction BR111 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				249, 1, 150, 1, 289, 1, 152, 1, 1);
		BR111.setName("BR111");
		BiochemicalReaction BR112 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 187, 2, 289, 1, 15, 2, 1);
		BR112.setName("BR112");
		BiochemicalReaction BR113 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 167, 1, 289, 1, 168, 1, 1);
		BR113.setName("BR113");
		BiochemicalReaction BR114 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 165, 1, 289, 1, 166, 1, 1);
		BR114.setName("BR114");
		BiochemicalReaction BR115 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 182, 1, 287, 1, 184, 1, 1);
		BR115.setName("BR115");
		BiochemicalReaction BR116 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				246, 1, 240, 1, 247, 1, 181, 1, 1);
		BR116.setName("BR116");
		BiochemicalReaction BR117 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				247, 1, 150, 1, 287, 1, 152, 1, 1);
		BR117.setName("BR117");
		BiochemicalReaction BR118 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 187, 2, 287, 1, 15, 2, 1);
		BR118.setName("BR118");
		BiochemicalReaction BR119 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 167, 1, 287, 1, 168, 1, 1);
		BR119.setName("BR119");
		BiochemicalReaction BR120 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 165, 1, 287, 1, 166, 1, 1);
		BR120.setName("BR120");
		BiochemicalReaction BR121 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 182, 1, 285, 1, 184, 1, 1);
		BR121.setName("BR121");
		BiochemicalReaction BR122 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				244, 1, 240, 1, 245, 1, 181, 1, 1);
		BR122.setName("BR122");
		BiochemicalReaction BR123 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				245, 1, 150, 1, 285, 1, 152, 1, 1);
		BR123.setName("BR123");
		BiochemicalReaction BR124 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 187, 2, 285, 1, 15, 2, 1);
		BR124.setName("BR124");
		BiochemicalReaction BR125 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 167, 1, 285, 1, 168, 1, 1);
		BR125.setName("BR125");
		BiochemicalReaction BR126 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 165, 1, 285, 1, 166, 1, 1);
		BR126.setName("BR126");
		BiochemicalReaction BR127 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 182, 1, 285, 1, 184, 1, 1);
		BR127.setName("BR127");
		BiochemicalReaction BR128 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				242, 1, 240, 1, 243, 1, 181, 1, 1);
		BR128.setName("BR128");
		BiochemicalReaction BR129 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				243, 1, 150, 1, 283, 1, 152, 1, 1);
		BR129.setName("BR129");
		BiochemicalReaction BR130 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 187, 2, 283, 1, 15, 2, 1);
		BR130.setName("BR130");
		BiochemicalReaction BR131 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 167, 1, 283, 1, 168, 1, 1);
		BR131.setName("BR131");
		BiochemicalReaction BR132 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 165, 1, 283, 1, 166, 1, 1);
		BR132.setName("BR132");
		BiochemicalReaction BR133 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 182, 1, 281, 1, 184, 1, 1);
		BR133.setName("BR133");
		BiochemicalReaction BR134 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				240, 1, 240, 1, 241, 1, 181, 1, 1);
		BR134.setName("BR134");
		BiochemicalReaction BR135 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				241, 1, 150, 1, 281, 1, 152, 1, 1);
		BR135.setName("BR135");
		BiochemicalReaction BR136 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 187, 2, 281, 1, 15, 2, 1);
		BR136.setName("BR136");
		BiochemicalReaction BR137 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 167, 1, 281, 1, 168, 1, 1);
		BR137.setName("BR137");
		BiochemicalReaction BR138 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 165, 1, 281, 1, 166, 1, 1);
		BR138.setName("BR138");
		BiochemicalReaction BR139 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 182, 1, 184, 1, 184, 1, 1);
		BR139.setName("BR139");
		BiochemicalReaction BR140 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				15, 2, 182, 1, 15, 2, 0, 0, 40);
		BR140.setName("BR140");
		BiochemicalReaction BR141 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				184, 1, 185, 1, 186, 1, 187, 1, 1);
		BR141.setName("BR141");
		BiochemicalReaction BR142 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				186, 1, 0, 0, 188, 1, 0, 0, 2);
		BR142.setName("BR142");
		BiochemicalReaction BR143 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				188, 1, 0, 0, 186, 1, 0, 0, 1);
		BR143.setName("BR143");
		BiochemicalReaction BR144 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				188, 1, 182, 1, 189, 1, 0, 0, 2);
		BR144.setName("BR144");
		BiochemicalReaction BR145 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				189, 1, 0, 0, 188, 1, 182, 1, 1);
		BR145.setName("BR145");
		BiochemicalReaction BR146 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				189, 1, 165, 1, 190, 1, 166, 1, 2);
		BR146.setName("BR146");
		BiochemicalReaction BR147 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				190, 1, 166, 1, 189, 1, 165, 1, 1);
		BR147.setName("BR147");
		BiochemicalReaction BR148 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				190, 1, 0, 0, 191, 1, 181, 2, 1);
		BR148.setName("BR148");
		BiochemicalReaction BR149 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				191, 1, 165, 1, 192, 1, 166, 1, 1);
		BR149.setName("BR149");
		BiochemicalReaction BR150 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				192, 1, 157, 1, 193, 1, 156, 1, 1);
		BR150.setName("BR150");
		BiochemicalReaction BR151 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				193, 1, 167, 1, 194, 1, 168, 1, 1);
		BR151.setName("BR151");
		BiochemicalReaction BR152 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				194, 1, 182, 2, 195, 1, 0, 0, 1);
		BR152.setName("BR152");
		BiochemicalReaction BR153 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				195, 1, 165, 1, 185, 1, 166, 1, 1);
		BR153.setName("BR153");
		BiochemicalReaction BR154 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				166, 1, 180, 1, 165, 1, 183, 2, 1);
		BR154.setName("BR154");
		BiochemicalReaction BR155 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				168, 1, 180, 1, 167, 1, 183, 2, 1);
		BR155.setName("BR155");
		BiochemicalReaction BR156 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				183, 1, 15, 1, 183, 1, 182, 1, 1);
		BR156.setName("BR156");
		BiochemicalReaction BR157 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				151, 1, 183, 1, 150, 1, 182, 1, 1);
		BR157.setName("BR157");
		liste.addToChamps("BR000	2	"+BR000.toString());
		liste.addToChamps("BR001	2	"+BR001.toString());
		liste.addToChamps("BR002	2	"+BR002.toString());
		liste.addToChamps("BR003	2	"+BR003.toString());
		liste.addToChamps("BR004	2	"+BR004.toString());
		liste.addToChamps("BR005	2	"+BR005.toString());
		liste.addToChamps("BR006	2	"+BR006.toString());
		liste.addToChamps("BR007	2	"+BR007.toString());
		liste.addToChamps("BR008	2	"+BR008.toString());
		liste.addToChamps("BR009	2	"+BR009.toString());
		liste.addToChamps("BR010	2	"+BR010.toString());
		liste.addToChamps("BR011	2	"+BR011.toString());
		liste.addToChamps("BR012	2	"+BR012.toString());
		liste.addToChamps("BR013	2	"+BR013.toString());
		liste.addToChamps("BR014	2	"+BR014.toString());
		liste.addToChamps("BR015	2	"+BR015.toString());
		liste.addToChamps("BR016	2	"+BR016.toString());
		liste.addToChamps("BR017	2	"+BR017.toString());
		liste.addToChamps("BR018	2	"+BR018.toString());
		liste.addToChamps("BR019	2	"+BR019.toString());
		liste.addToChamps("BR020	2	"+BR020.toString());
		liste.addToChamps("BR021	2	"+BR021.toString());
		liste.addToChamps("BR022	2	"+BR022.toString());
		liste.addToChamps("BR023	2	"+BR023.toString());
		liste.addToChamps("BR024	2	"+BR024.toString());
		liste.addToChamps("BR025	2	"+BR025.toString());
		liste.addToChamps("BR026	2	"+BR026.toString());
		liste.addToChamps("BR027	2	"+BR027.toString());
		liste.addToChamps("BR028	2	"+BR028.toString());
		liste.addToChamps("BR029	2	"+BR029.toString());
		liste.addToChamps("BR030	2	"+BR030.toString());
		liste.addToChamps("BR031	2	"+BR031.toString());
		liste.addToChamps("BR032	2	"+BR032.toString());
		liste.addToChamps("BR033	2	"+BR033.toString());
		liste.addToChamps("BR034	2	"+BR034.toString());
		liste.addToChamps("BR035	2	"+BR035.toString());
		liste.addToChamps("BR036	2	"+BR036.toString());
		liste.addToChamps("BR037	2	"+BR037.toString());
		liste.addToChamps("BR038	2	"+BR038.toString());
		liste.addToChamps("BR039	2	"+BR039.toString());
		liste.addToChamps("BR040	2	"+BR040.toString());
		liste.addToChamps("BR041	2	"+BR041.toString());
		liste.addToChamps("BR042	2	"+BR042.toString());
		liste.addToChamps("BR043	2	"+BR043.toString());
		liste.addToChamps("BR044	2	"+BR044.toString());
		liste.addToChamps("BR045	2	"+BR045.toString());
		liste.addToChamps("BR046	2	"+BR046.toString());
		liste.addToChamps("BR047	2	"+BR047.toString());
		liste.addToChamps("BR048	2	"+BR048.toString());
		liste.addToChamps("BR049	2	"+BR049.toString());
		liste.addToChamps("BR050	2	"+BR050.toString());
		liste.addToChamps("BR051	2	"+BR051.toString());
		liste.addToChamps("BR052	2	"+BR052.toString());
		liste.addToChamps("BR053	2	"+BR053.toString());
		liste.addToChamps("BR054	2	"+BR054.toString());
		liste.addToChamps("BR055	2	"+BR055.toString());
		liste.addToChamps("BR056	2	"+BR056.toString());
		liste.addToChamps("BR057	2	"+BR057.toString());
		liste.addToChamps("BR058	2	"+BR058.toString());
		liste.addToChamps("BR059	2	"+BR059.toString());
		liste.addToChamps("BR060	2	"+BR060.toString());
		liste.addToChamps("BR061	2	"+BR061.toString());
		liste.addToChamps("BR062	2	"+BR062.toString());
		liste.addToChamps("BR063	2	"+BR063.toString());
		liste.addToChamps("BR064	2	"+BR064.toString());
		liste.addToChamps("BR065	2	"+BR065.toString());
		liste.addToChamps("BR066	2	"+BR066.toString());
		liste.addToChamps("BR067	2	"+BR067.toString());
		liste.addToChamps("BR068	2	"+BR068.toString());
		liste.addToChamps("BR069	2	"+BR069.toString());
		liste.addToChamps("BR070	2	"+BR070.toString());
		liste.addToChamps("BR071	2	"+BR071.toString());
		liste.addToChamps("BR072	2	"+BR072.toString());
		liste.addToChamps("BR073	2	"+BR073.toString());
		liste.addToChamps("BR074	2	"+BR074.toString());
		liste.addToChamps("BR075	2	"+BR075.toString());
		liste.addToChamps("BR076	2	"+BR076.toString());
		liste.addToChamps("BR077	2	"+BR077.toString());
		liste.addToChamps("BR078	2	"+BR078.toString());
		liste.addToChamps("BR079	2	"+BR079.toString());
		liste.addToChamps("BR080	2	"+BR080.toString());
		liste.addToChamps("BR081	2	"+BR081.toString());
		liste.addToChamps("BR082	2	"+BR082.toString());
		liste.addToChamps("BR083	2	"+BR083.toString());
		liste.addToChamps("BR084	2	"+BR084.toString());
		liste.addToChamps("BR085	2	"+BR085.toString());
		liste.addToChamps("BR086	2	"+BR086.toString());
		liste.addToChamps("BR087	2	"+BR087.toString());
		liste.addToChamps("BR088	2	"+BR088.toString());
		liste.addToChamps("BR089	2	"+BR089.toString());
		liste.addToChamps("BR090	2	"+BR090.toString());
		liste.addToChamps("BR091	2	"+BR091.toString());
		liste.addToChamps("BR092	2	"+BR092.toString());
		liste.addToChamps("BR093	2	"+BR093.toString());
		liste.addToChamps("BR094	2	"+BR094.toString());
		liste.addToChamps("BR095	2	"+BR095.toString());
		liste.addToChamps("BR096	2	"+BR096.toString());
		liste.addToChamps("BR097	2	"+BR097.toString());
		liste.addToChamps("BR098	2	"+BR098.toString());
		liste.addToChamps("BR099	2	"+BR099.toString());
		liste.addToChamps("BR100	2	"+BR100.toString());
		liste.addToChamps("BR101	2	"+BR101.toString());
		liste.addToChamps("BR102	2	"+BR102.toString());
		liste.addToChamps("BR103	2	"+BR103.toString());
		liste.addToChamps("BR104	2	"+BR104.toString());
		liste.addToChamps("BR105	2	"+BR105.toString());
		liste.addToChamps("BR106	2	"+BR106.toString());
		liste.addToChamps("BR107	2	"+BR107.toString());
		liste.addToChamps("BR108	2	"+BR108.toString());
		liste.addToChamps("BR109	2	"+BR109.toString());
		liste.addToChamps("BR110	2	"+BR110.toString());
		liste.addToChamps("BR111	2	"+BR111.toString());
		liste.addToChamps("BR112	2	"+BR112.toString());
		liste.addToChamps("BR113	2	"+BR113.toString());
		liste.addToChamps("BR114	2	"+BR114.toString());
		liste.addToChamps("BR115	2	"+BR115.toString());
		liste.addToChamps("BR116	2	"+BR116.toString());
		liste.addToChamps("BR117	2	"+BR117.toString());
		liste.addToChamps("BR118	2	"+BR118.toString());
		liste.addToChamps("BR119	2	"+BR119.toString());
		liste.addToChamps("BR120	2	"+BR120.toString());
		liste.addToChamps("BR121	2	"+BR121.toString());
		liste.addToChamps("BR122	2	"+BR122.toString());
		liste.addToChamps("BR123	2	"+BR123.toString());
		liste.addToChamps("BR124	2	"+BR124.toString());
		liste.addToChamps("BR125	2	"+BR125.toString());
		liste.addToChamps("BR126	2	"+BR126.toString());
		liste.addToChamps("BR127	2	"+BR127.toString());
		liste.addToChamps("BR128	2	"+BR128.toString());
		liste.addToChamps("BR129	2	"+BR129.toString());
		liste.addToChamps("BR130	2	"+BR130.toString());
		liste.addToChamps("BR131	2	"+BR131.toString());
		liste.addToChamps("BR132	2	"+BR132.toString());
		liste.addToChamps("BR133	2	"+BR133.toString());
		liste.addToChamps("BR134	2	"+BR134.toString());
		liste.addToChamps("BR135	2	"+BR135.toString());
		liste.addToChamps("BR136	2	"+BR136.toString());
		liste.addToChamps("BR137	2	"+BR137.toString());
		liste.addToChamps("BR138	2	"+BR138.toString());
		liste.addToChamps("BR139	2	"+BR139.toString());
		liste.addToChamps("BR140	2	"+BR140.toString());
		liste.addToChamps("BR141	2	"+BR141.toString());
		liste.addToChamps("BR142	2	"+BR142.toString());
		liste.addToChamps("BR143	2	"+BR143.toString());
		liste.addToChamps("BR144	2	"+BR144.toString());
		liste.addToChamps("BR145	2	"+BR145.toString());
		liste.addToChamps("BR146	2	"+BR146.toString());
		liste.addToChamps("BR147	2	"+BR147.toString());
		liste.addToChamps("BR148	2	"+BR148.toString());
		liste.addToChamps("BR149	2	"+BR149.toString());
		liste.addToChamps("BR150	2	"+BR150.toString());
		liste.addToChamps("BR151	2	"+BR151.toString());
		liste.addToChamps("BR152	2	"+BR152.toString());
		liste.addToChamps("BR153	2	"+BR153.toString());
		liste.addToChamps("BR154	2	"+BR154.toString());
		liste.addToChamps("BR155	2	"+BR155.toString());
		liste.addToChamps("BR156	2	"+BR156.toString());
		liste.addToChamps("BR157	2	"+BR157.toString());



		//		for (int i = 0 ; i < liste.length() ; i++) 
		//			{ Terminal.ecrireStringln
		//				(liste.getGene(i).reverseTranslation(true)); }

		liste.printFile();
		Terminal.ecrireStringln("Recording done !");
	}
}
