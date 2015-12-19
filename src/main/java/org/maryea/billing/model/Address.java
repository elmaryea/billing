package org.maryea.billing.model;

public class Address{
	  int streetNumber, zipCode;
	  String street, city, state;
	  
	  public Address(int number, String street, String c, String state, int zip){
	    streetNumber = number;
	    zipCode = zip;
	    this.street = street;
	    city = c;
	    this.state = state;
	  }
	  
	  public void setStreetNumber(int number){
	    streetNumber = number;
	  }

	  public void setZipCode(int zip){
	    zipCode = zip;
	  }

	  public void setStreet(String street){
	    this.street = street;
	  }

	  public void setCity(String city){
	    this.city = city;
	  }

	  public void setState(String state){
	    this.state = state;
	  }

	  public int getStreetNumber(){
	    return streetNumber;
	  }
	  
	  public String getStreet(){
	    return street;
	  }
	  
	  public String getCity(){
	    return city;
	  }
	  
	  public String getState(){
	    return state;
	  }
	  
	  public int getZipCode(){
	    return zipCode;
	  }
	}