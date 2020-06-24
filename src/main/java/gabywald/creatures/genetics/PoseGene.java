package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * This is the arrangement of body parts for a specific pose.
 * <br>Pose Genes are made up of a 15 (16 in C3) character string, the ordering of each element is as follows:
 * <ul>
 * 		<li>Direction</li>
 * 		<li>Head</li>
 * 		<li>Body</li>
 * 		<li>Left Thigh</li>
 * 		<li>Left Shin</li>
 * 		<li>Left Foot</li>
 * 		<li>Right Thigh</li>
 * 		<li>Right Shin</li>
 * 		<li>Right Foot</li>
 * 		<li>Left Humerus</li>
 * 		<li>Left Radius</li>
 * 		<li>Right Humerus</li>
 * 		<li>Right Radius</li>
 * 		<li>Tail Root</li>
 * 		<li>Tail Tip</li>
 * </ul>
 * <br>The meaning of each pose gene element is explained below:	
 * <ul>
 * 		<li>Direction - 0: Face away from screen</li>
 * 		<li>Direction - 1: Face out of screen</li>
 * 		<li>Direction - 2: Face right</li>
 * 		<li>Direction - 3: Face left</li>
 * 		<li>Direction: - ?: Face towards _IT_</li>
 * 		<li>Direction - !: Face away from _IT_</li>
 * 		<li>Head - ?: Look towards _IT_</li>
 * 		<li>For all Parts - 0, 1, 2, 3: Furthest down/back pose to furthest up/forward pose. Each body part has 4 degrees of rotation on it to the left and right.</li>
 * 		<li>For all Parts - X: No change in part arrangement </li>
 * </ul>
 * @author Gabriel Chandesris (2013)
 */
public class PoseGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param kind (int) Pose (see also GaitGene).
	 * @param info (String)
	 * @see GaitGene#GAIT_GENE_POSES
	 * @deprecated Use {@link PoseGene#PoseGene(List)} instead !
	 */
	private PoseGene(int kind, String info) {
		super(2, 3);
		this.data.add(new UnsignedByte(kind));
		this.addToData(info, 15); // Expected length is 15 (C3 : 16)
	}
	
	public PoseGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 3, datas, 1 + 15); /* size expected is 16 (C3 : 17) ! */ }

	public int getPose()		{ return this.data.get(0).getValue(); }
	public String getInfos()	{ return this.stringifyFromData(1, 15 /** C3: 16 !! */); } 
	
}
