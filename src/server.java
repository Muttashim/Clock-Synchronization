import java.io.*;
import java.net.*;
import java.util.Date;

public class server{
	public static void main(String []args) throws IOException{
		ServerSocket s = new ServerSocket(50555);
		int server_timeout = 30000;
		s.setSoTimeout(server_timeout);
		displayBanner();
		System.out.println("|\n|-Server is running...");

		try{
			while(true){
				Socket  s1 = s.accept();
				System.out.println("|\n|-Connection Received : " + s1);
				DataOutputStream dos = new DataOutputStream(s1.getOutputStream());

				Date dt = new Date();
				System.out.println("    |\n    |-Sent to client :  " + dt);
				dos.writeLong(dt.getTime());

				System.out.println("    |\n    |-Connection Terminated : " + s1);
				s1.close();
				dos.close();

			}
		}catch(SocketTimeoutException e){
			System.out.println("|\nTerminating server due to timeout...");
			System.out.println("Server is terminated!");
			s.close();
		}
	}

	public static void displayBanner(){
		String bannerHead = " _____ _            _    \n/  __ \\ |          | |   \n| /  \\/ | ___   ___| | __\n| |   | |/ _ \\ / __| |/ /\n| \\__/\\ | (_) | (__|   < \n \\____/_|\\___/ \\___|_|\\_\\\n                         ";
		String bannerTail = " _____                  _                     _          _   _             \n/  ___|                | |                   (_)        | | (_)            \n\\ `--. _   _ _ __   ___| |__  _ __ ___  _ __  _ ______ _| |_ _  ___  _ __  \n `--. \\ | | | '_ \\ / __| '_ \\| '__/ _ \\| '_ \\| |_  / _` | __| |/ _ \\| '_ \\ \n/\\__/ / |_| | | | | (__| | | | | | (_) | | | | |/ / (_| | |_| | (_) | | | |\n\\____/ \\__, |_| |_|\\___|_| |_|_|  \\___/|_| |_|_/___\\__,_|\\__|_|\\___/|_| |_|\n        __/ |                                                              \n       |___/                                                               \n";
		System.out.println(bannerHead);
		System.out.println(bannerTail);
	    System.out.println("[+]--Clock Synchronization Server v1.0.1--- initializing...");
	}
}