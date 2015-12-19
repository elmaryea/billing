package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;

public class OverviewPanel{
	private MainModel model;

	public OverviewPanel(MainModel m){
		super();
		model = m;
		model.setOverviewPanel(this);
	}
}
