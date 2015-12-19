package org.maryea.billing.popups;

import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.model.Password;
import org.maryea.billing.model.UserHandler;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ChangePasswordBox{
	private ChangePasswordPanel panel;

	public ChangePasswordBox(BillingWindow bw){
		setBox(bw);
	}

	public void setBox(BillingWindow bw){
		panel = new ChangePasswordPanel();
		Object[] options = {"Cancel", "Okay"};
		int choice = JOptionPane.showOptionDialog(bw, panel, "Reset Password", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		if(choice == 1){
			if(!Arrays.equals(panel.getNewOne(), panel.getNewTwo())){
				JOptionPane.showMessageDialog(bw, "Your passwords did not match, try again.");
				setBox(bw);
			}else if(!UserHandler.verifyPassword(panel.getOrig(), bw.getModel().getCurrentUser().getUsername())){
				JOptionPane.showMessageDialog(bw, "That's not your current password.");
				setBox(bw);
			}else if(Arrays.equals(panel.getOrig(), panel.getNewTwo())){
				JOptionPane.showMessageDialog(bw, "Your new password must be different than your old password.");
				setBox(bw);
			}else{
				try{
					UserHandler.changePassword(bw.getModel().getRootStatement(), bw.getModel().getCurrentUser().getUsername(), Password.createHash(panel.getNewOne()));
				}catch(Exception e){
					JOptionPane.showMessageDialog(bw, "There was an error changing your password.");
				}
			}
		}
	}
}