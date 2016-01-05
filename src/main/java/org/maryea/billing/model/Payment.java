package org.maryea.billing.model;

import java.util.Date;
import java.util.Set;

public class Payment{
  public static final boolean CASH = false;
  public static final boolean CHECK = true;

  private int id, checkNumber;
  private Date date;
  private double amountPaid, balance;
  private boolean isCash;
  private Set<Payment> payments;
  
  public Payment(){
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
  
  public Date getDate(){
  	return date;
  }
  
  public int getId(){
  	return id;
  }
  
  public boolean getIsCash(){
  	return isCash;
  }
  
  public Set<Payment> getPayments(){
  	return payments;
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
  
  public void setDate(Date date){
  	this.date = date;
  }
  
  public void setId(int id){
  	this.id = id;
  }
  
  public void setIsCash(boolean isCash){
  	this.isCash = isCash;
  }
  
  public void setPayments(Set<Payment> payments){
  	this.payments = payments;
  }
}
