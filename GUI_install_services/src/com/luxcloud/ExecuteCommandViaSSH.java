package com.luxcloud;


import com.jcraft.jsch.*;
import com.openstack.MainWindow;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
public class ExecuteCommandViaSSH {
	private String SFTPHOST;
    private int SFTPPORT;
    private String SFTPUSER;
    private String password;
    private String SFTPWORKINGDIR;
    private Channel channel = null;
    private Session session= null;
    private String serverType;

	ExecuteCommandViaSSH(String host,String user, String password, String serverType){
		this.SFTPHOST = host;
        this.SFTPPORT = 22;
        this.SFTPUSER = user;
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        this.password = password;
        this.SFTPWORKINGDIR = "/home/"+user+"/";
        this.serverType=serverType;
	}
	ExecuteCommandViaSSH(String host,String user, String password, String serverType, String command){
		this.SFTPHOST = host;
        this.SFTPPORT = 22;
        this.SFTPUSER = user;
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        this.password = password;
        this.SFTPWORKINGDIR = "/home/"+user+"/";
        this.serverType=serverType;
        this.CreateConnection();
        this.StartCommand(command);
        this.CloseConnection();
        
	}
	
	
    
    public void CreateConnection() {
    	JSch jSch = new JSch();
        
        
        ChannelSftp channelSftp = null;
        
        
        try {
        
        //System.out.println("Private Key Added.");
        session = jSch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
        session.setPassword(password);
        System.out.println("session created.");
        GUI.setMessage("Session for execute command via ssh was opened with host "+SFTPHOST,serverType);
        
        
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();


        
        }catch(Exception e){
            System.out.println(e);
            GUI.showErrorMessage(e.getMessage().toString());
        }
    }
        
     public void StartCommand(String command){
    	 
    	 try {
 			channel = session.openChannel("exec");
 		} catch (JSchException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 			if (e1.getMessage().toString().contains("session is down")){
 				GUI.showErrorMessage("Connection with host "+this.SFTPHOST+" was interupted \n"+e1.getMessage().toString());
 				
 			}
 			else{
 			GUI.showErrorMessage(e1.getMessage().toString());
 			}
 		}

          channel.setInputStream(null);
     	 String result=" ";
     	 ((ChannelExec)channel).setCommand("sudo -S -p '' "+ command);
          ((ChannelExec)channel).setErrStream(System.err);
          InputStream in;
 		try {
 			in = channel.getInputStream();
 		
          OutputStream out=channel.getOutputStream();
          ((ChannelExec)channel).setPty(true);
          try {
 			channel.connect();
 		} catch (JSchException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			GUI.showErrorMessage(e.getMessage().toString());
 		}
          
          out.write((password+"\n").getBytes());
          out.flush();
          out.close();

          StringWriter writer = new StringWriter();
          IOUtils.copy(in, writer);
          String theString = writer.toString();
          result=theString;
          System.out.println(result);
          GUI.setMessage("Command was sent: "+command,serverType);
          GUI.setMessage(result,serverType);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			GUI.showErrorMessage(e.getMessage().toString());
 		}
     } 
     
     public String StartCommandAndReturnResult(String command) {

 		try {
 			channel = session.openChannel("exec");
 		} catch (JSchException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 			if (e1.getMessage().toString().contains("session is down")) {
 				MainWindow.showErrorMessage(
 						"Connection with host " + this.SFTPHOST + " was interupted \n" + e1.getMessage().toString());

 			} else {
 				MainWindow.showErrorMessage(e1.getMessage().toString());
 			}
 		}

 		channel.setInputStream(null);
 		String result = " ";
 		((ChannelExec) channel).setCommand("sudo -S -p '' " + command);
 		((ChannelExec) channel).setErrStream(System.err);
 		InputStream in;
 		try {
 			in = channel.getInputStream();

 			OutputStream out = channel.getOutputStream();
 			((ChannelExec) channel).setPty(true);
 			try {
 				channel.connect();
 			} catch (JSchException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 				MainWindow.showErrorMessage(e.getMessage().toString());
 			}

 			out.write((password + "\n").getBytes());
 			out.flush();
 			out.close();

 			StringWriter writer = new StringWriter();
 			IOUtils.copy(in, writer);
 			String theString = writer.toString();
 			result = theString;

 			System.out.println(result);
 			MainWindow.setMessage("Command was sent: " + command, serverType);
 			MainWindow.setMessage(result, serverType);

 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			MainWindow.showErrorMessage(e.getMessage().toString());
 		}
 		return result;
 	}
     
 public void StartCommand(String[] commands){
    	 for (int i=0; i<commands.length;i++){
    	 try {
 			channel = session.openChannel("exec");
 		} catch (JSchException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 			GUI.showErrorMessage(e1.getMessage());
 		}

          channel.setInputStream(null);
     	 String result=" ";
     	 ((ChannelExec)channel).setCommand("sudo -S -p '' "+ commands[i]);
          ((ChannelExec)channel).setErrStream(System.err);
          InputStream in;
 		try {
 			in = channel.getInputStream();
 		
          OutputStream out=channel.getOutputStream();
          ((ChannelExec)channel).setPty(true);
          try {
 			channel.connect();
 		} catch (JSchException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			GUI.showErrorMessage(e.getMessage());
 		}
          
          out.write((password+"\n").getBytes());
          out.flush();
          out.close();

          StringWriter writer = new StringWriter();
          IOUtils.copy(in, writer);
          String theString = writer.toString();
          result=theString;
          System.out.println(result);
          GUI.setMessage("Command was sent: "+commands[i],serverType);
          GUI.setMessage(result,serverType);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			GUI.showErrorMessage(e.getMessage());
 		}
    	 }
     } 
     
     
     public void CloseConnection(){
    	 channel.disconnect();
         session.disconnect();
         System.out.println("Finish");
         GUI.setMessage("Session for execute command via ssh was closed with host "+SFTPHOST,serverType);
		}

}

