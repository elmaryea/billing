package org.maryea.billing.model;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserHandler{
	public static String CREDENTIALS = "src/main/resources/credentials.pass";
	private static String EMAIL_ADDRESS = "billing.donotreply";
	private static String EMAIL_PASSWORD = "dnrbilling";
	private static String EMAIL_SUBJECT = "Billing Program Password Reset";
	private static String EMAIL_BODY = "Your temporary password is ";

	
	public static void addUserPrivilege(String fileName, String username, SessionFactory factory){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "GRANT SELECT,INSERT,DELETE,DROP,ALTER,UPDATE ON " + fileName + ".* TO '" + username + "'";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
			tx.commit();
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	public static void changeSQLPassword(SessionFactory factory, String username, String hash){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "SET PASSWORD FOR '" + username + "' = PASSWORD('" + hash + "')";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
			tx.commit();
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public static void changePassword(SessionFactory factory, String username, String hash){
		Scanner fileScan, lineScan;
		String line, user;
		File temp = new File("com/src/resources/temp.pass");
		FileWriter out;
		try{
			if(!temp.exists()){
				temp.createNewFile();
			}

			fileScan = new Scanner(new File(CREDENTIALS));
			out = new FileWriter(temp, true);
			temp.createNewFile();
			while(fileScan.hasNextLine()){
				line = fileScan.nextLine();
				lineScan = new Scanner(line);
				lineScan.useDelimiter("\t");
				user = lineScan.next();
				if(!user.equals(username)){
					out.write(line + "\n");
				}
			}
			Files.delete(new File(CREDENTIALS).toPath());
			out.close();
			File credentials = new File(CREDENTIALS);
			temp.renameTo(credentials);
			createAccount(username, hash);
			changeSQLPassword(factory, username, hash);
		}catch(Exception e){
			System.out.println("There was an error changing the password.");
			e.printStackTrace();
		}
	}

	public static void createAccount(String username, String hash){
		try{
			FileWriter out = new FileWriter(new File(CREDENTIALS), true);
			out.write(username + "\t" + hash + "\n");//Password.encryptLine(username + "\t" + Password.createHash(password) + "\n"));
			out.close();
		}catch(Exception e){
			System.out.println("io exception bottom");
			e.printStackTrace();
		}
	}

	public static void createCheckFile(String username, String dbName){
		File checkFile = new File("src/main/resources/" + username + ".cred");
		if(!checkFile.exists()){
			try{
				checkFile.createNewFile();
			}catch(Exception e){
				System.out.println("Error creating check file.");
				e.printStackTrace();
			}
		}
		if(!dbName.equals("")){
			try{
				FileWriter out = new FileWriter(checkFile.getPath(), true);
				out.write(dbName + ".bil\n");
				out.close();
			}catch(Exception e){
				System.out.println("Error writing to check file.");
				e.printStackTrace();
			}
		}
	}

	public static String createDatabase(SessionFactory factory, String username, String dbName){
		String db = newDBName(factory, dbName);
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String command = "SHOW DATABASES";
			SQLQuery sqlQuery = session.createSQLQuery(command);
			List<String> results = sqlQuery.list();
			boolean exists = false;
			for(String result : results){
				if(result.equals(dbName)){
					exists = true;
					break;
				}
			}
			if(!exists){
				command = "CREATE DATABASE " + db;
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "GRANT CREATE,SELECT,INSERT,DELETE,DROP,ALTER,UPDATE on " + db + ".* to '" + username + "'";
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "USE " + db;
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "CREATE TABLE Accounts (Account_ID INT NOT NULL AUTO_INCREMENT, "
						+ "FirstName1 VARCHAR(30) NOT NULL, "
						+ "LastName1 VARCHAR(30) NOT NULL, "
						+ "FirstName2 VARCHAR(30), "
						+ "LastName2 VARCHAR(30), "
						+ "Address VARCHAR(30) NOT NULL, "
						+ "City VARCHAR(30) NOT NULL, "
						+ "State VARCHAR(30) NOT NULL, "
						+ "ZipCode VARCHAR(5) NOT NULL, "
						+ "HomePhone VARCHAR(11), "
						+ "CellPhone VARCHAR(11), "
						+ "Balance DECIMAL(6, 2) NOT NULL, "
						+ "PRIMARY KEY (Account_ID))";
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "CREATE TABLE Children (Child_ID INT NOT NULL AUTO_INCREMENT, "
						+ "FirstName VARCHAR(30) NOT NULL, "
						+ "LastName VARCHAR(30) NOT NULL, "
						+ "Balance DECIMAL(6, 2) NOT NULL, "
						+ "Account_ID INT NOT NULL, "
						+ "PRIMARY KEY (Child_ID), "
						+ "FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID))";
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "CREATE TABLE Payers (Payer_ID INT NOT NULL AUTO_INCREMENT, "
						+ "FirstName VARCHAR(30) NOT NULL, "
						+ "LastName VARCHAR(30) NOT NULL, "
						+ "Address VARCHAR(30) NOT NULL, "
						+ "City VARCHAR(30) NOT NULL, "
						+ "State VARCHAR(30) NOT NULL, "
						+ "ZipCode VARCHAR(5) NOT NULL, "
						+ "HomePhone VARCHAR(11), "
						+ "CellPhone VARCHAR(11), "
						+ "Account_ID int, "
						+ "PRIMARY KEY (Payer_ID), "
						+ "FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID))";
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				command = "CREATE TABLE Payments (Payment_ID INT NOT NULL AUTO_INCREMENT, "
						+ "DatePaid DATE NOT NULL, "
						+ "AmountPaid DECIMAL(6, 2) NOT NULL, "
						+ "Balance DECIMAL(6, 2) NOT NULL, "
						+ "Cash TINYINT NOT NULL, "
						+ "CheckNumber INT, "
						+ "Account_ID INT NOT NULL, "
						+ "Payer_ID INT NOT NULL, "
						+ "Payment_ID_FKRef INT, "
						+ "Child_ID INT, "
						+ "PRIMARY KEY (Payment_ID), "
						+ "FOREIGN KEY (Account_ID) REFERENCES Accounts(Account_ID), "
						+ "FOREIGN KEY (Payer_ID) REFERENCES Payers(Payer_ID), "
						+ "FOREIGN KEY (Payment_ID_FKRef) REFERENCES Payments(Payment_ID), "
						+ "FOREIGN KEY (Child_ID) REFERENCES Children(Child_ID))";
				sqlQuery = session.createSQLQuery(command);
				sqlQuery.executeUpdate();
				tx.commit();
			}
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return db;
	}

	public static void createMainFile(String businessName, String os){
		File mainFile;
		if(os.indexOf("win") >= 0){
			mainFile = new File(System.getenv("HOMEPATH") + "/" + businessName + ".bil");
		}else if(os.indexOf("nux") >= 0  || os.indexOf("nix") >= 0){
			mainFile = new File(System.getenv("HOME") + "/Documents/" + businessName + ".bil");
		}else{
			mainFile = new File(businessName + ".bil");
		}
		if(!mainFile.exists()){
			try{
				mainFile.createNewFile();
			}catch(Exception e){
				System.out.println("Error creating main file.");
				e.printStackTrace();
			}
		}
		try{
			FileWriter out = new FileWriter(mainFile.getPath(), true);
			out.write(businessName.replaceAll("\\s", "") + "\n");
			out.close();
		}catch(Exception e){
			System.out.println("Error writing to main file.");
			e.printStackTrace();
		}
	}

	public static String createSQLEntry(SessionFactory factory, String username, String hash, String firstName, String lastName, String businessName, String emailAddress){
		String db = "";
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String command = "CREATE USER '" + username + "' identified by 'temp'";
			SQLQuery query = session.createSQLQuery(command);
			query.executeUpdate();
			changeSQLPassword(factory, username, hash);
			if(!businessName.equals("")){
				db = newDBName(factory, businessName.replaceAll("\\s", ""));
				createDatabase(factory, username, db);
			}
			Calendar c = Calendar.getInstance();
			command = "INSERT INTO billingAdmin.users (username, firstName, lastName, businessName, email, lastPassChange) values ('" + 
				username + "', '" + firstName + "', '" + lastName + "', '" + businessName + "', '" + emailAddress + "', '" + 
				c.get(Calendar.YEAR)  +"-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) +"')";
			query = session.createSQLQuery(command);
			query.executeUpdate();
			tx.commit();
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return db;
	}

	public static boolean dbExists(SessionFactory factory, String testName){
		boolean ret = false;
		org.hibernate.Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "SELECT businessName FROM billingAdmin.users WHERE businessName = '" + testName + "'";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			List<Object> results = sqlQuery.list();
			ret = !results.isEmpty();
			tx.commit();
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return ret;
	}

	public static List<User> loadUsers(SessionFactory factory){
		org.hibernate.Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			List<User> result = (List<User>)session.createQuery("FROM org.maryea.billing.model.User").list();
			tx.commit();
			return result;
		}catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	public static String newDBName(SessionFactory factory, String bName){
		String newName = bName;
		int count = 1;
		while(dbExists(factory, newName)){
			newName = bName + Integer.toString(count);
			count++;
		}
		return newName;
	}

	public static void resetPassword(SessionFactory factory, String username){
		SecureRandom random = new SecureRandom();
		String tempPassword = new BigInteger(130, random).toString(15);
		try{
			String hash = Password.createHash(tempPassword);
			changePassword(factory, username, hash);
		}catch(Exception e){
			System.out.println("Error resetting the password.");
			e.printStackTrace();
		}
		org.hibernate.Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "SELECT email FROM billingAdmin.users WHERE username = '" + username + "'";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			List<String> result = (List<String>)sqlQuery.list();
			sendResetEmail(result.get(0), tempPassword);
			tx.commit();
		}catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public static void sendResetEmail(String email, String tempPassword){
		Properties props = System.getProperties();
	    String host = "smtp.gmail.com";
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", EMAIL_ADDRESS);
	    props.put("mail.smtp.password", EMAIL_PASSWORD);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");

	    javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	    	message.setFrom(new InternetAddress(EMAIL_ADDRESS));
	    	InternetAddress[] toAddress = {new InternetAddress(email)};//Enter recipient here


	    	for( int i = 0; i < toAddress.length; i++) {
	        	message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    	}

	    	message.setSubject(EMAIL_SUBJECT);
		    message.setText(EMAIL_BODY + tempPassword);
	    	Transport transport = session.getTransport("smtp");
	    	transport.connect(host, EMAIL_ADDRESS, EMAIL_PASSWORD);
	    	transport.sendMessage(message, message.getAllRecipients());
	    	transport.close();
	    }catch (Exception e) {
	    	System.out.println("There was an error sending the password reset email.");
	    	e.printStackTrace();
	    }
	}

	public static boolean userExists(String username){
		File file = new File(CREDENTIALS);
		String line;
		try{
			//TODO: check into closing scanner
			@SuppressWarnings("resource")
			Scanner scanFile = new Scanner(file);
			while(scanFile.hasNextLine()){
				line = scanFile.nextLine();
				if(line.contains(username)){
					return true;
				}
			}
		}catch(Exception e){
			System.out.println("There was an error verifying the username.");
			e.printStackTrace();
		}
		return false;
	}

	public static boolean usernameExists(String username){
		try{
			Scanner scanFile = new Scanner(new File(CREDENTIALS));
			Scanner scanLine;
			String user;
			while(scanFile.hasNextLine()){
				scanLine = new Scanner(scanFile.nextLine());
				scanLine.useDelimiter("\t");
				user = scanLine.next();
				if(user.equals(username)){
					scanFile.close();
					scanLine.close();
					return true;
				}
			}
			scanFile.close();
		}catch(Exception e){
			System.out.println("file not found");
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyPassword(char[] password, String username){
		try{
			Scanner fileScan, lineScan;
			String currentLine, knownUser, knownHash;
			fileScan = new Scanner(new File(CREDENTIALS));
			while(fileScan.hasNextLine()){
				currentLine = fileScan.nextLine();
				lineScan = new Scanner(currentLine);
				lineScan.useDelimiter("\t");
				if(lineScan.hasNextLine()){
					knownUser = lineScan.next();
					knownHash = lineScan.next();
					if(knownUser.equals(username)){
						if(Password.validatePassword(password, knownHash)){
							fileScan.close();
							lineScan.close();
							return true;
						}
					}
				}
			}
			fileScan.close();
		}catch(Exception e){
			System.out.println("Error validating password.");
			e.printStackTrace();
		}
		return false;
	}
}
