package org.maryea.billing.model;

import java.util.Date;
import java.util.Vector;

import org.hibernate.SessionFactory;

public class AccountHandler{
	
	public static Vector<Account> loadAccounts(SessionFactory factory){
		Vector<Account> accounts = new Vector<Account>();
		return accounts;
	}
}
