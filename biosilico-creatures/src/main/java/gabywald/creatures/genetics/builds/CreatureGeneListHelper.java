package gabywald.creatures.genetics.builds;

import java.util.List;
import java.util.stream.Collectors;

import gabywald.creatures.genetics.BrainLobeGene;
import gabywald.creatures.launcher.CreaturesLauncher.GeneDenomination;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public abstract class CreatureGeneListHelper {
	
//	public static int getCountOf(List<ICreatureGene> list, int type, int subtype) 
//		{ return CreatureGeneListHelper.getCountOf(list, new UnsignedByte(type), new UnsignedByte(subtype)); }

	public static int getCountOf(List<ICreatureGene> list, UnsignedByte type, UnsignedByte subtype) {
//		List<Boolean> toSelectBoolean = list.stream()
//			.map( elt -> elt.getType().equals(type) && elt.getSubtype().equals(subtype) )
//			.collect(Collectors.toList());
		List<ICreatureGene> toSelect = list.stream()
				.filter( elt -> elt.getType().equals(type) && elt.getSubtype().equals(subtype) )
				.collect(Collectors.toList());
		return toSelect.size();
	}
	
	
	
	private static List<ICreatureGene> getSubListOf(List<ICreatureGene> list, UnsignedByte type, UnsignedByte subtype) {
		return list.stream()
				.filter( elt -> elt.getType().equals(type) && elt.getSubtype().equals(subtype) )
				.collect(Collectors.toList());
	}



	public static List<ICreatureGene> getSubListOf(List<ICreatureGene> list, GeneDenomination geneDenom) {
		return CreatureGeneListHelper.getSubListOf(list, geneDenom.getType(), geneDenom.getSubt()) ;
	}
	
//	public static List<? extends ICreatureGene> getFilered(List<ICreatureGene> list, Class<? extends ICreatureGene> classeOfGene) {
//		return CreatureGeneListHelper.getSubListOf(list.stream()
//				.map( elt -> (elt instanceof classeOfGene)?(classeOfGene)elt:null)
//				.filter( elt -> (elt != null))
//				.collect(Collectors.toList()));
//	}
	
	public static BrainLobeGene getBrainLobeGene(List<BrainLobeGene> lobes, int ub) {
		for (BrainLobeGene lobe : lobes) {
			if (lobe.getSequenceNumber().getValue() == ub) { return lobe; }
		}
		return null;
	}
}
