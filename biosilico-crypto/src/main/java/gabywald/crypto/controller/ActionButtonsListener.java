package gabywald.crypto.controller;

import gabywald.crypto.model.GenBankFileCreator;
import gabywald.crypto.model.GenBankFileReader;
import gabywald.crypto.view.CryptoFrame;
import gabywald.global.data.Fichier;
import gabywald.global.data.Repertoire;
import gabywald.global.data.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class ActionButtonsListener implements ActionListener {
	private int type;
	private GenBankFileCreator gbfc;
	private GenBankFileReader gbfr;
	
	public ActionButtonsListener(int type) 
		{ this.type = type;this.gbfc = new GenBankFileCreator(); }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooseFile	= new JFileChooser(".");
		CryptoFrame cfInstance	= CryptoFrame.getInstance();
		switch(this.type){
		case(0): /** 'Select a file. ' */
			chooseFile.setDialogType(JFileChooser.OPEN_DIALOG);
			int returnVal01 = chooseFile.showOpenDialog(cfInstance);
			if (returnVal01 == JFileChooser.APPROVE_OPTION) {
				File file	= chooseFile.getSelectedFile();
				String path	= file.getAbsolutePath();
				/** String name = file.getName(); */
				cfInstance.setFileNamePath(path);
				
				Fichier toLoad = new Fichier(path);
				String content = new String("");
				for (int i = 0 ; i < toLoad.getNbLines() ; i++) 
					{ content += toLoad.getLine(i)+"\n"; }
				cfInstance.setClearContent(content);
			}
			break;
		case(1): /** 'Select a directory. ' */
			chooseFile.setDialogType(JFileChooser.OPEN_DIALOG);
			chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /** !! */
			int returnVal02 = chooseFile.showOpenDialog(CryptoFrame.getInstance());
			if (returnVal02 == JFileChooser.APPROVE_OPTION) {
				File file	= chooseFile.getSelectedFile();
				String path	= file.getAbsolutePath();
				/** String name = file.getName(); */
				cfInstance.setFileNamePath(path);
				
				List<String> filesListe = new ArrayList<String>();
				int lastPos = path.lastIndexOf(path.matches("^(.*?)\\\\(.*?)$")?"\\":"/");
				filesListe.add(path.substring(lastPos+1)+"/");
				ActionButtonsListener.getAwareDirectoriesContent(path, filesListe);
				
				String content			= new String("");
				for (int i = 0 ; i < filesListe.size() ; i++) 
					{ content += filesListe.get(i)+"\n"; }
				
				cfInstance.setClearContent(content);
			}
			break;
		case(2): /** 'Encrypt the file(s). ' */
			String toEncrypt	= cfInstance.getClearContent();
			String pathOfFile	= cfInstance.getFilePathToUse();
			/** System.out.println("\t'"+pathOfFile+"'\n\""+toEncrypt+"\""); */
			if (pathOfFile.equals("")) {
				/** Direct content treatment... */
				// this.gbfc.setContent(toEncrypt);
				this.gbfc.setPathAndContent("", toEncrypt);
				// cfInstance.setCryptContent(this.gbfc.getEncodedCont()
				// 							+"\n\n"+this.gbfc.getFullEncryption());
			} else {
				/** File or directory treatment... */
				pathOfFile			= pathOfFile.replaceAll("\\\\", "/"); /** !! */
				String current		= Utils.getExtension(new File(pathOfFile));
				if (current != null) {
					/** File treatment. */
					// /** String encodedPath = */this.gbfc.setPath(pathOfFile);
					// /** String encodedFile = */this.gbfc.setContent(toEncrypt);
					this.gbfc.setPathAndContent(pathOfFile, toEncrypt);
				} else {
					/** Directory(ies) treament. */
					String[] pathes = toEncrypt.split("\n");
					for (int i = 0 ; i <  pathes.length ; i++) {
						File tmp			= new File(pathes[i]);
						String pathOfDir	= pathes[i].replaceAll("\\\\", "/"); /** !! */
						
						if ( (tmp.exists()) && (tmp.isDirectory()) ) {
							// String encodedPath	=  this.gbfc.setPath(pathOfDir);
							if (i == 0) { this.gbfc.setPathAndContent(pathOfDir, ""); }
							else { this.gbfc.addPathAndContent(pathOfDir, ""); }
						} else {
							// String encodedPath			= this.gbfc.setPath(pathOfDir);
							// Fichier toGetClearContent	= new Fichier(pathes[i]);
							// String content				= new String("");
							// for (int j = 0 ; j < toGetClearContent.getNbLines() ; j++) 
							// 	{ content += toGetClearContent.getLine(j)+"\n"; }
							// String encodedFile			= this.gbfc.setContent(toEncrypt);
							if (i == 0) { this.gbfc.setPathAndContent(pathOfDir, toEncrypt); }
							else { this.gbfc.addPathAndContent(pathOfDir, toEncrypt); }
						}
					}
				} /** END (File|directory(ies)) treatment... */
			} /** END else / if (pathOfFile.equals("")) */
			cfInstance.setCryptContent(this.gbfc.getEncodedCont() + "\n\n" + this.gbfc.getFullEncryption());
			break;
		case(3): /** 'Decrypt the file(s). ' */
			String toDecrypt		= cfInstance.getClearContent();
			this.gbfr				= new GenBankFileReader(toDecrypt);
			
			cfInstance.setCryptContent(this.gbfr.getPath()+Utils.repeat("\t**\n", 5)+this.gbfr.getContent());
			// TODO ...
			break;
		case(4): /** 'Record ENcryption. ' */
			chooseFile.setDialogType(JFileChooser.SAVE_DIALOG);
			// TODO ...
			break;
		case(5): /** 'Record DEcryption. ' */
			chooseFile.setDialogType(JFileChooser.SAVE_DIALOG);
			// TODO ...
			break;
		}
	}
	
	private static void getAwareDirectoriesContent(String path, List<String> filesAndDirs) {
		Repertoire repDir		= new Repertoire(path);
		String[] listOfFiles	= repDir.listContent();
		for (int i = 0 ; i < listOfFiles.length ; i++) {
			String shortPath			= path.substring(path.lastIndexOf("\\")+1);
			String currentStampShort	= shortPath+"/"+listOfFiles[i];
			String currentStampLong		= path+"/"+listOfFiles[i];
			File tmp = new File(currentStampLong);
			
			String extension  = Utils.getExtension(tmp);
			if ( ( (extension != null) 
					&& (ActionButtonsListener.isAccepted(extension)) ) 
						|| (tmp.isDirectory()) )
				{ filesAndDirs.add(currentStampShort+((tmp.isDirectory())?"/":"")); }
			
			if (tmp.isDirectory()) {
				if (extension != null) {
					System.out.println(currentStampShort);
					System.out.println(extension);
				}
				ActionButtonsListener.getAwareDirectoriesContent
					(currentStampLong, filesAndDirs);
			} else { /** System.out.println("\t....."); */ }
		}
	}
	
	private static boolean isAccepted(String ext) {
		if (ext.equals("txt"))	{ return true; }
		if (ext.equals("conf"))	{ return true; }
		if (ext.equals("prt"))	{ return true; }
		if (ext.equals("pl"))	{ return true; }
		if (ext.equals("java"))	{ return true; }
		
		return false;
	}

}
