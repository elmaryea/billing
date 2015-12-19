package org.maryea.billing.model;

import java.util.Vector;

public class Payer{
  String firstName, lastName;
  int phone;
  Address address;
  Vector<Payment> payments;
  
  public Payer(String first, String last){
    firstName = first;
    lastName = last;
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

  public Payment getPayment(int n){
    return payments.get(n);
  }
  
  public int getPhone(){
    return phone;
  }
  
  public void setAddress(Address address){
    this.address = address;
  }
  
  public void setPhoneNumber(int number){
    phone = number;
  }
}
