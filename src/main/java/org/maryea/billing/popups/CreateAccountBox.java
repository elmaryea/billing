package org.maryea.billing.popups;

import org.maryea.billing.model.AccountHandler;
import org.maryea.billing.content.BillingWindow;
import javax.swing.JOptionPane;

public class CreateAccountBox{
	private CreateAccountPanel panel;

	public CreateAccountBox(BillingWindow b){
		panel = new CreateAccountPanel(b);
		Object[] options = {"Continue", "Cancel"};
		int choice = JOptionPane.showOptionDialog(b, panel, "New Account", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		if(choice == 0){
			if(!(panel.getFirstName().equals("") || panel.getLastName().equals(""))){
				//b.getDesktop().addAccount(panel.getFirstName(), panel.getLastName());
			}
		}
	}
}
