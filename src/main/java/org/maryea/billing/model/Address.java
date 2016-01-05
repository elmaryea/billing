package org.maryea.billing.model;

public class Address{
	private int id;
	private String address, city, state, zipCode;
	
	public Address(){
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getCity(){
		return city;
	}
	
	public int getId(){
		return id;
	}
	
	public String getState(){
		return state;
	}
	
	public String getZipCode(){
		return zipCode;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
	
}