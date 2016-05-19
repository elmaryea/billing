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
import org.hibernate.cfg.Configuration;

/*
	This is a class of static methods to help with user creation, and modification
*/
public class UserHandler{
	public static String CREDENTIALS = "src/main/resources/credentials.pass"; // File to store the user credentials (username, password hash)
	private static String EMAIL_ADDRESS = "billing.donotreply";								// Email address for password resets
	private static String EMAIL_PASSWORD = "dnrbilling";											// Password for email address
	private static String EMAIL_SUBJECT = "Billing Program Password Reset";		// Subject for password reset emails
	private static String EMAIL_BODY = "Your temporary password is ";					// Partial body of password reset email

	/*
		Allows a user to access a businesses SQL database. Basically this allows them to use the
		application as their credentials are also used for the Hibernate transactions to the database
		for an action they perform while they are logged in. This fileName is the name of the business.
	*/
	public static void addUserPrivilege(String fileName, String username, SessionFactory factory){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "INSERT INTO User_Business (Business_ID, User_ID) SELECT Business_ID, User_ID FROM "
					+ "Businesses b INNER JOIN Users u WHERE b.Business_Name = '" + fileName + "' AND u.Username = '" + username + "'";
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

	/*
		This method changes a user's password. The method takes the username and the hash of the password.
		The file that currently holds the credentials is read and each username, hash is written to the new
		file with the changed username and hash. The old file is then deleted and the new one is renamed to
		same as the old one. It's a little hacky, but it works.
	*/
	public static void changePassword(String username, String hash){
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
		}catch(Exception e){
			System.out.println("There was an error changing the password.");
			e.printStackTrace();
		}
	}

	/*
		This method writes the new user credentials to the credentials file
	*/
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

	/*
		This method adds to, or creates and adds to, a check file of type .cred that contains a list of 
		databases the user has access to. In reality these are actually businesses that the user has access
		to and they aren't separate databases in the MySQL server.
	*/
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

	/*
		This method creates a new entry in the businesses table for a new business. In addition
		the user with username will be added to the User_Business table so it has access to the
		new business.
	*/
	public static String createDatabase(SessionFactory factory, String username, String dbName){
		String db = newDBName(factory, dbName.replaceAll("\\s", ""));
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "INSERT INTO Businesses (Business_Name) VALUES ('" + db + "')";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
			query = "INSERT INTO User_Business (Business_ID, User_ID) SELECT Business_ID, User_ID FROM "
					+ "Businesses b INNER JOIN Users u WHERE b.Business_Name = '" + db + "' AND u.Username = '" + username + "'";
			sqlQuery = session.createSQLQuery(query);
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
		return db;
	}

	/*
		This creates a file for the business of type .bil. In unix/linux/mac systems this
		file is created in the ~/ folder. In Windows systems the file is created in 
		C:\Users\username\Documents\.
	*/
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

	/*
		Adds a user the Users table in the database.
	*/
	public static String createSQLEntry(SessionFactory factory, String username, String hash, String firstName, String lastName, String businessName, String emailAddress){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Calendar c = Calendar.getInstance();
			String command = "INSERT INTO Users (Username, First_Name, Last_Name,  Email_Address, Last_Pass_Change) values ('" + 
				username + "', '" + firstName + "', '" + lastName + "', '" + emailAddress + "', '" + 
				c.get(Calendar.YEAR)  +"-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) +"')";
			SQLQuery query = session.createSQLQuery(command);
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
		
		String db = "";
		if(!businessName.equals("")){
			db = createDatabase(factory, username, businessName);
		}
		return db;
	}

	/*
		This methods checks if the testName is in the Businesses table such that there is a user who has
		access to it.
	*/
	public static boolean dbExists(SessionFactory factory, String testName){
		boolean ret = false;
		org.hibernate.Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String query = "SELECT Business_Name FROM Businesses JOIN User_Business ON Businesses.Business_ID=User_Business.Business_ID "
					+ "JOIN Users ON User_Business.User_ID=Users.User_ID WHERE Business_Name = '" + testName + "'";
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

	/*
		This method gets a list of users from the Users table in the database
	*/
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

	/*
		While the businesses table contains a business with the current name, 
		an incremented integer is added to the name, until it isn't found
		in the database. This ensure that unique business names, and therefore
		files, are created.
	*/
	public static String newDBName(SessionFactory factory, String bName){
		String newName = bName;
		int count = 1;
		while(dbExists(factory, newName)){
			newName = bName + Integer.toString(count);
			count++;
		}
		return newName;
	}

	/*
		Creates a random password for a user who wants reset their password (and forgot
		the current one) and calls the sendResetEmail method
	*/
	public static void resetPassword(List<User> users, String username){
		SecureRandom random = new SecureRandom();
		String tempPassword = new BigInteger(130, random).toString(15);
		try{
			String hash = Password.createHash(tempPassword);
			changePassword(username, hash);
		}catch(Exception e){
			System.out.println("Error resetting the password.");
			e.printStackTrace();
		}
		for(User user : users){
			if(user.getUsername().equals(username)){
				sendResetEmail(user.getEmailAddress(), tempPassword);
				break;
			}
		}
	}

	/*
		Sends an email to the email on file for the user, containing a temporary password
		with which they can use to login and then change their password
	*/
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
	
	/*
		Adds a business to the check file of username so that they have access
		to that business when they log in. Only users who already have access to 
		the business can't grant access to users who don't.
	*/
	public static void updateCheckFile(String fileName, String username){
		File checkFile = new File("src/main/resources/" + username + ".cred");
		if(!checkFile.exists()){
			try{
				checkFile.createNewFile();
			}catch(Exception e){
				System.out.println("Error creating check file.");
				e.printStackTrace();
			}
		}
		if(!fileName.equals("")){
			try{
				FileWriter out = new FileWriter(checkFile.getPath(), true);
				out.write(fileName + ".bil\n");
				out.close();
			}catch(Exception e){
				System.out.println("Error writing to check file.");
				e.printStackTrace();
			}
		}
	}

	/*
		checks if the username currently exists in the credentials file
	*/
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

	/*
		Checks if the username exists in the credentials file.Checks
		
		NOTE: I think this is a duplicate of the above method userExists,
		but I haven't checked if it is in use elsewhere yet.
		TODO: check if usernameExists is used elsewhere in the program
	*/
	
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

	/*
		Checks if the password that the user typed to login matches the password that is on file.
	*/
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
