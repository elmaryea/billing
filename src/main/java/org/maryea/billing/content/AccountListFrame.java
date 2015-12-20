package org.maryea.billing.content;

import org.maryea.billing.model.MainModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

public class AccountListFrame extends JInternalFrame implements ComponentListener{

	private static final long serialVersionUID = 7310718746891794846L;
	private AccountListPanel panel;
	private AccountViewFrame accountViewFrame;
	private BillViewFrame billViewFrame;
	private Desktop desktop;
	private JScrollPane scrollPane;
	private OverviewFrame overviewFrame;
	
	public AccountListFrame(Desktop d, MainModel m){
		desktop = d;
		panel = new AccountListPanel(m);
		scrollPane = new JScrollPane(panel);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addAccountViewFrame(AccountViewFrame f){
		accountViewFrame = f;
	}
	
	public void addBillViewFrame(BillViewFrame f){
		billViewFrame = f;
	}
	
	public void addOverviewFrame(OverviewFrame f){
		overviewFrame = f;
	}
	
	public void componentResized(ComponentEvent e){
		if(desktop.getWidth() != getWidth() + overviewFrame.getWidth() + billViewFrame.getWidth()){
			if(getWidth() > desktop.getWidth() / 3){
				overviewFrame.setLocation(new Point(getWidth(), 0));
				accountViewFrame.setLocation(new Point(getWidth(), overviewFrame.getHeight()));

				overviewFrame.setSize(new Dimension((desktop.getWidth() - getWidth()) / 2, overviewFrame.getHeight()));
				accountViewFrame.setSize(new Dimension((desktop.getWidth() - getWidth()) / 2, accountViewFrame.getHeight()));
			}else{
				overviewFrame.setLocation(new Point(getWidth(), 0));
				accountViewFrame.setLocation(new Point(getWidth(), overviewFrame.getHeight()));

				overviewFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - billViewFrame.getWidth(), overviewFrame.getHeight()));
				accountViewFrame.setSize(new Dimension(desktop.getWidth() - getWidth() - billViewFrame.getWidth(), accountViewFrame.getHeight()));
			}
		}
		if(getX() != 0){
			setLocation(new Point(0, 0));
			setSize(new Dimension(getWidth() - getX(),getHeight()));
		}
		if(getY() != 0){
			setLocation(new Point(0, 0));
			setSize(new Dimension(getWidth(), desktop.getHeight()));
		}
		if(getWidth() > desktop.getWidth() / 2){
			setSize(new Dimension(desktop.getWidth() / 2, getHeight()));
		}
	}
	
	public void componentMoved(ComponentEvent e){
		setLocation(new Point(0, 0));
	}
	
	public void componentShown(ComponentEvent e){
		
	}
	
	public void componentHidden(ComponentEvent e){
		
	}
}
