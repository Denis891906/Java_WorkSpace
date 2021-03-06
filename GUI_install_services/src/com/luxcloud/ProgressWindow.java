package com.luxcloud;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProgressWindow extends JFrame implements Runnable {
	private GUI parent;
	private Thread pdcInstall;

	// Cunstructor for method which show window with "Cancel" button.
	public ProgressWindow(GUI parent) {
		this.parent = parent;

	}

	public void openProgessWindow() {
		setSize(550, 100);
		parent.setEnabled(false);
		setLocationRelativeTo(parent);
		setFocusable(true);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JLabel message = new JLabel("Please wait till URTDSM services will be installed and configured.");
		JPanel p = new JPanel();
		add(p);
		p.setLayout(new FlowLayout());

		p.add(message);

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
		this.openProgessWindow();
		while (Thread.currentThread().isInterrupted() == false) {

		}
		this.closeProgressWindow();

	}

}
