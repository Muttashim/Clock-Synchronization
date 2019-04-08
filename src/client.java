import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class client{
	public static void main(String []args) throws IOException{
		displayBanner();

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

		System.out.println("|\n|-Time returned by server : " + time_pattern.format(server_time));
		long process_delay_latency = res_time - req_time;
		System.out.println("|\n|-Process Delay Latency : " + (double)process_delay_latency/1000.0 + " seconds");
		System.out.println("|\n|-Actual clock time at client side :" + time_pattern.format(actual_time));

		long client_time = s_time + process_delay_latency/2;
		dt.setTime(client_time);
		System.out.println("|\n|-Synchronzed process client time : " + time_pattern.format(dt));

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String sys_time = "" + df.format(dt);

		long error = res_time - client_time;
		System.out.println("|\n|-Synchronization error : " + (double)error/1000.0 + " seconds");

		System.out.println("Changing system time...");
		try{
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
		changeSystemTime(sys_time);

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

	public static void displayBanner(){
		String bannerHead = " _____ _            _    \n/  __ \\ |          | |   \n| /  \\/ | ___   ___| | __\n| |   | |/ _ \\ / __| |/ /\n| \\__/\\ | (_) | (__|   < \n \\____/_|\\___/ \\___|_|\\_\\\n                         ";
		String bannerTail = " _____                  _                     _          _   _             \n/  ___|                | |                   (_)        | | (_)            \n\\ `--. _   _ _ __   ___| |__  _ __ ___  _ __  _ ______ _| |_ _  ___  _ __  \n `--. \\ | | | '_ \\ / __| '_ \\| '__/ _ \\| '_ \\| |_  / _` | __| |/ _ \\| '_ \\ \n/\\__/ / |_| | | | | (__| | | | | | (_) | | | | |/ / (_| | |_| | (_) | | | |\n\\____/ \\__, |_| |_|\\___|_| |_|_|  \\___/|_| |_|_/___\\__,_|\\__|_|\\___/|_| |_|\n        __/ |                                                              \n       |___/                                                               \n";
		System.out.println(bannerHead);
		System.out.println(bannerTail);
	    System.out.println("[+]--Clock Synchronization Client v1.0.1---[+]");
	}
}