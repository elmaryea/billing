package org.maryea.billing.model;

import java.util.Set;

/*
	This class represents a child care business, which is comprised of the name of 
	the child care center and a list of accounts for children who are enrolled. There
	is also an id field that Hibernate needs in order to load an instance of the class
	from the database.
*/
public class Business {
	private int id;
	private Set<Account> accounts;
	private String businessName;
	
	public Business(){
	}
	
	public Set<Account> getAccounts(){
		return accounts;
	}
	
	public String getBusinessName(){
		return businessName;
	}
	
	public int getId(){
		return id;
	}
	
	public void setAccounts(Set<Account> accounts){
		this.accounts = accounts;
	}
	
	public void setBusinessName(String name){
		businessName = name;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
