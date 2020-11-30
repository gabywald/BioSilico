package gabywald.creatures.genetics.main;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1 {
    private String type;
    private char[] header;
    private int attemptedLength;
    private List<Byte> contents	= new ArrayList<Byte>();
    private int haserror		= 0;
    
    public GeneCreatures1(String type, char[] header, int attemptedLength) {
    	this.type = type;
    	this.header = header;
    	this.attemptedLength = attemptedLength;
    }
    
    public static String[] convert(char[] header) {
    	String[] tableOfStr = new String[header.length];
    	for (int i = 0 ; i < header.length ; i++) 
    		{ tableOfStr[i] = new String( header[i] + "" ); }
    	return tableOfStr;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	

    	
    	sb.append("GeneCreatures1 ( ")	.append(this.type).append(" , [")
    									.append( String.join(", ", GeneCreatures1.convert(this.header) ) )
    									.append("] )");
    	sb.append("\t contents: ").append( this.contents.toString() ).append("\n");
    	if (this.haserror > 0) {
    		sb.append("\t has (").append(this.haserror).append(" errors \n");
    	}
    	
    	return sb.toString();
    }
    
    public String printInline() {
	    StringBuilder sb = new StringBuilder();
    	
    	sb	.append(this.type).append(" : [")
    		.append( String.join(", ", GeneCreatures1.convert(this.header) ) )
    		.append("] => ").append( this.contents.toString() );
    	if (this.haserror > 0) {
    		sb.append("\t has (").append(this.haserror).append(" errors ");
    	}
    	
    	return sb.toString();
    }
    
    // TODO complete with autocheck

	public String getType() 
		{ return this.type; }

	public void setType(String type) 
		{ this.type = type; }


	public char[] getHeader() 
		{ return this.header; }
	
	public void setHeader(char[] header) 
		{ this.header = header; }

	public int getAttemptedLength() 
		{ return this.attemptedLength; }

	public void setAttemptedLength(int attemptedLength) 
		{ this.attemptedLength = attemptedLength; }

	public boolean addContent(Byte content) 
		{ return this.contents.add(content); }

	public boolean addContents(List<Byte> contents) 
		{ return this.contents.addAll(contents); }

	public int getHaserror() 
		{ return this.haserror; }

	public void setHaserror(int haserror) 
		{ this.haserror = haserror; }
    
}
