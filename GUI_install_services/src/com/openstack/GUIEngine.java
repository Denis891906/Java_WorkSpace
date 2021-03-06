package com.openstack;

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

import com.openstack.Find.Finder;

public class GUIEngine implements ActionListener {

	MainWindow parent;
	String pdcBuildPath = null;
	String appServerBuildPath = null;
	String historianBuildPath = null;
	String keyPath;
	private static boolean  pdcFail, appFail, histFail=true;
	public static void setPDCFailConnection(){
		pdcFail=false;
	}
	public static void setAppFailConnection(){
		appFail=false;
	}
	public static void setHistFailConnection(){
		histFail=false;
	}
	
	public static boolean getPDCFailConnection(){
		return pdcFail;
	}
	public static boolean getAppFailConnection(){
		return appFail;
	}
	public static boolean getHistFailConnection(){
		return histFail;
	}
	// pdcCommands=new String[4];
	/*
	 * String[] appServerCommands; String[] historainCommands;
	 */

	// Commands which should be executed to remove PDC service and pdc rmp file
	String[] pdcRemoveCommands = { 
			"sudo rpm -e phasorpoint-pdc", 
			"sudo rm -rf *pdc*",
			"sudo rm -rf /var/log/phasorpoint-pdc/pmu_data/"

	};

	// Commands which should be executed to remove AppServer service and pdc rmp
	// file
	String[] appServerRemoveCommands = { 
			"sudo rpm -e phasorpoint-appserver", 
			"sudo rm -rf *appserver*",
			"sudo rm -rf /var/log/phasorpoint-appserver/"

	};

	// Commands which should be executed to remove Historian service and pdc rmp
	// file
	String[] histRemoveCommands = { 
			"sudo rpm -e phasorpoint-historian", 
			"sudo rm -rf *historian*",
			"sudo rm -rf /var/log/phasorpoint-historian/", "sudo rm -rf /var/phasorpoint-historian/"

	};

	GUIEngine(MainWindow parent) {
		this.parent = parent;
	}

	public void componentResized(ComponentEvent e) {
		parent.pack();
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		JButton clickedButton = null;

		if (src instanceof JButton) {
			clickedButton = (JButton) src;
		}

		if (clickedButton.getText() == "Install selected services") {
			// Commands which should be executed to install PDC service
			String[] pdcCommands = { 
					"sudo rpm -i *pdc*", 
					"sudo /opt/phasorpoint-pdc/bin/createdb -u postgres -p P0stgres -d"

			};

			// Commands which should be executed to install AppServer
			// service
			String[] appServerCommands = { 
					"sudo rpm -i *appserver*",
					"sudo sh -c \"echo 'infrastructure.input.addresses=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'naming.strategy=urtdsm' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'pdc.1.host=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'db.1.host=" + parent.GetInternalHistorianIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"" };

			// Commands which should be executed to install Historian
			// service
			String[] historainCommands = { 
					"sudo rpm -i *historian*",
					"sudo /opt/phasorpoint-historian/bin/createdb -u postgres -p P0stgres -d",
					"sudo sed -i.bak \"s/#listen_addresses = 'localhost'/listen_addresses = '*'/\" /var/lib/pgsql/9.4/data/postgresql.conf",
					"sudo sh -c \"echo 'host all all " + parent.GetInternalAppServerIP().toString()
							+ "/32 md5' >> /var/lib/pgsql/9.4/data/pg_hba.conf\"",
					"sudo service postgresql-9.4 restart",
					"sudo sh -c \"echo  'infrastructure.input.addresses=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'appserver.1.host=" + parent.GetInternalAppServerIP().toString()
							+ "' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'repository.dir=/var/phasorpoint-historian/data' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'summary.repository.dir=/var/phasorpoint-historian/summary' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'repository.size.limit=100000' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'summary.size.limit=50' >> /etc/phasorpoint-historian/historian.properties\"" };
			
			
			
			// Verifications that all data were entered.
			if (keyPath == null) {
				parent.ShowWarningDialog("Please select key file.");
			} else if (parent.GetInternalPDCIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal PDC IP.");
			} else if (parent.GetExternalPDCIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External PDC IP.");
			} else if (parent.GetInternalAppServerIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal AppServer IP.");
			} else if (parent.GetExternalAppServerIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External AppServer IP.");
			} else if (parent.GetInternalHistorianIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal Historian IP.");
			} else if (parent.GetExternalHistorianIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External Historian IP.");
			} else if (parent.GetBuildPath().toString().isEmpty()) {
				parent.ShowWarningDialog("Please select folder with URTDSM build.");
			} else if (parent.getSudoPassword().toString().isEmpty()) {
				parent.ShowWarningDialog("Please enter 'sudo' password for VM's.");
			} else if (parent.getUserName().toString().isEmpty()) {
				parent.ShowWarningDialog("Please enter user name.");
			} else {
				Thread progressWind = new Thread(new ProgressWindow(parent));
				progressWind.start();
					if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==true){
						System.out.println("install Three services");
						// PDC Installation
						System.out.println("Installetion process for PDC with external IP " + parent.GetExternalPDCIP());
						Thread pdcInstall = new Thread(new deployService(parent.GetExternalPDCIP(), parent.getUserName(),
								parent.getSudoPassword(), keyPath, pdcBuildPath, "pdc", pdcCommands));
						pdcInstall.start();

						// AppServer Installation
						System.out.println(
								"Installetion process for AppServer with external IP " + parent.GetExternalAppServerIP());
						Thread appServerInstall = new Thread(
								new deployService(parent.GetExternalAppServerIP(), parent.getUserName(),
										parent.getSudoPassword(), keyPath, appServerBuildPath, "app", appServerCommands));
						appServerInstall.start();

						// Historian Installation
						System.out.println(
								"Installetion process for Historian with external IP " + parent.GetExternalHistorianIP());
						Thread histServerInstall = new Thread(
								new deployService(parent.GetExternalHistorianIP(), parent.getUserName(),
										parent.getSudoPassword(), keyPath, historianBuildPath, "hist", historainCommands));
						histServerInstall.start();
						
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (pdcFail==false || appFail==false || histFail==false){
							progressWind.interrupt();
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								MainWindow.showErrorMessage("URTDSM services weren't installed because ");
							}
						}else{
							
						Thread startAllServices = new Thread(new waitFinishAppAndHistServicesDeploy(pdcInstall,
									appServerInstall, histServerInstall, parent.GetExternalPDCIP(), parent.GetExternalAppServerIP(),
									parent.GetExternalHistorianIP(), parent.getUserName(), parent.getSudoPassword(), keyPath,
									progressWind));
							startAllServices.start();			
					}
						
					}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==true){
						System.out.println("Install PDC and Hist");
						//Install PDC and Hist
						
					}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==false){
						System.out.println(" PDC only");
						// PDC only
					
					}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==false){
						System.out.println("App Only");
					//App Only
				
					}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==false){
						System.out.println("Nothing install window, please select any checkbox");
						//Nothing install window, please select any checkbox
					}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==true){
						System.out.println("Historian only");
						//Historian only
					}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==true){
						System.out.println("App and Historian");
						//App and Historian
					}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==false){
						System.out.println("PDC and APP");
						//PDC and App
					}

				
			}
			
		} else if (clickedButton.getText() == "Reinstall selected services") {
			 String[] pdcCommands = { 
					"sudo rpm -i *pdc*", 
					"sudo /opt/phasorpoint-pdc/bin/createdb -u postgres -p P0stgres -d"

			};

			// Commands which should be executed to install AppServer
			// service
			String[] appServerCommands = { 
					"sudo rpm -i *appserver*",
					"sudo sh -c \"echo 'infrastructure.input.addresses=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'naming.strategy=urtdsm' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'pdc.1.host=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"",
					"sudo sh -c \"echo 'db.1.host=" + parent.GetInternalHistorianIP().toString()
							+ "' >> /etc/phasorpoint-appserver/appserver.properties\"" };

			// Commands which should be executed to install Historian
			// service
			String[] historainCommands = { 
					"sudo rpm -i *historian*",
					"sudo /opt/phasorpoint-historian/bin/createdb -u postgres -p P0stgres -d",
					"sudo sed -i.bak \"s/#listen_addresses = 'localhost'/listen_addresses = '*'/\" /var/lib/pgsql/9.4/data/postgresql.conf",
					"sudo sh -c \"echo 'host all all " + parent.GetInternalAppServerIP().toString()
							+ "/32 md5' >> /var/lib/pgsql/9.4/data/pg_hba.conf\"",
					"sudo service postgresql-9.4 restart",
					"sudo sh -c \"echo  'infrastructure.input.addresses=" + parent.GetInternalPDCIP().toString()
							+ "' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'appserver.1.host=" + parent.GetInternalAppServerIP().toString()
							+ "' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'repository.dir=/var/phasorpoint-historian/data' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'summary.repository.dir=/var/phasorpoint-historian/summary' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'repository.size.limit=100000' >> /etc/phasorpoint-historian/historian.properties\"",
					"sudo sh -c \"echo 'summary.size.limit=50' >> /etc/phasorpoint-historian/historian.properties\"" };
			
			// Verifications that all data were entered.
			if (keyPath == null) {
				parent.ShowWarningDialog("Please select key file.");
			} else if (parent.GetInternalPDCIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal PDC IP.");
			} else if (parent.GetExternalPDCIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External PDC IP.");
			} else if (parent.GetInternalAppServerIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal AppServer IP.");
			} else if (parent.GetExternalAppServerIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External AppServer IP.");
			} else if (parent.GetInternalHistorianIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter Internal Historian IP.");
			} else if (parent.GetExternalHistorianIP().toString().isEmpty()) {
				parent.ShowWarningDialog("Please eneter External Historian IP.");
			} else if (parent.GetBuildPath().toString().isEmpty()) {
				parent.ShowWarningDialog("Please select folder with URTDSM build.");
			} else if (parent.getSudoPassword().toString().isEmpty()) {
				parent.ShowWarningDialog("Please enter 'sudo' password for VM's.");
			} else if (parent.getUserName().toString().isEmpty()) {
				parent.ShowWarningDialog("Please enter user name.");
			} else {
				//Open progress window
				Thread progressWind = new Thread(new ProgressWindow(parent));
				progressWind.start();
			
				// Commands which should be executed to install PDC service
				
				
				if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==true){
					System.out.println("install Three services");
					//install Three services
					
					try{
					// Execute remove commands in the PDC VM
					ExecuteCommandViaSSH pdcRemove = new ExecuteCommandViaSSH(parent.GetExternalPDCIP(),
							parent.getUserName(), keyPath, parent.getSudoPassword(), "pdc");
					pdcRemove.CreateConnection();
					pdcRemove.StartCommand(pdcRemoveCommands);
					pdcRemove.CloseConnection();

					// PDC Installation
					System.out.println("Installetion process for PDC with external IP " + parent.GetExternalPDCIP());
					Thread pdcInstall = new Thread(new deployService(parent.GetExternalPDCIP(), parent.getUserName(),
							parent.getSudoPassword(), keyPath, pdcBuildPath, "pdc", pdcCommands));
					pdcInstall.start();

					// Execute remove commands in the AppServer VM
					ExecuteCommandViaSSH appRemove = new ExecuteCommandViaSSH(parent.GetExternalAppServerIP(),
							parent.getUserName(), keyPath, parent.getSudoPassword(), "app");
					appRemove.CreateConnection();
					appRemove.StartCommand(appServerRemoveCommands);
					appRemove.CloseConnection();

					// AppServer Installation
					System.out.println(
							"Installetion process for AppServer with external IP " + parent.GetExternalAppServerIP());
					Thread appServerInstall = new Thread(
							new deployService(parent.GetExternalAppServerIP(), parent.getUserName(),
									parent.getSudoPassword(), keyPath, appServerBuildPath, "app", appServerCommands));
					appServerInstall.start();

					// Execute remove commands in the Historian VM
					ExecuteCommandViaSSH histRemove = new ExecuteCommandViaSSH(parent.GetExternalHistorianIP(),
							parent.getUserName(), keyPath, parent.getSudoPassword(), "hist");
					histRemove.CreateConnection();
					histRemove.StartCommand(histRemoveCommands);
					histRemove.CloseConnection();

					// Historian Installation
					System.out.println(
							"Installetion process for Historian with external IP " + parent.GetExternalHistorianIP());
					Thread histServerInstall = new Thread(
							new deployService(parent.GetExternalHistorianIP(), parent.getUserName(),
									parent.getSudoPassword(), keyPath, historianBuildPath, "hist", historainCommands));
					histServerInstall.start();

					Thread startAllServices = new Thread(new waitFinishAppAndHistServicesDeploy(pdcInstall,
							appServerInstall, histServerInstall, parent.GetExternalPDCIP(), parent.GetExternalAppServerIP(),
							parent.GetExternalHistorianIP(), parent.getUserName(), parent.getSudoPassword(), keyPath,
							progressWind));
					startAllServices.start();
					}catch(Exception error){
						progressWind.interrupt();
					}

				}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==true){
					System.out.println("Install PDC and Hist");
					//Install PDC and Hist
										
				}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==false){
					System.out.println(" PDC only");
					// PDC only
				
				}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==false){
					System.out.println("App Only");
				//App Only
			
				}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==false){
					System.out.println("Nothing install window, please select any checkbox");
			//Nothing install window, please select any checkbox
				}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==false && parent.getHistCheckBoxValue()==true){
					System.out.println("Historian only");
			//Historian only
				}else if (parent.getPDCCheckBoxValue()==false && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==true){
					System.out.println("App and Historian");
			//App and Historian
				}else if (parent.getPDCCheckBoxValue()==true && parent.getAppCheckBoxValue()==true && parent.getHistCheckBoxValue()==false){
					System.out.println("PDC and APP");
			//PDC and App
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				

				

				
				
							}

		} else if (clickedButton.getText() == "Select Build Folder") {

			JFileChooser chooser;
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Dialog");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			//
			// disable the "All files" option.
			//
			chooser.setAcceptAllFileFilterUsed(false);
			//
			if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
				Path directPath = Paths.get(chooser.getSelectedFile().getAbsolutePath());

				Find temp = new Find();

				System.out.println("Returned: " + temp.returnFilePath(directPath, "*pdc*").toString());

				if ((temp.returnFilePath(directPath, "*pdc*").toString()).matches("NONE")) {
					parent.ShowWarningDialog("PDC install rpm file wasn't found in the directory " + directPath
							+ " . \nPlease specify directory again.");
				} else if ((temp.returnFilePath(directPath, "*app*").toString()).matches("NONE")) {
					parent.ShowWarningDialog("AppServer install rpm file wasn't found in the directory " + directPath
							+ " . \nPlease specify directory again.");
				} else if ((temp.returnFilePath(directPath, "*hist*").toString()).matches("NONE")) {
					parent.ShowWarningDialog("Historian install rpm file wasn't found in the directory " + directPath
							+ " . \nPlease specify directory again.");
				} else if (chooser.getSelectedFile().toString().length() > 81) {
					parent.SetBuildPath("..." + chooser.getSelectedFile().getName().toString(),
							temp.returnFilePath(directPath, "*pdc*").toString(),
							temp.returnFilePath(directPath, "*app*").toString(),
							temp.returnFilePath(directPath, "*hist*").toString());
					pdcBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*pdc*").toString();
					appServerBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*app*").toString();
					historianBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*hist*").toString();
				} else {
					parent.SetBuildPath(chooser.getSelectedFile().getAbsolutePath(),
							temp.returnFilePath(directPath, "*pdc*").toString(),
							temp.returnFilePath(directPath, "*app*").toString(),
							temp.returnFilePath(directPath, "*hist*").toString());
					pdcBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*pdc*").toString();
					appServerBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*app*").toString();
					historianBuildPath = chooser.getSelectedFile().getAbsolutePath() + "\\"
							+ temp.returnFilePath(directPath, "*hist*").toString();

				}

				parent.pack();
			} else {
				System.out.println("No Selection ");
			}
		} else if (clickedButton.getText() == "Select Key File") {

			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PEM", "pem");
			chooser.setFileFilter(filter);
			chooser.setCurrentDirectory(new java.io.File("."));
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				parent.SetKeyPath(chooser.getSelectedFile().getAbsolutePath());
				keyPath = chooser.getSelectedFile().getAbsolutePath();
				parent.pack();
			}
		} else if (clickedButton.getText() == "PDC Install log") {

			LogFrame my = new LogFrame(parent.pdcInstallLog, "PDC deployment log");

		} else if (clickedButton.getText() == "AppServer Install log") {

			LogFrame my = new LogFrame(parent.appServerInstallLog, "AppServer deployment log");

		} else if (clickedButton.getText() == "Historian Install log") {

			LogFrame my = new LogFrame(parent.historianInstallLog, "Historian deployment log");

		}

	}
}
