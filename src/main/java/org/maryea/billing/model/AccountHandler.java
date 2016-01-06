package org.maryea.billing.model;

import java.util.Vector;
import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.popups.NewAccountBox;
import org.hibernate.SessionFactory;

public class AccountHandler{
	public static NewAccountBox newAccountBox;
	
	public static void addAccount(BillingWindow bw){
		newAccountBox = new NewAccountBox(bw);
	}
	
	public static Vector<Account> loadAccounts(SessionFactory factory){
		Vector<Account> accounts = new Vector<Account>();
		return accounts;
	}
}
