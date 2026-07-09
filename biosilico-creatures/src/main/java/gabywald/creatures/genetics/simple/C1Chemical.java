package gabywald.creatures.genetics.simple;

/**
 * ## ;"Number Hex";"Number Dec";"Class";"Name";"Half-Life Hex";"Half-Life Dec"
 * @author Gabriel Chandesris (2026)
 */
public class C1Chemical {

	private String numHEX, numDEC, classe, name, hlHEX, hlDEC;
	private int numDECasINT;
	
	/**
	 * Constructor (could only be called by same package). 
	 * @param numHEX
	 * @param numDEC
	 * @param classe
	 * @param name
	 * @param hlHEX
	 * @param hlDEC
	 */
	C1Chemical(String numHEX, String numDEC, String classe, String name, String hlHEX, String hlDEC) {
		this.numHEX = numHEX;
		this.numDEC = numDEC;
		this.classe = classe;
		this.name = name.replaceAll("\"", "");
		this.hlDEC = hlDEC;
		this.hlHEX = hlHEX;
		this.numDECasINT = Integer.parseInt(this.numDEC);
	}
	
	public String getNumHEX() { return this.numHEX; }
	public String getNumDEC() { return this.numDEC; }
	public String getClasse() { return this.classe; }
	public String getName()   { return this.name; }
	public String getHlHEX()  { return this.hlHEX; }
	public String getHlDEC()  { return this.hlDEC; }
	
	public int getNumlDECasINT() { return this.numDECasINT; }
}
