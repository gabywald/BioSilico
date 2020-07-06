package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * This specifies the appearance of the parts of the creature's body. 
 * <br>{ BodyPart ; Variant [C3: ; GenusOfDonor] }
 * @author Gabriel Chandesris (2013)
 */
public class AppearanceGene extends CreatureGene {
	/**
	 * Old variant of constructor. 
	 * @param bodyArea (int) Which part of body is concerned.
	 * @param partsIden (int) [spriteIden] Which sprite / graphical it is using. 
	 * @deprecated Use {@link AppearanceGene#AppearanceGene(List)} instead !
	 */
	private AppearanceGene(int bodyArea, int partsIden) {
		super(2, 3);
		this.data.add(new UnsignedByte(bodyArea));
		this.data.add(new UnsignedByte(partsIden));
	}
	
	public AppearanceGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 3, datas, 2); /* size expected is 2 ! */ }
	
	public int getBodyPart()	{ return this.data.get(0).getValue(); }
	public int getSpriteIden()	{ return this.data.get(1).getValue(); }
	
	public static String[] APPEARANCE_BODY_PARTS_C1 = 
		{ "Head", "Body", "Legs", "Arms", "Tail + create" };
	
	public static String[] APPEARANCE_BODY_PARTS_C2 = {
		"Head", "Body", 
		"Left Thigh", 
		"Left Shin", 
		"Left Foot", 
		"Right Thigh", 
		"Right Shin", 
		"Right Foot", 
		"Left Humerus", 
		"Left Radius", 
		"Right Humerus", 
		"Right Radius", 
		"Tail Root", 
		"Tail Tip"
	};
}
