package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JInternalFrame;

public class OverviewFrame extends JInternalFrame implements ComponentListener{

	private static final long serialVersionUID = -2234160204790441304L;
	private AccountListFrame accountListFrame;
	private AccountViewFrame accountViewFrame;
	private BillViewFrame billViewFrame;
	private Desktop desktop;
	//private OverviewPanel panel;
	
	public OverviewFrame(Desktop d, MainModel m){
		desktop = d;
		//panel = new OverviewPanel(m);
	}
	
	public void addAccountListFrame(AccountListFrame f){
		accountListFrame = f;
	}
	
	public void addAccountViewFrame(AccountViewFrame f){
		accountViewFrame = f;
	}
	
	public void addBillViewFrame(BillViewFrame f){
		billViewFrame = f;
	}
	
	public void componentResized(ComponentEvent e){
		if(desktop.getWidth() != getWidth() + accountViewFrame.getWidth() + billViewFrame.getWidth()){
			if(accountListFrame.getWidth() == getX()){
				billViewFrame.setLocation(new Point(getWidth() + accountListFrame.getWidth(), 0));
				billViewFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - accountListFrame.getWidth(), billViewFrame.getHeight()));
				accountViewFrame.setSize(new Dimension(accountViewFrame.getWidth(), accountViewFrame.getHeight()));
			}else{
				accountViewFrame.setLocation(new Point(accountListFrame.getWidth(), getHeight()));
				accountListFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - billViewFrame.getWidth(), accountListFrame.getHeight()));
				accountViewFrame.setSize(new Dimension(accountViewFrame.getWidth(), desktop.getHeight() - getHeight()));
			}
		}
		if(desktop.getHeight() != getHeight() + accountViewFrame.getHeight()){
			accountViewFrame.setLocation(new Point(accountListFrame.getWidth(), getHeight()));
			accountViewFrame.setSize(new Dimension(accountViewFrame.getWidth(), desktop.getHeight() - getHeight()));
		}
	}
	
	public void componentMoved(ComponentEvent e){
		setLocation(new Point(accountListFrame.getWidth(), 0));
	}
	
	public void componentShown(ComponentEvent e){
	}
	public void componentHidden(ComponentEvent e){
	}
}
