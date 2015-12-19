package org.maryea.billing.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class AccountHandler{
	
	public static Vector<Account> loadAccounts(Statement statement){
		Vector<Account> accounts = new Vector<Account>();
		Vector<Child> children = new Vector<Child>();
		Vector<Payer> payers = new Vector<Payer>();

		Account accountToAdd;
		boolean isCheck;
		Child childToAdd;
		Date date;
		Double balance, amount;
		int checkNumber;
		Payer payerToAdd;
		Payment paymentToAdd;
		ResultSet accountResults, childResults, payerResults, paymentResults;
		Statement duplicateStatement;
		String accountName, firstName, lastName, payerName, command;

		try{
			duplicateStatement = statement.getConnection().createStatement();			

			command = "Select * from Accounts";
			accountResults = statement.executeQuery(command);
			while(accountResults.next()){
				accountName = accountResults.getString("Name");
				balance = accountResults.getDouble("Balance");
				accountToAdd = new Account(accountName, balance);

				command = "Select * from Children where Account='" + accountName + "'";
				childResults =  duplicateStatement.executeQuery(command);
				while(childResults.next()){
					firstName = childResults.getString("FirstName");
					lastName = childResults.getString("LastName");
					balance = childResults.getDouble("Balance");
					childToAdd = new Child(firstName, lastName, balance);
					children.add(childToAdd);
				}

				command = "Select * from Payers where Account='" + accountName + "'";
				payerResults = duplicateStatement.executeQuery(command);
				while(payerResults.next()){
					firstName = payerResults.getString("FirstName");
					lastName = payerResults.getString("LastName");
					payerToAdd = new Payer(firstName, lastName);
					payers.add(payerToAdd);
				}
				
				command = "Select * from Transactions where Account='" + accountName + "'";
				paymentResults = duplicateStatement.executeQuery(command);
				while(paymentResults.next()){
					date = paymentResults.getDate("Date");
					firstName = paymentResults.getString("Child");
					lastName = paymentResults.getString("Payer");
					isCheck = paymentResults.getBoolean("IsCheck");
					balance = paymentResults.getDouble("Balance");
					amount = paymentResults.getDouble("PaymentAmount");
					if(isCheck){
						checkNumber = paymentResults.getInt("CheckNumber");
						paymentToAdd = new Payment(date, amount, Payment.CHECK, balance, checkNumber);
					}else{
						paymentToAdd = new Payment(date, amount, Payment.CASH, balance);
					}
					
					for(int i = 0; i < children.size(); i++){
						if(children.get(i).getFirstName().equals(firstName)){
							children.get(i).addPayment(paymentToAdd);
						}
					}
					for(int i = 0; i < payers.size(); i++){
						if(payers.get(i).getFirstName().equals(lastName)){
							payers.get(i).addPayment(paymentToAdd);
						}
					}
					accountToAdd.addPayment(paymentToAdd);
				}				
				accountToAdd.setChildren(new Vector<Child>(children));
				accountToAdd.setPayers(new Vector<Payer>(payers));
				accounts.add(accountToAdd);

				children.clear();
				payers.clear();
			}
		}catch(Exception e){
			System.out.println("There was an error loading the accounts.");
			e.printStackTrace();
		}
		
		return accounts;
	}
}
