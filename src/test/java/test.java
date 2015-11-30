/**
 * Created by dolebedev on 24.11.2015.
 */
public class test {
    public static void main(String[] args) {
/*        SendFileViaSFTP remote=new SendFileViaSFTP();
        SendFileViaSFTP one= new SendFileViaSFTP();
        remote.SendFile("E:\\Test_Automation_Plan.xlsm");*/
        ExecuteCommandViaSSH tt=new ExecuteCommandViaSSH();
        String result=tt.SendCommand("ls");
        if (result.contains("GO_C37")){
            System.out.println(result);
            System.out.println(tt.SendCommand("cd GO_C37 && ls && cat 100stations_03jun15-NMS_a.ini"));
        };
    }

}
