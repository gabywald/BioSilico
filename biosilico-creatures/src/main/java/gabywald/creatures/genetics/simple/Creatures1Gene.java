package gabywald.creatures.genetics.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class Creatures1Gene implements ICreaturesGene {
	/** Type / SubType / Name. */
	private GeneTypeSubType type;
	/** Header (expected of size of 6 for C1, 7 for C2 and 8 for C3). */
	private List<UnsignedByte> header;
	private List<UnsignedByte> contents	= new ArrayList<UnsignedByte>();
	private List<String> contentsSTR	= new ArrayList<String>();
	private int haserror				= 0;
	
	public Creatures1Gene(GeneTypeSubType type, List<UnsignedByte> header) {
		this.type = type;
		this.header = header;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("GeneCreatures1 ( ")	.append( this.type.getShortName() ).append(" , ")
										.append( this.header.toString() )
										.append(" )").append("\t contents: ");
		sb.append( this.contents.toString() );
		sb.append("\n");
		if (this.haserror > 0) {
			sb.append("\t has (").append(this.haserror).append(") errors \n");
		}
		
		return sb.toString();
	}
	
	@Override
	public String printInline() {
		StringBuilder sb = new StringBuilder();
		
		sb	.append( this.type.getShortName() ).append(" : ")
			.append( this.header.toString() ).append(" => ");
		sb.append( this.contents.toString() );
		if (this.haserror > 0) 
			{ sb.append("\t has (").append(this.haserror).append(") errors "); }
		
		return sb.toString();
	}
	
	@Override
	public String print4human() {
		StringBuilder sb = new StringBuilder();
		
		sb	.append( this.type.getShortName() ).append(" : ")
			.append( this.header.toString() ).append(" => ");
		if (this.haserror > 0) 
			{ sb.append("\t has (").append(this.haserror).append(") errors "); }
		
		sb.append( "\n" ).append( this.contentsSTR.stream().collect(Collectors.joining("\n")) );
		
		return sb.toString();
	}
	
	@Override
	public ICreaturesGene autocheck() {
		// if (this.attemptedLength == null) { print "attempted NOT defined !"; } 
		if (this.contents == null)		{ this.contents = new ArrayList<UnsignedByte>(); }
		if (this.contentsSTR == null)	{ this.contentsSTR = new ArrayList<String>(); }
		
		if (this.contentsSTR.size() > 0) 
			{ this.haserror = CreaturesGenesHelper.applyCheckContent(this.contentsSTR, this.type.getAttemptedLengthC1(), String.class); }
		
		this.haserror = CreaturesGenesHelper.applyCheckContent(this.contents, this.type.getAttemptedLengthC1(), UnsignedByte.class);
		
		if (this.haserror > 0) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "ERROR: " + this.printInline());
		}
		
		return this;
	}
	
	@Override
	public GeneTypeSubType getType() 
		{ return this.type; }

	@Override
	public List<UnsignedByte> getHeader() 
		{ return Collections.unmodifiableList( this.header ); }
	
	@Override
	public List<UnsignedByte> getContents() 
		{ return Collections.unmodifiableList( this.contents ); }
	
	@Override
	public boolean addContent(UnsignedByte content) 
		{ return this.contents.add(content); }

	@Override
	public boolean addContents(List<UnsignedByte> contents) 
		{ return this.contents.addAll(contents); }
	
	@Override
	public boolean addContents(UnsignedByte... contents) 
		{ return this.addContents(Arrays.asList(contents)); }
	
	@Override
	public boolean addContentSTR(String content) 
		{ return this.contentsSTR.add(content); }
	
	@Override
	public boolean addContentSTR(String... contents) 
		{ return this.contentsSTR.addAll(Arrays.asList(contents)); }
	
	public int getHaserror() 
		{ return this.haserror; }

	public void oneMoreError() 
		{ this.haserror++; }
	
}
