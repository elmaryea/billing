package org.maryea.billing.content;

import org.maryea.billing.model.UserHandler;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.EmptyResizableIcon;
//import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
//import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntrySecondary;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;

public class Ribbon extends JRibbon{
	//TODO: create ribbonband for invoice and place it under account. It is only available
	//if an account or a payment is selected.

	private static final long serialVersionUID = -1500137129130554395L;
	RibbonTask accountTask, userTask;
	ArrayList<JCommandButton> commandButtons;
	JCommandButton addUserPrivilegeBtn, changePasswordBtn, newAccountBtn, newPaymentBtn, invoiceBtn, nextPaymentBtn, newBusinessBtn, logoutBtn, deleteAccountBtn;
	JRibbonBand userBand, accountEditBand, accountAddBand, accountActionBand;
	RibbonApplicationMenu menuButton;
	RibbonApplicationMenuEntryPrimary openEntry, saveEntry, closeEntry, quitEntry, logoutEntry;
	RibbonApplicationMenuEntrySecondary saveOptions;
	BillingWindow billingWindow;

	public Ribbon(BillingWindow bw){
		billingWindow = bw;

		createAppMenu();

		createButtons();

		createRibbonBands();

		JRibbonBand[] accountBands = {accountActionBand, accountAddBand, accountEditBand};
		JRibbonBand[] userBands = {userBand};
		
		accountTask = new RibbonTask("Account", accountBands);
		userTask = new RibbonTask("User", userBands);
		
		addTask(accountTask);
		addTask(userTask);

		setMaximumSize(new Dimension(1600, 150));
	}

	public void createAppMenu(){
		menuButton = new RibbonApplicationMenu();

		openEntry = new RibbonApplicationMenuEntryPrimary(new EmptyResizableIcon(32), "Open", new Open(), JCommandButton.CommandButtonKind.ACTION_ONLY);
		saveEntry = new RibbonApplicationMenuEntryPrimary(new EmptyResizableIcon(32), "Save", new Save(), JCommandButton.CommandButtonKind.ACTION_ONLY);
		closeEntry = new RibbonApplicationMenuEntryPrimary(new EmptyResizableIcon(32), "Close", new CloseFile(), JCommandButton.CommandButtonKind.ACTION_ONLY);
		quitEntry = new RibbonApplicationMenuEntryPrimary(new EmptyResizableIcon(32), "Quit", new Quit(), JCommandButton.CommandButtonKind.ACTION_ONLY);
		logoutEntry = new RibbonApplicationMenuEntryPrimary(new EmptyResizableIcon(32), "Logout", new Logout(), JCommandButton.CommandButtonKind.ACTION_ONLY);

		saveOptions = new RibbonApplicationMenuEntrySecondary(new EmptyResizableIcon(32), "Save As", new Save(), JCommandButton.CommandButtonKind.ACTION_ONLY);
		saveEntry.addSecondaryMenuGroup("", saveOptions);

		menuButton.addMenuEntry(openEntry);
		menuButton.addMenuEntry(saveEntry);
		menuButton.addMenuEntry(closeEntry);
		menuButton.addMenuEntry(logoutEntry);
		menuButton.addMenuEntry(quitEntry);

		setApplicationMenu(menuButton);
	}

	public void createButtons(){
		commandButtons = new ArrayList<JCommandButton>();

		//0
		newAccountBtn = new JCommandButton("New Account", null);
		newAccountBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//billingWindow.viewCreateAccountBox();
			}
		});
		commandButtons.add(newAccountBtn);
		//1
		newPaymentBtn = new JCommandButton("Add Payment", null);
		newPaymentBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		commandButtons.add(newPaymentBtn);
		//2
		deleteAccountBtn = new JCommandButton("Remove Account", null);
		deleteAccountBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int result = JOptionPane.showConfirmDialog(billingWindow, "Are you sure you want to remove this account?", "Remove Account", JOptionPane.YES_NO_OPTION);
				if(result == 0){
					//billingWindow.getDesktop().removeAccount();
				}
			}
		});
		commandButtons.add(deleteAccountBtn);
		//3
		invoiceBtn = new JCommandButton("Create Invoice", null);
		invoiceBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

			}
		});
		commandButtons.add(invoiceBtn);
		//4
		nextPaymentBtn = new JCommandButton("Set Next Payment", null);
		nextPaymentBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

			}
		});
		commandButtons.add(nextPaymentBtn);
		//5
		logoutBtn = new JCommandButton("Logout", null);
		logoutBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				billingWindow.getModel().logout();
				billingWindow.getLoginScreen().clearScreen();
				billingWindow.getLoginScreen().setEnabled(true);
				billingWindow.getLoginScreen().setVisible(true);
				billingWindow.getDesktop().setEnabled(false);
				billingWindow.getDesktop().setVisible(false);
			}
		});
		commandButtons.add(logoutBtn);
		//6
		addUserPrivilegeBtn = new JCommandButton("Add User Privilege", null);
		addUserPrivilegeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				String username = JOptionPane.showInputDialog(billingWindow, "Username:", "New Business", JOptionPane.QUESTION_MESSAGE);
				while(UserHandler.userExists(username) == false){
					JOptionPane.showMessageDialog(billingWindow, "That username doesn't exit.");
					username = JOptionPane.showInputDialog(billingWindow, "Business Name:", "New Business", JOptionPane.QUESTION_MESSAGE);
				}
				File currentFile = billingWindow.getModel().getCurrentFile();
				try{
					Scanner scanFile = new Scanner(currentFile);
					String dbName =scanFile.nextLine();
					scanFile.close();
					UserHandler.addUserPrivilege(dbName, username, billingWindow.getModel().getSF());
					UserHandler.updateCheckFile(dbName, username);
				}catch(Exception e){
					System.out.println("There was an issue getting the database name from the current file.");
					e.printStackTrace();
				}
			}
		});
		commandButtons.add(addUserPrivilegeBtn);
		//7
		changePasswordBtn = new JCommandButton("Change Password", null);
		changePasswordBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//billingWindow.viewChangePasswordBox();
			}
		});
		commandButtons.add(changePasswordBtn);
		//8
		newBusinessBtn = new JCommandButton("New Business", null);
		newBusinessBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(billingWindow.getModel().saveOption()){
					billingWindow.getModel().closeFile();
				}
				//billingWindow.viewNewFileBox();
			}
		});
		commandButtons.add(newBusinessBtn);
	}

	public void createRibbonBands(){
		userBand = new JRibbonBand("Account Actions", null);
		accountEditBand = new JRibbonBand("Edit", null);
		accountAddBand = new JRibbonBand("Add", null);
		accountActionBand = new JRibbonBand("Action", null);

		userBand = new JRibbonBand("Account Actions", null);
		accountEditBand = new JRibbonBand("Edit", null);
		accountAddBand = new JRibbonBand("Add", null);
		accountActionBand = new JRibbonBand("Action", null);
		
		userBand.addCommandButton(logoutBtn, RibbonElementPriority.TOP);
		userBand.addCommandButton(addUserPrivilegeBtn, RibbonElementPriority.MEDIUM);
		userBand.addCommandButton(changePasswordBtn, RibbonElementPriority.MEDIUM);
		userBand.addCommandButton(newBusinessBtn, RibbonElementPriority.MEDIUM);
		
		accountAddBand.addCommandButton(newAccountBtn, RibbonElementPriority.TOP);
		accountAddBand.addCommandButton(newPaymentBtn, RibbonElementPriority.MEDIUM);
		accountAddBand.addCommandButton(deleteAccountBtn, RibbonElementPriority.MEDIUM);
		
		accountActionBand.addCommandButton(invoiceBtn, RibbonElementPriority.TOP);
		accountActionBand.addCommandButton(nextPaymentBtn, RibbonElementPriority.MEDIUM);
		
		userBand.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(new CoreRibbonResizePolicies.None(userBand.getControlPanel()),
			new IconRibbonBandResizePolicy(userBand.getControlPanel())));
		accountEditBand.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(new IconRibbonBandResizePolicy(accountEditBand.getControlPanel())));
		accountAddBand.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(new CoreRibbonResizePolicies.None(accountAddBand.getControlPanel()),
			new IconRibbonBandResizePolicy(accountAddBand.getControlPanel())));
		accountActionBand.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(new CoreRibbonResizePolicies.None(accountActionBand.getControlPanel()),
			new IconRibbonBandResizePolicy(accountActionBand.getControlPanel())));
	}

	public void disable(){
		for(JCommandButton b : commandButtons){
			b.setEnabled(false);
		}
	}

	public void enable(){
		for(JCommandButton b : commandButtons){
			b.setEnabled(true);
		}
	}

	public void disableButton(String name){
		for(JCommandButton b : commandButtons){
			if(b.getText().equals(name)){
				b.setEnabled(false);
			}
		}
	}
	
	public void enableButton(String name){
		for(JCommandButton b : commandButtons){
			if(b.getText().equals(name)){
				b.setEnabled(true);
			}
		}
	}

	private class Save implements ActionListener{
		public Save(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			billingWindow.getModel().saveOption();
		}
	}
	private class Open implements ActionListener{
		public Open(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			billingWindow.viewOpenScreen();
		}
	}
	/*private class SaveAs implements ActionListener{
		public SaveAs(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			billingWindow.viewSaveScreen(billingWindow.getModel().getCurrentFile().getName());
		}
	}*/
	private class CloseFile implements ActionListener{
		public CloseFile(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			if(billingWindow.getModel().saveOption()){
				billingWindow.getModel().closeFile();
			}
		}
	}
	private class Quit implements ActionListener{
		public Quit(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			billingWindow.getModel().quit();
		}
	}
	private class Logout implements ActionListener{
		public Logout(){
			super();
		}
		public void actionPerformed(ActionEvent e){
			billingWindow.getModel().logout();
			billingWindow.getLoginScreen().clearScreen();
			billingWindow.getLoginScreen().setEnabled(true);
			billingWindow.getLoginScreen().setVisible(true);
			billingWindow.getDesktop().setEnabled(false);
			billingWindow.getDesktop().setVisible(false);
		}
	}
}