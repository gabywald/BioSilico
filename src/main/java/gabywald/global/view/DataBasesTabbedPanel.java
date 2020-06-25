package gabywald.global.view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * This abstract class to provide a model for Tabbed Pane with a set of names. 
 * @author Gabriel Chandesris (2011)
 * @see PanelsFactory
 */
public abstract class DataBasesTabbedPanel extends JTabbedPane {
	private String[] databasesNames;
	private String[] enabledDBnames;
	/** [could be null] */
	private PanelsFactory factoryOfTabbedPanes;

	/**
	 * 
	 * @param databases (String[]) Set of names of databases / tabs. 
	 * @param factory (PanelsFactory) [could be null]How to build each subPanel depending of name. 
	 */
	protected DataBasesTabbedPanel(String[] databases, 
								   PanelsFactory factory) 
		{ this.init(databases, new String[0], factory); }
	
	/**
	 * 
	 * @param databases (String[]) Set of names of databases / tabs. 
	 * @param enabledDB (String[]) Set of names of 'enabled' databases / tabs. 
	 * @param factory (PanelsFactory) [could be null]How to build each subPanel depending of name. 
	 */
	protected DataBasesTabbedPanel(String[] databases, 
								   String[] enabledDB, 
								   PanelsFactory factory) 
		{ this.init(databases, enabledDB, factory); }
	
	/**
	 * Constructors Helpers. 
	 * @param databases (String[]) Set of names of databases / tabs. 
	 * @param enabledDB (String[]) Set of names of 'enabled' databases / tabs. 
	 * @param factory (PanelsFactory) [could be null]How to build each subPanel depending of name. 
	 */
	private void init(String[] databases, 
					  String[] enabledDB, 
					  PanelsFactory factory) {
		this.databasesNames			= databases;
		this.enabledDBnames			= enabledDB;
		this.factoryOfTabbedPanes	= factory;
		boolean isFactory = (this.factoryOfTabbedPanes != null);
		for (int i = 0 ; i < this.databasesNames.length ; i++) {
			String currentName		= this.databasesNames[i];
			JPanel newPanel			= null;
			if (isFactory) /** If a factory : panel pre-defined. */
				{ newPanel	= this.factoryOfTabbedPanes.getPanelWithName(currentName); }
			this.addTab(currentName, null, newPanel, currentName);
			this.setEnabledAt(i, this.isEnabledDatabase(currentName));
		}
	}
	
	/**
	 * To know if a database is enabled (if set of enabled has length 0 : always true). 
	 * @param name (String)
	 * @return (boolean)
	 */
	public boolean isEnabledDatabase(String name) {
		if (this.enabledDBnames.length == 0) { return true; }
		for (int i = 0 ; i < this.enabledDBnames.length ; i++) 
			{ if (name.equals(this.enabledDBnames[i]))
				{ return true; } }
		return false;
	}

}
