package gabywald.creatures.genetics.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1 {
	private String type;
	private UnsignedByte[] header;
	private int attemptedLength;
	private List<UnsignedByte> contents	= new ArrayList<UnsignedByte>();
	private List<String> contentsSTR	= new ArrayList<String>();
	private int haserror		= 0;
	
	public GeneCreatures1(String type, UnsignedByte[] header, int attemptedLength) {
		this.type = type;
		this.header = header;
		this.attemptedLength = attemptedLength;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("GeneCreatures1 ( ")	.append(this.type).append(" , ")
										.append( Arrays.asList(this.header).toString() )
										.append(" )").append("\t contents: ");
		if (this.contentsSTR.size() > 0)
			{ sb.append( this.contentsSTR.toString() ); }
		else { sb.append( this.contents.toString() ); }
		sb.append("\n");
		if (this.haserror > 0) {
			sb.append("\t has (").append(this.haserror).append(") errors \n");
		}
		
		return sb.toString();
	}
	
	public String printInline() {
		StringBuilder sb = new StringBuilder();
		
		sb	.append(this.type).append(" : ")
			.append( Arrays.asList(this.header).toString() ).append(" => ");
		if (this.contentsSTR.size() > 0)
			{ sb.append( this.contentsSTR.toString() ); }
		else { sb.append( this.contents.toString() ); }
		if (this.haserror > 0) {
			sb.append("\t has (").append(this.haserror).append(") errors ");
		}
		
		return sb.toString();
	}
	
	// TODO complete with autocheck

	public String getType() 
		{ return this.type; }


	public UnsignedByte[] getHeader() 
		{ return this.header; }
	
	public int getAttemptedLength() 
		{ return this.attemptedLength; }

	public boolean addContentSTR(String content) 
		{ return this.contentsSTR.add(content); }
	
	public boolean addContent(UnsignedByte content) 
		{ return this.contents.add(content); }

	public boolean addContents(List<UnsignedByte> contents) 
		{ return this.contents.addAll(contents); }
	
	public boolean addContents(UnsignedByte... contents) 
		{ return this.addContents(Arrays.asList(contents)); }

	public int getHaserror() 
		{ return this.haserror; }

	public void oneMoreError() 
		{ this.haserror++; }
	
}
