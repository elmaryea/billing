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

public class AddPayerPanel extends JPanel implements TableModelListener, ActionListener{
	private DefaultTableModel model;
	private JButton add, delete;
	private JLabel header, fn, ln, str, ci, st, zp;
	private JScrollPane scollPane;
	private JTable table;
	private JTextField firstName, lastName, street, city, state, zipCode;
	
	
	public AddPayerPanel(String[][] payers){
		
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}
