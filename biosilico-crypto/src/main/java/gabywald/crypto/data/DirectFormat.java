package gabywald.crypto.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.data.composition.Sequence;

/**
 * ID:protein:nucletoid
* @author Gabriel Chandesris (2026)
*/
public class DirectFormat extends BiologicalFormat {
	
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		
		toReturn.append(this.someDatas[4]).append("\n").append(this.origin).append("\n");

		return toReturn.toString();
	}
	
	public static List<DirectFormat> fromString(String content) {
		String[] cont			= content.split("\n");
		List<DirectFormat> toReturn	= new ArrayList<DirectFormat>();
		
		for (int i = 0 ; i < cont.length ; i += 2) {
			String[] pathes = cont[i].split(":");
			String[] contes = cont[i+1].split(":");
			if (pathes.length != contes.length ) { return toReturn; }
			for (int j = 0 ; j < pathes.length ; j++) {
				DirectFormat df = new DirectFormat();
				df.setComment(pathes[j]);
				df.setSequence(new Sequence("", contes[j]));
				toReturn.add(df);
			}
		}
		return toReturn;
	}
}
