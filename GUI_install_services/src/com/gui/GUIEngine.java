package com.gui;

import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.gui.Find.Finder;

public class GUIEngine implements ActionListener {
	MainWindow parent;
	String pdcBuildPath;
	String appServerBuildPath;
	String historianBuildPath;
	String keyPath;
	String [] pdcCommands={"echo Hello 1",
			"cat /etc/phasorpoint-pdc/security.properties",
			"sudo service phasorpoint-pdc restart"};
	String [] appServerCommands={"echo Hello 2"};
	String [] historainCommands={"echo Hello 3"};
	
	String[] pdcRemoveCommands={"echo Reinstall command"};
	String[] appServerRemoveCommands={"echo Reinstall command"};
	String[] histRemoveCommands={"echo Reinstall command"};
	
	
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
			//PDC Installation
			System.out.println("Installetion process for PDC with external IP "+parent.GetExternalPDCIP());	
			Thread pdcInstall=new Thread(new deployService(parent.GetExternalPDCIP(), parent.getUserName(), parent.getSudoPassword() ,keyPath, pdcBuildPath,"pdc" ,pdcCommands));
			pdcInstall.start();
			
			//AppServer Installation
			System.out.println("Installetion process for AppServer with external IP "+parent.GetExternalAppServerIP());	
			Thread appServerInstall=new Thread(new deployService(parent.GetExternalAppServerIP(), parent.getUserName(), parent.getSudoPassword(), keyPath, appServerBuildPath,"app",appServerCommands));
			appServerInstall.start();
			
			//Historian Installation
			System.out.println("Installetion process for Historian with external IP "+parent.GetExternalHistorianIP());	
			Thread histServerInstall=new Thread(new deployService(parent.GetExternalHistorianIP(), parent.getUserName(), parent.getSudoPassword(), keyPath, historianBuildPath,"hist",historainCommands));
			histServerInstall.start();
			
		
		
			parent.pack();
		}else if (clickedButton.getText()=="Reinstall Three Services"){
			parent.clearTextAreas();
			
			
			//Execute remove commands in the PDC VM
			ExecuteCommandViaSSH pdcRemove=new ExecuteCommandViaSSH(parent.GetExternalPDCIP(), parent.getUserName(), keyPath, parent.getSudoPassword(), "pdc");
			pdcRemove.CreateConnection();
			pdcRemove.StartCommand(pdcRemoveCommands);
			pdcRemove.CloseConnection();
			
			//PDC Installation
			System.out.println("Installetion process for PDC with external IP "+parent.GetExternalPDCIP());	
			Thread pdcInstall=new Thread(new deployService(parent.GetExternalPDCIP(), parent.getUserName(), parent.getSudoPassword() ,keyPath, pdcBuildPath,"pdc" ,pdcCommands));
			pdcInstall.start();
			
			//Execute remove commands in the AppServer VM
			ExecuteCommandViaSSH appRemove=new ExecuteCommandViaSSH(parent.GetExternalAppServerIP(), parent.getUserName(), keyPath, parent.getSudoPassword(), "app");
			appRemove.CreateConnection();
			appRemove.StartCommand(appServerRemoveCommands);
			appRemove.CloseConnection();
			
			//AppServer Installation
			System.out.println("Installetion process for AppServer with external IP "+parent.GetExternalAppServerIP());	
			Thread appServerInstall=new Thread(new deployService(parent.GetExternalAppServerIP(), parent.getUserName(), parent.getSudoPassword(), keyPath, appServerBuildPath,"app",appServerCommands));
			appServerInstall.start();
			
			//Execute remove commands in the Historian VM
			ExecuteCommandViaSSH histRemove=new ExecuteCommandViaSSH(parent.GetExternalHistorianIP(), parent.getUserName(), keyPath, parent.getSudoPassword(), "hist");
			histRemove.CreateConnection();
			histRemove.StartCommand(histRemoveCommands);
			histRemove.CloseConnection();
					
			//Historian Installation
			System.out.println("Installetion process for Historian with external IP "+parent.GetExternalHistorianIP());	
			Thread histServerInstall=new Thread(new deployService(parent.GetExternalHistorianIP(), parent.getUserName(), parent.getSudoPassword(), keyPath, historianBuildPath,"hist",historainCommands));
			histServerInstall.start();
			
			
			
			
			
			
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
		      
		      if ((temp.returnFilePath(directPath,"*pdc.rpm*").toString()).matches("NONE") ){
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


