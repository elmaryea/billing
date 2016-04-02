package org.maryea.billing.model;

import java.util.Set;

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
