package gabywald.biosilico.model;

import gabywald.biosilico.exceptions.BrainLengthException;

/**
 * Builder for Brain Model class. 
 * @author Gabriel Chandesris (2020)
 */
public class BrainBuilder {
	
	/** Avoid BrainBuilder instanciation. */
	private BrainBuilder() { ; }
	
	public static Brain brainBuilder() throws BrainLengthException {
		return BrainBuilder.brainBuilder(Brain.MAX_HEIGHT, Brain.MAX_WIDTH, 0);
	}

	public static Brain brainBuilder(int height, int width) throws BrainLengthException {
		return BrainBuilder.brainBuilder(height, width, 0);
	}
	
	public static Brain brainBuilder(int height, int width, int depth) throws BrainLengthException {
		if (height > Brain.MAX_HEIGHT)	{ height	= Brain.MAX_HEIGHT; }
		if (width > Brain.MAX_WIDTH)	{ width		= Brain.MAX_WIDTH; }
		if (depth > Brain.MAX_DEPTH)	{ depth		= Brain.MAX_DEPTH; }
		if (height < 0)		{ throw new BrainLengthException("Height cannot be under 0. "); }
		if (width < 0)		{ throw new BrainLengthException("Width cannot be under 0. "); }
		if (depth < 0)		{ throw new BrainLengthException("Depth cannot be under 0. "); }
		return new Brain(height, width, depth);
	}
	
	private static Brain instance = null;
	
	public static Brain getBrainExampleInstance() {
		if (BrainBuilder.instance == null) {
			try {
				BrainBuilder.instance = BrainBuilder.brainBuilder();
			} catch (BrainLengthException e) {
				// e.printStackTrace();
				BrainBuilder.instance = null;
			}
		}
		return BrainBuilder.instance;
	}
	
}
