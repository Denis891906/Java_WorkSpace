package com.openstack;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainWindow extends JFrame {
	public JLabel buildPathLable;
	public JLabel keyPathLable;
	public JLabel buildPDCPathLable1;
	public JLabel buildPDCPathLable2;
	public JLabel buildAppServerPathLable1;
	public JLabel buildAppServerPathLable2;
	public JLabel buildHistoranPathLable1;
	public JLabel buildHistoranPathLable2;
	private JTextField internalPdcIPText;
	private JTextField externalPdcIPText;
	private static JTextArea pdcTextArea;
	private static JTextArea appServerTextArea;
	private static JTextArea histTextArea;
	private JTextField internalAppIPText;
	private JTextField externalAppIPText;
	private JTextField internalHistIPText;
	private JTextField externalHistIPText;
	private PrintStream standardOut;
	private JTextField usernameText;
	private JTextField sudoPasswordText;
	public static String pdcInstallLog = "";
	public static String appServerInstallLog = "";
	public static String historianInstallLog = "";
	private JButton pdcInstallLogButton;
	private JButton appServerInstallLogButton;
	private JButton histInstallLogButton;
	private JCheckBox pdcCheckBox;
	private JCheckBox appCheckBox;
	private JCheckBox histCheckBox;
	
	public boolean getPDCCheckBoxValue(){
		return pdcCheckBox.isSelected();
	}
	public boolean getAppCheckBoxValue(){
		return appCheckBox.isSelected();
	}
	public boolean getHistCheckBoxValue(){
		return histCheckBox.isSelected();
	}

	public void enableLogsButtons() {
		pdcInstallLogButton.setEnabled(true);
		appServerInstallLogButton.setEnabled(true);
		histInstallLogButton.setEnabled(true);
	}

	public static void setMessage(String text, String serverType) {
		if (serverType == "pdc") {
			pdcInstallLog = pdcInstallLog + "\n" + text;
			// pdcTextArea.setText(pdcTextArea.getText()+"\n"+text);
		} else if (serverType == "app") {
			// appServerTextArea.setText(appServerTextArea.getText()+"\n"+text);
			appServerInstallLog = appServerInstallLog + "\n" + text;
		} else if (serverType == "hist") {
			historianInstallLog = historianInstallLog + "\n" + text;
			// histTextArea.setText(histTextArea.getText()+"\n"+text);
		}
	}

	public static void setProgressMessage(String text, String serverType) {
		if (serverType == "pdc") {
			pdcInstallLog = pdcInstallLog + "\n" + text;
			// pdcTextArea.setText(pdcTextArea.getText()+text);
		} else if (serverType == "app") {
			appServerInstallLog = appServerInstallLog + "\n" + text;
			// appServerTextArea.setText(appServerTextArea.getText()+text);
		} else if (serverType == "hist") {
			historianInstallLog = historianInstallLog + "\n" + text;
			// histTextArea.setText(histTextArea.getText()+text);
		}
	}

	public static void showErrorMessage(String text) {
		JOptionPane.showMessageDialog(new JFrame(), text, "Execption dialog", JOptionPane.ERROR_MESSAGE);
	}

	public static void showProgressDialog() {
		JOptionPane.showMessageDialog(new JFrame(), "mafsdnklsnfk", "Execption dialog",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public static void closeProgeressDialog() {
		JOptionPane.getRootFrame().dispose();
	}

	public static void showInfoMessage(String text) {
		JOptionPane.showMessageDialog(new JFrame(), text, "Execption dialog", JOptionPane.INFORMATION_MESSAGE);
	}

	public MainWindow() {

		setTitle("Tool for deploy URTDSM build to OpenStack (r0.1)");

		this.setSize(500, 600);

		//////////////////////////////////////////////////////////////////////////////
		// Section for OpenStack Vm install
		//////////////////////////////////////////////////////

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		// this.setSize(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ);
		
		JLabel pdcIPLable = new JLabel("Internal PDC IP");
		pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel pdcIPLable2 = new JLabel("External PDC IP");
		pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel appIPLable = new JLabel("Internal AppServer IP");
		appIPLable.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel appIPLable2 = new JLabel("External AppServer IP");
		appIPLable.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel histIPLable = new JLabel("Internal Historian IP");
		histIPLable.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel histIPLable2 = new JLabel("External Historian IP");
		histIPLable.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel usernameLable = new JLabel("username");
		pdcIPLable.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel rootPasswordIPLable = new JLabel("root password");
		rootPasswordIPLable.setHorizontalAlignment(SwingConstants.LEFT);
		
		pdcCheckBox = new JCheckBox("PDC",true);
		appCheckBox = new JCheckBox("AppServer",true);
		histCheckBox = new JCheckBox("Historian",true);
		
		
		pdcCheckBox.setEnabled(false); ;
		appCheckBox.setEnabled(false);
		histCheckBox.setEnabled(false);
		

		buildPathLable = new JLabel();
		keyPathLable = new JLabel();
		buildPDCPathLable1 = new JLabel();
		buildPDCPathLable2 = new JLabel();
		buildAppServerPathLable1 = new JLabel();
		buildAppServerPathLable2 = new JLabel();
		buildHistoranPathLable1 = new JLabel();
		buildHistoranPathLable2 = new JLabel();

		internalPdcIPText = new JTextField(16);
		internalPdcIPText.setText("192.168.160.154");
		externalPdcIPText = new JTextField(16);
		externalPdcIPText.setText("10.35.204.56");

		internalAppIPText = new JTextField(16);
		internalAppIPText.setText("192.168.160.146");
		externalAppIPText = new JTextField(16);
		externalAppIPText.setText("10.35.204.14");

		internalHistIPText = new JTextField(16);
		internalHistIPText.setText("192.168.160.150");
		externalHistIPText = new JTextField(16);
		externalHistIPText.setText("10.35.204.119");
		usernameText = new JTextField(16);
		usernameText.setText("centos");
		sudoPasswordText = new JTextField(16);
		sudoPasswordText.setText("Alstom$123");

		JButton pdcInstallButton = new JButton("Install selected services");
		JButton appInstallButton = new JButton("Reinstall selected services");
		JButton selectBuildFolderButton = new JButton("Select Build Folder");
		JButton selectKeyFileButton = new JButton("Select Key File");
		pdcInstallLogButton = new JButton("PDC Install log");
		appServerInstallLogButton = new JButton("AppServer Install log");
		histInstallLogButton = new JButton("Historian Install log");

		pdcInstallLogButton.setEnabled(false);
		appServerInstallLogButton.setEnabled(false);
		histInstallLogButton.setEnabled(false);

		pdcTextArea = new JTextArea(23, 63);
		pdcTextArea.setEditable(false);

		appServerTextArea = new JTextArea(23, 63);
		appServerTextArea.setEditable(false);

		histTextArea = new JTextArea(23, 63);
		histTextArea.setEditable(false);

		// Create main Tab panel

		JTabbedPane tabPanel = new JTabbedPane();

		// Add three tabs to the main tab panel
		tabPanel.add("PDC logs", pdcTextArea);
		tabPanel.add("AppServer logs", appServerTextArea);
		tabPanel.add("Historian logs", histTextArea);

		// add Main tab panel to on scroll pane
		JScrollPane mainScrollPane = new JScrollPane(tabPanel);

		//

		// PrintStream printStream = new PrintStream(new
		// CustomOutputStream(textArea));

		// keeps reference of standard output stream
		// standardOut = System.out;

		// re-assigns standard output stream and error output stream

		// Add listener for button selectBuildFolder

		GUIEngine guiEngine = new GUIEngine(this);

		selectBuildFolderButton.addActionListener(guiEngine);
		pdcInstallButton.addActionListener(guiEngine);
		appInstallButton.addActionListener(guiEngine);
		selectKeyFileButton.addActionListener(guiEngine);
		pdcInstallLogButton.addActionListener(guiEngine);
		appServerInstallLogButton.addActionListener(guiEngine);
		histInstallLogButton.addActionListener(guiEngine);

		// this.addComponentListener(new resizeWindow(this));

		this.setResizable(false);

		// Line 1 with PDC elements
		c.gridy = 0; // line
		c.gridx = 0; // column
		c.gridwidth = 1;// ������� ������ �������� ������� � ������
		c.gridheight = 1;// ������� ������ �������� ������� � ������
		c.weightx = 0;// ��� ����� ����������� �������� ���������� �� �
		c.weighty = 0;// ��� ����� ����������� �������� ���������� �� Y
		c.anchor = GridBagConstraints.BASELINE_LEADING; // ��� ������� �����
														// �������������
		// c.fill=GridBagConstraints.HORIZONTAL; //��� ����� �������������
		// �������
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.ipadx = 1; // �� ������� �������������� ����������� ������ ��������
		c.ipady = 1; // �� ������� �������������� ����������� ������ ��������

		this.add(pdcIPLable, c);
		
		

		c.gridx = 1;
		c.gridwidth = 3;
		c.weightx = 0;
		this.add(internalPdcIPText, c);
		
		//CheckBoxes Line 1
		c.gridy = 0;
		c.gridx=2;
		c.insets = new Insets(1, 200, 1, 1);
		this.add(pdcCheckBox,c);
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 250, 1, 1);
		c.gridx=2;
		this.add(appCheckBox,c);
		c.insets = new Insets(1, 350, 1, 1);
		c.gridx=2;
		this.add(histCheckBox,c);
		
		c.gridy=1;
		c.gridwidth = 1;
		c.gridx = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 200, 0, 0); // ������� ����������� ( top,left,
												// bottom, right )
		this.add(pdcInstallButton, c);

		// Line 2 with external PDC elements

		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		this.add(pdcIPLable2, c);
		c.gridx = 1;
		this.add(externalPdcIPText, c);
		
		
		
		

		// Line 3 with AppServer elements
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		this.add(appIPLable, c);
		c.gridx = 1;
		this.add(internalAppIPText, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 200, 0, 0); // ������� ����������� ( top,left,
		c.gridy = 2;										// bottom, right )
		c.gridx = 2;
		this.add(appInstallButton, c);

		// Line 4 with AppServer elements
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridy = 3;
		c.gridx = 0;
		this.add(appIPLable2, c);
		c.gridx = 1;
		this.add(externalAppIPText, c);
		
		
		
		// Line 5 with Historian elements
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridy = 4;
		c.gridx = 0;
		this.add(histIPLable, c);
		c.gridx = 1;
		this.add(internalHistIPText, c);
		c.gridx = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 200, 0, 0); // ������� ����������� ( top,left,
												// bottom, right )
		// this.add(histInstallButton, c);

		// Line 6 with Historian elements

		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridy = 5;
		c.gridx = 0;
		this.add(histIPLable2, c);
		c.gridx = 1;
		this.add(externalHistIPText, c);

		// Builds path elements
		c.gridx = 0;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		this.add(selectBuildFolderButton, c);

		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.gridwidth = 5;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		;

		this.add(buildPathLable, c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		this.add(buildPDCPathLable1, c);

		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 5;
		this.add(buildPDCPathLable2, c);

		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		this.add(buildAppServerPathLable1, c);

		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 5;
		this.add(buildAppServerPathLable2, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		this.add(buildHistoranPathLable1, c);

		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 5;
		this.add(buildHistoranPathLable2, c);

		// key path elements
		c.gridx = 0;
		c.gridy = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		this.add(selectKeyFileButton, c);

		c.gridx = 1;
		c.gridwidth = 7;
		this.add(keyPathLable, c);

		// Line with user name text box

		c.gridx = 0;
		c.gridy = 11;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		this.add(usernameLable, c);

		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridy = 11;
		c.gridx = 0;
		c.gridx = 1;
		this.add(usernameText, c);

		// Sudo password Line
		c.gridx = 0;
		c.gridy = 12;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		this.add(rootPasswordIPLable, c);

		c.insets = new Insets(0, 0, 0, 0); // ������� ����������� ( top,left,
											// bottom, right )
		c.fill = GridBagConstraints.NONE;
		c.gridy = 12;
		c.gridx = 0;
		c.gridx = 1;
		this.add(sudoPasswordText, c);

		c.gridy = 13;
		c.gridx = 0;
		this.add(pdcInstallLogButton, c);
		c.gridx = 1;
		this.add(appServerInstallLogButton, c);
		c.gridx = 2;
		this.add(histInstallLogButton, c);

		/*
		 * // Add main Scroll pane to the main window c.gridx=0; c.gridy=13;
		 * c.fill=GridBagConstraints.BOTH; c.gridwidth=23;
		 * c.gridheight=63;//������� ������ �������� ������� � ������
		 * 
		 * this.add(mainScrollPane, c);
		 */

		this.pack(); // delete empty space from window
	}

	/////////////////////////////////////////////////

	public void SetBuildPath(String path, String pdcPath, String appPath, String histPath) {
		buildPathLable.setText(path);
		buildPDCPathLable1.setText("PDC rpm file path:");
		buildPDCPathLable2.setText(pdcPath);
		buildAppServerPathLable1.setText("AppServer rpm file path:");
		buildAppServerPathLable2.setText(appPath);
		buildHistoranPathLable1.setText("Historian rpm file path:");
		buildHistoranPathLable2.setText(histPath);
	}

	public String GetBuildPath() {
		System.out.println("GetBuildPath method returns path: " + buildPathLable.getText());
		return buildPathLable.getText();
	}

	public void SetKeyPath(String path) {
		keyPathLable.setText(path);
	}

	public void ShowWarningDialog(String text) {
		JOptionPane.showMessageDialog(new JFrame(), text, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	public String GetExternalPDCIP() {

		return externalPdcIPText.getText();

	}

	public String GetInternalPDCIP() {
		return internalPdcIPText.getText();
	}

	public String GetExternalAppServerIP() {
		return externalAppIPText.getText();
	}

	public String GetInternalAppServerIP() {
		return internalAppIPText.getText();
	}

	public String GetExternalHistorianIP() {
		return externalHistIPText.getText();
	}

	public String GetInternalHistorianIP() {
		return internalHistIPText.getText();
	}

	public String getUserName() {
		return usernameText.getText();
	}

	public String getSudoPassword() {
		return sudoPasswordText.getText();

	}

	public void clearTextAreas() {
		pdcTextArea.setText("");
		appServerTextArea.setText("");
		histTextArea.setText("");
	}

	/*public static void main(String[] args) {
		MainWindow flt = new MainWindow();
		flt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		flt.setVisible(true);
	}*/

}
