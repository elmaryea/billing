package org.maryea.billing.model;

import java.util.Set;

public class Account{
    private int id;
    private String firstName1, firstName2, lastName1, lastName2, homePhone, cellPhone;
    private Address address;
    private double balance;
    private Set<Child> children;
    private Set<Payment> payments;
    private Set<Payer> payers;
    
    public Account(){
    }
    
    public Account(String fn1, String ln1, String fn2, String ln2, String street, String city, String state, String zipCode, String hp, String cp){
    	balance = 0.0;
    	firstName1 = fn1;
    	lastName1 = ln1;
    	firstName2 = fn2;
    	lastName2 = ln2;
    	homePhone = hp;
    	cellPhone = cp;
    	address = new Address(street, city, state, zipCode);
    }
    
    public Address getAddress(){
    	return address;
    }
    
    public double getBalance(){
    	return balance;
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
    
    public void setAddress(Address address){
    	this.address = address;
    }
    
    public void setBalance(double balance){
    	this.balance = balance;
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
