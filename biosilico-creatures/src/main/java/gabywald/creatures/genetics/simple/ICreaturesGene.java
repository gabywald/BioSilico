package gabywald.creatures.genetics.simple;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface ICreaturesGene {
	
	public GeneTypeSubType getType();
	
	public List<UnsignedByte> getHeader();
	
	public List<UnsignedByte> getContents();
	
	public ICreaturesGene autocheck();
	
	public String printInline();
	
	public String print4human();
	
	public String toString();
	
	public boolean addContent(UnsignedByte content);
	public boolean addContents(List<UnsignedByte> contents);
	public boolean addContents(UnsignedByte... contents);

	public boolean addContentSTR(String strContent);
	public boolean addContentSTR(String... strContents);
	
}
