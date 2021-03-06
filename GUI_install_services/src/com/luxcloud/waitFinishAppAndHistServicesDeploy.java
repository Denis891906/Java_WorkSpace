package com.luxcloud;

import com.luxcloud.*;


public class waitFinishAppAndHistServicesDeploy implements Runnable {
	waitFinishAppAndHistServicesDeploy(Thread server1,Thread server2, Thread server3,String pdcIP, String appIP,String histIP, String userName,String password,Thread  progressWind){
		this.server1=server1;
		this.server2=server2;
		this.appIP=appIP;
		this.userName=userName;
		this.password=password;
	
		this.server3=server3;
		this.pdcIP=pdcIP;
		this.histIP=histIP;
		this.progressWind=progressWind;
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
	Thread  progressWind;
	
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
			
			
			
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Get PDC service status
			ExecuteCommandViaSSH pdcStartCheck = new ExecuteCommandViaSSH(pdcIP, userName,  password,"pdc");
			pdcStartCheck.CreateConnection();
			String responsePDC = pdcStartCheck.StartCommandAndReturnResult("sudo service phasorpoint-pdc status");
			pdcStartCheck.CloseConnection();

			// Get Historian service status
			ExecuteCommandViaSSH histStartCheck = new ExecuteCommandViaSSH(histIP, userName, password, "hist");
			histStartCheck.CreateConnection();
			String responseHistorian = histStartCheck
					.StartCommandAndReturnResult("sudo service phasorpoint-historian status");
			histStartCheck.CloseConnection();

			// Get AppServer service status
			ExecuteCommandViaSSH appStartCheck = new ExecuteCommandViaSSH(appIP, userName,  password,"app");
			appStartCheck.CreateConnection();
			String responseAppServer = appStartCheck
					.StartCommandAndReturnResult("sudo service phasorpoint-appserver status");
			appStartCheck.CloseConnection();

			// Verify if services are running
			if (responsePDC.contains(" is running") && responseAppServer.contains(" is running")
					&& responseHistorian.contains(" is running")) {
				GUI.showInfoMessage(
						"URTDSM servises were installed and started.\n All services have status \"is running\". \nPlease enjoy!");
			} else if (!responsePDC.contains(" is running")) {
				GUI.showErrorMessage(
						"PDC service was installed but wasn't started.\n You can find more details in the /var/log/phasorpoint-pdc/pdc.log file on the PDC VM");
			} else if (!responseAppServer.contains(" is running")) {
				GUI.showErrorMessage(
						"AppServer service was isntalled but wasn't started.\n You can find more details in the /var/log/phasorpoint-appserver/appserver.log file on the PDC VM");
			} else if (!responseHistorian.contains(" is running")) {
				GUI.showErrorMessage(
						"Historian service was isntalled but wasn't started.\n You can find more details in the /var/log/phasorpoint-historian/historian.log file on the PDC VM");
			} else {
				GUI.showErrorMessage("Some error in the services installetion");
			}

			progressWind.interrupt();
			break;
			}
			
		}
		
}
}