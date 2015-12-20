package org.maryea.billing.popups;

import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.model.Password;
import org.maryea.billing.model.RequestFocusListener;
import org.maryea.billing.model.UserHandler;
import java.io.File;
/*import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;*/
import java.util.Arrays;
import javax.swing.JOptionPane;


public class CreateUserBox{
	private CreateUserPanel panel;
	private BillingWindow billingWindow;

	public CreateUserBox(BillingWindow bw){
		billingWindow = bw;
		createWindow();
	}

	public void createWindow(){
		panel = new CreateUserPanel();
		internalWindowCall();
	}

	public void createWindow(String user, String first, String last, String business, String email){
		panel = new CreateUserPanel();
		panel.setUsername(user);
		panel.setFirstName(first);
		panel.setLastName(last);
		panel.setBusinessName(business);
		panel.setEmailAddress(email);
		internalWindowCall();
	}

	public void createWindow(String first, String last, String business, String email){
		panel = new CreateUserPanel();
		panel.setFirstName(first);
		panel.setLastName(last);
		panel.setBusinessName(business);
		panel.setEmailAddress(email);
		internalWindowCall();
	}
	private void internalWindowCall(){
		Object[] options = {"Cancel", "Continue"};
		int choice = JOptionPane.showOptionDialog(billingWindow, panel, "Create Account", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(choice == 1){
			if(!panel.checkAll()){
				JOptionPane.showMessageDialog(billingWindow, "Some information is missing.");
				createWindow(panel.getUsername(), panel.getFirstName(), panel.getLastName(), panel.getBusinessName(), panel.getEmailAddress());
			}else if(!Arrays.equals(panel.getPassword(), panel.getRedundant())){
				JOptionPane.showMessageDialog(billingWindow, "Your passwords didn't match.\nPlease try again.");
				createWindow(panel.getUsername(), panel.getFirstName(), panel.getLastName(), panel.getBusinessName(), panel.getEmailAddress());
			}else if(UserHandler.usernameExists(panel.getUsername())){
				JOptionPane.showMessageDialog(billingWindow, "That username alreay exists.");
				createWindow(panel.getFirstName(), panel.getLastName(), panel.getBusinessName(), panel.getEmailAddress());
			}else{
				try{
					String hash = Password.createHash(panel.getPassword());
					UserHandler.createAccount(panel.getUsername(), hash);
					String db = UserHandler.createSQLEntry(billingWindow.getModel().getRootStatement(), panel.getUsername(), hash, panel.getFirstName(), panel.getLastName(), panel.getBusinessName(), panel.getEmailAddress());
					UserHandler.createCheckFile(panel.getUsername(), db);
					if(!db.equals("")){
						UserHandler.createMainFile(db, billingWindow.getModel().getOS());
					}
					billingWindow.getModel().loadUsers();
					billingWindow.getLoginScreen().validLogin(panel.getUsername());
					if(!db.equals("")){
						File dbName;
						if(billingWindow.getModel().getOS().indexOf("win") >= 0){
							dbName = new File(System.getenv("HOMEPATH") + panel.getBusinessName() + ".bil");
						}else if(billingWindow.getModel().getOS().indexOf("nux") >= 0  || billingWindow.getModel().getOS().indexOf("nix") >= 0){
							dbName = new File(System.getenv("HOME") + "/Documents/" + panel.getBusinessName() + ".bil");
						}else{
							dbName = new File(panel.getBusinessName() + ".bil");
						}
						billingWindow.getModel().openFile(dbName);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("There was an issue creating the account.");
				}
			}
		}
	}
	
}
