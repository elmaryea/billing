package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JInternalFrame;

public class BillViewFrame extends JInternalFrame implements ComponentListener{
	private AccountListFrame accountListFrame;
	private AccountViewFrame accountViewFrame;
	private Desktop desktop;
	private BillViewPanel panel;
	private OverviewFrame overviewFrame;
	
	public BillViewFrame(Desktop d, MainModel m){
		desktop = d;
		panel = new BillViewPanel(m);
	}
	
	public void addAccountListFrame(AccountListFrame f){
		accountListFrame = f;
	}
	
	public void addAccountViewFrame(AccountViewFrame f){
		accountViewFrame = f;
	}
	
	public void addOverviewFrame(OverviewFrame f){
		overviewFrame = f;
	}
	
	public void componentResized(ComponentEvent e){
		if(desktop.getWidth() != getWidth() + overviewFrame.getWidth() + accountViewFrame.getWidth()){
			overviewFrame.setSize(desktop.getWidth() - getWidth() - accountListFrame.getWidth(), overviewFrame.getHeight());
			accountViewFrame.setSize(desktop.getWidth() - getWidth() - accountListFrame.getWidth(), accountViewFrame.getHeight());
		}
	}
	
	public void componentMoved(ComponentEvent e){
		setLocation(new Point(accountListFrame.getWidth() + accountViewFrame.getWidth(), 0));
	}
	
	public void componentShown(ComponentEvent e){
		
	}
	
	public void componentHidden(ComponentEvent e){
		
	}

}
