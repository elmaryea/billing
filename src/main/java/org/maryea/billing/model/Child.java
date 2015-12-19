package org.maryea.billing.model;

import java.util.Vector;

public class Child{
  String firstName, lastName;
  Address address;
  int[] schedule;
  double balance;
  Vector<Payment> payments;
  
  public Child(String first, String last, double balance){//, Address address){
    firstName = first;
    lastName = last;
    //this.address = address; 
    schedule = new int[5];
    this.balance = balance;
    payments = new Vector<Payment>();
  }
  
  public void addPayment(Payment p){
    payments.add(p);
  }
  
  public String getFirstName(){
    return firstName;
  }
  public String getLastName(){
    return lastName;
  }
  
  public Address getAddress(){
    return address;
  }
  
  public int[] getSchedule(){
    return schedule;
  }

  public double getBalance(){
    return balance;
  }
  
  public void setBalance(int balance){
    this.balance = balance;
  }

  public void setSchedule(int[] schedule){
    this.schedule = schedule;
  }

  public void updateSchedule(int day, int hours){
    schedule[day] = hours;
  }
}
