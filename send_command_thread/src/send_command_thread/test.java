package send_command_thread;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*SendFileViaSFTP remote=new SendFileViaSFTP();
        remote.SendFile("E:\\phasorpoint-pdc-7.0-1.x86_64_b951_r29359.rpm");*/

       // SendFileViaSFTP one = new SendFileViaSFTP();

       // one.SendFile("E:\\trunkPDCReinstallBuild.sh");
	executeThread run=new executeThread();
	run.CreateConnection();
	System.out.println("I executed the 1 command:");
	run.StartCommand("pwd");
	System.out.println("I executed the 2 command:");
	run.StartCommand("ls -la");
	/*System.out.println("I executed the 3 command:");
	run.StartCommand("ls /etc/phasorpoint-pdc/");
	System.out.println("I executed the 4 command:");
	run.StartCommand("cat /etc/phasorpoint-pdc/security.properties");
	System.out.println("I executed the 5 command:");
	run.StartCommand("service phasorpoint-pdc restart");
	
	System.out.println("I executed the 6 command:");
	run.StartCommand("service phasorpoint-pdc restart");
	
	System.out.println("I executed the 6 command:");
	run.StartCommand("grep -e 'authentication group'  /etc/phasorpoint-pdc/security.properties");
	*/
	
	run.CloseConnection();	
		
        /*ExecuteCommandViaSSH tt=new ExecuteCommandViaSSH();

        String ressa=tt.SendCommand("ls","pwd");
        System.out.println(ressa);
        
        System.out.println("------");*/
       /* ExecuteCommandViaSSH tt1=new ExecuteCommandViaSSH();
        String result=tt1.SendCommand("pwd","top");
        System.out.println(result);*/
        /*if (result.contains("")){
            System.out.println(result);
            System.out.println(tt.SendCommand("cd GO_C37 && ls && cat 100stations_03jun15-NMS_a.ini"));
        };*/
	}

}
