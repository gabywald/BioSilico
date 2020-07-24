package gabywald.crypto.model.tests;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.model.EncodingNode;
import gabywald.crypto.model.EncodingNodeBuilder;
import gabywald.crypto.model.EncodingNodeException;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class EncodingNodeTests {

//	@Test
//	void testEncodingNode() {
//		String bases	= "acgt";
//		String values	= "A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z;1;2;3;4;5;6;7;8;9;0;1;2";
//		// a 3-4based-64elts example !
//		EncodingNode en	= new EncodingNode(3, bases.toCharArray(), values.split(";"));
//		
//		Arrays.asList(en.getCodons()).stream().forEach(System.out::println);
//		
//		System.out.println(EncodingNode.treeView(en));
//	}
	
	@Test
	void testEncodingNodeBuilder01() {
		EncodingNodeBuilder enb	= new EncodingNodeBuilder();
		EncodingNode en			= null;
		try {
			en = enb.build();
		} catch (EncodingNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assertions.assertNotNull( en );
		
		Arrays.asList(en.getCodons()).stream().forEach(System.out::println);
		
		System.out.println(EncodingNode.treeView(en));
	}
	
	@Test
	void testEncodingNodeBuilder02() {
		EncodingNodeBuilder enb	= new EncodingNodeBuilder();
		
		EncodingNodeException ene = Assertions.assertThrows(EncodingNodeException.class, () -> enb.values("A;B;;D;E;F;G".split(";")).build());
		// Assertions.assertThrows(expectedType, executable, message)
		Assertions.assertEquals("maxLvls (3) nbBases (4) expElts (64) real [7]", ene.getMessage());
	}
	
	// TODO complete these tests !! EncodingNodeTests

//	void testIsStart() {
//		fail("Not yet implemented");
//	}
//
//	void testIsStop() {
//		fail("Not yet implemented");
//	}
//
//	void testSetStart() {
//		fail("Not yet implemented");
//	}
//
//	void testSetStop() {
//		fail("Not yet implemented");
//	}
//
//	void testGetLevel() {
//		fail("Not yet implemented");
//	}
//
//	void testGetChilds() {
//		fail("Not yet implemented");
//	}
//
//	void testGetValuesAA() {
//		fail("Not yet implemented");
//	}
//
//	void testGetConcatenedAA() {
//		fail("Not yet implemented");
//	}
//
//	void testGetCodons() {
//		fail("Not yet implemented");
//	}
//
//	void testGetChildWith() {
//		fail("Not yet implemented");
//	}
//
//	void testGetCurrentCode() {
//		fail("Not yet implemented");
//	}
//
//	void testGetCurrentValue() {
//		fail("Not yet implemented");
//	}
//
//	void testGetCharacter() {
//		fail("Not yet implemented");
//	}
//
//	void testGetLeaves() {
//		fail("Not yet implemented");
//	}
//
//	void testGetCode() {
//		fail("Not yet implemented");
//	}
//
//	void testGetValue() {
//		fail("Not yet implemented");
//	}
//
//	void testGetNode() {
//		fail("Not yet implemented");
//	}
//
//	void testGetRoot() {
//		fail("Not yet implemented");
//	}
//
//	void testGetNumber() {
//		fail("Not yet implemented");
//	}
//
//	void testToString() {
//		fail("Not yet implemented");
//	}
//
//	void testEqualsEncodingNode() {
//		fail("Not yet implemented");
//	}
//
//	void testTreeView() {
//		fail("Not yet implemented");
//	}
//
//	void testMultiple() {
//		fail("Not yet implemented");
//	}

}
