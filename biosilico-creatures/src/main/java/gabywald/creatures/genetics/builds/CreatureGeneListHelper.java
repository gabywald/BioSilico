package gabywald.creatures.genetics.builds;

import java.util.List;
import java.util.stream.Collectors;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
public abstract class CreatureGeneListHelper {
	
	public static int getCountOf(List<ICreatureGene> list, int type, int subtype) {
		return CreatureGeneListHelper.getCountOf(list, new UnsignedByte(type), new UnsignedByte(subtype));
	}

	public static int getCountOf(List<ICreatureGene> list, UnsignedByte type, UnsignedByte subtype) {
//		List<Boolean> toSelectBoolean = list.stream()
//			.map( elt -> elt.getType().equals(type) && elt.getSubtype().equals(subtype) )
//			.collect(Collectors.toList());
		List<ICreatureGene> toSelect = list.stream()
				.filter( elt -> elt.getType().equals(type) && elt.getSubtype().equals(subtype) )
				.collect(Collectors.toList());
		return toSelect.size();
	}
}
