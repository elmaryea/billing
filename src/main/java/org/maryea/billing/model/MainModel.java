package org.maryea.billing.model;

import org.maryea.billing.content.AccountListPanel;
import org.maryea.billing.content.AccountViewPanel;
import org.maryea.billing.content.BillingWindow;
import org.maryea.billing.content.BillViewPanel;
import org.maryea.billing.content.OverviewPanel;
import java.awt.Component;
import java.io.File;
import java.util.Vector;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainModel{
	private final File CREDENTIAL_FILE = new File("src/main/resources/credentials.pass");

	private AccountListPanel accountListPanel;
	private AccountViewPanel accountViewPanel;
	private BillingWindow billingWindow;
	private BillViewPanel billViewPanel;
	private File currentFile;
	private OverviewPanel overviewPanel;
	private Payer currentPayer;
	private SessionFactory sessionFactory;
	private String osName;
	private User currentUser;
	private List<Account> accounts;
	private List<User> users;
	

	public MainModel(BillingWindow bw, String osName){
		billingWindow = bw;
		this.osName = osName;
		accounts = new Vector<Account>();
		users = new Vector<User>();

		sessionFactory = new Configuration().configure("billingAdmin.cfg.xml").buildSessionFactory();
	}

	public void addAccount(Account account){
		accounts.add(account);
	}

	public boolean checkFilePass(File toOpen){
		File checkFile = new File("src/main/resources/" + currentUser.getUsername() + ".cred");
		try{
			//TODO: look into closing the following scanner
			@SuppressWarnings("resource")
			Scanner fileScan = new Scanner(checkFile);
			String check;
			while(fileScan.hasNextLine()){
				check = fileScan.nextLine();
				if(check.equals(toOpen.getName())){
					return true;
				}
			}
		}catch(Exception e){
			System.out.println("Error reading check file.");
			e.printStackTrace();
		}
		return false;
	}
	public void closeFile(){
		for(Component c : billingWindow.getDesktop().getComponents()){
			c.setEnabled(false);
			c.setVisible(false);
		}
		billingWindow.getRibbon().disableButton("New Account");
		billingWindow.getRibbon().disableButton("Add User Privilege");
		currentFile = null;
	}
	public BillingWindow getBillingWindow(){
		return billingWindow;
	}

	public File getCurrentFile(){
		return currentFile;
	}
	public Payer getCurrentPayer(){
		return currentPayer;
	}
	public User getCurrentUser(){
		return currentUser;
	}
	public String getOS(){
		return osName;
	}
	public List<User> getUsers(){
		return users;
	}
	public SessionFactory getSF(){
		return sessionFactory;
	}

	public void loadUser(String username){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(username)){
				currentUser = users.get(i);
				break;
			}
		}
	}
	public void loadUsers(){
		users = UserHandler.loadUsers(sessionFactory);
	}
	public void logout(){
		if(currentFile != null){
			saveOption();
		}
		currentUser = null;
		currentFile = null;
		currentPayer = null;
		accounts = null;
	}
	public void setWorkingScreen(String name){
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			String stmt = "UPDATE Users SET Last_Program_Open='" + name + "' WHERE Username='" + currentUser.getUsername() + "'";
			try{
				tx = session.beginTransaction();
				SQLQuery query = session.createSQLQuery(stmt);
				query.executeUpdate();
				tx.commit();
			}catch(HibernateException h){
				if(tx != null){
					tx.rollback();
				}
				h.printStackTrace();
			}finally{
				session.close();
			}

			accounts = AccountHandler.loadAccounts(sessionFactory);
			billingWindow.getDesktop().placeComponents();
			billingWindow.getRibbon().enableButton("Add User Privilege");
			billingWindow.getRibbon().enableButton("New Account");
	}
	
	public void openFile(File file){
		currentFile = file;
		try{
			Scanner scan = new Scanner(file);
			String name = scan.nextLine();
			scan.close();
			setWorkingScreen(file.getAbsolutePath());
		}catch(Exception e){
			System.out.println("There was an error opening the file.");
			e.printStackTrace();
		}
	}

	public void quit(){
		if(currentFile != null){
			if(saveOption()){
				try{
					sessionFactory.close();
				}catch(Exception e){
					System.out.println("There was an error closing the SQL Connections.");
					e.printStackTrace();
				}
				System.exit(0);
			}
		}else{
			try{
				sessionFactory.close();
			}catch(Exception e){
				System.out.println("There was an error closing the SQL Connections");
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	public boolean saveOption(){
		return true;
	}
	public void setAccountListPanel(AccountListPanel a){
		accountListPanel = a;
	}
	public void setAccountViewPanel(AccountViewPanel a){
		accountViewPanel = a;
	}
	public void setBillViewPanel(BillViewPanel b){
		billViewPanel = b;
	}
	public void setCurrentUser(User user){
		currentUser = user;

	}
	public void setOverviewPanel(OverviewPanel o){
		overviewPanel = o;
	}
}
