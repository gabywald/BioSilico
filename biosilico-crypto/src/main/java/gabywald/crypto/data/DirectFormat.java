package gabywald.crypto.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.data.composition.Sequence;

/**
 * protein:@@@@@nucletoid:
* @author Gabriel Chandesris (2026)
*/
public class DirectFormat extends BiologicalFormat {
	
	public static String MAJOR_CUTTER = "@@@@@";
	public static String MINOR_CUTTER = ":";
	
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		
		toReturn// .append(this.getIdentification())
				.append(this.someDatas[4])
				.append( DirectFormat.MAJOR_CUTTER )
				.append(this.origin).append("\n");

		return toReturn.toString();
	}
	
	public static List<DirectFormat> fromString(String content) {
		String[] cont = content.split( DirectFormat.MAJOR_CUTTER );
		
		List<DirectFormat> toReturn	= new ArrayList<DirectFormat>();
		
		for (int i = 0 ; i < cont.length ; i += 2) {
			String[] pathes = cont[i].split( DirectFormat.MINOR_CUTTER );
			String[] contes = cont[i+1].split( DirectFormat.MINOR_CUTTER );
			int max = Math.max(pathes.length, contes.length);
			for (int j = 0 ; j < max ; j++) {
				DirectFormat df = new DirectFormat();
				// df.setIdentification(BiologicalUtils.generateIdentifier());
				df.setComment((j >= pathes.length)?"":pathes[j]);
				df.setSequence(new Sequence("", (j >= contes.length)?"":contes[j]));
				toReturn.add(df);
			}
		}
		return toReturn;
	}
}
