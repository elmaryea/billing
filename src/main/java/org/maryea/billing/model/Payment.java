package org.maryea.billing.model;

import java.util.Date;
import java.util.Set;

/*
  This class represents a payment. Sets of payments are formed for Accounts, Payers and Children.
  Each payment has an id (for Hibernate to use for the database), a check number if the payment was
  by check, the date the payment was made, the resulting balance after the payment, a boolean representing
  whether or not the payment was made in cash or by check, the Account associated withe the payments,
  the Payer who made the payment, and the child the payment was made for.
*/

public class Payment{
  public static final boolean CASH = false;
  public static final boolean CHECK = true;

  private int id, checkNumber;
  private Date date;
  private double amountPaid, balance;
  private boolean isCash;
  private Set<Payment> payments;
  private Account account;
  private Payer payer;
  private Child child;
  
  public Payment(){
  }
  
  /*
    Getter methods for Hibernate to use
  */
  public Account getAccount(){
  	return account;
  }
  
  public double getAmountPaid(){
  	return amountPaid;
  }
  
  public double getBalance(){
  	return balance;
  }
  
  public int getCheckNumber(){
  	return checkNumber;
  }
  
  public Child getChild(){
  	return child;
  }
  
  public Date getDate(){
  	return date;
  }
  
  public int getId(){
  	return id;
  }
  
  public boolean getIsCash(){
  	return isCash;
  }
  
  public Payer getPayer(){
  	return payer;
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
  
  public void setAmountPaid(double amount){
  	amountPaid = amount;
  }
  
  public void setBalance(double balance){
  	this.balance = balance;
  }
  
  public void setCheckNumber(int number){
  	checkNumber = number;
  }
  
  public void setChild(Child child){
  	this.child = child;
  }
  
  public void setDate(Date date){
  	this.date = date;
  }
  
  public void setId(int id){
  	this.id = id;
  }
  
  public void setIsCash(boolean isCash){
  	this.isCash = isCash;
  }
  
  public void setPayer(Payer payer){
  	this.payer = payer;
  }
  
  public void setPayments(Set<Payment> payments){
  	this.payments = payments;
  }
}
