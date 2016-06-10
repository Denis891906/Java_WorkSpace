package com.gui;

import java.util.List;

public class deployService implements Runnable {
	
	private String externalPpcIP;
	private String userName;
	private String keyPath;
	private String pdcBuildPath; 
	String[] commandList;
	private String sudoPassword;
	private String serverType;
		
	public deployService(String externalPpcIP,String userName,String sudoPassword,String keyPath,String pdcBuildPath, String serverType, String[] commandList) {
		this.externalPpcIP=externalPpcIP;
		this.userName=userName;
		this.keyPath=keyPath;
		this.pdcBuildPath=pdcBuildPath;
		this.commandList=commandList;
		this.sudoPassword=sudoPassword;
		this.serverType=serverType;
	}
	
	public void run() {
		//Copy file to VM
		SendFileViaSFTP sendBuildToVM=new SendFileViaSFTP(externalPpcIP, userName, keyPath,serverType);
		sendBuildToVM.SendFile(pdcBuildPath);
		
		
		//Execute commands on the VM
		ExecuteCommandViaSSH executeCommandOnVM=new ExecuteCommandViaSSH(externalPpcIP, userName, keyPath,sudoPassword,serverType);
		executeCommandOnVM.CreateConnection();
		
		for (int i=0; i< this.commandList.length; i++){
			executeCommandOnVM.StartCommand(this.commandList[i]);	
			
		}
		
		
		
		//System.out.println("Before Close");
		executeCommandOnVM.CloseConnection();
		
	}
	
	

}
