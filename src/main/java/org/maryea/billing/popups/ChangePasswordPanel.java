package org.maryea.billing.popups;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

public class ChangePasswordPanel extends JPanel{
	private JLabel orig, pass, pass2;
	private JPasswordField original, password, password2;
	private SpringLayout layout;

	public ChangePasswordPanel(){
		orig = new JLabel("Old Password:");
		pass = new JLabel("New Password:");
		pass2 = new JLabel("New Password:");
		original = new JPasswordField(20);
		password = new JPasswordField(20);
		password2 = new JPasswordField(20);
		layout = new SpringLayout();

		setLayout(layout);
		setPreferredSize(new Dimension(400, 225));

		add(orig);
		add(pass);
		add(pass2);
		add(original);
		add(password);
		add(password2);

		layoutForm();
	}

	public void layoutForm(){
		layout.putConstraint(SpringLayout.WEST, orig, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, pass, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, pass2, 35, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.WEST, original, 150, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, password, 150, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, password2, 150, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, orig, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, pass, 15, SpringLayout.SOUTH, orig);
		layout.putConstraint(SpringLayout.NORTH, pass2, 15, SpringLayout.SOUTH, pass);

		layout.putConstraint(SpringLayout.NORTH, original, 48, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, password, 13, SpringLayout.SOUTH, orig);
		layout.putConstraint(SpringLayout.NORTH, password2, 13, SpringLayout.SOUTH, pass);

	}

	public char[] getOrig(){
		return original.getPassword();
	}

	public char[] getNewOne(){
		return password.getPassword();
	}

	public char[] getNewTwo(){
		return password2.getPassword();
	}
}
