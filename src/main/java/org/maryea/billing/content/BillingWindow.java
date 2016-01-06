package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import org.maryea.billing.popups.ChangePasswordBox;
import org.maryea.billing.popups.CreateUserBox;
import org.maryea.billing.popups.NewFileBox;
import org.maryea.billing.popups.NewPaymentBox;
import org.maryea.billing.popups.OpenScreen;
import org.maryea.billing.popups.ResetPasswordBox;
import org.maryea.billing.popups.SaveScreen;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;

public class BillingWindow extends JRibbonFrame{

	private static final long serialVersionUID = -6431594020696819995L;
	private ChangePasswordBox changePasswordBox;
	private CreateUserBox createUserBox;
	private Desktop desktop;
	private LoginScreen loginScreen;
	private MainModel model;
	private NewFileBox newFileBox;
	private NewPaymentBox newPaymentBox;
	private OpenScreen openScreen;
	private ResetPasswordBox resetPasswordBox;
	private Ribbon ribbon;
	private SaveScreen saveScreen;

	public BillingWindow(String osName){
		super("Billing Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(300, 300));

		toFront();

		model = new MainModel(this, osName);
		model.loadUsers();

		desktop = new Desktop(model);
		loginScreen = new LoginScreen(this);
		ribbon = new Ribbon(this);
		
		desktop.setPreferredSize(new Dimension(getWidth(), getHeight() - 150));
		loginScreen.setPreferredSize(new Dimension(getWidth(), getHeight() - 150));
		ribbon.setPreferredSize(new Dimension(getWidth(), 150));

		getContentPane().add(ribbon, BorderLayout.PAGE_START);
		getContentPane().add(loginScreen);

		setVisible(true);
		desktop.setVisible(false);
		desktop.setEnabled(false);
		ribbon.disable();

		loginScreen.setVisible(true);
		loginScreen.setFocus();
		
		loginScreen.testAutoLogin();
	}
	public Desktop getDesktop(){
		return desktop;
	}
	public LoginScreen getLoginScreen(){
		return loginScreen;
	}
	public MainModel getModel(){
		return model;
	}
	public Ribbon getRibbon(){
		return ribbon;
	}	
	public void viewChangePasswordBox(){
		changePasswordBox = new ChangePasswordBox(this);
	}
	public void viewCreateUserBox(){
		createUserBox = new CreateUserBox(this); 
	}
	public void viewNewFileBox(){
		newFileBox = new NewFileBox(model);
	}
	public void viewNewPaymentBox(){
		newPaymentBox = new NewPaymentBox();
	}
	public void viewOpenScreen(){
		openScreen = new OpenScreen();
		int returnVal = openScreen.showOpenDialog(this);
		File fileToOpen;
		if(returnVal == OpenScreen.APPROVE_OPTION){
			fileToOpen = openScreen.getSelectedFile();
			if(model.checkFilePass(fileToOpen)){
				model.openFile(fileToOpen);
			}else{
				JOptionPane.showMessageDialog(this, "You don't have permission to access that file.", "", JOptionPane.ERROR_MESSAGE);
				viewOpenScreen();
			}
		}
	}
	public void viewResetPasswordBox(){
		resetPasswordBox = new ResetPasswordBox(this);
	}
	public File viewSaveScreen(String file){
		saveScreen = new SaveScreen(file);
		File savedFile = new File("");
		int result = saveScreen.showSaveDialog(this);
		if(result == SaveScreen.APPROVE_OPTION){
			savedFile = saveScreen.getSelectedFile();
		}
		return savedFile;
	}
}
