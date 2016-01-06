package org.maryea.billing.model;

import java.util.Set;

public class Payer{
  private int id;
  private String firstName, lastName, homePhone, cellPhone;
  private Set<Payment> payments;
  
  public Payer(){
  }
  
  public Payer(String fn, String ln, String hp, String cp){
  	firstName = fn;
  	lastName = ln;
  	homePhone = hp;
  	cellPhone = cp;
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
