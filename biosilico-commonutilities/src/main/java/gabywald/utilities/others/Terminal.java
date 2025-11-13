package gabywald.utilities.others;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe utilisee pour les entree / sorties. 
 * <br/>N'est pas faire pour etre instanciee toutes les methodes sont statiques. 
 * <br/>Merci au CNAM : http://deptinfo.cnam.fr/Enseignement/CycleA/APA/
 * @author Gabriel Chandesris (2007)
 * @deprecated Use an alternative class to show some data !
 */
public abstract class Terminal {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Lire un String / chaine de caracteres. 
	 * @return (String)
	 */
	public static String lireString() {
		String tmp = "";
		/** char C = '\0'; */
		try { tmp = in.readLine(); }
		catch (IOException e) { Terminal.exceptionHandler(e); }
		return tmp;
	}
	
	/**
	 * Lire un entier. 
	 * @return (int)
	 */
	public static int lireInt(){
		int x = 0;
		try { x = Integer.parseInt(Terminal.lireString()); }
		catch (NumberFormatException e) { Terminal.exceptionHandler(e); }	
		return x ;
	}
	
	/**
	 * Lire un booleen. 
	 * @return (boolean)
	 */
	public static boolean lireBoolean() {
		boolean b = true;
		try { b = Boolean.valueOf(Terminal.lireString()).booleanValue(); }
		catch (NumberFormatException e) { Terminal.exceptionHandler(e); }	
		return b;
	}
	
	/**
	 * Lire un double. 
	 * @return (double)
	 */
	public  static double lireDouble() {
		double x = 0.0;
		try { x = Double.valueOf(Terminal.lireString()).doubleValue(); }
		catch (NumberFormatException e) { Terminal.exceptionHandler(e); }	
		return x ;
	}
	
	/**
	 * Lire un caractere. 
	 * @return (char)
	 */
	public  static char lireChar() {
		String tmp = Terminal.lireString();
		if (tmp.length() == 0) { return '\n'; }
		else { return tmp.charAt(0); }
	}
	
	/**
	 * Afficher un String.
	 * @param s (String)
	 */
	public static void ecrireString(String s) {
		try{ System.out.print(s); } 
		catch (Exception ex) { Terminal.exceptionHandler(ex); }
	}
	
	/**
	 * Afficher un String, puis un saut de ligne. 
	 * @param s (String)
	 */
	public static void ecrireStringln(String s) {
		Terminal.ecrireString(s);
		Terminal.sautDeLigne();
	}
	
	/**
	 * Afficher un entier. 
	 * @param i (int)
	 */
	public static void ecrireInt(int i)
		{ Terminal.ecrireString(""+i); }
	
	/**
	 * Afficher un entier puis un saut de ligne. 
	 * @param i (int)
	 */
	public static void ecrireIntln(int i) {
		Terminal.ecrireString(""+i);
		Terminal.sautDeLigne();
	}
	
	/**
	 * Afficher un booleen. 
	 * @param b (boolean)
	 */
	public static void ecrireBoolean(boolean b)
		{ Terminal.ecrireString(""+b); }
	
	/**
	 * Afficher un booleen puis un saut de ligne. 
	 * @param b (boolean)
	 */
	public static void ecrireBooleanln(boolean b){
		Terminal.ecrireString(""+b);
		Terminal.sautDeLigne();
	}
	
	/**
	 * Afficher un double. 
	 * @param d (double)
	 */
	public  static void ecrireDouble(double d)
		{ Terminal.ecrireString(""+d); }
	
	/**
	 * Afficher un double puis un saut de ligne. 
	 * @param d (double)
	 */
	public  static void ecrireDoubleln(double d) {
		Terminal.ecrireDouble(d);
		Terminal.sautDeLigne();
	}
	
	/**
	 * Afficher un caractere. 
	 * @param c (char)
	 */
	public  static void ecrireChar(char c)  // Afficher un caractere
		{ Terminal.ecrireString(""+c); }
	
	/**
	 * Afficher un caractere puis un saut de ligne. 
	 * @param c (char)
	 */
	public  static void ecrireCharln(char c)  // Afficher un caractere
	{
		Terminal.ecrireChar(c);
		Terminal.sautDeLigne();
	}
	
	/**
	 * Afficher un saut de ligne (passage a la ligne)
	 */
	public static void sautDeLigne() {
		try { System.out.println(); }
		catch(Exception ex) { Terminal.exceptionHandler(ex); }
	}
	
	/**
	 * Methode / procedure de levee d'exception. 
	 * @param ex (Exception)
	 */
	protected static void exceptionHandler(Exception ex){
		TerminalException err = new TerminalException(ex);
		throw err;
	}
	
	/**
	 * Afficher l'exception levee. 
	 * @param ex (Throwable)
	 */
	public static void ecrireException(Throwable ex){
		Terminal.ecrireString(ex.toString());
		ex.printStackTrace(System.err);
	}
}

/**
 * Un type d'exception pouvant etre levee par la classe Terminal. 
 */
@SuppressWarnings("serial")
class TerminalException extends RuntimeException{
	Exception ex;
	TerminalException(Exception e) { ex = e; }
	/** To avoid Warning. */
	// static final long serialVersionUID = 123456789;
}



