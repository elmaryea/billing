package org.maryea.billing.popups.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.maryea.billing.model.JTextFieldDigitLimit;

public class AddPayerPanel extends JPanel implements TableModelListener, ActionListener, KeyListener{
	private static final long serialVersionUID = -3629578543354896633L;
	private PayerTableModel model;
	private JButton add, delete, cancel, next, finish;
	private JLabel header, fn, ln, hp, cp;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField firstName, lastName, homePhoneArea, homePhone3, homePhone4, cellPhoneArea, cellPhone3, cellPhone4;
	private JTextFieldDigitLimit ha, h3, h4, ca, c3, c4;
	private boolean hadone = false, h3done = false, cadone = false, c3done = false; 
	
	
	public AddPayerPanel(LinkedList<String> initialAccount){
		Object[][] input;
		if(!initialAccount.get(2).equals("")){
			input = new Object[2][4];
			input[0][0] = initialAccount.get(0);
			input[0][1] = initialAccount.get(1);
			input[0][2] = initialAccount.get(8);//home
			input[0][3] = initialAccount.get(9);//cell
			input[1][0] = initialAccount.get(2);
			input[1][1] = initialAccount.get(3);
			input[1][2] = initialAccount.get(8);//home
			input[1][3] = initialAccount.get(9);//cell
		}else{
			input = new Object[1][4];
			input[0][0] = initialAccount.get(0);
			input[0][1] = initialAccount.get(1);
			input[0][2] = initialAccount.get(8);//home
			input[0][3] = initialAccount.get(9);//cell
		}
		Object[] columns = {"First Name", "Last Name", "Home Phone", "Cell Phone"};
		model = new PayerTableModel(input, columns);
		
		table = new JTable(model){
			private static final long serialVersionUID = -75073715402777940L;

			public boolean isCellEditable(){
				return false;
			}
		};
		for(int i = 2; i < 10; i++){
			table.getColumnModel().removeColumn(new TableColumn(2));
		}
		
		scrollPane = new JScrollPane(table);
		add = new JButton("Add");
		delete = new JButton("Delete");
		cancel = new JButton("Cancel");
		next = new JButton("Next");
		finish = new JButton("Finish");
		next.setEnabled(false);
		
		add.addActionListener(this);
		delete.addActionListener(this);
		model.addTableModelListener(this);
		
		header = new JLabel("Enter payer information below:");
		fn = new JLabel("First Name:");
		ln = new JLabel("Last Name:");
		hp = new JLabel("Home phone:");
		cp = new JLabel("Cell phone:");
		firstName = new JTextField(17);
		lastName = new JTextField(17);
		homePhoneArea = new JTextField(4);
		homePhone3 = new JTextField(4);
		homePhone4 = new JTextField(5);
		cellPhoneArea = new JTextField(4);
		cellPhone3 = new JTextField(4);
		cellPhone4 = new JTextField(5);
		
		ha = new JTextFieldDigitLimit(3);
		h3 = new JTextFieldDigitLimit(3);
		h4 = new JTextFieldDigitLimit(4);
		ca = new JTextFieldDigitLimit(3);
		c3 = new JTextFieldDigitLimit(3);
		c4 = new JTextFieldDigitLimit(4);
		homePhoneArea.setDocument(ha);
		homePhone3.setDocument(h3);
		homePhone4.setDocument(h4);
		cellPhoneArea.setDocument(ca);
		cellPhone3.setDocument(c3);
		cellPhone4.setDocument(c4);
		
		homePhoneArea.addKeyListener(this);
		homePhone3.addKeyListener(this);
		cellPhoneArea.addKeyListener(this);
		cellPhone3.addKeyListener(this);
		
		add(header);
		add(fn);
		add(firstName);
		add(ln);
		add(lastName);
		add(hp);
		add(homePhoneArea);
		add(homePhone3);
		add(homePhone4);
		add(cp);
		add(cellPhoneArea);
		add(cellPhone3);
		add(cellPhone4);
		add(add);
		add(delete);
		add(scrollPane);
		add(cancel);
		add(next);
		add(finish);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(add) && firstName.getText() != null && lastName.getText() != null){
			Object[] toAdd = {firstName.getText(), lastName.getText(), homePhoneArea.getText() + homePhone3.getText() + homePhone4.getText(),
			  cellPhoneArea.getText() + cellPhone3.getText() + cellPhone4.getText()};
			model.addRow(toAdd);
			firstName.setText("");
			lastName.setText("");
			cellPhoneArea.setText("");
			cellPhone3.setText("");
			cellPhone4.setText("");
			homePhoneArea.setText("");
			homePhone3.setText("");
			homePhone4.setText("");
		}else if(e.getSource().equals(delete)){
			int row = table.getSelectedRow();
			if(row != -1 && row != 0 && row != 1){
				model.removeRow(row);
			}
		}
	}
	
	public JButton getCancelButton(){
		return cancel;
	}
	public JButton getFinishbutton(){
		return finish;
	}
	public String[][] getResults(){
		String[][] results = new String[model.getRowCount()][model.getDataColumns()];
		for(int i = 0; i < model.getRowCount(); i++){
			for(int j = 0; j < model.getDataColumns(); j++){
				results[i][j] = (String)model.getValueAt(i, j);
			}
		}
		return results;
	}

	public void tableChanged(TableModelEvent e) {
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
