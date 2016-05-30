/**
 * Created by dolebedev on 24.11.2015.
 */
public class test {
    public static void main(String[] args) {
        /*SendFileViaSFTP remote=new SendFileViaSFTP();
        remote.SendFile("E:\\phasorpoint-pdc-7.0-1.x86_64_b951_r29359.rpm");*/

        SendFileViaSFTP one = new SendFileViaSFTP();

        one.SendFile("E:\\trunkPDCReinstallBuild.sh");
        ExecuteCommandViaSSH tt=new ExecuteCommandViaSSH();

        String ressa=tt.SendCommand("chmod a+x trunkPDCReinstallBuild.sh");
        System.out.println(ressa);

        ExecuteCommandViaSSH tt1=new ExecuteCommandViaSSH();
        String result=tt1.SendCommand("/home/centos/trunkPDCReinstallBuild.sh");
        System.out.println(result);
        /*if (result.contains("")){
            System.out.println(result);
            System.out.println(tt.SendCommand("cd GO_C37 && ls && cat 100stations_03jun15-NMS_a.ini"));
        };*/
    }

}
