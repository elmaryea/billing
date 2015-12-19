package org.maryea.billing.model;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserHandler{
	public static String CREDENTIALS = "src/main/resources/credentials.pass";
	private static String EMAIL_ADDRESS = "billing.donotreply";
	private static String EMAIL_PASSWORD = "dnrbilling";
	private static String EMAIL_SUBJECT = "Billing Program Password Reset";
	private static String EMAIL_BODY = "Your temporary password is ";

	
	public static void addUserPrivilege(String fileName, String username, Statement statement){
		try{
			createCheckFile(username, fileName);
			String command = "grant select,insert,delete,drop on " + fileName + ".* to '" + username + "'";
			statement.execute(command);
		}catch(Exception e){
			System.out.println("There was an issue adding to a users privileges.");
			e.printStackTrace();
		}
	}
	public static void changeSQLPassword(Statement statement, String username, String hash){
		try{
			String command = "set password for '" + username + "' = password('" + hash + "')";
			statement.execute(command);
		}catch(Exception e){
			System.out.println("There was an error setting/change the sql password for " + username);
			e.printStackTrace();
		}
	}

	public static void changePassword(Statement statement, String username, String hash){
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
			changeSQLPassword(statement, username, hash);
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

	public static String createDatabase(Statement statement, String username, String dbName){
		String db = newDBName(statement, dbName);
		try{
			String command = "show databases";
			ResultSet results = statement.executeQuery(command);
			boolean exists = false;
			while(results.next()){
				if(results.getString("Database").equals(dbName)){
					exists = true;
				}
			}
			if(exists == false){
				command = "create database " + db;
				statement.execute(command);
				command = "grant create,select,insert,delete,drop on " + db + ".* to '" + username + "'";
				statement.execute(command);
				command = "use " + db;
				statement.execute(command);
				command = "create table Accounts (name varchar(20))";
				statement.execute(command);
			}
		}catch(Exception e){
			System.out.println("Error creating Database.");
			e.printStackTrace();
		}
		return db;
	}

	public static void createMainFile(String businessName, String os){
		//TODO: Check OS and create file based on OS;
		File mainFile;
		if(os.indexOf("win") >= 0){
			mainFile = new File(System.getenv("HOMEPATH") + "/" + businessName + ".bil");
		}else if(os.indexOf("nux") >= 0  || os.indexOf("nix") >= 0){
			mainFile = new File(System.getenv("HOME") + "/Documents/" + businessName + ".bil");
		}else{
			mainFile = new File(businessName + ".bil");
		}
		System.out.println(mainFile.getName());
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

	public static String createSQLEntry(Statement statement, String username, String hash, String firstName, String lastName, String businessName, String emailAddress){
		String db = "";
		try{
			String command = "create user '" + username + "' identified by 'temp'";
			statement.execute(command);
			changeSQLPassword(statement, username, hash);
			if(!businessName.equals("")){
				db = newDBName(statement, businessName.replaceAll("\\s", ""));
				createDatabase(statement, username, db);
			}
			command = "use billingAdmin";
			statement.execute(command);
			Calendar c = Calendar.getInstance();
			command = "insert into users (username, firstName, lastName, businessName, email, lastPassChange) values ('" + 
				username + "', '" + firstName + "', '" + lastName + "', '" + businessName + "', '" + emailAddress + "', '" + 
				c.get(Calendar.YEAR)  +"-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) +"')";
			statement.execute(command);
		}catch(Exception e){
			System.out.println("Error createing SQL credentials.");
			e.printStackTrace();
		}
		return db;
	}

	public static boolean dbExists(Statement statement, String testName){
		boolean ret = false;
		try{
			String query = "select businessName from billingAdmin.users where businessName = '" + testName + "'";
			ResultSet result = statement.executeQuery(query);
			ret = result.next();
		}catch(Exception e){
			System.out.println("There was an error checking for an existing database.");
			e.printStackTrace();
		}
		return ret;
	}

	public static Vector<User> loadUsers(Statement statement){
		Vector<User> users = new Vector<User>();
		try{
			String query = "select * from billingAdmin.users";
			ResultSet result = statement.executeQuery(query);
			String username, firstName, lastName, businessName, email;
			Date lastPassChange;
			while(result.next()){
				username = result.getString("username");
				firstName = result.getString("firstName");
				lastName = result.getString("lastName");
				businessName = result.getString("businessName");
				email = result.getString("email");
				lastPassChange = result.getDate("lastPassChange");
				users.add(new User(username, firstName, lastName, businessName, email, lastPassChange));
		}
		}catch(Exception e){
			System.out.println("Error loading users.");
			e.printStackTrace();
		}
		return users;
	}

	public static String newDBName(Statement statement, String bName){
		String newName = bName;
		int count = 1;
		while(dbExists(statement, newName)){
			newName = bName + Integer.toString(count);
			count++;
		}
		return newName;
	}

	public static void resetPassword(Statement statement, String username){
		SecureRandom random = new SecureRandom();
		String tempPassword = new BigInteger(130, random).toString(15);
		try{
			String hash = Password.createHash(tempPassword);
			changePassword(statement, username, hash);
		}catch(Exception e){
			System.out.println("Error resetting the password.");
			e.printStackTrace();
		}
		try{
			String query = "select email from billingAdmin.users where username = '" + username + "'";
			ResultSet result = statement.executeQuery(query);
			result.next();
			sendResetEmail(result.getString("email"), tempPassword);
		}catch(Exception e){
			System.out.println("Error getting user name.");
			e.printStackTrace();
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

	    Session session = Session.getDefaultInstance(props);
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
		Scanner scanFile;
		String line;
		try{
			scanFile = new Scanner(file);
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
					return true;
				}
			}
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
							return true;
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println("Error validating password.");
			e.printStackTrace();
		}
		return false;
	}
}