package com;

public class waitFinishAppAndHistServicesDeploy implements Runnable {
	waitFinishAppAndHistServicesDeploy(Thread server1,Thread server2,String IP,String userName,String password, String serverType){
		this.server1=server1;
		this.server2=server2;
		this.IP=IP;
		this.userName=userName;
		this.password=password;
		this.serverType=serverType;
	}
	
	Thread server1;
	Thread server2;
	String IP;
	String userName;
	String password;
	String serverType;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String[]StartAppServer= {"sudo service phasorpoint-appserver start"};
		while (true){
			if (server1.isAlive()==false && server2.isAlive()==false){
			ExecuteCommandViaSSH appStart=new ExecuteCommandViaSSH(IP, userName,password , "app");
			appStart.CreateConnection();
			appStart.StartCommand(StartAppServer);
			appStart.CloseConnection();
			GUI.showInfoMessage("URTDSM servises were installed and started.\nPlease enjoy!");
			
			break;
			}
	}

}
}