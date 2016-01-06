package org.maryea.billing.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AddPayerPanel extends JPanel implements TableModelListener, ActionListener{
	private static final long serialVersionUID = -3629578543354896633L;
	private DefaultTableModel model;
	private JButton add, delete, cancel, next, finish;
	private JLabel header, fn, ln, hp, cp;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField firstName, lastName, homePhoneArea, homePhone3, homePhone4, cellPhoneArea, cellPhone3, cellPhone4;
	private JTextFieldDigitLimit ha, h3, h4, ca, c3, c4;
	
	
	public AddPayerPanel(LinkedList<String> initialAccount){
		Object[][] input;
		if(initialAccount.get(2) != null){
			input = new Object[2][4];
			input[0][0] = initialAccount.get(0);
			input[0][1] = initialAccount.get(1);
			input[0][2] = initialAccount.get(4);//home
			input[0][3] = initialAccount.get(5);//cell
			input[1][0] = initialAccount.get(2);
			input[1][1] = initialAccount.get(3);
			input[1][2] = initialAccount.get(4);//home
			input[1][3] = initialAccount.get(5);//cell
		}else{
			input = new Object[1][4];
			input[0][0] = initialAccount.get(0);
			input[0][1] = initialAccount.get(1);
			input[0][2] = initialAccount.get(4);//home
			input[0][3] = initialAccount.get(5);//cell
		}
		Object[] columns = {"First Name", "Last Name", "Home Phone", "Cell Phone"};
		model = new DefaultTableModel(input, columns){
			public int getColumnCount(){
				return 2;
			}
		};
		
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
		hp = new JLabel("Cell phone:");
		cp = new JLabel("Home phone:");
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
		String[][] results = new String[model.getRowCount()][model.getColumnCount()];
		for(int i = 0; i < model.getRowCount(); i++){
			for(int j = 0; j < model.getColumnCount(); j++){
				results[i][j] = (String)model.getValueAt(i, j);
			}
		}
		return results;
	}

	public void tableChanged(TableModelEvent e) {
	}
}
