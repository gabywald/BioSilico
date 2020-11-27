package gabywald.creatures.genetics.main;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1 {
    private String type;
    private List<Byte> header;
    private int attemptedLength;
    private List<Byte> contents;
    private int haserror = 0;
    
    public GeneCreatures1(String type, List<Byte> header, int attemptedLength) {
    	this.type = type;
    	this.header = header;
    	this.attemptedLength = attemptedLength;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("GeneCreatures1 ( ")	.append(this.type).append(" , ")
    									.append( this.header.toString() )
    									.append(" )");
    	sb.append("\t contents: ").append( this.contents.toString() ).append("\n");
    	if (this.haserror > 0) {
    		sb.append("\t has (").append(this.haserror).append(" errors \n");
    	}
    	
    	return sb.toString();
    }
    
    public String printInline() {
	    StringBuilder sb = new StringBuilder();
    	
    	sb.append(this.type).append(" : ").append( this.header.toString() ).append(" => ").append( this.contents.toString() );
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

	public List<Byte> getHeader() 
		{ return this.header; }

	public void setHeader(List<Byte> header) 
		{ this.header = header; }

	public int getAttemptedLength() 
		{ return this.attemptedLength; }

	public void setAttemptedLength(int attemptedLength) 
		{ this.attemptedLength = attemptedLength; }

	public List<Byte> getContents() 
		{ return this.contents; }

	public void setContents(List<Byte> contents) 
		{ this.contents = contents; }

	public int getHaserror() 
		{ return this.haserror; }

	public void setHaserror(int haserror) 
		{ this.haserror = haserror; }
    
}
