package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a collection of poses which show movement. 
 * @author Gabriel Chandesris (2013)
 */
public class GaitGene extends CreatureGene {
	/**
	 * Old variant of constructor. 
	 * @param kind (int) { Normal Gait ; Special Gait [1-7] }
	 * @param pose1 (int) 8 poses / positions into { <empty>;Reach low near;Reach lowish near;Reach highish near;Reach high near;Reach low medium;Reach lowish medium;Reach highish medium;Reach high medium;Reach low far;Reach lowish far;Reach highish far;Reach high far;Stand;Approach 1;Approach 2;Approach 3;Approach 4;Pain Approach 1;Pain Approach 2;Pain Approach 3;Pain Approach 4;Tired Approach 1;Tired Approach 2;Tired Approach 3;Tired Approach 4;Fear Approach 1;Fear Approach 2;Fear Approach 3;Fear Approach 4;Anger Approach 1;Anger Approach 2;Anger Approach 3;Anger Approach 4;To Camera;Express general need;Express Pain;Express Hunger;Express Tired;Express Lonely;Express Fear;Toe Tap 1;Toe Tap 2;Angry 1;Angry 2;Swing Arms 1;Swing Arms 2;Shiver 1;Shiver 2;Crouch ; Lay egg;Walk Away 1;Walk Away 2;Walk Away 3;Walk Away 4;Flee 1;Flee 2;Flee 3;Flee 4;Rest;Sleep;East;West;Wander 1;Wander 2;Wander 3;Wander 4;Throw 1;Throw 2;Kick 1;Kick 2;Drum 1;Drum 2;Sneeze 1;Sneeze 2;Eat 1;Eat 2;Startled;Kiss;Dead;Limp 1;Limp 2;Limp 3;Limp 4;Drunken Stagger 1;Drunken Stagger 2;Drunken Stagger 3;Drunken Stagger 4;86;87;88;89;90;91;92;93;94;95;96;97;98;99 }
	 * @param pose2 (int) ...
	 * @param pose3 (int) ...
	 * @param pose4 (int) ...
	 * @param pose5 (int) ...
	 * @param pose6 (int) ...
	 * @param pose7 (int) ...
	 * @param pose8 (int) ...
	 * @deprecated Use {@link GaitGene#GaitGene(List)} instead !
	 */
	private GaitGene(int kind, 
					int pose1, int pose2, int pose3, int pose4, 
					int pose5, int pose6, int pose7, int pose8) {
		super(2, 4);
		this.data = new ArrayList<UnsignedByte>(9);
		this.data.add(new UnsignedByte(kind));
		this.data.add(new UnsignedByte(pose1));
		this.data.add(new UnsignedByte(pose2));
		this.data.add(new UnsignedByte(pose3));
		this.data.add(new UnsignedByte(pose4));
		this.data.add(new UnsignedByte(pose5));
		this.data.add(new UnsignedByte(pose6));
		this.data.add(new UnsignedByte(pose7));
		this.data.add(new UnsignedByte(pose8));
	}
	
	public GaitGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 4, datas, 9); /* size expected is 9 ! */ }
	
	public int getKind()	{ return this.data.get(0).getValue(); }
	public int getPose1()	{ return this.data.get(1).getValue(); }
	public int getPose2()	{ return this.data.get(2).getValue(); }
	public int getPose3()	{ return this.data.get(3).getValue(); }
	public int getPose4()	{ return this.data.get(4).getValue(); }
	public int getPose5()	{ return this.data.get(5).getValue(); }
	public int getPose6()	{ return this.data.get(6).getValue(); }
	public int getPose7()	{ return this.data.get(7).getValue(); }
	public int getPose8()	{ return this.data.get(8).getValue(); }
	
	public static String[] GAIT_GENE_KIND = {
		"Normal Gait", "Special Gait 1",
		"Special Gait 2", "Special Gait 3", 
		"Special Gait 4", "Special Gait 5", 
		"Special Gait 6", "Special Gait 7", 
	};
	
	public static String[] GAIT_GENE_POSES = {
		"<empty>", 
		"Reach low near", 
		"Reach lowish near", 
		"Reach highish near", 
		"Reach high near", 
		"Reach low medium", 
		"Reach lowish medium", 
		"Reach highish medium", 
		"Reach high medium", 
		"Reach low far", 
		"Reach lowish far", 
		"Reach highish far", 
		"Reach high far", 
		"Stand", 
		"Approach 1", "Approach 2", "Approach 3", "Approach 4", 
		"Pain Approach 1", "Pain Approach 2", "Pain Approach 3", "Pain Approach 4", 
		"Tired Approach 1", "Tired Approach 2", "Tired Approach 3", "Tired Approach 4", 
		"Fear Approach 1", "Fear Approach 2", "Fear Approach 3", "Fear Approach 4", 
		"Anger Approach 1", "Anger Approach 2", "Anger Approach 3", "Anger Approach 4", 
		"To Camera", 
		"Express general need", 
		"Express Pain", 
		"Express Hunger", 
		"Express Tired", 
		"Express Lonely", 
		"Express Fear", 
		"Toe Tap 1", "Toe Tap 2", 
		"Angry 1", "Angry 2", 
		"Swing Arms 1", "Swing Arms 2", 
		"Shiver 1", "Shiver 2", 
		"Crouch / Lay egg", 
		"Walk Away 1", "Walk Away 2", "Walk Away 3", "Walk Away 4", 
		"Flee 1", "Flee 2", "Flee 3", "Flee 4", 
		"Rest", 
		"Sleep", 
		"East", 
		"West", 
		"Wander 1", "Wander 2", "Wander 3", "Wander 4", 
		"Throw 1", "Throw 2", 
		"Kick 1", "Kick 2", 
		"Drum 1", "Drum 2", 
		"Sneeze 1", "Sneeze 2", 
		"Eat 1", "Eat 2", 
		"Startled", 
		"Kiss", 
		"Dead", 
		"Limp 1", "Limp 2", "Limp 3", "Limp 4", 
		"Drunken Stagger 1", "Drunken Stagger 2", "Drunken Stagger 3", "Drunken Stagger 4", 
		"86", "87", "88", "89", 
		"90", "91", "92", "93", "94", 
		"95", "96", "97", "98", "99"
	};
}
