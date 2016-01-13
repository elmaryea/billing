package org.maryea.billing.popups.account;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.maryea.billing.model.JTextFieldDigitLimit;

public class AccountNamePanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = -2387757769626204868L;
	private final int EDGE_PAD = 15;
	private final int SECTION_OFFSET = 12;
	private final int TEXT_OFFSET = 5;
	private final int BUTTON_PAD = 30;
	private final int PHONE_PAD = 2;
	private final int LINE_PAD = 5;
	private final int SECTION_PAD = 150;
	
	private boolean hadone = false, h3done = false, cadone = false, c3done = false;
	private JButton cancel, finish, next;
	private JLabel pa1, pa2, fn1, fn2, ln1, ln2, hp, cp, ad, str, ci, st, zp;
  private JTextField firstName1, firstName2, lastName1, lastName2, homePhoneArea, homePhone3, homePhone4, cellPhoneArea, cellPhone3, cellPhone4, street, city, state, zipCode;
	private JTextFieldDigitLimit homeArea, cellArea, home3, cell3, home4, cell4, five;
  private SpringLayout layout;
	
	public AccountNamePanel(){
		super();
		
		layout = new SpringLayout();
		
		cancel = new JButton("Cancel");
		finish = new JButton("Finish");
		next = new JButton("Next");
		pa1 = new JLabel("Parent 1");
		pa2 = new JLabel("Parent 2");
		fn1 = new JLabel("First Name:");
		fn2 = new JLabel("First Name:");
		ln1 = new JLabel("Last Name:");
		ln2 = new JLabel("Last Name:");
		hp = new JLabel("Home Phone:");
		cp = new JLabel("Cell Phone:");
		ad = new JLabel("Address:");
		str = new JLabel("Street:");
		ci = new JLabel("City:");
		st = new JLabel("State:");
		zp = new JLabel("Zip Code:");
		firstName1 = new JTextField(17);
		firstName2 = new JTextField(17);
		lastName1 = new JTextField(17);
		lastName2 = new JTextField(17);
		homePhoneArea = new JTextField(4);
		homePhone3 = new JTextField(4);
		homePhone4 = new JTextField(5);
		cellPhoneArea = new JTextField(4);
		cellPhone3 = new JTextField(4);
		cellPhone4 = new JTextField(5);
		street = new JTextField(17);
		city = new JTextField(17);
		state = new JTextField(17);
		zipCode = new JTextField(6);
		
		homeArea = new JTextFieldDigitLimit(3);
		home3 = new JTextFieldDigitLimit(3);
		home4 = new JTextFieldDigitLimit(4);
		cellArea = new JTextFieldDigitLimit(3);
		cell3 = new JTextFieldDigitLimit(3);
		cell4 = new JTextFieldDigitLimit(4);
		five = new JTextFieldDigitLimit(5);
		zipCode.setDocument(five);
		homePhoneArea.setDocument(homeArea);
		homePhone3.setDocument(home3);
		homePhone4.setDocument(home4);
		cellPhoneArea.setDocument(cellArea);
		cellPhone3.setDocument(cell3);
		cellPhone4.setDocument(cell4);
		
		homePhoneArea.addKeyListener(this);
		homePhone3.addKeyListener(this);
		cellPhoneArea.addKeyListener(this);
		cellPhone3.addKeyListener(this);
		
		setPreferredSize(new Dimension(650, 650));
		setLayout(layout);
		
		add(cancel);
		add(next);
		add(finish);
		add(pa1);
		add(pa2);
		add(fn1);
		add(fn2);
		add(ln1);
		add(ln2);
		add(hp);
		add(cp);
		add(ad);
		add(str);
		add(ci);
		add(st);
		add(zp);
		add(firstName1);
		add(firstName2);
		add(lastName1);
		add(lastName2);
		add(homePhoneArea);
		add(homePhone3);
		add(homePhone4);
		add(cellPhoneArea);
		add(cellPhone3);
		add(cellPhone4);
		add(street);
		add(city);
		add(state);
		add(zipCode);

		
		layoutForm();
	}
	
	public JButton getCancelButton(){
		return cancel;
	}
	
	public JButton getFinishButton(){
		return finish;
	}
	
	public JButton getNextButton(){
		return next;
	}
	
	public LinkedList<String> getResults(){
		LinkedList<String> results = new LinkedList<String>();
		results.add(firstName1.getText());
		results.add(lastName1.getText());
		results.add(firstName2.getText());
		results.add(lastName2.getText());
		results.add(street.getText());
		results.add(city.getText());
		results.add(state.getText());
		results.add(zipCode.getText());
		results.add(homePhoneArea.getText() + homePhone3.getText() + homePhone4.getText());
		results.add(cellPhoneArea.getText() + cellPhone3.getText() + cellPhone4.getText());
		return results;
	}
	
	private void layoutForm(){
		layout.putConstraint(SpringLayout.WEST, pa1, EDGE_PAD, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, pa2, EDGE_PAD, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, fn1, SECTION_OFFSET, SpringLayout.WEST, pa1);
		layout.putConstraint(SpringLayout.WEST, fn2, SECTION_OFFSET, SpringLayout.WEST, pa2);
		layout.putConstraint(SpringLayout.WEST, ln1, SECTION_OFFSET, SpringLayout.WEST, pa1);
		layout.putConstraint(SpringLayout.WEST, ln2, SECTION_OFFSET, SpringLayout.WEST, pa2);
		layout.putConstraint(SpringLayout.WEST, ad, EDGE_PAD, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, cp, EDGE_PAD, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, hp, EDGE_PAD, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, str, SECTION_OFFSET, SpringLayout.WEST, ad);
		layout.putConstraint(SpringLayout.WEST, ci, SECTION_OFFSET, SpringLayout.WEST, ad);
		layout.putConstraint(SpringLayout.WEST, st, SECTION_OFFSET, SpringLayout.WEST, ad);
		layout.putConstraint(SpringLayout.WEST, zp, SECTION_OFFSET, SpringLayout.WEST, ad);
		
		layout.putConstraint(SpringLayout.NORTH, pa1, EDGE_PAD, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, pa2, EDGE_PAD, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, fn1, LINE_PAD, SpringLayout.SOUTH, pa1);
		layout.putConstraint(SpringLayout.NORTH, fn2, LINE_PAD, SpringLayout.SOUTH, pa2);
		layout.putConstraint(SpringLayout.NORTH, ln1, LINE_PAD, SpringLayout.SOUTH, fn1);
		layout.putConstraint(SpringLayout.NORTH, ln2, LINE_PAD, SpringLayout.SOUTH, fn2);
		layout.putConstraint(SpringLayout.NORTH, ad, SECTION_PAD, SpringLayout.SOUTH, pa1);
		layout.putConstraint(SpringLayout.NORTH, str, LINE_PAD, SpringLayout.SOUTH, ad);
		layout.putConstraint(SpringLayout.NORTH, ci, LINE_PAD, SpringLayout.SOUTH, str);
		layout.putConstraint(SpringLayout.NORTH, st, LINE_PAD, SpringLayout.SOUTH, ci);
		layout.putConstraint(SpringLayout.NORTH, zp, LINE_PAD, SpringLayout.SOUTH, st);
		layout.putConstraint(SpringLayout.NORTH, hp, SECTION_PAD, SpringLayout.SOUTH, pa2);
		layout.putConstraint(SpringLayout.NORTH, cp, LINE_PAD, SpringLayout.SOUTH, hp);
		
		
		layout.putConstraint(SpringLayout.WEST, firstName1, TEXT_OFFSET, SpringLayout.EAST, fn1);
		layout.putConstraint(SpringLayout.WEST, firstName2, TEXT_OFFSET, SpringLayout.EAST, fn2);
		layout.putConstraint(SpringLayout.WEST, lastName1, TEXT_OFFSET, SpringLayout.EAST, ln1);
		layout.putConstraint(SpringLayout.WEST, lastName2, TEXT_OFFSET, SpringLayout.EAST, ln2);
		layout.putConstraint(SpringLayout.WEST, street, TEXT_OFFSET, SpringLayout.EAST, str);
		layout.putConstraint(SpringLayout.WEST, city, TEXT_OFFSET, SpringLayout.EAST, ci);
		layout.putConstraint(SpringLayout.WEST, state, TEXT_OFFSET, SpringLayout.EAST, st);
		layout.putConstraint(SpringLayout.WEST, zipCode, TEXT_OFFSET, SpringLayout.EAST, zp);
		layout.putConstraint(SpringLayout.WEST, cellPhoneArea, TEXT_OFFSET, SpringLayout.EAST, cp);
		layout.putConstraint(SpringLayout.WEST, cellPhone3, PHONE_PAD, SpringLayout.EAST, cellPhoneArea);
		layout.putConstraint(SpringLayout.WEST, cellPhone4, PHONE_PAD, SpringLayout.EAST, cellPhone3);
		layout.putConstraint(SpringLayout.WEST, homePhoneArea, TEXT_OFFSET, SpringLayout.EAST, hp);
		layout.putConstraint(SpringLayout.WEST, homePhone3, PHONE_PAD, SpringLayout.EAST, homePhoneArea);
		layout.putConstraint(SpringLayout.WEST, homePhone4, PHONE_PAD, SpringLayout.EAST, homePhone3);
		
		layout.putConstraint(SpringLayout.SOUTH, firstName1, 0, SpringLayout.SOUTH, fn1);
		layout.putConstraint(SpringLayout.SOUTH, firstName2, 0, SpringLayout.SOUTH, fn2);
		layout.putConstraint(SpringLayout.SOUTH, lastName1, 0, SpringLayout.SOUTH, ln1);
		layout.putConstraint(SpringLayout.SOUTH, lastName2, 0, SpringLayout.SOUTH, ln2);
		layout.putConstraint(SpringLayout.SOUTH, street, 0, SpringLayout.SOUTH, str);
		layout.putConstraint(SpringLayout.SOUTH, city, 0, SpringLayout.SOUTH, ci);
		layout.putConstraint(SpringLayout.SOUTH, state, 0, SpringLayout.SOUTH, st);
		layout.putConstraint(SpringLayout.SOUTH, zipCode, 0, SpringLayout.SOUTH, zp);
		layout.putConstraint(SpringLayout.SOUTH, cellPhoneArea, 0, SpringLayout.SOUTH, cp);
		layout.putConstraint(SpringLayout.SOUTH, cellPhone3, 0, SpringLayout.SOUTH, cp);
		layout.putConstraint(SpringLayout.SOUTH, cellPhone4, 0, SpringLayout.SOUTH, cp);
		layout.putConstraint(SpringLayout.SOUTH, homePhoneArea, 0, SpringLayout.SOUTH, hp);
		layout.putConstraint(SpringLayout.SOUTH, homePhone3, 0, SpringLayout.SOUTH, hp);
		layout.putConstraint(SpringLayout.SOUTH, homePhone4, 0, SpringLayout.SOUTH, hp);
		
		layout.putConstraint(SpringLayout.EAST, finish, -BUTTON_PAD, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, next, -BUTTON_PAD, SpringLayout.WEST, finish);
		layout.putConstraint(SpringLayout.EAST, cancel, -BUTTON_PAD, SpringLayout.WEST, next);
		
		layout.putConstraint(SpringLayout.SOUTH, cancel, -EDGE_PAD, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.SOUTH, next, 0, SpringLayout.SOUTH, cancel);
		layout.putConstraint(SpringLayout.SOUTH, finish, 0, SpringLayout.SOUTH, cancel);
		
		
	}
	
	public boolean isValidForm(){
		return ((firstName1.getText() != null  && lastName2.getText() != null) || 
				(firstName2.getText() != null && lastName2.getText() != null)) &&
				street.getText() != null && city != null && state != null && zipCode != null && 
				((homePhoneArea.getText() != null && homePhone3.getText() != null && homePhone4.getText() != null) || 
				(cellPhoneArea.getText() != null && cellPhone3.getText() != null && cellPhone4.getText() != null));
	}

	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(homePhoneArea)){
			if(homePhoneArea.getCaretPosition() == 2 && !hadone){
				homePhone3.requestFocus();
				homePhone3.setCaretPosition(0);
				hadone = true;
			}
		}else if(e.getSource().equals(homePhone3)){
			if(homePhone3.getCaretPosition() == 2 && !h3done){
				homePhone4.requestFocus();
				homePhone4.setCaretPosition(0);
				h3done = true;
			}
		}else if(e.getSource().equals(cellPhoneArea)){
			if(cellPhoneArea.getCaretPosition() == 2 && !cadone){
				cellPhone3.requestFocus();
				cellPhone3.setCaretPosition(0);
				cadone = true;
			}
		}else if(e.getSource().equals(cellPhone3)){
			if(cellPhone3.getCaretPosition() == 2 && !c3done){
				cellPhone4.requestFocus();
				cellPhone4.setCaretPosition(0);
				c3done = true;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
}
