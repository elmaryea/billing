package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import javax.swing.JPanel;

public class BillViewPanel extends JPanel{

	private static final long serialVersionUID = 5682906798686779019L;
	private MainModel model;

	public BillViewPanel(MainModel m){
		super();
		model = m;
		model.setBillViewPanel(this);
	}
}
