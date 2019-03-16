package socket4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * 
 * @author Jeffrey Wei
 *
 * Multiplayer char room
 * 
 * This program supports people with access to chat rooms, 
 */

public class Client {

	 private Socket socket;
	 
	 public Client() {
		 
		 System.out.println("Connecting with server....");
		 try {
			socket = new Socket("localhost", 12000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 System.out.println("Connected!");
	 }
	
	 public void start() {
		 
		 ServerHandler sh = new ServerHandler();
		 Thread t = new Thread(sh);
		 t.start();
	 
		 
		 try {
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");
			BufferedWriter bw = new BufferedWriter(osw);
			PrintWriter pw = new PrintWriter(bw,true);
			
			
			Scanner kb = new Scanner(System.in);
			System.out.println("Please enter message");
			while(true) {
				
				String str = kb.nextLine();
				pw.println(str);
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	 }
	
	
	
	public static void main(String[] args) {
		
		Client client = new Client();
		client.start();
	}
	
	
	
	private class ServerHandler implements Runnable{
		
		
		
		public void run() {
			
			try {
				
				InputStream input = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(input);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while((line = br.readLine())!= null) {
					
					System.out.println(line);
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}
	}

}
