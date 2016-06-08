package com.gui;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.awt.event.ActionEvent;


import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class MainWindow extends JFrame{
	public JLabel buildPathLable;
	public JLabel keyPathLable;
	public JLabel buildPDCPathLable1;
	public JLabel buildPDCPathLable2;
	public JLabel buildAppServerPathLable1;
	public JLabel buildAppServerPathLable2;
	public JLabel buildHistoranPathLable1;
	public JLabel buildHistoranPathLable2;
	private JTextField internalPdcIPText;
	private JTextField externalPdcIPText;
	private static JTextArea textArea;
	private JTextField internalAppIPText;
	private JTextField externalAppIPText;
	private JTextField internalHistIPText;
	private JTextField externalHistIPText;
	private PrintStream standardOut;
	
	public static void setMessage(String text){
		textArea.setText(textArea.getText()+"\n"+text);
		
	}
	
	
	
	public MainWindow(){
		
		
		setSize(950, 1500);
		
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        getContentPane().setLayout(gridbag);
       // this.setSize(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ);
        
        
        
              
        
        JLabel pdcIPLable=new JLabel("Internal PDC IP");  
        pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel pdcIPLable2=new JLabel("External PDC IP");
        pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel appIPLable=new JLabel("Internal AppServer IP");
        appIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel appIPLable2=new JLabel("External AppServer IP");
        appIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        
        
        JLabel histIPLable=new JLabel("Internal Historian IP");
        histIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel histIPLable2=new JLabel("External Historian IP");
        histIPLable.setHorizontalAlignment(SwingConstants.LEFT);
        
        buildPathLable=new JLabel();
        keyPathLable=new JLabel();
        buildPDCPathLable1 = new JLabel();
        buildPDCPathLable2 = new JLabel();
        buildAppServerPathLable1 = new JLabel();
    	buildAppServerPathLable2 = new JLabel();
    	buildHistoranPathLable1 = new JLabel();
    	buildHistoranPathLable2 = new JLabel();
        
        
    	internalPdcIPText=new JTextField(16);
    	internalPdcIPText.setText("192.168.160.129");
    	externalPdcIPText=new JTextField(16);
    	externalPdcIPText.setText("10.35.204.56");
    	
        internalAppIPText=new JTextField(16);
        internalAppIPText.setText("192.168.160.130");
        externalAppIPText=new JTextField(16);
        externalAppIPText.setText("10.35.204.166");
        
        internalHistIPText=new JTextField(16);
        internalHistIPText.setText("192.168.160.131");
        externalHistIPText=new JTextField(16);
        externalHistIPText.setText("10.35.204.167");
        
        JButton pdcInstallButton=new JButton("Install PDC, AppServer, Historian");
        JButton appInstallButton=new JButton("Install 2");
        JButton histInstallButton=new JButton("Install 3");
        JButton selectBuildFolderButton=new JButton("Select Build Folder");
        JButton selectKeyFileButton=new JButton("Select Key File");
        
        
        
        
        
        
        
        
        //
        
        textArea = new JTextArea(23,1);
        textArea.isMaximumSizeSet();
        textArea.setEditable(false);
        //PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
         
        // keeps reference of standard output stream
        //standardOut = System.out;
         
        // re-assigns standard output stream and error output stream
        /*System.setOut(printStream);
        System.setErr(printStream);*/
        
        
        
        
        
        //Add listener for button selectBuildFolder
        
        GUIEngine guiEngine=new GUIEngine(this);
        
        selectBuildFolderButton.addActionListener(guiEngine);
        pdcInstallButton.addActionListener(guiEngine);
        selectKeyFileButton.addActionListener(guiEngine);
        
      
        
        
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
        c.gridwidth=1;
        c.weightx=0;
        this.add(internalPdcIPText, c);
        
        c.gridx=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        this.add(pdcInstallButton, c);
        
        //Line 2 with external PDC elements
        
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridx=0;
        c.gridy=1;
        this.add(pdcIPLable2, c);
        c.gridx=1;
        this.add(externalPdcIPText, c);
        
        
        
      //Line 3 with AppServer elements
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridx=0;
        c.gridy=2;
        this.add(appIPLable, c);
        c.gridx=1;
        this.add(internalAppIPText, c);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.gridx=2;
        this.add(appInstallButton, c);
        
        
        //Line 4 with AppServer elements
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=3;
        c.gridx=0;
        this.add(appIPLable2, c);
        c.gridx=1;
        this.add(externalAppIPText, c);
        
      //Line 5 with Historian elements
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=4;
        c.gridx=0;
        this.add(histIPLable, c);
        c.gridx=1;
        this.add(internalHistIPText, c);
        c.gridx=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(1, 200, 0, 0); // ������� ����������� ( top,left, bottom, right )
        this.add(histInstallButton, c);
        
      //Line 6 with Historian elements  
        
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.fill=GridBagConstraints.NONE;
        c.gridy=5;
        c.gridx=0;
        this.add(histIPLable2, c);
        c.gridx=1;
        this.add(externalHistIPText, c);
        
        
        
        
             
        //Builds path elements
        c.gridx=0;
        c.gridy=6;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        this.add(selectBuildFolderButton, c);
        
        c.gridx=1;
        c.gridy=6;
                c.insets=new Insets(0, 0, 0, 0); // ������� ����������� ( top,left, bottom, right )
        c.gridwidth=5;
        c.fill=GridBagConstraints.HORIZONTAL;;
        this.add(buildPathLable, c);   
    	
        c.gridx=0;
        c.gridy=7;
        c.gridwidth=1;
        this.add(buildPDCPathLable1,c);
        
        
        c.gridx=1;
        c.gridy=7;
        c.gridwidth=5;
        this.add(buildPDCPathLable2,c);
        
        c.gridx=0;
        c.gridy=8;
        c.gridwidth=1;
        this.add(buildAppServerPathLable1, c);
        
        c.gridx=1;
        c.gridy=8;
        c.gridwidth=5;
        this.add(buildAppServerPathLable2, c);
    	
        c.gridx=0;
        c.gridy=9;
        c.gridwidth=1;
        this.add(buildHistoranPathLable1, c);
    	
        c.gridx=1;
        c.gridy=9;
        c.gridwidth=5;
        this.add(buildHistoranPathLable2, c);
        
         
      //key path elements       
        c.gridx=0;
        c.gridy=10;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridwidth=1;
        this.add(selectKeyFileButton,c);
        
        c.gridx=1;
        c.gridwidth=7;
        this.add(keyPathLable,c);
        
        // text area
       c.gridx=0;
        c.gridy=11;
        c.fill=GridBagConstraints.BOTH;
        c.gridwidth=7;
        c.gridheight=60;//������� ������ �������� ������� � ������
        //c.ipady=10;
        //c.weighty=22;
        //c.anchor=GridBagConstraints.CENTER;
       JScrollPane pane=new JScrollPane(textArea);
       //pane.setPreferredSize(getMaximumSize());
        this.add(pane, c);
        
        
        
        
        this.pack(); // delete empty space from window
	}
	public void SetBuildPath(String path, String pdcPath,String appPath,String histPath){
		buildPathLable.setText(path);
		buildPDCPathLable1.setText("PDC rpm file path:");
		buildPDCPathLable2.setText(pdcPath);
		buildAppServerPathLable1.setText("AppServer rpm file path:");
		buildAppServerPathLable2.setText(appPath);
		buildHistoranPathLable1.setText("Historian rpm file path:");
		buildHistoranPathLable2.setText(histPath);
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
	
	public String GetExternalPDCIP(){
		return externalPdcIPText.getText();
	}
	public String GetInternalPDCIP(){
		return internalPdcIPText.getText();
	}
	public String GetExternalAppServerIP(){
		return externalAppIPText.getText();
	}
	public String GetInternalAppServerIP(){
		return internalAppIPText.getText();
	}	
	public String GetExternalHistorianIP(){
		return externalHistIPText.getText();
	}
	public String GetInternalHistorianIP(){
		return internalHistIPText.getText();
	}
    public static void main(String[] args)
    {
    	MainWindow flt = new MainWindow();
        flt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flt.setVisible(true);
    }


}
