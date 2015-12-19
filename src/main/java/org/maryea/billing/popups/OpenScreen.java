package org.maryea.billing.popups;

import java.io.File;
import javax.swing.JFileChooser;

public class OpenScreen extends JFileChooser{
	public OpenScreen(){
		super();
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setCurrentDirectory(new File(System.getProperty("user.home")));
	}
}
