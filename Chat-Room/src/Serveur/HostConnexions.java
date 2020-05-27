package Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class HostConnexions extends Thread {
	
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	Serveur server;
	boolean exit=false;
	
	public HostConnexions(Socket OurSocket,Serveur OurServer)
	{
		super("HostConnexions");//server connection thread
		this.socket=OurSocket;
		this.server=OurServer;
	}
	
	public void ServerOutClientIn(String OutText)
	{
		try {
			long ThreadID=this.getId();
			dataOut.writeUTF(OutText);
			dataOut.flush();//this is because of a buffer error :<
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ServerOutAllClientIn(String OutText)
	{
		for(int i=0;i<server.listThreads.size();i++)
		{
			HostConnexions Connection=server.listThreads.get(i);
			Connection.ServerOutClientIn(OutText);
		}
	}
	
	public void run()
	{
		try {
			dataIn=new DataInputStream(socket.getInputStream());
			dataOut=new DataOutputStream(socket.getOutputStream());
			
			while(!exit)
			{
				while(dataIn.available()==0)
				{
					try {
						Thread.sleep(1);//sleep if there is not data coming
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String ComingText=dataIn.readUTF();
				ServerOutAllClientIn(ComingText);
			}
			dataIn.close();
			dataOut.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}