package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import javax.swing.JPanel;

public class BillViewPanel extends JPanel{
	private MainModel model;

	public BillViewPanel(MainModel m){
		super();
		model = m;
		model.setBillViewPanel(this);
	}
}
