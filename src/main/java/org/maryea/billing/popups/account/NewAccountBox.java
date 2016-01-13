package org.maryea.billing.popups.account;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.model.Account;
import org.maryea.billing.model.AccountHandler;
import org.maryea.billing.model.Child;
import org.maryea.billing.model.Payer;

public class NewAccountBox extends JDialog implements ActionListener{
	private AccountNamePanel accountNamePanel;
	private Account newAccount;
	private AddPayerPanel addPayerPanel;
	private AddChildPanel addChildPanel;
	private LinkedList<String> initialResults;
	private String[][] childResults, payerResults;
	private BillingWindow bw;
	
	public NewAccountBox(BillingWindow bw){
		super(bw, "New Account", Dialog.ModalityType.APPLICATION_MODAL);

		this.bw = bw;
		
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
				initialResults = accountNamePanel.getResults();
				newAccount = new Account(initialResults.get(0), initialResults.get(1), initialResults.get(2), initialResults.get(3), 
						initialResults.get(4), initialResults.get(5), initialResults.get(6), initialResults.get(7), initialResults.get(8),
						initialResults.get(9), bw.getModel().getCurrentBusiness());
				accountNamePanel.setEnabled(false);
				accountNamePanel.setVisible(false);
				add(addChildPanel);
				addChildPanel.getCancelButton().addActionListener(this);
				addChildPanel.getNextButton().addActionListener(this);
				addChildPanel.getFinishButton().addActionListener(this);
			}else if(addChildPanel != null){
				addPayerPanel = new AddPayerPanel(initialResults);
				addChildPanel.setEnabled(false);
				addChildPanel.setVisible(false);
				add(addPayerPanel);
				addPayerPanel.getCancelButton().addActionListener(this);
				addPayerPanel.getFinishbutton().addActionListener(this);
			}else{
				JOptionPane.showMessageDialog(this, "Some information is missing.");
			}
		}else{
			assert ((JButton)e.getSource()).getText().equals("Finish");
			if(accountNamePanel.isValidForm()){
				initialResults = accountNamePanel.getResults();
				if(addChildPanel != null){
					childResults = addChildPanel.getResults();
					if(addPayerPanel != null){
						payerResults = addPayerPanel.getResults();
					}
				}
				AccountHandler.persistNewAccount(bw, newAccount, getChildren(), getPayers());
				dispose();
			}else{
				JOptionPane.showMessageDialog(this, "Some information is missing.");
			}
		}
	}
	
	public List<Child> getChildren(){
		List<Child> children = new LinkedList<Child>();
		Child toAdd;
		for(int i = 0; i < childResults.length; i++){
			toAdd = new Child(childResults[i][0], childResults[i][1], newAccount);
			children.add(toAdd);
		}
		return children;
	}
	
	public List<Payer> getPayers(){
		List<Payer> payers = new LinkedList<Payer>();
		Payer toAdd;
		for(int i = 0; i < payerResults.length; i++){
			toAdd = new Payer(payerResults[i][0], payerResults[i][1], payerResults[i][2], payerResults[i][3], newAccount);
			payers.add(toAdd);
		}
		return payers;
	}
}