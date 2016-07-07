package com.luxcloud;

import java.util.List;

public class deployService implements Runnable {
	
	private String IP;
	private String userName;
	private String buildPath; 
	String[] commandList;
	private String sudoPassword;
	private String serverType;
		
	public deployService(String IP,String userName,String sudoPassword,String buildPath, String serverType, String[] commandList) {
		this.IP=IP;
		this.userName=userName;
		this.buildPath=buildPath;
		this.commandList=commandList;
		this.sudoPassword=sudoPassword;
		this.serverType=serverType;
	}
	
	public void run() {
		//Copy file to VM
		SendFileViaSFTP sendBuildToVM=new SendFileViaSFTP(IP, userName, sudoPassword,serverType);
		sendBuildToVM.SendFile(buildPath);
		
		
		//Execute commands on the VM
		ExecuteCommandViaSSH executeCommandOnVM=new ExecuteCommandViaSSH(IP, userName, sudoPassword,serverType);
		executeCommandOnVM.CreateConnection();
		
		for (int i=0; i< this.commandList.length; i++){
			executeCommandOnVM.StartCommand(this.commandList[i]);	
			
		}
		
		
		
		//System.out.println("Before Close");
		executeCommandOnVM.CloseConnection();
		
		
	}
	
	

}
