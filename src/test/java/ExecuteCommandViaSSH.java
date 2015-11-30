import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dolebedev on 26.11.2015.
 */
public class ExecuteCommandViaSSH {
    ExecuteCommandViaSSH() {
        this.SFTPHOST = "10.35.204.50";
        this.SFTPPORT = 22;
        this.SFTPUSER = "centos";
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        this.privateKey = "E:\\denys_key.pem";
        this.SFTPWORKINGDIR = "/home/centos/";
    }

    private String SFTPHOST;
    private int SFTPPORT;
    private String SFTPUSER;
    private String privateKey;
    private String SFTPWORKINGDIR;

    public StringBuilder  SendCommand(String command) {
        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        
        StringBuilder  result= new BufferedReader(new InputStreamReader("df".t));
        try {
            jSch.addIdentity(privateKey);
            System.out.println("Private Key Added.");
            session = jSch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            System.out.println("session created.");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();


            channel = session.openChannel("exec");

            channel.setInputStream(null);
            ((ChannelExec)channel).setCommand(command);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();

            channel.connect();



           byte[] tmp=new byte[10024];
            while(true){
                while(in.available()>0){
                    /*int i=in.read(tmp, 0, 10024);
                    if(i<0)break;
                    result= new Str(tmp, 0, i);*/
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }

                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);
                }catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);


        }
        return result;
    }
}
