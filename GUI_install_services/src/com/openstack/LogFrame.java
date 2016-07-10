package com.openstack;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class LogFrame extends JFrame {

	public LogFrame(String logMessage, String windowName) {
		setTitle(windowName);
		setResizable(false);
		setBounds(100, 100, 590, 410);

		setVisible(true);

		getContentPane().setLayout(null);

		JTextArea textField = new JTextArea();
		textField.setEditable(false);

		textField.setText(logMessage);

		JScrollPane scroll = new JScrollPane(textField);
		scroll.setBounds(15, 11, 555, 355);

		// <-- THIS
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scroll);
		setLocationRelativeTo(null);

	}

}