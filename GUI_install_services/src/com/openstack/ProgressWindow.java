package com.openstack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.luxcloud.GUI;

public class ProgressWindow extends JFrame implements Runnable {
	private MainWindow parent;
	private Thread pdcInstall;

	// Cunstructor for method which show window with "Cancel" button.
	public ProgressWindow(MainWindow parent) {
		this.parent = parent;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(550, 100);
		JLabel message = new JLabel("Please wait till URTDSM services will be installed and configured.");
		JPanel p = new JPanel();
		add(p);
		p.setLayout(new FlowLayout());
		p.add(message);
		pack();
	
		
		

	}

	public void openProgessWindow() {
		
		parent.setEnabled(false);
		setLocationRelativeTo(parent);
		setResizable(false);
		setVisible(true);
		this.repaint();
		
		//JOptionPane.showMessageDialog(parent, "Please wait till URTDSM services will be installed and configured.");
		

		
	}

	public void closeProgressWindow() {
		setFocusable(false);
		this.setVisible(false);
		this.dispose();
		parent.setEnabled(true);
		parent.enableLogsButtons();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		openProgessWindow();
		
		while (Thread.currentThread().isInterrupted() == false) {

		}
		closeProgressWindow();

	}

}
