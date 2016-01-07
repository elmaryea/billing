package org.maryea.billing.model;

import java.util.List;
import java.util.Vector;
import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.popups.account.NewAccountBox;
import org.hibernate.Session;
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
	
	public static void persistNewAccount(BillingWindow bw, Account account, List<Child> children, List<Payer> payers){
		/*Account account = newAccountBox.getAccount();
		List<Child> children = newAccountBox.getChildren();
		List<Payer> payers = newAccountBox.getPayers();*/
		
		Session session = bw.getModel().getSF().openSession();
		try{
			session.beginTransaction();
			session.save(account);
			for(Child c : children){
				session.save(c);
			}
			for(Payer p : payers){
				session.save(p);
			}
			session.getTransaction().commit();
		}catch(Exception e){
			System.out.println("There was an error persisting the account.");
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		
	}
}
