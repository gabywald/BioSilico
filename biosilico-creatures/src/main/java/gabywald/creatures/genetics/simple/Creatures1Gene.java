package gabywald.creatures.genetics.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	/** Header (expected of size of 6 for C1). */
	private UnsignedByte[] header;
	private List<UnsignedByte> contents	= new ArrayList<UnsignedByte>();
	private List<String> contentsSTR	= new ArrayList<String>();
	private int haserror				= 0;
	
	public Creatures1Gene(GeneTypeSubType type, UnsignedByte[] header) {
		this.type = type;
		this.header = header;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("GeneCreatures1 ( ")	.append( this.type.getShortName() ).append(" , ")
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
	
	@Override
	public String printInline() {
		StringBuilder sb = new StringBuilder();
		
		sb	.append( this.type.getShortName() ).append(" : ")
			.append( Arrays.asList(this.header).toString() ).append(" => ");
		if (this.contentsSTR.size() > 0)
			{ sb.append( this.contentsSTR.toString() ); }
		else { sb.append( this.contents.toString() ); }
		if (this.haserror > 0) {
			sb.append("\t has (").append(this.haserror).append(") errors ");
		}
		
		return sb.toString();
	}
	
	public Creatures1Gene autocheck() {
		// if (this.attemptedLength == null) { print "attempted NOT defined !"; } 
		if (this.contents == null)		{ this.contents = new ArrayList<UnsignedByte>(); }
		if (this.contentsSTR == null)	{ this.contentsSTR = new ArrayList<String>(); }
		
		if (this.contentsSTR.size() > 0) {
			this.haserror = Creatures1Gene.applyCheckContent(this.contentsSTR, this.type.getAttemptedLengthC1(), String.class);
		} else if (this.contents.size() > 0) {
			this.haserror = Creatures1Gene.applyCheckContent(this.contents, this.type.getAttemptedLengthC1(), UnsignedByte.class);
		} else { ; }
		
		if (this.haserror > 0) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "ERROR: " + this.printInline());
		}
		
		return this;
	}
	
	/**
	 * 
	 * @param <T> Common class used here !
	 * @param lstContent
	 * @param attemptedLength
	 * @param classe
	 * @return Number of errors. 
	 */
	private static <T> int applyCheckContent(List<T> lstContent, int attemptedLength, Class<T> classe) {
		int haserror = 0;
		
		while (lstContent.size() < attemptedLength) {
			try { lstContent.add( classe.newInstance() ); } 
			// XXX NOTE : throw an exception here ?!
			catch (InstantiationException e)	{ e.printStackTrace(); } 
			catch (IllegalAccessException e)	{ e.printStackTrace(); }
			haserror++;
		}
		if (lstContent.size() > attemptedLength) {
			lstContent = lstContent.subList(attemptedLength, lstContent.size());
		}
		return haserror;
	}
	
	@Override
	public GeneTypeSubType getType() 
		{ return this.type; }

	@Override
	public UnsignedByte[] getHeader() 
		{ return this.header; }
	
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
