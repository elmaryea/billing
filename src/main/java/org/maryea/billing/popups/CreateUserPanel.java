package org.maryea.billing.popups;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class CreateUserPanel extends JPanel{
	private JLabel instruct, user, pass, pass2, first, last, business, email;
	private JTextField username, firstName, lastName, businessName, emailAddress;
	private JPasswordField password, password2;
	private SpringLayout layout;

	public CreateUserPanel(){
		//initialize components
		instruct = new JLabel("Please enter your information below:");
		user = new JLabel("*Username:");
		pass = new JLabel("*Password:");
		pass2 = new JLabel("*Password:");
		first = new JLabel(" First Name:");
		last = new JLabel(" Last Name:");
		business = new JLabel(" Business Name:");
		email = new JLabel("*Email Address:");
		username = new JTextField(20);
		firstName = new JTextField(20);
		lastName = new JTextField(20);
		businessName = new JTextField(20);
		emailAddress = new JTextField(20);
		password = new JPasswordField(20);
		password2 = new JPasswordField(20);
		layout = new SpringLayout();

		setLayout(layout);
		setPreferredSize(new Dimension(450, 325));

		//add components to panel
		add(instruct);
		add(user);
		add(pass);
		add(pass2);
		add(first);
		add(last);
		add(business);
		add(email);
		add(username);
		add(firstName);
		add(lastName);
		add(businessName);
		add(emailAddress);
		add(password);
		add(password2);

		layoutForm();
	}

	private void layoutForm(){
		layout.putConstraint(SpringLayout.WEST, instruct, 90, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, user, 55, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, pass, 55, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, pass2, 55, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, first, 58, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, last, 58, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, business, 58, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, email, 55, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, instruct, 45, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, first, 40, SpringLayout.SOUTH, instruct);
		layout.putConstraint(SpringLayout.NORTH, last, 11, SpringLayout.SOUTH, first);
		layout.putConstraint(SpringLayout.NORTH, business, 11, SpringLayout.SOUTH, last);
		layout.putConstraint(SpringLayout.NORTH, email, 11, SpringLayout.SOUTH, business);
		layout.putConstraint(SpringLayout.NORTH, user, 11, SpringLayout.SOUTH, email);
		layout.putConstraint(SpringLayout.NORTH, pass, 11, SpringLayout.SOUTH, user);
		layout.putConstraint(SpringLayout.NORTH, pass2, 11, SpringLayout.SOUTH, pass);

		layout.putConstraint(SpringLayout.WEST, username, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, password, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, password2, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, firstName, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, lastName, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, businessName, 180, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, emailAddress, 180, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, firstName, 38, SpringLayout.SOUTH, instruct);
		layout.putConstraint(SpringLayout.NORTH, lastName, 9, SpringLayout.SOUTH, first);
		layout.putConstraint(SpringLayout.NORTH, businessName, 9, SpringLayout.SOUTH, last);
		layout.putConstraint(SpringLayout.NORTH, emailAddress, 9, SpringLayout.SOUTH, business);
		layout.putConstraint(SpringLayout.NORTH, username, 9, SpringLayout.SOUTH, email);
		layout.putConstraint(SpringLayout.NORTH, password, 9, SpringLayout.SOUTH, user);
		layout.putConstraint(SpringLayout.NORTH, password2, 9, SpringLayout.SOUTH, pass);
	}

	public String getFirstName(){
		return firstName.getText();
	}

	public String getLastName(){
		return lastName.getText();
	}

	public String getUsername(){
		return username.getText();
	}

	public String getBusinessName(){
		return businessName.getText();
	}

	public String getEmailAddress(){
		return emailAddress.getText();
	}

	public char[] getPassword(){
		return password.getPassword();
	}

	public char[] getRedundant(){
		return password2.getPassword();
	}

	public void setUsername(String userN){
		username.setText(userN);
	}

	public void setFirstName(String firstN){
		firstName.setText(firstN);
	}

	public void setLastName(String lastN){
		lastName.setText(lastN);
	}

	public void setBusinessName(String businessN){
		businessName.setText(businessN);
	}

	public void setEmailAddress(String emailA){
		emailAddress.setText(emailA);
	}

	public boolean checkAll(){
		try{
			return !(username.getText().equals("") || Arrays.equals(password.getPassword(), "".toCharArray()) || emailAddress.getText().equals(""));
		}catch(NullPointerException e){
			return false;
		}
	}

}