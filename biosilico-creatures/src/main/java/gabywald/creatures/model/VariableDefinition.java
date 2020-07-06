package gabywald.creatures.model;

import gabywald.global.exceptions.MessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableDefinition {
	private String numberHexa, classOfVar, nameOfVar, hlValueHexa;
	private int numberInt, hlValueInt;
	
	/** "00	0	None	none	F8	248" */
	public static final Pattern vdLineReader01 = 
			Pattern.compile("([0-9A-F]+)\t([0-9]+)\t([a-zA-Z\\? ]*)\t([a-zA-Z 0-9\\-\\+'\\(\\)]+)\t([0-9A-F]+)\t([0-9]+)");
	
	/** "49-4F	73-79	Unused	unused	FF	255" */
	public static final Pattern vdLineReader02 = 
		Pattern.compile("([0-9A-F]+\\-[0-9A-F]+)\t([0-9]+\\-[0-9]+)\t([a-zA-Z\\? ]*)\t([a-zA-Z 0-9\\-\\+']+)\t([0-9A-F]+)\t([0-9]+)");

	public static List<VariableDefinition> loadVariableDefinitions(String content) throws MessageException {
		List<VariableDefinition> toReturn = new ArrayList<VariableDefinition>();
		Matcher matcher01 = VariableDefinition.vdLineReader01.matcher(content);
		Matcher matcher02 = VariableDefinition.vdLineReader02.matcher(content);
		if (matcher01.matches()) {
			VariableDefinition vd = new VariableDefinition();
			vd.numberHexa		= matcher01.group(1);
			vd.numberInt		= Integer.parseInt(matcher01.group(2));
			vd.classOfVar		= matcher01.group(3);
			vd.nameOfVar		= matcher01.group(4);
			vd.hlValueHexa		= matcher01.group(5);
			vd.hlValueInt		= Integer.parseInt(matcher01.group(6));
			toReturn.add(vd);
		} 
		else if (matcher02.matches()) {
			// String tmpNumberHexa	= matcher02.group(1);
			String tmpNumberInt		= matcher02.group(2);
			String classe			= matcher02.group(3);
			String name				= matcher02.group(4);
			String hlValueH			= matcher02.group(5);
			int hlValueI			= Integer.parseInt(matcher02.group(6));
			// System.out.println( tmpNumberHexa + "\t" + tmpNumberInt);
			String[] splitter = tmpNumberInt.split("-");
			int start = Integer.parseInt(splitter[0]);
			int stopp = Integer.parseInt(splitter[1]);
			for (int i = start ; i <= stopp ; i++) {
				VariableDefinition vd = new VariableDefinition();
				vd.numberHexa		= Integer.toHexString(i);
				vd.numberInt		= i; /** !! */
				vd.classOfVar		= classe;
				vd.nameOfVar		= name;
				vd.hlValueHexa		= hlValueH;
				vd.hlValueInt		= hlValueI;
				toReturn.add(vd);
			} /** END "for (int i = start ; i <= stopp ; i++)" */
		}
		else { throw new MessageException("Not a variable Line !"); }
		
		return toReturn;
	}
	
	private VariableDefinition() {
		this.numberHexa		= "00";
		this.numberInt		= 0;
		this.classOfVar		= "None";
		this.nameOfVar		= "none";
		this.hlValueHexa	= "FF";
		this.hlValueInt		= 255;
	}
	
	protected VariableDefinition(	String numberH, int numberI, 
									String classe, String name, 
									String hlValueH, int hlValueI) {
		this.numberHexa		= numberH;
		this.numberInt		= numberI;
		this.classOfVar		= classe;
		this.nameOfVar		= name;
		this.hlValueHexa	= hlValueH;
		this.hlValueInt		= hlValueI;
	}

	public String getNumberHexa()	{ return this.numberHexa; }
	public String getClassOfVar()	{ return this.classOfVar; }
	public String getNameOfVar()	{ return this.nameOfVar; }
	public String getHlValueHexa()	{ return this.hlValueHexa; }
	public int getNumberInt()		{ return this.numberInt; }
	public int getHlValueInt()		{ return this.hlValueInt; }

	
	public String toString() {
		String toReturn = new String("");
		
		toReturn += this.numberHexa + "\t" + this.numberInt + "\t" + 
					this.classOfVar + "\t" + this.nameOfVar + "\t" + 
					this.hlValueHexa + "\t" + this.hlValueInt;
		
		return toReturn;
	}
}
