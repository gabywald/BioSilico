package gabywald.javabio.data.composition;

import java.util.ArrayList;

public class FeaturesListe extends ArrayList<Feature> {
	
	public FeaturesListe getFeaturesWith(String key) {
		FeatureDefinition fd = FeatureDefinition.getFromFactory(key);
		return this.getFeaturesWith(fd);
	}
	
	public FeaturesListe getFeaturesWith(FeatureDefinition fd) {
		FeaturesListe toReturn = new FeaturesListe();
		for (int i = 0 ; i < this.size() ; i++) {
			Feature current = this.get(i);
			if (current.hasDefinition(fd)) 
				{ toReturn.add(current); }
		}
		
		return toReturn;
	}
}
