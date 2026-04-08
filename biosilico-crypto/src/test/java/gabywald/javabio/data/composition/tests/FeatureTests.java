package gabywald.javabio.data.composition.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.composition.Feature;
import gabywald.crypto.data.composition.FeatureDefinition;

class FeatureTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFeature() {
		FeatureDefinition ftd_CDS = FeatureDefinition.getFromFactory("CDS");
		Assertions.assertNotNull( ftd_CDS );
		Feature ft = new Feature(ftd_CDS, "1..42");
		
		Assertions.assertNotNull( ft );
		Assertions.assertFalse( ft.hasDefinition( FeatureDefinition.getFromFactory("CDS") ) );
		Assertions.assertTrue( ft.hasDefinitionFeatureKey( FeatureDefinition.getFromFactory("CDS") ) );
		Assertions.assertTrue( ft.hasDefinition( ftd_CDS ) );
		Assertions.assertNull( ft.get("any") );
		
		Assertions.assertNotNull( ft.getQualifiers() );
		Assertions.assertEquals(0, ft.getQualifiers().size() );
		ft.addQualifier("translation", "PATHorTRANSLATEDsequence");
		Assertions.assertEquals(1, ft.getQualifiers().size() );
		Assertions.assertEquals("PATHorTRANSLATEDsequence", ft.get("translation") );
	}
	
	@Test
	void testFeatureDefinitions() {
		List<String> attemptedNames = Arrays.asList( 
										"attenuator", "C_region", "CAAT_signal", "CDS", "D-loop", "D_segment", 
										"enhancer", "exon", "gap", "GC_signal", "gene", "iDNA", "intron", 
										"J_segment", "LTR", "mat_peptide", "misc_binding", "misc_difference", 
										"misc_feature", "misc_recomb", "misc_RNA", "misc_signal", "misc_structure", 
										"mobile_element", "modified_base", "mRNA", "ncRNA", "N_region", 
										"old_sequence", "operon", "oriT", "polyA_signal", "polyA_site", "precursor_RNA", 
										"prim_transcript", "primer_bind", "promoter", "protein_bind", "RBS", 
										"repeat_region", "rep_origin", "rRNA", "S_region", "sig_peptide", "source", 
										"stem_loop", "STS", "TATA_signal", "terminator", "tmRNA", "transit_peptide", 
										"tRNA", "unsure", "V_region", "V_segment", "variation", "3'UTR", "5'UTR", 
										"-10_signal");
		attemptedNames.stream().forEach( str -> {
			FeatureDefinition ftd = FeatureDefinition.getFromFactory("CDS");
			Assertions.assertNotNull(ftd);
		});

	}

}
