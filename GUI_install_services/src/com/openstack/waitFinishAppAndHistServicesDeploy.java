package com.openstack;

public class waitFinishAppAndHistServicesDeploy implements Runnable {
	//For three server installetion
	waitFinishAppAndHistServicesDeploy(Thread server1, Thread server2, Thread server3, String pdcIP, String appIP,
			String histIP, String userName, String password, String publicKeyPath, Thread progressWind) {
		this.server1 = server1;
		this.server2 = server2;
		this.appIP = appIP;
		this.userName = userName;
		this.password = password;

		this.server3 = server3;
		this.pdcIP = pdcIP;
		this.histIP = histIP;
		this.publicKeyPath = publicKeyPath;
		this.progressWind = progressWind;
	}
	//For PDC and Hist servier instalettion
	
	
	
	

	Thread server1;
	Thread server2;
	Thread server3;
	String pdcIP;
	String appIP;
	String histIP;
	String userName;
	String password;
	String serverType;
	String publicKeyPath;
	Thread progressWind;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String StartAppServer = "sudo service phasorpoint-appserver start";
		String StartPDCServer = "sudo service phasorpoint-pdc start";
		String StartHistServer = "sudo service phasorpoint-historian start";
		while (true) {
			try {
			if (server1.isAlive() == false && server2.isAlive() == false && server3.isAlive() == false) {
				if (GUIEngine.getPDCFailConnection()==false && GUIEngine.getAppFailConnection()==false && GUIEngine.getHistFailConnection()==false){
					//PDC, App, Hist weren't installed
					progressWind.interrupt();
					MainWindow.showErrorMessage("PDC, AppServer, Historian services weren't deploment!");
					break;
				}else if (GUIEngine.getPDCFailConnection()==false && GUIEngine.getAppFailConnection()==false){
					//PDC, AppServer wasn't installed
					progressWind.interrupt();
					break;
				}else if (GUIEngine.getAppFailConnection()==false && GUIEngine.getHistFailConnection()==false){
					//AppServer and Hist weren't installed
					progressWind.interrupt();
					break;
				}else if(GUIEngine.getPDCFailConnection()==false && GUIEngine.getHistFailConnection()==false){
					//PDC and Hist weren't installed
					progressWind.interrupt();
					break;
				}else{
				
				ExecuteCommandViaSSH pdcStart = new ExecuteCommandViaSSH(pdcIP, userName, publicKeyPath, password,
							"pdc");
					
						pdcStart.CreateConnection();
					
					pdcStart.StartCommand(StartPDCServer);
					pdcStart.CloseConnection();
	
					ExecuteCommandViaSSH appStart = new ExecuteCommandViaSSH(appIP, userName, publicKeyPath, password,
							"app");
					appStart.CreateConnection();
					appStart.StartCommand(StartAppServer);
					appStart.CloseConnection();
	
					ExecuteCommandViaSSH histStart = new ExecuteCommandViaSSH(histIP, userName, publicKeyPath, password,
							"hist");
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
					ExecuteCommandViaSSH pdcStartCheck = new ExecuteCommandViaSSH(pdcIP, userName, publicKeyPath, password,
							"pdc");
					pdcStartCheck.CreateConnection();
					String responsePDC = pdcStartCheck.StartCommandAndReturnResult("sudo service phasorpoint-pdc status");
					pdcStartCheck.CloseConnection();
	
					// Get Historian service status
					ExecuteCommandViaSSH histStartCheck = new ExecuteCommandViaSSH(histIP, userName, publicKeyPath,
							password, "hist");
					histStartCheck.CreateConnection();
					String responseHistorian = histStartCheck
							.StartCommandAndReturnResult("sudo service phasorpoint-historian status");
					histStartCheck.CloseConnection();
	
					// Get AppServer service status
					ExecuteCommandViaSSH appStartCheck = new ExecuteCommandViaSSH(appIP, userName, publicKeyPath, password,
							"app");
					appStartCheck.CreateConnection();
					String responseAppServer = appStartCheck
							.StartCommandAndReturnResult("sudo service phasorpoint-appserver status");
					appStartCheck.CloseConnection();

					// Verify if services are running
					if (responsePDC.contains(" is running") && responseAppServer.contains(" is running")&& responseHistorian.contains(" is running")) {
						//All were started
						MainWindow.showInfoMessage(
								"URTDSM servises were installed and started.\n All services have status \"is running\". \nPlease enjoy!");
					}else if (responsePDC.contains(" is running") && !responseAppServer.contains(" is running")&& responseHistorian.contains(" is running") ) {
						//PDC and Hist were started
						MainWindow.showErrorMessage(
								"PDC and Historian services were installed and started.\n AppServer service was installed but wasn't started.\n You can find more details in the /var/log/phasorpoint-appserver/appserver.log file on the AppServer VM");
					}else if (responsePDC.contains(" is running") && !responseAppServer.contains(" is running")&& !responseHistorian.contains(" is running") ) {
						// Only PDC was started
						MainWindow.showErrorMessage(
								"PDC service was installed and started.\n AppServer and Historian services were installed but weren't started.\n You can find more details in the /var/log/phasorpoint-appserver/appserver.log on the AppServer VM \n and in the /var/log/phasorpoint-historian/historian.log files on the Historian VM");
					}else if (!responsePDC.contains(" is running") && responseAppServer.contains(" is running")&& !responseHistorian.contains(" is running") ) {
						//Only App was started
						MainWindow.showErrorMessage(
								" AppServer service was installed and started. \n PDC and Historian services were installed but weren't started.\n You can find more details in the /var/log/phasorpoint-pdc/pdc.log file on the PDC VM \n and in the /var/log/phasorpoint-historian/historian.log file on the Historian VM");
					}else if (!responsePDC.contains(" is running") && !responseAppServer.contains(" is running")&& !responseHistorian.contains(" is running") ) {
						//PDC, App and Hist weren't started
						MainWindow.showErrorMessage(
								"PDC, AppServer services were installed but weren't started.\n You can find more details in the /var/log/phasorpoint-pdc/pdc.log file on the PDC VM \n and in the /var/log/phasorpoint-appserver/appserver.log file on the AppServer VM \n and in the /var/log/phasorpoint-historian/historian.log file on the Historian VM");
					}else if (!responsePDC.contains(" is running") && !responseAppServer.contains(" is running")&& responseHistorian.contains(" is running") ) {
						//Only Hist was started
						MainWindow.showErrorMessage(
								"Historian service was installed and started. \n PDC and AppServer service were installed but weren't started.\n You can find more details in the /var/log/phasorpoint-pdc/pdc.log file on the PDC VM \n and in the /var/log/phasorpoint-appserver/appserver.log file on the AppServer VM");
					}else if (!responsePDC.contains(" is running") && responseAppServer.contains(" is running")&& responseHistorian.contains(" is running") ) {
						//App and Hist were started
						MainWindow.showErrorMessage(
								"AppServer and Historian services were installed and started.\n PDC service was installed but wasn't started.\n You can find more details in the /var/log/phasorpoint-pdc/pdc.log file on the PDC VM");
					}else if (responsePDC.contains(" is running") && !responseAppServer.contains(" is running")&& responseHistorian.contains(" is running") ) {
						//PDC and Hist were started
						MainWindow.showErrorMessage(
								"PDC and Historian services were installed and started.\n AppServer service was installed but wasn't started.\n You can find more details in the /var/log/phasorpoint-appserver/appserver.log file on the AppServer VM");
					}else {
						MainWindow.showErrorMessage("Some error in the services installation");
					}
	
					progressWind.interrupt();
					break;
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				progressWind.interrupt();
				break;
			}
			
		}

	}
}