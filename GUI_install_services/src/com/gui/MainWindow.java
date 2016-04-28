package com.gui;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends JFrame{
	
	public MainWindow(){
		
		setSize(400, 600);
		
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        getContentPane().setLayout(gridbag);
              
        
        JLabel pdcIPLable=new JLabel("PDC IP");    
        JLabel appIPLable=new JLabel("AppServer IP");
        JLabel histIPLable=new JLabel("Historian IP");
        
        JTextField pdcIPText=new JTextField(16);
        JTextField appIPText=new JTextField(16);
        JTextField histIPText=new JTextField(16);
        
        JButton pdcInstallButton=new JButton("Install PDC");
        JButton appInstallButton=new JButton("Install AppServer");
        JButton histInstallButton=new JButton("Install Historian");
        
        //Line with PDC elements
        c.gridy=0;
        c.gridx=0;
        this.add(pdcIPLable, c);
        
        c.gridx=1;
        this.add(pdcIPText, c);
        
        c.gridx=2;
        this.add(pdcInstallButton, c);
        
        
      //Line with AppServer elements
        c.gridy=1;
        c.gridx=0;
        this.add(appIPLable, c);
        
        c.gridx=1;
        this.add(appIPText, c);
        c.gridx=2;
        this.add(appInstallButton, c);
        
        
      //Line with Historian elements
        c.gridy=2;
        c.gridx=0;
        this.add(histIPLable, c);
        c.gridx=1;
        this.add(histIPText, c);
        c.gridx=2;
        this.add(histInstallButton, c);
             
                
        
 
         
       
	}

   
 
    public static void main(String[] args)
    {
    	MainWindow flt = new MainWindow();
        flt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flt.setVisible(true);
    }

}
