package org.maryea.billing.content;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.maryea.billing.model.UserHandler;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginScreen extends JPanel{

	private static final long serialVersionUID = -3008179859062274369L;
	private BillingWindow billingWindow;
	private JLabel username, password;
	private JButton createAccount, login, forgotPassword;
	private JPasswordField passwordField;
	private JTextField loginName;
	private SpringLayout layout;

	public LoginScreen(BillingWindow bw){
		super();

		billingWindow = bw;

		layout = new SpringLayout();
		setLayout(layout);
		setPreferredSize(new Dimension(315, 315));

		//initialize variables
		username = new JLabel("Username:");
		password = new JLabel("Password:");
		loginName = new JTextField(16);
		passwordField = new JPasswordField(16);
		createAccount = new JButton("<html><div align=\"center\">New <br />User</div></html>");
		login = new JButton("<html><div align=\"center\">Login <br /></div></html>");
		forgotPassword = new JButton("<html><div align=\"center\">Forgot<br />Password</div></html>");

		//ActionListener for the Login button
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				checkLoginAction();
			}
		});

		//ActionListener for the account create button
		createAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				billingWindow.viewCreateUserBox();
			}
		});

		forgotPassword.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				billingWindow.viewResetPasswordBox();
			}
		});

		loginName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkLoginAction();
			}
		});
		
		passwordField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkLoginAction();
			}
		});

		//add items
		add(username);
		add(password);
		add(loginName);
		add(passwordField);
		add(createAccount);
		add(login);
		add(forgotPassword);

		//set layout
		setLayoutPattern();
	}

	public void checkLoginAction(){
		String user = loginName.getText();
		char[] input = passwordField.getPassword();
		if(user.equals("")){
			JOptionPane.showMessageDialog(billingWindow, "You must enter a username.", "", JOptionPane.ERROR_MESSAGE);
		}else if(input.length == 0){
			JOptionPane.showMessageDialog(billingWindow, "You must enter a password.", "", JOptionPane.ERROR_MESSAGE);
		}else if(UserHandler.verifyPassword(input, user)){
			validLogin(user);
			Session session = billingWindow.getModel().getSF().openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				String stmt = "SELECT Last_Program_Open FROM Users WHERE Username='" + user + "'";
				SQLQuery query = session.createSQLQuery(stmt);
				List<Object> results = query.list();
				if(!results.isEmpty()){
					String dbName = (String)results.get(0);
					billingWindow.getModel().openFile(new File(dbName));
				}
				tx.commit();
			}catch(HibernateException h){
				if(tx != null){
					tx.rollback();
				}
			}finally{
				session.close();
			}
		}else{
			JOptionPane.showMessageDialog(billingWindow, "Incorrect username or password.", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearScreen(){
		loginName.setText("");
		passwordField.setText("");
	}

	public void setFocus(){
		loginName.requestFocus();
	}

	private void setLayoutPattern(){
		layout.putConstraint(SpringLayout.WEST, username, 600, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, password, 600, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, username, 250, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, password, 8, SpringLayout.SOUTH, username);

		layout.putConstraint(SpringLayout.WEST, loginName, 685, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, passwordField, 685, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, loginName, 249, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, passwordField, 2, SpringLayout.SOUTH, loginName);

		layout.putConstraint(SpringLayout.WEST, createAccount, 712, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, login, 710, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, forgotPassword, 700, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, createAccount, 10, SpringLayout.SOUTH, forgotPassword);
		layout.putConstraint(SpringLayout.NORTH, login, 20, SpringLayout.SOUTH, passwordField);
		layout.putConstraint(SpringLayout.NORTH, forgotPassword, 10, SpringLayout.SOUTH, login);
	}

	public void validLogin(String user){
		billingWindow.getRibbon().enableButton("Logout");
		billingWindow.getRibbon().enableButton("Change Password");
		billingWindow.getRibbon().enableButton("New Business");
		billingWindow.getModel().loadUser(user);
		setEnabled(false);
		setVisible(false);
		billingWindow.getContentPane().add(billingWindow.getDesktop());
		billingWindow.getDesktop().setVisible(true);
		billingWindow.getDesktop().setEnabled(true);
	}
}
