package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

public class Desktop extends JDesktopPane{
	private AccountListFrame accountListFrame;
	private AccountViewFrame accountViewFrame;
	private BillViewFrame billViewFrame;
	private OverviewFrame overviewFrame;
	
	public Desktop(MainModel m){
		accountListFrame = new AccountListFrame(this, m);
		accountViewFrame = new AccountViewFrame(this, m);
		billViewFrame = new BillViewFrame(this, m);
		overviewFrame = new OverviewFrame(this, m);
		
		add(accountListFrame);
		add(accountViewFrame);
		add(billViewFrame);		
		add(overviewFrame);
		
		accountListFrame.addAccountViewFrame(accountViewFrame);
		accountListFrame.addBillViewFrame(billViewFrame);
		accountListFrame.addOverviewFrame(overviewFrame);
		
		accountViewFrame.addAccountListFrame(accountListFrame);
		accountViewFrame.addBillViewFrame(billViewFrame);
		accountViewFrame.addOverviewFrame(overviewFrame);
		
		billViewFrame.addAccountListFrame(accountListFrame);
		billViewFrame.addAccountViewFrame(accountViewFrame);
		billViewFrame.addOverviewFrame(overviewFrame);
		
		overviewFrame.addAccountListFrame(accountListFrame);
		overviewFrame.addAccountViewFrame(accountViewFrame);
		overviewFrame.addBillViewFrame(billViewFrame);
	}
	
	public void placeComponents(){
		accountListFrame.setVisible(true);
		accountViewFrame.setVisible(true);
		billViewFrame.setVisible(true);
		overviewFrame.setVisible(true);
		try{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					accountListFrame.setLocation(new Point(0, 0));
					accountListFrame.setSize(new Dimension(400, getHeight()));
					overviewFrame.setLocation(new Point(accountListFrame.getWidth(), 0));
					overviewFrame.setSize(new Dimension(800, 350));
					accountViewFrame.setLocation(new Point(accountListFrame.getWidth(), overviewFrame.getHeight()));
					accountViewFrame.setSize(new Dimension(800, getHeight() - overviewFrame.getHeight()));
					billViewFrame.setLocation(new Point(accountListFrame.getWidth() + accountViewFrame.getWidth(), 0));
					billViewFrame.setSize(new Dimension(getWidth() - accountListFrame.getWidth() - accountViewFrame.getWidth(), getHeight()));
				}
			});
		}catch(Exception e){
			System.out.println("There was an issue placing the frames.");
			e.printStackTrace();
		}
	}
}
