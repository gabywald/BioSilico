package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

/**
 * This specifies the appearance of the parts of the creature's body. 
 * <br>{ BodyPart ; Variant [C3: ; GenusOfDonor] }
 * @author Gabriel Chandesris (2013, 2026)
 */
public class AppearanceGene extends CreatureGene {
	/**
	 * Old variant of constructor. 
	 * @param bodyArea (int) Which part of body is concerned.
	 * @param partsIden (int) [spriteIden] Which sprite / graphical it is using. 
	 */
	public AppearanceGene(int bodyArea, int partsIden) {
		super(2, 3);
		this.data.add(new UnsignedByte(bodyArea));
		this.data.add(new UnsignedByte(partsIden));
	}
	
	/**
	 * New variant of constructor. 
	 * @param bodyArea (int) Which part of body is concerned.
	 * @param partsIden (int) [spriteIden] Which sprite / graphical it is using. 
	 */
	public AppearanceGene(UnsignedByte bodyArea, UnsignedByte partsIden) {
		super(2, 3);
		this.data.add(bodyArea);
		this.data.add(partsIden);
	}
	
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
