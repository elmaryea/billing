package org.maryea.billing.popups;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import org.maryea.billing.content.BillingWindow;

public class NewAccountBox extends JDialog implements ActionListener{
	private AccountNamePanel accountNamePanel;
	private AddPayerPanel addPayerPanel;
	private AddChildPanel addChildPanel;
	private BillingWindow bw;
	private JButton cancel, continueAction, finish;
	
	public NewAccountBox(BillingWindow bw){
		super(bw, "New Account", Dialog.ModalityType.APPLICATION_MODAL);
		cancel = new JButton("Cancel");
		add(cancel);
		pack();
		setVisible(true);
		setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancel)){
			dispose();
		}else if(e.getSource().equals(continueAction)){
			
		}else{
			assert e.getSource().equals(finish);
			
			dispose();
		}
	}
}
