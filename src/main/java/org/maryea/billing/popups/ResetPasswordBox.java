package org.maryea.billing.popups;

import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.model.UserHandler;
import javax.swing.JOptionPane;

public class ResetPasswordBox{
	private ResetPasswordPanel panel;

	public ResetPasswordBox(BillingWindow bw){
		createBox(bw);
	}

	public void createBox(BillingWindow bw){
		panel = new ResetPasswordPanel();
		Object[] options = {"Cancel", "Send"};
		int choice = JOptionPane.showOptionDialog(bw, panel, "Forgot Password", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
			null, options, null);
		if(choice == 1){
			String user = panel.getUsername();
			if(UserHandler.userExists(user)){
				UserHandler.resetPassword(bw.getModel().getUsers(), user);
				
				JOptionPane.showMessageDialog(bw, "An email has been sent to you with a temporary password which you can use to log in.");
			}else{
				JOptionPane.showMessageDialog(bw, "That username doesn't exist. Try again.");
			}
		}
	}
}