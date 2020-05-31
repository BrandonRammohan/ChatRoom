package Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServeurLaunch {

	ServerSocket serverSocket;
	boolean exit = false;
	ArrayList<HostConnexions> listThreads = new ArrayList<HostConnexions>();
	
	public static void main(String[] args) {
		new ServeurLaunch();
	}
	
	public ServeurLaunch() {
		try {
			serverSocket=new ServerSocket(3333);
			
			while(!exit)
			{
				Socket s=serverSocket.accept();
				HostConnexions OurConnection = new HostConnexions(s,this);
				OurConnection.start();
				listThreads.add(OurConnection);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}