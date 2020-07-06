package gabywald.biosilico.interfaces;

/**
 * This interface defines for a given structure the use of a record File for current list. 
 * <br>NOTE 20100420 : path to the file must be explicitely indicated in a static String. 
 * @author Gabriel Chandesris (2010)
 */
public interface StructureRecordFile {
	/** To load the content of the File into the list (create file if does not exists). */
	public void readFile();
	/** To write the content of the File. */
	public void printFile();
	/** To add a line. */
	public void addToChamps(String line);
	/** To change a given numberedline. */
	public void setChamps(int index,String line);
	/** To remove a line. */
	public void removeChamps(int i);
}
