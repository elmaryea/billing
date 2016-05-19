package org.maryea.billing.model;

import java.util.Set;

/*
  This class represents an account in the child care program. Each account contains
  one or two parents (firstName and lastName), their home phone and cellphone, home
  address, the current account balance, any children they have that are currently 
  enrolled in the program, a set of all payments that have been made for the account,
  a set of authorized payers (the parents are automatically made authorized payers),
  and the program that they belong to (in order to prevent massive method strings to
  get the program).
*/
public class Account{
    private int id;
    private String firstName1, firstName2, lastName1, lastName2, homePhone, cellPhone;
    private Address address;
    private double balance;
    private Set<Child> children;
    private Set<Payment> payments;
    private Set<Payer> payers;
    private Business business;
    
    /*
      Creates an empty account
    */
    public Account(){
    }
    
    /*
      Creates an account with all information filled in except for the set of kids, set of payments
      and set of payers
    */
    public Account(String fn1, String ln1, String fn2, String ln2, String street, String city, String state, String zipCode, String hp, String cp, Business b){
    	balance = 0.0;
    	firstName1 = fn1;
    	lastName1 = ln1;
    	firstName2 = fn2;
    	lastName2 = ln2;
    	homePhone = hp;
    	cellPhone = cp;
    	address = new Address(street, city, state, zipCode);
    	business = b;
    }
    
    /*
      Creates a new account with the same information as Account account
    */
    public Account(Account a){
    	id = a.getId();
    	firstName1 = a.getFirstName1();
    	lastName1 = a.getLastName1();
    	firstName2 = a.getFirstName2();
    	lastName2 = a.getLastName2();
    	homePhone = a.getHomePhone();
    	cellPhone = a.getCellPhone();
    	address = a.getAddress();
    	business = a.getBusiness();
      children = a.getChildren();
      payments = a.getPayments();
      payers = a.getPayers();
    	balance = 0.0;
    }
    
    /*
      Getter methods for each field for Hibernate to use
    */
  
    public Address getAddress(){
    	return address;
    }
    
    public double getBalance(){
    	return balance;
    }
    
    public Business getBusiness(){
    	return business;
    }
    
    public String getCellPhone(){
    	return cellPhone;
    }
    
    public Set<Child> getChildren(){
    	return children;
    }
    
    public String getFirstName1(){
    	return firstName1;
    }
    
    public String getFirstName2(){
    	return firstName2;
    }
    
    public String getHomePhone(){
    	return homePhone;
    }
    
    public String getLastName1(){
    	return lastName1;
    }
    
    public String getLastName2(){
    	return lastName2;
    }
    
    public int getId(){
    	return id;
    }
    
    public Set<Payer> getPayers(){
    	return payers;
    }
    
    public Set<Payment> getPayments(){
    	return payments;
    }
    
    /*
      Setter methods for each field for Hibernate to use
    */
  
    public void setAddress(Address address){
    	this.address = address;
    }
    
    public void setBalance(double balance){
    	this.balance = balance;
    }
    
    public void setBusiness(Business business){
    	this.business = business;
    }
    
    public void setCellPhone(String phone){
    	cellPhone = phone;
    }
    
    public void setChildren(Set<Child> children){
    	this.children = children;
    }
    
    public void setFirstName1(String name){
    	firstName1 = name;
    }
    
    public void setFirstName2(String name){
    	firstName2 = name;
    }
    
    public void setHomePhone(String phone){
    	homePhone = phone;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    
    public void setLastName1(String name){
    	lastName1 = name;
    }
    
    public void setLastName2(String name){
    	lastName2 = name;
    }
    
    public void setPayers(Set<Payer> payers){
    	this.payers = payers;
    }
    
    public void setPayments(Set<Payment> payments){
    	this.payments = payments;
    }

}
