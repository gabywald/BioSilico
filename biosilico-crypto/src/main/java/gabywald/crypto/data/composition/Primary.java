package gabywald.crypto.data.composition;

/**
 * <i>'Third Party Annotation (TPA) and Transcriptome Shotgun Assembly (TSA)'</i>
 * @author Gabriel Chandesris (2011)
 * @see gabywald.crypto.data.GenBank
 * @see gabywald.crypto.data.Embl
 */
public class Primary {
	/** Some useful attributes [refSeqSpanStart, refSeqSpanEnd, primarySpanStart, primarySpanEnd]... */
	private int[] someDatas;
	/** Primary Identifier / other data... */
	private String primaryID, compos;

	/**
	 * Constructor for a primary record.
	 * @param refSeqStart (int)
	 * @param refSeqEnd (int)
	 * @param iden (String) Primary Identifier. 
	 * @param spanStart (int)
	 * @param spanEnd (int)
	 */
	public Primary(int refSeqStart, 
				   int refSeqEnd, 
				   String iden, 
				   int spanStart, 
				   int spanEnd) 
		{ this.init(refSeqStart, refSeqEnd, iden, 
					spanStart, spanEnd, null); }
	
	/**
	 * Constructor for a primary record. 
	 * @param refSeqStart (String) HAS TO be parseable into an integer. 
	 * @param refSeqEnd (String) HAS TO be parseable into an integer. 
	 * @param iden (String) Primary Identifier. 
	 * @param spanStart (String) HAS TO be parseable into an integer. 
	 * @param spanEnd (String) HAS TO be parseable into an integer. 
	 */
	public Primary(String refSeqStart, 
				   String refSeqEnd, 
				   String iden, 
				   String spanStart, 
				   String spanEnd) 
		{ this.init(Integer.parseInt(refSeqStart), 
					Integer.parseInt(refSeqEnd), iden, 
					Integer.parseInt(spanStart), 
					Integer.parseInt(spanEnd), null); }
	
	/**
	 * Constructor for a primary record.
	 * @param refSeqStart (int)
	 * @param refSeqEnd (int)
	 * @param iden (String) Primary Identifier. 
	 * @param spanStart (int)
	 * @param spanEnd (int)
	 * @param comp (String)
	 */
	public Primary(int refSeqStart, 
				   int refSeqEnd, 
				   String iden, 
				   int spanStart, 
				   int spanEnd, 
				   String comp) 
		{ this.init(refSeqStart, refSeqEnd, iden, 
					spanStart, spanEnd, comp); }
	
	/**
	 * Constructor for a primary record. 
	 * @param refSeqStart (String) HAS TO be parseable into an integer. 
	 * @param refSeqEnd (String) HAS TO be parseable into an integer. 
	 * @param iden (String) Primary Identifier. 
	 * @param spanStart (String) HAS TO be parseable into an integer. 
	 * @param spanEnd (String) HAS TO be parseable into an integer. 
	 * @param comp (String)
	 */
	public Primary(String refSeqStart, 
				   String refSeqEnd, 
				   String iden, 
				   String spanStart, 
				   String spanEnd, 
				   String comp) 
		{ this.init(Integer.parseInt(refSeqStart), 
					Integer.parseInt(refSeqEnd), iden, 
					Integer.parseInt(spanStart), 
					Integer.parseInt(spanEnd), comp); }
	
	
	/**
	 * Constructors Helper. 
	 * @param refSeqStart (int)
	 * @param refSeqEnd (int)
	 * @param iden (String)
	 * @param spanStart (int)
	 * @param spanEnd (int)
	 * @param comp (String)
	 */
	private void init(int refSeqStart, 
					 int refSeqEnd, 
					 String iden, 
					 int spanStart, 
					 int spanEnd, 
					 String comp) {
		this.someDatas		= new int[4];
		this.someDatas[0]	= refSeqStart;
		this.someDatas[1]	= refSeqEnd;
		this.someDatas[2]	= spanStart;
		this.someDatas[3]	= spanEnd;
		this.primaryID		= iden;
		this.compos			= comp;
	}
	
	/** @deprecated Use another toString*() !! */
	public String toString() { return this.toStringGeneBank(); }
	
	public String toStringEMBL() {
		String toReturn = new String("");
		/** 
AH   LOCAL_SPAN     PRIMARY_IDENTIFIER     PRIMARY_SPAN     COMP
AS   1-426          AC004528.1             18665-19090         
AS   427-526        AC001234.2             1-100            c
AS   527-1000       TI55475028             not_available
		 */
		String refSeq01	= this.someDatas[0]+"-"+this.someDatas[1];
		refSeq01 = Sequence.completeDataWith(refSeq01, ' ', false, 10);
		toReturn += "AS   "+refSeq01+"     ";
		toReturn += this.primaryID+"             ";
		
		String refSeq02	= this.someDatas[2]+"-"+this.someDatas[3];
		refSeq02 = Sequence.completeDataWith(refSeq02, ' ', false, 12);
		toReturn += refSeq02;
		
		if (this.compos != null) 
			{  toReturn += "     "+Sequence.completeDataWith(this.compos, 
												' ', false, 4); }
		
		return toReturn;
	}
	
	public String toStringGeneBank() {
		String toReturn = new String("");
		// '            1097-1212           BX679671.7         19083-19198'
		String refSeq	= this.someDatas[0]+"-"+this.someDatas[1];
		refSeq = Sequence.completeDataWith(refSeq, ' ', false, 9);
		
		toReturn += ComposUtils.BEGIN_EMPTY_LINE+refSeq+"           ";
		toReturn += this.primaryID+"         ";
		
		String refSeq02	= this.someDatas[2]+"-"+this.someDatas[3];
		refSeq02 = Sequence.completeDataWith(refSeq02, ' ', false, 12);
		toReturn += refSeq02;
		
		if (this.compos != null) 
			{  toReturn += "        "+Sequence.completeDataWith(this.compos, 
															' ', false, 4); }
		
		return toReturn;
	}
}
