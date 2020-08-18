package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.model.chemicals.ChemicalsBuilder;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class ChemicalsTests {

	@Test
	void testChemicals() {
		IChemicals che = ChemicalsBuilder.build();
		
		Assertions.assertEquals(ChemicalsHelper.CHEMICAL_LENGTH, che.length());
		
		IntStream.range(0, che.length() )
			.forEach(i -> Assertions.assertEquals(0, che.getVariable(i)) );

		che.setVariable(42, 42);
		Assertions.assertEquals(42, che.getVariable( 42 ));
		
		che.setVarLessLess(42);
		Assertions.assertEquals(41, che.getVariable( 42 ));
		
		che.setVarPlusPlus(42);
		Assertions.assertEquals(42, che.getVariable( 42 ));
		
		che.setVarLess(42, 10);
		Assertions.assertEquals(32, che.getVariable( 42 ));
		
		che.setVarPlus(42, 20);
		Assertions.assertEquals(52, che.getVariable( 42 ));
		
		// ***** initialise for incorporation
		IntStream.range(0, che.length() )
			.forEach(i -> che.setVariable(i, 0) );
		IntStream.range(0, che.length() )
			.forEach(i -> Assertions.assertEquals(0, che.getVariable(i)) );
		IChemicals che2incorporate = ChemicalsBuilder.build();
		IntStream.range(0, che2incorporate.length() )
			.forEach(i -> che2incorporate.setVariable(i, 42) );
		IntStream.range(0, che2incorporate.length() )
			.forEach(i -> Assertions.assertEquals(42, che2incorporate.getVariable(i)) );
		
		che.incorporate(che2incorporate);
		
		IntStream.range(0, che.length() )
			.forEach(i -> Assertions.assertEquals(42, che.getVariable(i)) );
		
		// ***** initialise for incorporation level 2
		IntStream.range(0, che.length() )
			.forEach(i -> che.setVariable(i, 5) );
		
		IntStream.range(0, che.length() )
		.forEach(i -> Assertions.assertEquals(5, che.getVariable(i)) );
		
		IntStream.range(0, che.length() )
			.forEach(i -> che2incorporate.setVariable(i, 2222) );
		
		IntStream.range(0, che2incorporate.length() )
			.forEach(i -> Assertions.assertEquals(999, che2incorporate.getVariable(i)) );
		
		che.incorporate(che2incorporate);
		IntStream.range(0, che.length() )
			.forEach(i -> Assertions.assertEquals(999, che.getVariable(i)) );
		
		// ***** initialise for incorporation level 3
		IntStream.range(0, che.length() )
			.forEach(i -> che.setVariable(i, 5) );
		IntStream.range(0, che.length() )
			.forEach(i -> che2incorporate.setVariable(i, -500) );
		
		IntStream.range(0, che2incorporate.length() )
			.forEach(i -> Assertions.assertEquals(0, che2incorporate.getVariable(i)) );
		
		che.incorporate(che2incorporate);
		IntStream.range(0, che.length() )
			.forEach(i -> Assertions.assertEquals(5, che.getVariable(i)) );
	}

}
