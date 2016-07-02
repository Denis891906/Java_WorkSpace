package com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProgressWindow extends JFrame {
	private JButton cancelButton;
	
	public ProgressWindow(GUI parent) {
		// TODO Auto-generated constructor stub
		setSize(350, 100);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JLabel message=new JLabel("Please wait till URTDSM services will be deploment.");
		cancelButton=new JButton("Cancel");
		
		JPanel p = new JPanel(); 
        add(p);
        p.setLayout(new FlowLayout());
		
        p.add(message);
        p.add(cancelButton);         
        setLocationRelativeTo(parent);
        parent.setEnabled(false);
        setResizable(false);
        		
		
	}	
	
	
	//method which should start deploment process for all cervices
	private void startDeplometCervices(){
		
	}
	
	
	//Add action listener for cancell button
}