package com;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class GUI extends JFrame{
	public JLabel buildPathLable;
	public JLabel keyPathLable;
	public JLabel buildPDCPathLable1;
	public JLabel buildPDCPathLable2;
	public JLabel buildAppServerPathLable1;
	public JLabel buildAppServerPathLable2;
	public JLabel buildHistoranPathLable1;
	public static String pdcInstallLog="";
	public static String appServerInstallLog="";
	public static String historianInstallLog="";
	
	public JLabel buildHistoranPathLable2;
	private JTextField internalPdcIPText;
	
	private static JTextArea pdcTextArea;
	private static JTextArea appServerTextArea;
	private static JTextArea histTextArea;
	
	private JTextField internalAppIPText;
	
	private JTextField internalHistIPText;
	
	
	private JTextField usernameText;
	private JTextField sudoPasswordText;
	
	public static void setMessage(String text,String serverType){
		if (serverType=="pdc"){
			pdcInstallLog=pdcInstallLog+"\n"+text;
			
		//pdcTextArea.setText(pdcTextArea.getText()+"\n"+text);
		}else if(serverType=="app"){
			appServerInstallLog=appServerInstallLog+"\n"+text;
			//appServerTextArea.setText(appServerTextArea.getText()+"\n"+text);
		}else if (serverType=="hist"){
			historianInstallLog=historianInstallLog+"\n"+text;
			
		//histTextArea.setText(histTextArea.getText()+"\n"+text);
		}
	}
	public static void setProgressMessage(String text,String serverType){
		if (serverType=="pdc"){
			pdcInstallLog=pdcInstallLog+"\n"+text;
			//pdcTextArea.setText(pdcTextArea.getText()+text);
			}else if(serverType=="app"){
				appServerInstallLog=appServerInstallLog+"\n"+text;
				//appServerTextArea.setText(appServerTextArea.getText()+text);
			}else if (serverType=="hist"){
				historianInstallLog=historianInstallLog+"\n"+text;
				//histTextArea.setText(histTextArea.getText()+text);
			}
	}
	public static void showErrorMessage(String text){
		JOptionPane.showMessageDialog(new JFrame(), text, "Execption dialog", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	
	public GUI(){
		
		
		setTitle("Tool for deploy URTDSM build to LuxCloud (r0.1)");
		
		this.setSize(500, 600);
		
				
		
		//////////////////////////////////////////////////////////////////////////////
		//Section for OpenStack Vm install
		//////////////////////////////////////////////////////
		
	
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);
       // this.setSize(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ);
        
        
        
              
        
        JLabel pdcIPLable=new JLabel("Internal PDC IP");  
        pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        
        
        JLabel appIPLable=new JLabel("Internal AppServer IP");
        appIPLable.setHorizontalAlignment(SwingConstants.LEFT);
       
        
        
        JLabel histIPLable=new JLabel("Internal Historian IP");
        histIPLable.setHorizontalAlignment(SwingConstants.LEFT);
      
        
        JLabel usernameLable=new JLabel("username");  
        pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel rootPasswordIPLable=new JLabel("root password");  
        rootPasswordIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        
        buildPathLable=new JLabel();
        keyPathLable=new JLabel();
        buildPDCPathLable1 = new JLabel();
        buildPDCPathLable2 = new JLabel();
        buildAppServerPathLable1 = new JLabel();
    	buildAppServerPathLable2 = new JLabel();
    	buildHistoranPathLable1 = new JLabel();
    	buildHistoranPathLable2 = new JLabel();
        
        
    	internalPdcIPText=new JTextField(16);
    	internalPdcIPText.setText("172.26.128.0");
    	
    	
        internalAppIPText=new JTextField(16);
        internalAppIPText.setText("172.26.128.0");
        
        
        internalHistIPText=new JTextField(16);
        internalHistIPText.setText("172.26.128.0");
        
        usernameText = new JTextField(16);
        usernameText.setText("tempuser");
        sudoPasswordText=new JTextField(16);
        sudoPasswordText.setText("1qaz@WSX");
        
        JButton pdcInstallButton=new JButton("Install PDC, AppServer, Historian");
        JButton appInstallButton=new JButton("Reinstall Three Services");
        JButton selectBuildFolderButton=new JButton("Select Build Folder");
        JButton selectKeyFileButton=new JButton("Select Key File");
        JButton pdcInstallLogButton=new JButton("PDC Install log");
        JButton appServerInstallLogButton=new JButton("AppServer Install log");
        JButton histInstallLogButton=new JButton("Historian Install log");
        //JButton openIE=new JButton("Open Internet Expolorer");
        
        pdcTextArea = new JTextArea(23,63);
        pdcTextArea.setEditable(false);
        
        appServerTextArea = new JTextArea(23,63);
        appServerTextArea.setEditable(false);
        
        histTextArea = new JTextArea(23,63);
        histTextArea.setEditable(false);
        
        
        
        
        //Create main Tab panel
        
        JTabbedPane tabPanel = new JTabbedPane();
        
        // Add three tabs to the main tab panel
        tabPanel.add("PDC logs", pdcTextArea);
        tabPanel.add("AppServer logs", appServerTextArea);
        tabPanel.add("Historian logs", histTextArea);
       
       //add Main tab panel to on scroll pane 
        JScrollPane mainScrollPane=new JScrollPane(tabPanel);
        
        
        //
        
       
        //PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
         
        // keeps reference of standard output stream
        //standardOut = System.out;
         
        // re-assigns standard output stream and error output stream
       
        
        
        
        
        
        //Add listener for button selectBuildFolder
        
        Engine guiEngine=new Engine(this);
        
        selectBuildFolderButton.addActionListener(guiEngine);
        pdcInstallButton.addActionListener(guiEngine);
        appInstallButton.addActionListener(guiEngine);
        selectKeyFileButton.addActionListener(guiEngine);
        pdcInstallLogButton.addActionListener(guiEngine);
        appServerInstallLogButton.addActionListener(guiEngine);
        histInstallLogButton.addActionListener(guiEngine);
       // openIE.addActionListener(guiEngine);
        
      //  this.addComponentListener(new resizeWindow(this));
        
        this.setResizable(false);
        
        
        //Line 1 with PDC elements
        c.gridy=0; //line
        c.gridx=0; //column
        c.gridwidth=1;//������� ������ �������� ������� � ������
        c.gridheight=1;//������� ������ �������� ������� � ������
        c.weightx=0;// ��� ����� ����������� �������� ���������� �� �
        c.weighty=0;// ��� ����� ����������� �������� ���������� �� Y
        c.anchor=GridBagConstraints.BASELINE_LEADING; // ��� ������� ����� �������������
        //c.fill=GridBagConstraints.HORIZONTAL; //��� ����� ������������� �������
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.ipadx=1; // �� ������� �������������� ����������� ������ ��������
        c.ipady=1; // �� ������� �������������� ����������� ������ ��������       
        this.add(pdcIPLable, c);
        
        
        c.gridx=1;
        c.gridwidth=3;
        c.weightx=0;
        this.add(internalPdcIPText, c);
        
        c.gridwidth=1;
        c.gridx=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        this.add(pdcInstallButton, c);
        
        
        
      
        
       
        
        
        
      //Line 3 with AppServer elements
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridx=0;
        c.gridy=1;
        this.add(appIPLable, c);
        c.gridx=1;
        this.add(internalAppIPText, c);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.gridx=2;
        this.add(appInstallButton, c);
        
        
       
        
      //Line 5 with Historian elements
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=3;
        c.gridx=0;
        this.add(histIPLable, c);
        c.gridx=1;
        this.add(internalHistIPText, c);
       
        
      //Line 6 with Historian elements  
        
       /* c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.gridx=2;
        this.add(openIE, c);*/
       
        
        
        
        
             
        //Builds path elements
        c.gridx=0;
        c.gridy=4;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        this.add(selectBuildFolderButton, c);
        
        c.gridx=1;
        c.gridy=4;
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.gridwidth=5;
        c.gridheight=1;
        c.fill=GridBagConstraints.HORIZONTAL;;
        
        this.add(buildPathLable, c);   
    	
        c.gridx=0;
        c.gridy=5;
        c.gridwidth=1;
        this.add(buildPDCPathLable1,c);
        
        
        c.gridx=1;
        c.gridy=5;
        c.gridwidth=5;
        this.add(buildPDCPathLable2,c);
        
        c.gridx=0;
        c.gridy=6;
        c.gridwidth=1;
        this.add(buildAppServerPathLable1, c);
        
        c.gridx=1;
        c.gridy=6;
        c.gridwidth=5;
        this.add(buildAppServerPathLable2, c);
    	
        c.gridx=0;
        c.gridy=7;
        c.gridwidth=1;
        this.add(buildHistoranPathLable1, c);
    	
        c.gridx=1;
        c.gridy=7;
        c.gridwidth=5;
        this.add(buildHistoranPathLable2, c);
        
         
     
        
        
        //Line with user name text box
        
        c.gridx=0;
        c.gridy=9;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        this.add(usernameLable,c);

        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=9;
        c.gridx=0;
        c.gridx=1;
        this.add(usernameText, c);
        
        
        //Sudo password Line
        c.gridx=0;
        c.gridy=10;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        this.add(rootPasswordIPLable,c);
        
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=10;
        c.gridx=0;
        c.gridx=1;
        this.add(sudoPasswordText, c);
        
        c.gridy=11;
        c.gridx=0;
        this.add(pdcInstallLogButton,c);
        c.gridx=1;
        this.add(appServerInstallLogButton,c);
        c.gridx=2;
        this.add(histInstallLogButton,c);
        
        
/*        // Add main Scroll pane to the main window
       c.gridx=0;
        c.gridy=12;
        c.fill=GridBagConstraints.BOTH;
        c.gridwidth=23;
        c.gridheight=63;//������� ������ �������� ������� � ������
       
        this.add(mainScrollPane, c);
*/        
        
		buildPDCPathLable1.setText("PDC rpm file path:");
		buildAppServerPathLable1.setText("AppServer rpm file path:");
		buildHistoranPathLable1.setText("Historian rpm file path:");
        
        this.pack(); // delete empty space from window
	}
	
	/////////////////////////////////////////////////
		
		
	public void SetBuildPath(String path, String pdcPath,String appPath,String histPath){
		this.clearTextAreas();
		
		
		buildPathLable.setText(path);	

		buildPDCPathLable2.setText(pdcPath);
		
		buildAppServerPathLable2.setText(appPath);
		
		buildHistoranPathLable2.setText(histPath);
		//this.pack();
	}
	
	public String GetBuildPath(){
		System.out.println("GetBuildPath method returns path: "+buildPathLable.getText());
		return buildPathLable.getText();
	}
	
	public void SetKeyPath(String path){
		keyPathLable.setText(path);
	}

	public void ShowWarningDialog (String text){
		JOptionPane.showMessageDialog(new JFrame(), text, "Dialog", JOptionPane.ERROR_MESSAGE);
	} 
	
	
	public static void showInfoMessage (String text){
		JOptionPane.showMessageDialog(new JFrame(), text, "Dialog", JOptionPane.INFORMATION_MESSAGE);
	} 
	
	
	public String GetInternalPDCIP(){
		return internalPdcIPText.getText();
	}
	
	public String GetInternalAppServerIP(){
		return internalAppIPText.getText();
	}	
	
	public String GetInternalHistorianIP(){
		return internalHistIPText.getText();
	}
	public String getUserName(){
		return usernameText.getText();
	}
	public String getSudoPassword(){
		return sudoPasswordText.getText().toString();
		
	}
	
	public void clearTextAreas(){
		pdcTextArea.setText("");
		appServerTextArea.setText("");
		histTextArea.setText("");
	}
}
