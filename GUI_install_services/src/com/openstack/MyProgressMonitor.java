package com.openstack;

import javax.swing.ProgressMonitor;

import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor implements SftpProgressMonitor {

	MyProgressMonitor(String serverType) {
		this.serverType = serverType;
	}

	// ProgressMonitor monitor;
	long count = 0;
	long max = 0;

	public void init(int op, String src, String dest, long max) {
		this.max = max;
		/*
		 * monitor=new ProgressMonitor(null, ((op==SftpProgressMonitor.PUT)?
		 * "put" : "get")+": "+src, "", 0, (int)max); count=0; percent=-1;
		 * monitor.setProgress((int)this.count);
		 * monitor.setMillisToDecideToPopup(1000);
		 */
		System.out.println("Uploading file ...");
		MainWindow.setMessage("Uploading file ", serverType);
	}

	private long percent = -1;
	private String serverType;

	public boolean count(long count) {
		this.count += count;

		if (percent >= this.count * 100 / max) {
			return true;
		}
		percent = this.count * 100 / max;
		if (percent == 0) {

			System.out.print("..." + percent + "%");
			MainWindow.setMessage("..." + percent + "%", serverType);
		} else if (percent == 10) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 20) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 30) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 40) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 50) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);

		} else if (percent == 60) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 70) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 80) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 90) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		} else if (percent == 100) {
			System.out.print("..." + percent + "%");
			MainWindow.setProgressMessage("..." + percent + "%", serverType);
		}

		/*
		 * monitor.setNote("Completed "+this.count+"("+percent+"%) out of "
		 * +max+"."); monitor.setProgress((int)this.count);
		 * 
		 * return !(monitor.isCanceled());
		 */
		return true;
	}

	public void end() {
		System.out.println("\nUploading was finished");
		MainWindow.setMessage("Uploading was finished", serverType);
		// monitor.close();
	}
}