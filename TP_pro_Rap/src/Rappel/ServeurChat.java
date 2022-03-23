package Rappel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat extends Thread {
private int nombreClient;
private List<Conversation> clients=new ArrayList<Conversation>();
	public static void main(String[] args)  {
	
		new ServeurChat().start();
	
	}
	

	public void run() {
	try {
		ServerSocket ss= new ServerSocket(1234);
		System.out.println("Démarage du serveur");
		while(true) {
			Socket socket=ss.accept();
			++nombreClient;
			
			Conversation conversation =new Conversation(socket,nombreClient);
			clients.add(conversation);
			conversation.start();
			
			
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	public class Conversation extends Thread{
		protected Socket socket;
		protected int nombreClient;
		
		public Conversation(Socket s,int num) {
			
			this.socket = s;
			this.nombreClient=num;
			
		}
		
		public void broadcastMessage(String message,Socket socket_N, int numClient) {
			
				try {
					for (Conversation client :clients) {
						if(client.socket!=socket_N) {
							if(client.nombreClient==numClient || numClient==-1) {
								PrintWriter printWriter;
							    printWriter = new PrintWriter(client.socket.getOutputStream(),true);
							    printWriter.println(message);
							}
							
						}
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			}
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
					if(req.contains("=>")) {
						String[] RequestParams=req.split("=>");
						if(RequestParams.length==2);
						String message=RequestParams[1];
						int numeroClient=Integer.parseInt(RequestParams[0]);
						  broadcastMessage(req,socket,numeroClient);
					}else {
						  broadcastMessage(req,socket,-1);

					}
					
					
				  
					
							}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
















