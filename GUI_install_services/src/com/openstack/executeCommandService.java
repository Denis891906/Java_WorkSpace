package com.openstack;

/*Current class allow to execute commands via ssh in the separete thread */
public class executeCommandService implements Runnable {
	private String externalPpcIP;
	private String userName;
	private String keyPath;
	String[] commandList;
	private String sudoPassword;
	private String serverType;

	public executeCommandService(String externalPpcIP, String userName, String sudoPassword, String keyPath,
			String serverType, String[] commandList) {
		this.externalPpcIP = externalPpcIP;
		this.userName = userName;
		this.keyPath = keyPath;
		this.commandList = commandList;
		this.sudoPassword = sudoPassword;
		this.serverType = serverType;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ExecuteCommandViaSSH executeCommandOnVM = new ExecuteCommandViaSSH(externalPpcIP, userName, keyPath,
				sudoPassword, serverType);
		try {
			executeCommandOnVM.CreateConnection();
		
		for (int i = 0; i < this.commandList.length; i++) {
			executeCommandOnVM.StartCommand(this.commandList[i]);

		}

		// System.out.println("Before Close");
		executeCommandOnVM.CloseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}

}
