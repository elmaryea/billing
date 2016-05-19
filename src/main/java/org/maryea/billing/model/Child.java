package org.maryea.billing.model;

import java.util.Set;

/*
	This class represents a child that is currently enrolled int the child care program.
	Each child has an id (need for hibernate to create an instance from the database), a
	first and last name, the balance for them (which is added to the balance for the Account
	they are under), a set of payments made for that child (which are added to the set of payments
	made for the Account they are under), and the Account they are associated with.
*/
public class Child implements Comparable<Object>{
  private int id;
  private String firstName, lastName;
  private double balance;
  private Set<Payment> payments;
  private Account account;
  
  public Child(){
  }
  
  public Child(String fn, String ln, Account a){
  	account = a;
  	firstName = fn;
  	lastName = ln;
  	balance = 0.0;
  }
  
	/*
		Getter methods for hibernate to use	
	*/
	
  public Account getAccount(){
  	return account;
  }
  
  public double getBalance(){
  	return balance;
  }
  
  public String getFirstName(){
  	return firstName;
  }
  
  public int getId(){
  	return id;
  }
  
  public String getLastName(){
  	return lastName;
  }
  
  public Set<Payment> getPayments(){
  	return payments;
  }
  
	/*
		Setter methods for Hibernate to use
	*/
	
  public void setAccount(Account account){
  	this.account = account;
  }
  
  public void setBalance(double balance){
  	this.balance = balance;
  }
  
  public void setFirstName(String name){
  	firstName = name;
  }
  
  public void setId(int id){
  	this.id = id;
  }
  
  public void setLastName(String name){
  	lastName = name;
  }
  
  public void setPayments(Set<Payment> payments){
  	this.payments = payments;
  }
  
  @Override
  public boolean equals(Object o){
	  Child c = (Child)o;
	  return c.getBalance()==balance && c.getFirstName()==firstName && c.getLastName()==lastName;
  }
  
  @Override
  public int compareTo(Object o) {
	if(equals(o)){
	  return 0;
	}else if(((Child)o).getId() < id){
		return 1;
	}else{
		return -1;
	}
  }
}
