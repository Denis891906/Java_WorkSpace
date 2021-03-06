package com.mainwindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import com.luxcloud.GUI;
import com.openstack.MainWindow;

public class GeneralWindow {

	private JRadioButton openStack;
    private JRadioButton luxcloud;
    private JFrame mainFrame;
    private JLabel question;
    
    public GeneralWindow() {
    	question = new JLabel("Please select type of cloud system for URTDSM deployment:");
    	openStack = new JRadioButton("OpenStack");
    	luxcloud = new JRadioButton("LuxCloud");
        ButtonGroup bgrp = new ButtonGroup();
        
        bgrp.add(openStack);
        bgrp.add(luxcloud);
        
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String msg;
                if ( openStack.isSelected() ){
                	hideGUI();
                	openOpeStackTool();
                }
                else if ( luxcloud.isSelected() ){
                	hideGUI();
                	openLuxCloudTool();
                }
                else
                    
                JOptionPane.showMessageDialog(mainFrame, "Please select one of the of cloud system type.");
            }
        });
        
        mainFrame = new JFrame("URTDSM deployment tool");
        mainFrame.setSize(380, 135);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        mainFrame.add(question);
        mainFrame.add(openStack);
        mainFrame.add(luxcloud);
        mainFrame.add(nextButton);
        
        mainFrame.setVisible(true);
    }
    
    public void hideGUI(){
    	mainFrame.setVisible(false);
    }
    
    public void openOpeStackTool() {
    	MainWindow flt = new MainWindow();
		flt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		flt.setVisible(true);
		
	}
    
    public void openLuxCloudTool(){
    	GUI app=new GUI();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
    }
}
