package socket4;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
	private ServerSocket server;
	private PrintWriter[]arr = {};
	public Server(){

		try {
			System.out.println("Server Starting");
			server = new ServerSocket(12000);
			System.out.println("Server Started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void start() {


		try {
			while(true) {
				System.out.println("Waiting for client...");
				Socket socket = server.accept();
				System.out.println("One Client connected...");

				ClientHandler ch = new ClientHandler(socket);
				Thread t = new Thread(ch);
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public static void main(String[]args) {
		Server server = new Server();
		server.start();
	}


	private class ClientHandler implements Runnable{

		private Socket socket;
		private String host;
		ClientHandler(Socket s){

			this.socket = s;
			InetAddress address = socket.getInetAddress();
			this.host = address.getHostAddress();
			
			
		}

		public void run() {


			try {

				InputStream input = socket.getInputStream();
				InputStreamReader  isr = new InputStreamReader(input);
				BufferedReader br = new BufferedReader(isr);
				String line = null;



				OutputStream out = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(out);
				BufferedWriter bw = new BufferedWriter(osw);
				PrintWriter pw = new PrintWriter(bw,true);
				arr = Arrays.copyOf(arr, arr.length+1);
				arr[arr.length - 1] = pw; 

				while((line = br.readLine())!= null) {
					System.out.println(host +":"+line);

					for(int i = 0 ; i < arr.length; i++) {
						arr[i].println(host + ":" + line);

					}
				}



			} catch (IOException e) {
				e.printStackTrace();
			}



		}
	}











}
