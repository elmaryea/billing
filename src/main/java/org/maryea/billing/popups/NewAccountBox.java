package org.maryea.billing.popups;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.maryea.billing.content.BillingWindow;

public class NewAccountBox extends JDialog implements ActionListener{
	private AccountNamePanel accountNamePanel;
	private AddPayerPanel addPayerPanel;
	private AddChildPanel addChildPanel;
	
	public NewAccountBox(BillingWindow bw){
		super(bw, "New Account", Dialog.ModalityType.APPLICATION_MODAL);

		accountNamePanel = new AccountNamePanel();
		add(accountNamePanel);
		
		accountNamePanel.getCancelButton().addActionListener(this);
		accountNamePanel.getNextButton().addActionListener(this);
		accountNamePanel.getFinishButton().addActionListener(this);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText().equals("Cancel")){
			dispose();
		}else if(((JButton)e.getSource()).getText().equals("Next")){
			if(addChildPanel == null && accountNamePanel.isValidForm()){
				addChildPanel = new AddChildPanel();
				accountNamePanel.setEnabled(false);
				accountNamePanel.setVisible(false);
				add(addChildPanel);
				addChildPanel.getCancelButton().addActionListener(this);
				addChildPanel.getNextButton().addActionListener(this);
				addChildPanel.getFinishButton().addActionListener(this);
			}else if(addChildPanel != null){
				addPayerPanel = new AddPayerPanel();
				addChildPanel.setEnabled(false);
				addChildPanel.setVisible(false);
				add(addPayerPanel);
			}else{
				JOptionPane.showMessageDialog(this, "Some information is missing.");
			}
		}else{
			assert ((JButton)e.getSource()).getText().equals("Finish");
			
			dispose();
		}
	}
}
