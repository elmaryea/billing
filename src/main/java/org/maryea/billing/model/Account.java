package org.maryea.billing.model;

import java.util.Vector;

public class Account{
    private String name;
    private Vector<Child> children;
    private Vector<Payer> payers;
	private Vector<Payment> payments;
    private double balance;
    
    public Account(String n, double b){
        name = n;
        balance = b;
		children = new Vector<Child>();
		payments = new Vector<Payment>();
		payers = new Vector<Payer>();
    }

    public void addChild(Child child){
        children.add(child);
    }
    
    public void addPayer(Payer payer){
        payers.add(payer);
    }
    
    public void addPayment(Payment payment){
        payments.add(payment);
    }

    public void setBalance(double amount){
        balance = amount;
    }
    
	public void addToBalance(double amount){
		balance+=amount;
	}

    public double getBalance(){
        return balance;
    }

    public Vector<Child> getChildren(){
        return children;
    }

    public String getName(){
        return name;
    }
	
	public Vector<Payer> getPayers(){
		return payers;
	}
	
    public void removePayer(Payer payer){
        payers.remove(payer);
    }
    
    public void removeChild(Child child){
        children.remove(child);
    }

    public void setChildren(Vector<Child> children){
        this.children = children;
    }

    public void setPayers(Vector<Payer> payers){
        this.payers = payers;
    }

}
