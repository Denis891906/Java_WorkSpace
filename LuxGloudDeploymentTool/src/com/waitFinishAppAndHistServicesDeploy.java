package com;

public class waitFinishAppAndHistServicesDeploy implements Runnable {
	waitFinishAppAndHistServicesDeploy(Thread server1,Thread server2, Thread server3,String pdcIP, String appIP,String histIP, String userName,String password){
		this.server1=server1;
		this.server2=server2;
		this.appIP=appIP;
		this.userName=userName;
		this.password=password;
	
		this.server3=server3;
		this.pdcIP=pdcIP;
		this.histIP=histIP;
	}
	
	Thread server1;
	Thread server2;
	Thread server3;
	String pdcIP;
	String appIP;
	String histIP;
	String userName;
	String password;
	String serverType;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String StartAppServer= "sudo service phasorpoint-appserver start";
		String StartPDCServer= "sudo service phasorpoint-pdc start";
		String StartHistServer= "sudo service phasorpoint-historian start";
		while (true){
			if (server1.isAlive()==false && server2.isAlive()==false && server3.isAlive()==false ){
			ExecuteCommandViaSSH pdcStart=new ExecuteCommandViaSSH(pdcIP, userName, password , "pdc");
			pdcStart.CreateConnection();
			pdcStart.StartCommand(StartPDCServer);
			pdcStart.CloseConnection();
			
			ExecuteCommandViaSSH appStart=new ExecuteCommandViaSSH(appIP, userName,password , "app");
			appStart.CreateConnection();
			appStart.StartCommand(StartAppServer);
			appStart.CloseConnection();
			
			ExecuteCommandViaSSH histStart=new ExecuteCommandViaSSH(histIP, userName,password , "hist");
			histStart.CreateConnection();
			histStart.StartCommand(StartHistServer);
			histStart.CloseConnection();
			GUI.showInfoMessage("URTDSM servises were installed and started.\nPlease enjoy!");
			break;
			}
			
		}
		
}
}