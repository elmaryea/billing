package org.maryea.billing.popups.account;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class PayerTableModel extends DefaultTableModel{
	private Vector<Vector<Object>> data;
	
	public PayerTableModel(Object[][] data, Object[] columnNames){
		super(data, columnNames);
		this.data = new Vector<Vector<Object>>();
		Vector<Object> toAdd;
		for(int i = 0; i < data.length; i++){
			toAdd = new Vector<Object>();
			for(int j = 0; j < data[i].length; j++){
				toAdd.add(data[i][j]);
			}
			this.data.addElement(toAdd);
		}
	}
	
	public void addRow(Object[] row){
		Vector<Object> toAdd = new Vector<Object>();
		for(int i = 0; i < row.length; i++){
			toAdd.add(row[i]);
		}
		data.add(toAdd);
		fireTableRowsUpdated(0, data.size() - 1);
	}
	
	public void addRow(Vector row){
		data.add(row);
		fireTableRowsUpdated(0, data.size() - 1);
	}
	
	public Vector<Vector<Object>> getAllData(){
		return data;
	}
	
	public int getColumnCount(){
		return 2;
	}
	
	public int getDataColumns(){
		return data.get(0).size();
	}
	
	public Vector<Vector<Object>> getDataVector(){
		Vector<Vector<Object>> results = new Vector<Vector<Object>>();
		Vector<Object> toAdd;
		for(int i = 0; i < data.size(); i++){
			toAdd = new Vector<Object>();
			for(int j = 0; j < 2; j++){
				toAdd.add(data.get(i).get(j));
			}
			results.add(toAdd);
		}
		return results;
	}
	
	public Object getValueAt(int row, int column){
		return data.get(row).get(column);
	}
}
