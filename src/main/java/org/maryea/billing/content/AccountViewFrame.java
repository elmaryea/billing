package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JInternalFrame;

public class AccountViewFrame extends JInternalFrame implements ComponentListener{

	private static final long serialVersionUID = -8101112602600094323L;
	private AccountListFrame accountListFrame;
	//private AccountViewPanel panel;
	private BillViewFrame billViewFrame;
	private Desktop desktop;
	private OverviewFrame overviewFrame;
	
	public AccountViewFrame(Desktop d, MainModel m){
		desktop = d;
		//panel = new AccountViewPanel(m);
	}
	
	public void addAccountListFrame(AccountListFrame f){
		accountListFrame = f;
	}
	
	public void addBillViewFrame(BillViewFrame f){
		billViewFrame = f;
	}
	
	public void addOverviewFrame(OverviewFrame f){
		overviewFrame = f;
	}
	
	public void componentResized(ComponentEvent e){
		if(desktop.getWidth() != getWidth() + getWidth() + billViewFrame.getWidth()){
			if(accountListFrame.getWidth() == getX()){
				if(getWidth() + accountListFrame.getWidth() > 15 * (desktop.getWidth() / 16)){
					setSize(new Dimension(15 * (desktop.getWidth() / 16) - accountListFrame.getWidth(), getHeight()));
				}else{
					billViewFrame.setLocation(new Point(getWidth() + accountListFrame.getWidth(), 0));

					billViewFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - accountListFrame.getWidth(), billViewFrame.getHeight()));
					overviewFrame.setSize(new Dimension(overviewFrame.getWidth(), overviewFrame.getHeight()));
				}
			}else if(getX() < desktop.getWidth() / 16){
					setLocation(new Point(desktop.getWidth() / 16, overviewFrame.getHeight()));
			}else{
				overviewFrame.setLocation(new Point(accountListFrame.getWidth(), 0));
				accountListFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - billViewFrame.getWidth(), accountListFrame.getHeight()));
				overviewFrame.setSize(new Dimension(overviewFrame.getWidth(), desktop.getHeight() - getHeight()));
			}
		}
		if(getDesktopPane().getHeight() != getHeight() + getHeight()){
			overviewFrame.setSize(new Dimension(overviewFrame.getWidth(), desktop.getHeight() - getHeight()));
		}
	}
	
	public void componentMoved(ComponentEvent e){
		setLocation(new Point(accountListFrame.getWidth(), overviewFrame.getHeight()));
	}
	
	public void componentShown(ComponentEvent e){
		
	}
	
	public void componentHidden(ComponentEvent e){
		
	}

}
