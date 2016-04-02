package org.maryea.billing.model;

import java.util.TreeSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	private Business business = new Business();
	private Account stockAccount = new Account("firstName", "lastName", "firstName2", "lastName2", "street", "city", "state", "zipCode", "homePhone", "cellPhone", business);
	private Account account;
	
	@Before
	public void beforeEachTest() {
		account = new Account(stockAccount);
	}
	
	@Test
	public void testGetAddress() {
		Address rightAddress = new Address("street", "city", "state", "zipCode");
		Address wrongAddress = new Address();
		assertTrue(account.getAddress().equals(rightAddress));
		assertFalse(account.getAddress().equals(wrongAddress));
	}
	
	@Test
	public void testGetBalance() {
		double rightBalance = 0.0, wrongBalance = 1.0;
		assertTrue(account.getBalance() == rightBalance);
		assertFalse(account.getBalance() == wrongBalance);
	}
	
	@Test
	public void testGetCellPhone(){
		String rightNumber = "homePhone", wrongNumber = "cellPhone";
		assertTrue(account.getHomePhone() == rightNumber);
		assertFalse(account.getHomePhone() == wrongNumber);
	}
	
	@Test
	public void testGetChildren(){
		assertNull(account.getChildren());
		Set<Child> children = new TreeSet<Child>();
		Child child1 = new Child("firstName1", "lastName1", new Account());
		Child child2 = new Child("firstName2", "lastName2", new Account());
		Child child3 = new Child("firstName3", "lastName3", new Account());
		children.add(child1);
		children.add(child2);
		account.setChildren(children);
		assertTrue(account.getChildren().equals(children));
		children = new TreeSet<Child>();
		children.add(child1);
		children.add(child2);
		children.add(child3);
		assertFalse(account.getChildren().equals(children));
	}
	
	@Test
	public void testGetFirstName1(){
		
	}
	
	@Test
	public void testGetFirstName2(){
		
	}
	
	@Test
	public void testGetHomePhone(){
		
	}
	
	@Test
	public void testGetLastName1(){
		
	}
	
	@Test
	public void testGetLastName2(){
		
	}
	
	@Test
	public void testGetId(){
		
	}
	
	@Test
	public void testGetPayers(){
		
	}
	
	@Test
	public void testGetPayments(){
		
	}
	
	@Test
	public void testSetAddress(){
		
	}
	
	@Test
	public void testSetBalance(){
		
	}
	
	@Test
	public void testSetBusiness(){
		
	}

	@Test
	public void testSetCellPhone(){
		
	}
	
	@Test
	public void testSetChildren(){
		
	}
	
	@Test
	public void testSetFirstName1(){
		
	}
	
	@Test
	public void testSetFirstName2(){
		
	}
	
	@Test
	public void testHomePhone(){
		
	}
	
	public void testSetId(){
		
	}
	
	@Test
	public void testSetLastName1(){
		
	}
	
	@Test
	public void testSetLastName2(){
		
	}
	
	@Test
	public void testSetPayers(){
		
	}
	
	@Test
	public void testSetPayments(){
		
	}
}
