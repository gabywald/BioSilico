package gabywald.crypto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import gabywald.crypto.controller.ActionButtonsListener;
import gabywald.global.view.GenericJFrame;

/**
 * 
 * @author Gabriel Chandesris (2011, 2022)
 */
@SuppressWarnings("serial")
public class CryptoFrame extends GenericJFrame {
	/** Font definition for complete information Text Area. */
	public static final Font COMPL_FONT		= new Font("Monospaced", Font.PLAIN, 11);
	private static CryptoFrame instance;	
	private JLabel fileNameAndPath;
	private JTextField filePathToUse;
	private JTextArea originalFileContent;
	private JTextArea cryptoedFileContent;
	/** [ selectAFile,selectADirectory,encrypt,decrypt ] */
	private JButton[] actionButtons;
	
	private static final String[] ACTION_BUTTONS_NAMES = {
		"Select a file. ", 
		"Select a directory. ", 
		"Encrypt the file(s). ", 
		"Decrypt the file(s). ", 
		"Record ENcryption. ", 
		"Record DEcryption. ", 
	};
	
	private CryptoFrame() {
		super("<<Genetic Cryptography>>");
		
		/** WEST part (buttons). */
		int localWidthWest		= 200;
		int actionButtonsHeight = 30;
		JPanel westPanel		= new JPanel();
		westPanel.setLayout(new GridLayout
				(CryptoFrame.FRAME_HEIGH/actionButtonsHeight, 1));
		westPanel.setSize(localWidthWest, CryptoFrame.FRAME_HEIGH);
		
		int actionButtonsNumber = CryptoFrame.ACTION_BUTTONS_NAMES.length;
		this.actionButtons = new JButton[actionButtonsNumber];
		for (int i = 0 ; i < actionButtonsNumber ; i++) { 
			this.actionButtons[i] = 
				new JButton(CryptoFrame.ACTION_BUTTONS_NAMES[i]);
			this.actionButtons[i].setSize(localWidthWest, actionButtonsHeight);
			this.actionButtons[i].addActionListener(new ActionButtonsListener(i));
			westPanel.add(this.actionButtons[i]);
		}
		this.add(westPanel, BorderLayout.WEST);
		
		/** Central Part. */
		int localWidthCent		= CryptoFrame.FRAME_WIDTH - localWidthWest;
		int localTextAreaHeight	= CryptoFrame.FRAME_HEIGH / 3;
		JPanel centPanel		= new JPanel();
		centPanel.setLayout(null);
		
		int cumulYPos = 0;
		
		this.fileNameAndPath	= new JLabel();
		this.fileNameAndPath.setSize(localWidthCent, actionButtonsHeight);
		this.fileNameAndPath.setLocation(0, cumulYPos);
		this.fileNameAndPath.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		cumulYPos += actionButtonsHeight;
		centPanel.add(this.fileNameAndPath);
		
		this.filePathToUse		= new JTextField();
		this.filePathToUse.setSize(localWidthCent, actionButtonsHeight);
		this.filePathToUse.setLocation(0, cumulYPos);
		cumulYPos += actionButtonsHeight;
		centPanel.add(this.filePathToUse);
		
		this.originalFileContent = new JTextArea();
		this.originalFileContent.setFont(CryptoFrame.COMPL_FONT);
		JScrollPane scroll01 = new JScrollPane(this.originalFileContent);
		scroll01.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll01.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll01.setSize(localWidthCent, localTextAreaHeight);
		scroll01.setLocation(0, cumulYPos);
		cumulYPos += localTextAreaHeight;
		centPanel.add(scroll01);
		
		this.cryptoedFileContent = new JTextArea();
		this.cryptoedFileContent.setFont(CryptoFrame.COMPL_FONT);
		JScrollPane scroll02 = new JScrollPane(this.cryptoedFileContent);
		scroll02.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll02.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll02.setSize(localWidthCent, localTextAreaHeight);
		scroll02.setLocation(0, cumulYPos);
		cumulYPos += localTextAreaHeight;
		centPanel.add(scroll02);
		
		this.add(centPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public static CryptoFrame getInstance() {
		if (CryptoFrame.instance == null) 
			{ CryptoFrame.instance = new CryptoFrame(); }
		return CryptoFrame.instance;
	}
	
	public void setFileNamePath(String pathName) { 
		this.fileNameAndPath.setText(pathName);
		int firstPos = pathName.indexOf(pathName.matches("^(.*?)\\\\(.*?)$")?"\\":"/");
		this.filePathToUse.setText(pathName.substring(firstPos));
	}
	
	public String getFilePathToUse() 
		{ return this.filePathToUse.getText(); }
	
	public void setClearContent(String content) 
		{ this.originalFileContent.setText(content); }

	public void setCryptContent(String content) 
		{ this.cryptoedFileContent.setText(content); }
	
	public String getClearContent() 
		{ return this.originalFileContent.getText(); }
	
	public String getCryptContent() 
		{ return this.cryptoedFileContent.getText(); }
	
}
