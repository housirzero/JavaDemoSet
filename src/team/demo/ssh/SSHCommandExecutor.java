package team.demo.ssh;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * This class provide interface to execute command on remote Linux.
 */

public class SSHCommandExecutor {
    private String ipAddress;

    private String username;

    private String password;

    public static final int DEFAULT_SSH_PORT = 22;

    private Vector<String> stdout;

    public SSHCommandExecutor(final String ipAddress, final String username, final String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        stdout = new Vector<String>();
    }

    public int execute(final String command) {
        int returnCode = 0;
        JSch jsch = new JSch();
        MyUserInfo userInfo = new MyUserInfo();

        try {
            // Create and connect session.
            Session session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
            session.setPassword(password);
            session.setUserInfo(userInfo);
            session.connect();

            // Create and connect channel.
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader(channel
                    .getInputStream()));

            channel.connect();
            System.out.println("The remote command is: " + command);

            // Get the output of remote command.
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
            input.close();

            // Get the return code only after the channel is closed.
            if (channel.isClosed()) {
                returnCode = channel.getExitStatus();
            }

            // Disconnect the channel and session.
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCode;
    }

    public Vector<String> getStandardOutput() {
        return stdout;
    }

    public static void main(final String [] args) {
        SSHCommandExecutor sshExecutor = new SSHCommandExecutor("10.2.9.42", "root", "123456");
        String classNameString = "csv.CsvReader";
        String canshu = " /wjy/BusStation_AfterTrans.csv /wjy/1.csv";
        String cmd = "/home/spark-1.2.0-bin-hadoop2.4/bin/spark-submit" +
        		" --class " + classNameString +
        		" --master yarn-cluster" +
        		" --num-executors 2" +
        		" --executor-memory 6g" +
        		" --executor-cores 4" +
//        		" --jars maptools.jar" +
        		" /home/wjy/csv_reader/target/simple-project-1.0.jar" +
        		canshu;
        
//        cmd = "ls -al";
        sshExecutor.execute(cmd);
        /**
         * spark-submit --class mode.StatCover --master yarn-cluster --num-executors 2 --executor-memory 6g --executor-cores 4 
         * --jars maptools.jar target/simple-project-1.0.jar /TripChain/20150104_TripChain3.csv
         */
        
        Vector<String> stdout = sshExecutor.getStandardOutput();
        for (String str : stdout) {
            System.out.println(str);
        }
    }
}
