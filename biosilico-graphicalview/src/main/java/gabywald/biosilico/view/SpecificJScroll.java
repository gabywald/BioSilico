package gabywald.biosilico.view;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import gabywald.biosilico.genetics.Gene;
import gabywald.global.view.graph.GenericJScroll;

/**
 * 
 * @author Gabriel Chandesdris (2022)
 */
@SuppressWarnings("serial")
public class SpecificJScroll extends GenericJScroll {
	
	public static final List<String> oneTo99	= SpecificJScroll.getZeroTo(  99 );
	public static final List<String> oneTo999	= SpecificJScroll.getZeroTo( 999 );
	
	private static List<String> getZeroTo(int max) 
		{ return IntStream	.range(0, max + 1).boxed()
							.map(i -> (max == 99)?Gene.convert0to99( i ):Gene.convert0to999( i ))
							.collect(Collectors.toList()); }
	
	public static SpecificJScroll getSpecificJScroll0to99() 
		{ return new SpecificJScroll(SpecificJScroll.oneTo99); }
	
	public static SpecificJScroll getSpecificJScroll0to999() 
		{ return new SpecificJScroll(SpecificJScroll.oneTo999); }
	
	public SpecificJScroll(List<String> integerSTRListe) { 
		super(1, integerSTRListe);
	}

}
