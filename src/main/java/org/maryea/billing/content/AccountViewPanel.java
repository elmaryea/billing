package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;

public class AccountViewPanel{
	private MainModel model;

	public AccountViewPanel(MainModel m){
		super();
		model = m;
		model.setAccountViewPanel(this);
	}
}
