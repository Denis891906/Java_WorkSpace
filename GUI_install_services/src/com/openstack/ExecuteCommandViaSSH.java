package com.openstack;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

public class ExecuteCommandViaSSH {

	ExecuteCommandViaSSH(String host, String user, String keyPath, String sudo_pass, String serverType) {
		this.SFTPHOST = host;
		this.SFTPPORT = 22;
		this.SFTPUSER = user;
		// this file can be id_rsa or id_dsa based on which algorithm is used to
		// create the key
		this.privateKey = keyPath;
		this.SFTPWORKINGDIR = "/home/" + user + "/";
		this.sudo_pass = sudo_pass;
		this.serverType = serverType;
	}

	ExecuteCommandViaSSH(String host, String user, String keyPath, String sudo_pass, String serverType,
			String command) throws Exception {
		this.SFTPHOST = host;
		this.SFTPPORT = 22;
		this.SFTPUSER = user;
		// this file can be id_rsa or id_dsa based on which algorithm is used to
		// create the key
		this.privateKey = keyPath;
		this.SFTPWORKINGDIR = "/home/" + user + "/";
		this.sudo_pass = sudo_pass;
		this.serverType = serverType;
		this.CreateConnection();
		this.StartCommand(command);
		this.CloseConnection();

	}

	private String SFTPHOST;
	private int SFTPPORT;
	private String SFTPUSER;
	private String privateKey;
	private String SFTPWORKINGDIR;
	private Channel channel = null;
	private Session session = null;
	private String sudo_pass;
	private String serverType;

	public void CreateConnection() throws Exception {
		JSch jSch = new JSch();

		ChannelSftp channelSftp = null;

		try {
			jSch.addIdentity(privateKey);
			System.out.println("Private Key Added.");
			session = jSch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);

			System.out.println("session created.");
			MainWindow.setMessage("Session for execute command via ssh was opened with host " + SFTPHOST, serverType);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

		} catch (Exception e) {
			System.out.println(e);
			//MainWindow.showErrorMessage(e.getMessage().toString());
			throw new Exception(e);
		}
	}

	public void StartCommand(String command) {

		try {
			channel = session.openChannel("exec");
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if (e1.getMessage().toString().contains("session is down")) {
				MainWindow.showErrorMessage(
						"Connection with host " + this.SFTPHOST + " was interupted \n" + e1.getMessage().toString());

			} else {
				MainWindow.showErrorMessage(e1.getMessage().toString());
			}
		}

		channel.setInputStream(null);
		String result = " ";
		((ChannelExec) channel).setCommand("sudo -S -p '' " + command);
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in;
		try {
			in = channel.getInputStream();

			OutputStream out = channel.getOutputStream();
			((ChannelExec) channel).setPty(true);
			try {
				channel.connect();
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MainWindow.showErrorMessage(e.getMessage().toString());
			}

			out.write((sudo_pass + "\n").getBytes());
			out.flush();
			out.close();

			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer);
			String theString = writer.toString();
			result = theString;
			System.out.println(result);
			MainWindow.setMessage("Command was sent: " + command, serverType);
			MainWindow.setMessage(result, serverType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainWindow.showErrorMessage(e.getMessage().toString());
		}
	}

	public String StartCommandAndReturnResult(String command) {

		try {
			channel = session.openChannel("exec");
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if (e1.getMessage().toString().contains("session is down")) {
				MainWindow.showErrorMessage(
						"Connection with host " + this.SFTPHOST + " was interupted \n" + e1.getMessage().toString());

			} else {
				MainWindow.showErrorMessage(e1.getMessage().toString());
			}
		}

		channel.setInputStream(null);
		String result = " ";
		((ChannelExec) channel).setCommand("sudo -S -p '' " + command);
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in;
		try {
			in = channel.getInputStream();

			OutputStream out = channel.getOutputStream();
			((ChannelExec) channel).setPty(true);
			try {
				channel.connect();
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MainWindow.showErrorMessage(e.getMessage().toString());
			}

			out.write((sudo_pass + "\n").getBytes());
			out.flush();
			out.close();

			StringWriter writer = new StringWriter();
			IOUtils.copy(in, writer);
			String theString = writer.toString();
			result = theString;

			System.out.println(result);
			MainWindow.setMessage("Command was sent: " + command, serverType);
			MainWindow.setMessage(result, serverType);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainWindow.showErrorMessage(e.getMessage().toString());
		}
		return result;
	}

	public void StartCommand(String[] commands) {
		for (int i = 0; i < commands.length; i++) {
			try {
				channel = session.openChannel("exec");
			} catch (JSchException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				MainWindow.showErrorMessage(e1.getMessage());
			}

			channel.setInputStream(null);
			String result = " ";
			((ChannelExec) channel).setCommand("sudo -S -p '' " + commands[i]);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in;
			try {
				in = channel.getInputStream();

				OutputStream out = channel.getOutputStream();
				((ChannelExec) channel).setPty(true);
				try {
					channel.connect();
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MainWindow.showErrorMessage(e.getMessage());
				}

				out.write((sudo_pass + "\n").getBytes());
				out.flush();
				out.close();

				StringWriter writer = new StringWriter();
				IOUtils.copy(in, writer);
				String theString = writer.toString();
				result = theString;
				System.out.println(result);
				MainWindow.setMessage("Command was sent: " + commands[i], serverType);
				MainWindow.setMessage(result, serverType);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MainWindow.showErrorMessage(e.getMessage());
			}
		}
	}

	public void CloseConnection() {
		channel.disconnect();
		session.disconnect();
		System.out.println("Finish");
		MainWindow.setMessage("Session for execute command via ssh was closed with host " + SFTPHOST, serverType);
	}

}
