package org.maryea.billing.content;

import java.util.List;

/*import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;*/
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.maryea.billing.model.Child;
import org.maryea.billing.model.Payer;
import org.maryea.billing.model.User;

public class Run{
	public static void main(String[] args){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
    		// If Nimbus is not available, you can set the GUI to another look and feel.
    		System.out.println("Look and Feel Not available.");
		}

		//constructor used to start window
		@SuppressWarnings("unused")
		BillingWindow mainWindow;
		mainWindow = new BillingWindow(System.getProperty("os.name"));
	}
}
