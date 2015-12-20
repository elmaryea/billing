package org.maryea.billing.content;

import org.maryea.billing.model.Account;
import org.maryea.billing.model.Child;
import org.maryea.billing.model.MainModel;
import org.maryea.billing.model.Payer;
import java.awt.Component;
//import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class AccountListPanel extends JTable{

	private static final long serialVersionUID = -4106551294292452278L;

	private class TableModel extends DefaultTableModel{

		private static final long serialVersionUID = 4649160851643808463L;

		public TableModel(Vector<Object> data, Vector<String> columnNames){
			super(data, columnNames);
		}

		public boolean isCellEditable(int row, int column){
			return false;
		}
	}

	//private final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
	private final Vector<String> COLUMN_NAMES = new Vector<String>(Arrays.asList("Account", "Children", "Payers", "Balance"));

	private TableModel tableModel;
	//private JTable children, payers;
	private MainModel model;
	private Vector<String> account;
	private Vector<JTable> children, payers;
	private Vector<Double> balance;

	public AccountListPanel(MainModel m){
		super();
		model = m;
		model.setAccountListPanel(this);

		setFillsViewportHeight(true);
	}

	public void loadTable(Vector<Account> accounts){
		account = new Vector<String>();
		children = new Vector<JTable>();
		payers = new Vector<JTable>();
		balance = new Vector<Double>();

		Vector<Vector<String>> payer = new Vector<Vector<String>>();
		Vector<Vector<String>> child = new Vector<Vector<String>>();
		Vector<Object> row = new Vector<Object>();
		JTable childTable, payerTable;

		for(Account a : accounts){
			account.add(a.getName());

			for(Child c : a.getChildren()){
				child.add(new Vector<String>(Arrays.asList(c.getFirstName())));
			}
			childTable = new JTable(child, new Vector<Object>());
			children.add(childTable);

			for(Payer p : a.getPayers()){
				payer.add(new Vector<String>(Arrays.asList(p.getFirstName())));
			}
			payerTable = new JTable(payer, new Vector<Object>());
			payers.add(payerTable);

			balance.add(a.getBalance());

			row.add(new Vector<Object>(Arrays.asList(a.getName(), childTable, payerTable, a.getBalance())));
		}
		tableModel = new TableModel(row, COLUMN_NAMES);
		setModel(tableModel);

		TableColumn tc = getColumnModel().getColumn(1);
    tc.setCellRenderer(new CustomTableCellRenderer());
    tc = getColumnModel().getColumn(2);
    tc.setCellRenderer(new CustomTableCellRenderer());
	}

	private class CustomTableCellRenderer extends DefaultTableCellRenderer{

		private static final long serialVersionUID = -516916689759819600L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			return this;
		}
	}
}