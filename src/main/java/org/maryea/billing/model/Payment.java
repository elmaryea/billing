package org.maryea.billing.model;

import java.util.Date;

public class Payment{
  public static final boolean CASH = false;
  public static final boolean CHECK = true;

  //private boolean type;
  private Date date;
  private double amount, resultingBalance;
  //private int checkNumber;
    
  public Payment(Date date, double amount, boolean type, double balance){
    this.date = date;
    this.amount = amount;
    //this.type = type;
    resultingBalance = balance;
  }

  public Payment(Date date, double amount, boolean type, double balance, int checkNumber){
    this.date = date;
    this.amount = amount;
    //this.type = type;;
    //this.checkNumber = checkNumber;
    resultingBalance = balance;
  }

  public void setBalance(int b){
    resultingBalance = b;
  }

  public void setAmount(int amount){
    this.amount = amount;
  }

  public void setDate(Date date){
    this.date = date;
  }

  public void setPaymentType(boolean type){
    //this.type = type;
  }

  public double getAmount(){
    return amount;
  }

public double getBalance(){
    return resultingBalance;
}

  public Date getDate(){
    return date;
  }
}
