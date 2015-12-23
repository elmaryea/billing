package org.maryea.billing.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User{
	@Id @GeneratedValue
	@Column(name = "User_ID")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String emailAddress;
	
	@Transient
	private boolean currentUser;
	
	@Column(name = "lastPassChange")
	private Date lastPassChange;

	//Default constructor for hibernate
	public User(){
		
	}
	
	public User(String user, String first, String last, String email, Date passChange, int id){
		username = user;
		firstName = first;
		lastName = last;
		emailAddress = email;
		currentUser = false;
		lastPassChange = passChange;
		this.id = id;
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

	public String getEmailAddress(){
		return emailAddress;
	}

	public Date getLastPassChange(){
		return lastPassChange;
	}

	public int getId(){
		return id;
	}
	
	public boolean getCurrentUser(){
		return currentUser;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public void setCurrentUser(boolean value){
		currentUser = value;
	}

	public void setLastPassChange(Date date){
		lastPassChange = date;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}
	
}