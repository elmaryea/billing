package org.maryea.billing.model;

import java.util.Set;

/*
  This class represents a person who is an eligible to pay for any children
  that are in the same account as the payer. Each payer has an id (for Hibernate
  to create an instance from the database), a first and last name, a home phone,
  a cell phone, a set of payments that they've made, and the Account that they're
  associated with.
*/
public class Payer{
  private int id;
  private String firstName, lastName, homePhone, cellPhone;
  private Set<Payment> payments;
  private Account account;
  
  public Payer(){
  }
  
  public Payer(String fn, String ln, String hp, String cp, Account a){
  	account = a;
  	firstName = fn;
  	lastName = ln;
  	homePhone = hp;
  	cellPhone = cp;
  }
  
  /*
    Getter methods for Hibernate to use
  */
  
  public Account getAccount(){
  	return account;
  }
  
  public String getCellPhone(){
  	return cellPhone;
  }
  
  public String getFirstName(){
  	return firstName;
  }
  
  public String getHomePhone(){
  	return homePhone;
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
  
  public void setCellPhone(String phone){
  	cellPhone = phone;
  }
  
  public void setFirstName(String name){
  	firstName = name;
  }
  
  public void setHomePhone(String phone){
  	homePhone = phone;
  }
  
  public void setLastName(String name){
  	lastName = name;
  }
  
  public void setId(int id){
  	this.id = id;
  }
  
  public void setPayments(Set<Payment> payments){
  	this.payments = payments;
  }
}
