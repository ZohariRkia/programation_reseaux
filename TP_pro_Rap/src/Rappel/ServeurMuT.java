package Rappel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMuT extends Thread {
private int nombreClient;

	public static void main(String[] args)  {
	
		new ServeurMuT().start();
	
	
	}
	

	public void run() {
	try {
		ServerSocket ss= new ServerSocket(1234);
		System.out.println("Démarage du serveur");
		while(true) {
			Socket socket=ss.accept();
			++nombreClient;
			new Conversation(socket,nombreClient).start();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	public class Conversation extends Thread{
		Socket socket;
		int nombreClient;
		
		public Conversation(Socket s,int num) {
			
			this.socket = s;
			this.nombreClient=num;
			
		}

		public void run() {
			//Socket socket = null;
			try {
				InputStream is = socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(is);
				BufferedReader br= new BufferedReader(isr);
				
				OutputStream os =socket.getOutputStream();
				PrintWriter pw=new 	PrintWriter(os,true);
				String IP=socket.getRemoteSocketAddress().toString();
				System.out.println("conexion de client numero :"+nombreClient+ "IP="+IP);
				pw.println("Binvenue vous etes le client numero : "+nombreClient);
				while(true) {
					
					String req=br.readLine();
					System.out.println("Le client IP="+IP+ " a envoyer une requet"+req);

					pw.println(req.length());
					
							}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
















