package org.maryea.billing.popups;

import org.maryea.billing.content.BillingWindow;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;

public class CreateAccountPanel extends JPanel{
	private JLabel first, last;
	private JTextField firstName, lastName;
	private SpringLayout layout;
	private BillingWindow billingWindow;

	public CreateAccountPanel(BillingWindow b){
		first = new JLabel("First name:");
		last = new JLabel("Last name:");
		firstName = new JTextField(20);
		lastName = new JTextField(20);
		layout = new SpringLayout();
		billingWindow = b;

		setLayout(layout);
		setPreferredSize(new Dimension(400, 300));

		add(first);
		add(last);
		add(firstName);
		add(lastName);

		lastName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(firstName.getText() != null && lastName.getText() != null && !(firstName.getText().equals("") || lastName.getText().equals(""))){
					//billingWindow.getDesktop().addAccount(firstName.getText(), lastName.getText());
					getTopLevelAncestor().setVisible(false);
				}
			}
		});

		layoutForm();
	}

	public void layoutForm(){
		layout.putConstraint(SpringLayout.WEST, first, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, last, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, first, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, last, 10, SpringLayout.SOUTH, first);

		layout.putConstraint(SpringLayout.WEST, firstName, 100, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, lastName, 100, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, firstName, 6, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, lastName, 1, SpringLayout.SOUTH, firstName);
	}

	public String getFirstName(){
		return firstName.getText();
	}

	public String getLastName(){
		return lastName.getText();
	}
}