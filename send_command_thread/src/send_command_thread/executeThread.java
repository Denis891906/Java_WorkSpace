package send_command_thread;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
public class executeThread {
	public executeThread() {
		this.SFTPHOST = "10.35.204.56";
        this.SFTPPORT = 22;
        this.SFTPUSER = "centos";
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        this.privateKey = "D:\\denys_key.pem";
        this.SFTPWORKINGDIR = "/home/centos/";
	}
	private String SFTPHOST;
    private int SFTPPORT;
    private String SFTPUSER;
    private String privateKey;
    private String SFTPWORKINGDIR;
    private Channel channel = null;
    private Session session = null;
    
    public void CreateConnection() {
    	JSch jSch = new JSch();
        
       
        ChannelSftp channelSftp = null;
        
        
        try {
        jSch.addIdentity(privateKey);
        System.out.println("Private Key Added.");
        session = jSch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
        System.out.println("session created.");

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();


        
        }catch(Exception e){
            System.out.println(e);
        }
    }
        
     public void StartCommand(String command){
    	 try {
			channel = session.openChannel("exec");
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

         channel.setInputStream(null);
    	 String result=" ";
    	 ((ChannelExec)channel).setCommand("sudo -S -p '' "+ command);
         ((ChannelExec)channel).setErrStream(System.err);
         InputStream in;
		try {
			in = channel.getInputStream();
		
         OutputStream out=channel.getOutputStream();
         ((ChannelExec)channel).setPty(true);
         try {
			channel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         String sudo_pass="Alstom$123";
         out.write((sudo_pass+"\n").getBytes());
         out.flush();
         out.close();

         StringWriter writer = new StringWriter();
         IOUtils.copy(in, writer);
         String theString = writer.toString();
         result=theString;
         System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		
		
     } 
     public void CloseConnection(){
    	 channel.disconnect();
         session.disconnect();
         System.out.println("Finish");
		}

}


