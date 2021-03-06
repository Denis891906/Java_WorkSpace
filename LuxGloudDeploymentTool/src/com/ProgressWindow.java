package com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProgressWindow extends JFrame implements ActionListener{
	private JButton cancelButton;
	public ProgressWindow(){}
	private GUI parent;
	private Thread pdcInstall, appServerInstall, histServerInstall, startAllServices;
	
	//Cunstructor for method which show window with "Cancel" button.
	public ProgressWindow(GUI parent) {
		this.parent=parent;
		// TODO Auto-generated constructor stub
		setSize(350, 100);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JLabel message=new JLabel("Please wait till URTDSM services will be deploment.");
		cancelButton=new JButton("Cancel");
		cancelButton.addActionListener(this);
		JPanel p = new JPanel(); 
        add(p);
        p.setLayout(new FlowLayout());
		
        p.add(message);
        p.add(cancelButton);         
        setLocationRelativeTo(parent);
        parent.setEnabled(false);
        setResizable(false);
        		
		
	}	
	
	
	
	//method which should start deploment process for all cervices
	private void startDeplometCervices(){
	String[] pdcCommands={
				
				"sudo rpm -i *pdc*",
				"sudo /opt/phasorpoint-pdc/bin/createdb -u postgres -p P0stgres -d"
				
				
		};
		
		//Commands which should be executed to install AppServer service	
		String [] appServerCommands={
				"sudo rpm -i *appserver*",
				"sudo sh -c \"echo 'infrastructure.input.addresses="+parent.GetInternalPDCIP().toString()+"' >> /etc/phasorpoint-appserver/appserver.properties\"",
				"sudo sh -c \"echo 'naming.strategy=urtdsm' >> /etc/phasorpoint-appserver/appserver.properties\"",
				"sudo sh -c \"echo 'pdc.1.host="+parent.GetInternalPDCIP().toString()+"' >> /etc/phasorpoint-appserver/appserver.properties\"",
				"sudo sh -c \"echo 'db.1.host="+parent.GetInternalHistorianIP().toString()+"' >> /etc/phasorpoint-appserver/appserver.properties\""
				
		};
		
		//Commands which should be executed to install Historian service
		String [] historainCommands={
				
				"sudo rpm -i *historian*",			
				"sudo /opt/phasorpoint-historian/bin/createdb -u postgres -p P0stgres -d",
				"sudo sed -i.bak \"s/#listen_addresses = 'localhost'/listen_addresses = '*'/\" /var/lib/pgsql/9.4/data/postgresql.conf",
				"sudo sh -c \"echo 'host all all "+parent.GetInternalAppServerIP().toString()+"/32 md5' >> /var/lib/pgsql/9.4/data/pg_hba.conf\"",
				"sudo service postgresql-9.4 restart",
				"sudo sh -c \"echo  'infrastructure.input.addresses="+parent.GetInternalPDCIP().toString()+"' >> /etc/phasorpoint-historian/historian.properties\"",
				"sudo sh -c \"echo 'appserver.1.host="+parent.GetInternalAppServerIP().toString()+"' >> /etc/phasorpoint-historian/historian.properties\"",
				"sudo sh -c \"echo 'repository.dir=/var/phasorpoint-historian/data' >> /etc/phasorpoint-historian/historian.properties\"",
				"sudo sh -c \"echo 'summary.repository.dir=/var/phasorpoint-historian/summary' >> /etc/phasorpoint-historian/historian.properties\"",
				"sudo sh -c \"echo 'repository.size.limit=100000' >> /etc/phasorpoint-historian/historian.properties\"",
				"sudo sh -c \"echo 'summary.size.limit=50' >> /etc/phasorpoint-historian/historian.properties\""
				
		};	
	
	
	
		
	
	
	//PDC Installation
	System.out.println("Installetion process for PDC with external IP "+parent.GetInternalPDCIP());	
	Thread pdcInstall=new Thread(new deployService(parent.GetInternalPDCIP(), parent.getUserName(), parent.getSudoPassword() ,parent.GetPDCBuildPath(),"pdc" ,pdcCommands));
	pdcInstall.start();
	
	//AppServer Installation
	System.out.println("Installetion process for AppServer with external IP "+parent.GetInternalAppServerIP());	
	Thread appServerInstall=new Thread(new deployService(parent.GetInternalAppServerIP(), parent.getUserName(), parent.getSudoPassword(),  parent.GetAppServerBuildPath(),"app",appServerCommands));
	appServerInstall.start();
	
	//Historian Installation
	System.out.println("Installetion process for Historian with external IP "+parent.GetInternalHistorianIP());	
	Thread histServerInstall=new Thread(new deployService(parent.GetInternalHistorianIP(), parent.getUserName(), parent.getSudoPassword(), parent.GetHistorianBuildPath(),"hist",historainCommands));
	histServerInstall.start();
	
	Thread startAllServices=new Thread(new waitFinishAppAndHistServicesDeploy(pdcInstall, appServerInstall, histServerInstall, parent.GetInternalPDCIP(), parent.GetInternalAppServerIP(), parent.GetInternalHistorianIP(), parent.getUserName(), parent.getSudoPassword()));
	startAllServices.start();
	}

// Action listener for "Cancel" button.
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		startAllServices.interrupt();
		pdcInstall.interrupt();
		appServerInstall.interrupt();
		histServerInstall.interrupt();
		this.setVisible(false);
		this.dispose();
		parent.setVisible(true);
			
	}
	
	
	
}
