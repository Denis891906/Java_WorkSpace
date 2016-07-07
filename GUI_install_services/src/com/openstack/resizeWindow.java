package com.openstack;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class resizeWindow implements ComponentListener {
	MainWindow parent;

	public resizeWindow(MainWindow parent) {
		this.parent = parent;

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		parent.pack();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
