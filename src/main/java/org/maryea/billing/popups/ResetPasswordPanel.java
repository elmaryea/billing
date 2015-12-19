package org.maryea.billing.popups;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ResetPasswordPanel extends JPanel{
	private JLabel user;
	private JTextField username;
	private SpringLayout layout;

	public ResetPasswordPanel(){
		user = new JLabel("Username:");
		username = new JTextField(20);
		layout = new SpringLayout();

		setLayout(layout);
		setPreferredSize(new Dimension(150, 300));

		add(user);
		add(username);

		layoutForm();
	}

	public void layoutForm(){
		
	}

	public String getUsername(){
		return username.getText();
	}
}