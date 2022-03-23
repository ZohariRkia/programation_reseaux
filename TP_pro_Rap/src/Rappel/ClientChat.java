package Rappel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application  {
	PrintWriter pw;
	public static void main(String[] args)  {
		
	launch(args);
	
	}
	
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client Chat");
		BorderPane borderPane=new BorderPane();
		Label labelHost=new Label("Host");
		
		TextField textFieldHost=new TextField("localhost");
		
           Label labelPort=new Label("Port");
		
		TextField textFieldPort=new TextField("1234");
		Button buttonConnecter=new Button("Connceter");
		
		HBox box =new HBox();
		box.setSpacing(10);
		box.setPadding(new Insets(10));
		box.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
		box.getChildren().addAll(labelHost,textFieldHost,labelPort,textFieldPort,buttonConnecter);
		
		borderPane.setTop(box);
		VBox vBox2= new VBox();
		vBox2.setSpacing(10);
		vBox2.setPadding(new Insets(10));
		ObservableList<String>listModel=FXCollections.observableArrayList();
		ListView<String>listView=new ListView<String>(listModel);
		vBox2.getChildren().add(listView);
		borderPane.setCenter(vBox2);
		
		
		Label labelMessage=new Label("Message:");
		TextField textFieldMessage=new TextField();
		textFieldMessage.setPrefSize(400, 30);
		Button buttonEnvoyer =new Button("Envoyer :");
		
		HBox box2 =new HBox();
		box2.setSpacing(10);
		box2.setPadding(new Insets(10));
		
		box2.getChildren().addAll(labelMessage,textFieldMessage,buttonEnvoyer);
		
		borderPane.setBottom(box2);
		
		
		Scene scene=new Scene(borderPane,800,600);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		buttonConnecter.setOnAction((evt)->{
			String host=textFieldHost.getText();
			int port=Integer.parseInt(textFieldPort.getText());
			try {
				Socket socket=new Socket(host,port);
				InputStream inputStream=socket.getInputStream();
				InputStreamReader isr=new InputStreamReader(inputStream);
				BufferedReader  bufferedReader=new BufferedReader(isr);
				pw=new PrintWriter(socket.getOutputStream(),true);
				
				new Thread(()->{
					while(true) {
					
							
								try {
								String reponse=bufferedReader.readLine();
								Platform.runLater(()->{
								listModel.add(reponse);
								});
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
						
							
							
						
						
					}
					
				}).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		buttonEnvoyer.setOnAction((evt)->{
			
			String meessage=textFieldMessage.getText();
			pw.println(meessage);
			
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
