import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * Created by dolebedev on 26.11.2015.
 */
public class ExecuteCommandViaSSH {
    ExecuteCommandViaSSH() {
        this.SFTPHOST = "10.35.204.56";
        this.SFTPPORT = 22;
        this.SFTPUSER = "centos";
        // this file can be id_rsa or id_dsa based on which algorithm is used to create the key
        this.privateKey = "E:\\Backup_from_laptop_Edinburht\\Keys\\URTDSM_CentOS_6\\dl_urtdsm_centos_6.pem";
        this.SFTPWORKINGDIR = "/home/centos/";
    }

    private String SFTPHOST;
    private int SFTPPORT;
    private String SFTPUSER;
    private String privateKey;
    private String SFTPWORKINGDIR;

    public String  SendCommand(String command) {
        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        
        String result=" ";
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

            ((ChannelExec)channel).setCommand("sudo -S -p '' "+ command);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            OutputStream out=channel.getOutputStream();
            ((ChannelExec)channel).setPty(true);
            channel.connect();
            String sudo_pass="Alstom$123";
            out.write((sudo_pass+"\n").getBytes());
            out.flush();
            out.close();

            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer);
            String theString = writer.toString();
            result=theString;


           byte[] tmp=new byte[10024];
            while(true){
            /*    while(in.available()>0){
                    int i=in.read(tmp, 0, 10024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));

                }
*/
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
