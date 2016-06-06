package com.gui;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by dolebedev on 24.11.2015.
 */
public class SendFileViaSFTP {
    public SendFileViaSFTP() {
    this.SFTPHOST = "10.35.204.50";
    this.SFTPPORT = 22;
    this.SFTPUSER = "centos";
    // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
    this.privateKey = "E:\\Backup_from_laptop_Edinburht\\Keys\\URTDSM_CentOS_6\\dl_urtdsm_centos_6.pem";
    this.SFTPWORKINGDIR = "/home/centos/";
}

    public SendFileViaSFTP(String remoteHost, String remoteUser, String privateKeyPath) {
            this.SFTPHOST = remoteHost;
            this.SFTPPORT = 22;
            this.SFTPUSER =remoteUser;
            // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
            this.privateKey = privateKeyPath;
            this.SFTPWORKINGDIR = "/home/"+remoteUser+"/";
            //File myFile = new File("E:\\denys_key.pem");
            //this.fileStream = null;

    }
    private String SFTPHOST;
    private int    SFTPPORT;
    private  String SFTPUSER;
    private String privateKey;
    private String SFTPWORKINGDIR;



        /*Below we have declared and defined the SFTP HOST, PORT, USER
                   and Local private key from where you will make connection */

    public void SendFile(String filePath){
        
    	JSch jSch = new JSch();
        Session session     = null;
        Channel channel     = null;
        ChannelSftp channelSftp = null;
        try {
            jSch.addIdentity(privateKey);
            System.out.println("Private Key Added.");
            session = jSch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            System.out.println("session created.");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            System.out.println("shell channel connected....");
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);

            System.out.println("Changed the directory to "+channelSftp.pwd());
            File f = new File(filePath);
            
            SftpProgressMonitor monitor=new MyProgressMonitor();
          
            		
           // channelSftp.put(new FileInputStream(f), f.getName(),monitor);
            channelSftp.put(filePath, channelSftp.pwd(),monitor);

            //Return result of command ls
            /*Vector lsResult =channelSftp.ls(SFTPWORKINGDIR);
            for (int i=0; i<lsResult.size(); i++){
                System.out.println(lsResult.get(i));
            }*/


        } catch(JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/ finally{
            if(channelSftp!=null){
                channelSftp.disconnect();
                channelSftp.exit();
            }
            if(channel!=null) channel.disconnect();
            if(session!=null) session.disconnect();
        }
    }
}



