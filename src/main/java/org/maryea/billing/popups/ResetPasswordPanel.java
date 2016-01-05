package org.maryea.billing.popups;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ResetPasswordPanel extends JPanel{

	private static final long serialVersionUID = -7882528666853586857L;
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
	}

	public String getUsername(){
		return username.getText();
	}
}