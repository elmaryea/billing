package org.maryea.billing.model;

import java.util.Date;

public class User{
	private String username, firstName, lastName, businessName, emailAddress;
	private boolean needsPasswordChange;
	private boolean currentUser;
	private Date lastPassChange;

	public User(String user, String first, String last, String business, String email, Date passChange){
		username = user;
		firstName = first;
		lastName = last;
		businessName = business;
		emailAddress = email;
		needsPasswordChange = false;
		currentUser = false;
		lastPassChange = passChange;
	}

	public String getUsername(){
		return username;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getBusinessName(){
		return businessName;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public Date getLastPassChange(){
		return lastPassChange;
	}

	public void setPasswordChange(boolean value){
		needsPasswordChange = value;
	}

	public boolean getPasswordChange(){
		return needsPasswordChange;
	}
	public void setCurrentUser(boolean value){
		currentUser = value;
	}

	public boolean geturrentUser(){
		return currentUser;
	}

	public void setLastPassChange(Date date){
		lastPassChange = date;
	}
}