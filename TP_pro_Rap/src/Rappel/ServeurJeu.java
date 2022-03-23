package Rappel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServeurJeu extends Thread {
private int nombreClient;
private int nombreSecret;
private boolean fin;
private String gagnant;
	public static void main(String[] args)  {
	
		new ServeurJeu().start();
	
	
	}
	

	public void run() {
	try {
		ServerSocket ss= new ServerSocket(1234);
		nombreSecret=new Random().nextInt(1000);
		System.out.println("Démarage du serveur");

		System.out.println("Le serveur a choisi son secret :"+nombreSecret);
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
				pw.println("devinez le nombre secret");
				while(true) {
					
					String req=br.readLine();
					int nombre = 0;
					boolean correctFormatRequest=false;
                     try {
     					 nombre=Integer.parseInt(req);
     					correctFormatRequest=true;
                     }catch(NumberFormatException e) {
                    	 correctFormatRequest=false;
                     }
                  if(correctFormatRequest) {
                		System.out.println(" client :"+IP+ "Tentative avec le nombre :"+nombre);

    					if(fin==false) {
    						if(nombre >nombreSecret) {
    							pw.println("votre nombre est supérieur au nombre secret");

    							
    						}
    						else if(nombre<nombreSecret) {
    							pw.println("votre nombre est inférieur au nombre secret");

    						}
    						else {
    							pw.println("Bravo , vous avez gagné");
    							gagnant=IP;
    							System.out.println("BRAVO au Gagnant, IP client :"+gagnant);
    							fin=true;
    						}
    					}else {
    						
    						pw.println("Je términé Le gagnant est :"+gagnant);

    					}
                  }else {
                	 pw.println("Format de nombre incorecte"); 
                  }
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
















