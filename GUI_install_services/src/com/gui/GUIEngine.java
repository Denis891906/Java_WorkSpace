package com.gui;

import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gui.Find.Finder;

public class GUIEngine implements ActionListener {
	MainWindow parent;
	String pdcBuildPath;
	String appServerBuildPath;
	String historianBuildPath;
	String keyPath;
	
	GUIEngine(MainWindow parent){
		 this.parent = parent;
		 }
	
	public void actionPerformed (ActionEvent e){
		Object src=e.getSource();
		JButton clickedButton=null;
		
		if (src instanceof JButton){
			clickedButton=(JButton) src;
		}
		
		if (clickedButton.getText()=="Install PDC, AppServer, Historian"){
			
			//Copy file to PDC VM
			SendFileViaSFTP sendBuildToPDC=new SendFileViaSFTP(parent.GetExternalPDCIP(), "centos", keyPath);
			sendBuildToPDC.SendFile(pdcBuildPath);
			//Execute commands on the PDC VM
			ExecuteCommandViaSSH executeCommandOnPDC=new ExecuteCommandViaSSH(parent.GetExternalPDCIP(), "centos", keyPath);
			executeCommandOnPDC.CreateConnection();
			executeCommandOnPDC.StartCommand("ls");
			//System.out.println("Before Close");
			executeCommandOnPDC.CloseConnection();
			
			//Copy file to the AppServer VM
			SendFileViaSFTP sendBuildToApp=new SendFileViaSFTP(parent.GetExternalAppServerIP(), "centos", keyPath);
			sendBuildToApp.SendFile(appServerBuildPath);
			
			//Execute commands on the AppServer VM
			ExecuteCommandViaSSH executeCommandOnApp=new ExecuteCommandViaSSH(parent.GetExternalAppServerIP(), "centos", keyPath);
			executeCommandOnApp.CreateConnection();
			executeCommandOnApp.StartCommand("pwd");
			executeCommandOnApp.CloseConnection();
					
			//Copy file to the Historian VM
			SendFileViaSFTP sendBuildToHist=new SendFileViaSFTP(parent.GetExternalHistorianIP(), "centos", keyPath);
			sendBuildToHist.SendFile(historianBuildPath);
			//Execute commands on the Historian VM
			ExecuteCommandViaSSH executeCommandOnHist=new ExecuteCommandViaSSH(parent.GetExternalHistorianIP(), "centos", keyPath);
			executeCommandOnHist.CreateConnection();
			executeCommandOnHist.StartCommand("pwd");
			executeCommandOnHist.CloseConnection();
			parent.pack();
		}else if(clickedButton.getText()=="Select Build Folder"){
			
			JFileChooser chooser;	         
		    chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Dialog");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //
		    // disable the "All files" option.
		    //
		    chooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): " 
		         +  chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " 
		         +  chooser.getSelectedFile());
		      Path directPath= Paths.get(chooser.getSelectedFile().getAbsolutePath());
		      
		      
		      Find temp=new Find();
		      
		      System.out.println("Returned: " + temp.returnFilePath(directPath,"*pdc*").toString());
		      
		      if ((temp.returnFilePath(directPath,"*pdc*").toString()).matches("NONE") ){
		    	  parent.ShowWarningDialog("PDC install rpm file wasn't found in the directory "+directPath + " . \nPlease specify directory again.");
		      }else if((temp.returnFilePath(directPath,"*app*").toString()).matches("NONE") ){
		    	  parent.ShowWarningDialog("AppServer install rpm file wasn't found in the directory "+directPath + " . \nPlease specify directory again.");
		      }else if((temp.returnFilePath(directPath,"*hist*").toString()).matches("NONE")){
		    	  parent.ShowWarningDialog("Historian install rpm file wasn't found in the directory "+directPath + " . \nPlease specify directory again.");
		    	  }else {
		    	  parent.SetBuildPath(chooser.getSelectedFile().getAbsolutePath(),
		    			  temp.returnFilePath(directPath,"*pdc*").toString(),
		    			  temp.returnFilePath(directPath,"*app*").toString(),
		    			  temp.returnFilePath(directPath,"*hist*").toString()
		    			  );
		    	  pdcBuildPath= chooser.getSelectedFile().getAbsolutePath()+"\\" + temp.returnFilePath(directPath,"*pdc*").toString() ;
		    	  appServerBuildPath=chooser.getSelectedFile().getAbsolutePath()+"\\" + temp.returnFilePath(directPath,"*app*").toString();
		    	  historianBuildPath=chooser.getSelectedFile().getAbsolutePath()+"\\" + temp.returnFilePath(directPath,"*hist*").toString();
		    	  
		      }
		      
		      parent.pack();
		      }
		    else {
		      System.out.println("No Selection ");
		      }
		}else if(clickedButton.getText()=="Select Key File"){
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "PEM","pem");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(parent);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("You chose to open this file: " +
		            chooser.getSelectedFile().getName());
		       parent.SetKeyPath(chooser.getSelectedFile().getAbsolutePath());
		       keyPath=chooser.getSelectedFile().getAbsolutePath();
		       parent.pack();
		    }
		}
		
		
		
		
		
	}
	}
