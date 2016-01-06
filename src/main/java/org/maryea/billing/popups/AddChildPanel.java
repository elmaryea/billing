package org.maryea.billing.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class AddChildPanel extends JPanel implements TableModelListener, ActionListener{

	private static final long serialVersionUID = 5434565026190081540L;
	private DefaultTableModel model;
	private JButton add, cancel, delete, next, finish;
	private JTable table;
	private JLabel header, fn, ln;
	private JTextField firstName, lastName;
	private JScrollPane scrollPane;
	
	public AddChildPanel(){
		super();
		
		Object[] columns = {"First Name", "Last Name"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model){
			private static final long serialVersionUID = 420612445832590705L;

			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		scrollPane = new JScrollPane(table);
		
		add = new JButton("Add Child");
		cancel = new JButton("Cancel");
		delete = new JButton("Delete");
		next = new JButton("Next");
		finish = new JButton("Finish");
		
		header = new JLabel("Enter Children's Names:");
		fn = new JLabel("First Name:");
		ln = new JLabel("Last Name:");
		firstName = new JTextField(17);
		lastName = new JTextField(17);
		
		add(header);
		add(fn);
		add(firstName);
		add(ln);
		add(lastName);
		add(add);
		add(delete);
		add(scrollPane);
		add(cancel);
		add(next);
		add(finish);
		
		
		add.addActionListener(this);
		model.addTableModelListener(this);
		
		table.setFillsViewportHeight(true);
	}
	
	public JButton getCancelButton(){
		return cancel;
	}
	
	public String[][] getData(){
		String[][] results = new String[model.getRowCount()][model.getColumnCount()];
		for(int i = 0; i < model.getRowCount(); i++){
			for(int j = 0; j < model.getColumnCount(); j++){
				results[i][j] = (String)model.getValueAt(i, j);
			}
		}
		return results;
	}
	
	public JButton getFinishButton(){
		return finish;
	}
	
	public JButton getNextButton(){
		return next;
	}

	public void tableChanged(TableModelEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(add) && firstName.getText() != null && lastName.getText() != null){
			Object[] toAdd = {firstName.getText(), lastName.getText()};
			model.addRow(toAdd);
			firstName.setText("");
			lastName.setText("");
		}else if(e.getSource().equals(delete)){
			int row = table.getSelectedRow();
			if(row != -1){
				model.removeRow(row);
			}
		}
	}
}
