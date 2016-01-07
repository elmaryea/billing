package org.maryea.billing.model;

import java.util.Set;

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
