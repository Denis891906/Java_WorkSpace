package com.gui;

import java.util.List;

public class removeService implements Runnable {
	
	private String externalPpcIP;
	private String userName;
	private String keyPath;
	String[] commandList;
	private String sudoPassword;
	private String serverType;
		
	public removeService(String externalPpcIP,String userName,String sudoPassword,String keyPath, String serverType, String[] commandList) {
		this.externalPpcIP=externalPpcIP;
		this.userName=userName;
		this.keyPath=keyPath;
	
		this.commandList=commandList;
		this.sudoPassword=sudoPassword;
		this.serverType=serverType;
	}
	
	public void run() {
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
