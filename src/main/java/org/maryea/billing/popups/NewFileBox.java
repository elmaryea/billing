package org.maryea.billing.popups;

import org.maryea.billing.model.MainModel;
import org.maryea.billing.model.UserHandler;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class NewFileBox{
	public NewFileBox(MainModel m){
		String fileName = JOptionPane.showInputDialog(m.getBillingWindow(), "Business Name:", "New Business", JOptionPane.QUESTION_MESSAGE);
		File newFile = m.getBillingWindow().viewSaveScreen(fileName + ".bil");
		if(newFile != null && !newFile.equals(new File(""))){
			fileName = UserHandler.createDatabase(m.getSF(), m.getCurrentUser().getUsername(), newFile.getName().replaceFirst("[.][^.]+$", ""));
			UserHandler.createCheckFile(m.getCurrentUser().getUsername(), newFile.getName().replaceFirst("[.][^.]+$", ""));
			try{
				FileWriter out = new FileWriter(newFile, true);
				out.write(fileName + "\n");
				out.close();
			}catch(Exception e){
				System.out.println("Error writing to main file.");
			}

			m.openFile(newFile);
		}
	}

}
