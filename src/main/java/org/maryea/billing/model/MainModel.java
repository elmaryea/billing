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

	private Account currentAccount;
	private AccountListPanel accountListPanel;
	private AccountViewPanel accountViewPanel;
	private BillingWindow billingWindow;
	private BillViewPanel billViewPanel;
	private Child currentChild;
	private Configuration userCfg;
	private File currentFile;
	private OverviewPanel overviewPanel;
	private Payer currentPayer;
	private SessionFactory rootSF, userSF;
	private String osName;
	private User currentUser;
	private Vector<Account> accounts;
	private List<User> users;
	

	public MainModel(BillingWindow bw, String osName){
		billingWindow = bw;
		this.osName = osName;
		accounts = new Vector<Account>();
		users = new Vector<User>();

		rootSF = new Configuration().configure("billingAdmin.cfg.xml").buildSessionFactory();
	}

	public void addAccount(Account account){
		accounts.add(account);
	}
	public void addChild(Child child){
		if(currentAccount != null){
			currentAccount.addChild(child);
		}
	}
	public void addPayer(Payer payer){
		if(currentAccount != null){
			currentAccount.addPayer(payer);
		}
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
	public Account getCurrentAccount(){
		return currentAccount;
	}
	public Child getCurrentChild(){
		return currentChild;
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
	public SessionFactory getRootSF(){
		return rootSF;
	}
	public SessionFactory getUserSF(){
		return userSF;
	}

	public void loadUser(String username){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUsername().equals(username)){
				currentUser = users.get(i);
				System.out.println(currentUser.getUsername());
				break;
			}
		}
	}
	public void loadUsers(){
		users = UserHandler.loadUsers(rootSF);
	}
	public void logout(){
		if(currentFile != null){
			saveOption();
		}
		try{
			if(userSF != null){
				userSF.close();
			}
		}catch(Exception e){
			System.out.println("There was an error closing the User's SQL Connection.");
			e.printStackTrace();
		}
		currentUser = null;
		currentFile = null;
		currentPayer = null;
		currentChild = null;
		currentAccount = null;
		accounts = null;
	}
	public void openWorkingDB(String name){
		try{
			Session sessionU = userSF.openSession();
			Session sessionR = rootSF.openSession();
			Transaction txU = null;
			Transaction txR = null;
			String stmtU = "USE " + name;
			String stmtR = "UPDATE users SET lastProgramOpen='" + name + "' WHERE username='" + currentUser.getUsername() + "'";
			try{
				txU = sessionU.beginTransaction();
				txR = sessionR.beginTransaction();
				SQLQuery queryU = sessionU.createSQLQuery(stmtU);
				SQLQuery queryR = sessionR.createSQLQuery(stmtR);
				queryU.executeUpdate();
				queryR.executeUpdate();
				txU.commit();
				txR.commit();
			}catch(HibernateException h){
				if(txU != null){
					txU.rollback();
				}
				if(txR != null){
					txR.rollback();
				}
				h.printStackTrace();
			}finally{
				sessionU.close();
				sessionR.close();
			}

			accounts = AccountHandler.loadAccounts(userSF);
			accountListPanel.loadTable(accounts);
			billingWindow.getDesktop().placeComponents();
			billingWindow.getRibbon().enableButton("Add User Privilege");
			billingWindow.getRibbon().enableButton("New Account");
		}catch(Exception e){
			System.out.println("There was an error connecting to the file database.");
			e.printStackTrace();
		}
	}
	
	public void openFile(File file){
		currentFile = file;
		try{
			Scanner scan = new Scanner(file);
			String name = scan.nextLine();
			scan.close();
			openWorkingDB(name);
		}catch(Exception e){
			System.out.println("There was an error opening the file.");
			e.printStackTrace();
		}
	}
	public void openUserSQL(){
		try{
			Scanner fileScan = new Scanner(CREDENTIAL_FILE);
			Scanner lineScan;
			String knownUser, hash, currentLine;
			while(fileScan.hasNextLine()){
				currentLine = fileScan.nextLine();
				lineScan = new Scanner(currentLine);
				lineScan.useDelimiter("\t");
				if(lineScan.hasNext()){
					knownUser = lineScan.next();
					hash = lineScan.next();
					if(knownUser.equals(currentUser.getUsername())){
						userCfg.setProperty("hibernate.connection.username", currentUser.getUsername());
						userCfg.setProperty("hibernate.connection.password", hash);
						userSF = userCfg.buildSessionFactory();
						break;
					}
				}
			}
			fileScan.close();
		}catch(Exception e){
			System.out.println("Other exception.");
			e.printStackTrace();
		}
	}
	public void quit(){
		if(currentFile != null){
			if(saveOption()){
				try{
					userSF.close();
					rootSF.close();
				}catch(Exception e){
					System.out.println("There was an error closing the SQL Connections.");
					e.printStackTrace();
				}
				System.exit(0);
			}
		}else{
			try{
				userSF.close();
				rootSF.close();
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
	public void setCurrentUser(User user, String hash){
		currentUser = user;
		try{
			userCfg.setProperty("hibernate.connection.username", user.getUsername());
			userCfg.setProperty("hibernate.connection.password", hash);
			userSF = userCfg.buildSessionFactory();
		}catch(Exception e){
			System.out.println("There was an issues connectiong to the user SQL account");
			e.printStackTrace();
		}
	}
	public void setOverviewPanel(OverviewPanel o){
		overviewPanel = o;
	}
}
