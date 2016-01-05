package org.maryea.billing.model;

import java.util.Set;

public class Child{
  public int id;
  public String firstName, lastName;
  public double balance;
  public Set<Payment> payments;
  
  public Child(){
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
}
