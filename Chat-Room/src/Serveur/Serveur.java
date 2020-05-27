package Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Serveur {

	ServerSocket serverSocket;
	boolean exit=false;
	ArrayList<HostConnexions> listThreads=new ArrayList<HostConnexions>();
	
	public static void main(String[] args) {
		new Serveur();

	}
	public Serveur() {
		try {
			//TODO use method to take this as an input from user)
			serverSocket=new ServerSocket(3333);//here we are using connection 3333 (change as you want
			while(!exit)
			{
				Socket s=serverSocket.accept();//when a connection to the domain is found we accept it
				HostConnexions OurConnection = new HostConnexions(s,this);
				OurConnection.start();//Start Thread
				listThreads.add(OurConnection);//add connection to our Domain Connection
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//make sure its bloody same with client it took my 15 min to realize that XD
	}
}