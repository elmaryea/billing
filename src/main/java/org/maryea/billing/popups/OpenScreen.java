package org.maryea.billing.popups;

import java.io.File;
import javax.swing.JFileChooser;

public class OpenScreen extends JFileChooser{

	private static final long serialVersionUID = 4377173565014208060L;

	public OpenScreen(){
		super();
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setCurrentDirectory(new File(System.getProperty("user.home")));
	}
}
