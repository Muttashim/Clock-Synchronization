import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class client{
	public static void main(String []args) throws IOException{
		Socket s = new Socket("localhost", 50555);

		DataInputStream dis = new DataInputStream(s.getInputStream());

		SimpleDateFormat time_pattern = new SimpleDateFormat("HH:mm:ss.SSSSSS");

		Date dt = new Date();
		long req_time = dt.getTime();
		
		long s_time = dis.readLong();
		Date server_time = new Date();
		server_time.setTime(s_time);
		Date actual_time = new Date();
		long res_time = actual_time.getTime(); 

		System.out.println("Time returned by server : " + time_pattern.format(server_time));
		long process_delay_latency = res_time - req_time;
		System.out.println("Process Delay Latency : " + (double)process_delay_latency/1000.0 + " seconds");
		System.out.println("Actual clock time at client side :" + time_pattern.format(actual_time));

		long client_time = s_time + process_delay_latency/2;
		dt.setTime(client_time);
		System.out.println("Synchronzed process client time : " + time_pattern.format(dt));

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String sys_time = "" + df.format(dt);
		changeSystemTime(sys_time);
		

		long error = res_time - client_time;
		System.out.println("Synchronization error : " + (double)error/1000.0 + " seconds");

		dis.close();
		s.close();
	}

	public static void changeSystemTime(String time)throws IOException{
		String os = detectOS();
		if (os.equals("linux")){
			Runtime.getRuntime().exec("date -s " + time);
			System.out.println("System time changed!");
		}
		else if (os.equals("win")){
			String cmd = "cmd /c time " + time;
			Runtime.getRuntime().exec(cmd);
		}
		else{
			System.out.println("Operating System Not Supported!");
		}
	}

	public static String detectOS()throws IOException{
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("win")>=0)
			return "win";
		else if (OS.indexOf("nix")>=0 || OS.indexOf("nux")>=0)
			return "linux";
		else
			return "notsupported";
	}
}