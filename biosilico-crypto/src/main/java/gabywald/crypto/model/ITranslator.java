package gabywald.crypto.model;

/**
 * Interface for needs for translator (encoder & decoder). 
 * @author Gabriel Chandesris (2026)
 */
public interface ITranslator {
	
	public enum TranslatorEnum { simple, more, random }
	
	public String encode(String sequence, TranslatorEnum which);
	
	public String decode(String sequence, int start, int frame);

}
