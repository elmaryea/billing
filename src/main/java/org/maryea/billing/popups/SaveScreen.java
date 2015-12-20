package org.maryea.billing.popups;

import java.io.File;
import javax.swing.JFileChooser;

public class SaveScreen extends JFileChooser{

	private static final long serialVersionUID = 3728857703536844009L;

	public SaveScreen(String path){
		super(path);
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setCurrentDirectory(new File("/home/eric/Documents/Databases"));
	}
}

