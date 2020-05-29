package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiThreads extends Thread {
	
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	boolean exit=false;
	public ClientData client;
	public ChatRoomGUI chatGUI;
	public Sauvegarde sauvegarde;
	
	public MultiThreads(Socket OurMultiSocket, ChatRoomGUI gui)
	{
		this.socket=OurMultiSocket;
		this.client=new ClientData();
		this.chatGUI=gui;
		sauvegarde = new Sauvegarde() ;
	}
	public void ClientOutServerIn(String Text)
	{
		//write the line from console to server
		try {
			System.out.println("ClientOutServerIn");
			if(Text.equals("change channel"))
			{
				System.out.print("sending changing channel: "+Text+"\n");
				dataOut.writeUTF(Text);
				dataOut.flush();
			}
			else if(Text.equals("new user"))
			{
				System.out.print("sending new user: "+ Text+"\n");
				dataOut.writeUTF(Text+":"+client.GetName()+"="+client.GetChannel());
				dataOut.flush();
				sauvegarde.writeUsersInFile(client.GetName());
			}
			else
			{
				dataOut.writeUTF(client.GetChannel()+"="+this.getName()+": "+Text);
				dataOut.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void SetClient(String channel,String Name)
	{
		System.out.println('4');
		client.SetName(Name);
		client.SetChannel(channel);
	}
	
	public void run()
	{
		try {
			System.out.println("run");
			getSavedMessageAndDisplay();
			getSavedUsersAndDisplay();
			dataIn=new DataInputStream(socket.getInputStream());
			dataOut=new DataOutputStream(socket.getOutputStream());
			while(!exit)
			{
				try {
					while(dataIn.available()==0)
					{
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String reply=dataIn.readUTF();
					String Chan=ExtractChannel(reply);
					String name=ExtractName(reply);
					if(name.equals("new user"))
					{
						System.out.print("new user in body: "+reply+"\n");
						//GUI.ClearDisplay();
						setChannel(reply);
					}
					else
					{
						PrintReply(Chan,reply);
						sauvegardeFichier(Chan, reply);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						dataIn.close();
						dataOut.close();
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				dataIn.close();
				dataOut.close();
				socket.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
			}
		}
	}

	public void CloseClient()
	{
		try {
			dataIn.close();
			dataOut.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ExtractName(String x)
	{
		String[]Y=x.split(":");
		return Y[0];
	}
	public String ExtractChannel(String X)
	{
		String[]Y=X.split("=");
		return Y[0];
	}
	
	public void sauvegardeFichier(String chan, String Rep) {
		String []Y=Rep.split("=");
		sauvegarde.writeMessagesInFile(Y[1]);
	}
	
	public void getSavedMessageAndDisplay() {
		String oldMessages = sauvegarde.readMessagesInFile();
		chatGUI.displaySavedMessaged(oldMessages);
	}
	
	private void getSavedUsersAndDisplay() {
		String oldUsers = sauvegarde.readUsersInFile();
		chatGUI.displaySavedUsers(oldUsers);
	}
	
	public void PrintReply(String Chan,String Rep)
	{
		if(client.GetChannel().equals(Chan))
		{
			System.out.println("Rep => "+ Rep);
			String []Y=Rep.split("=");
			System.out.println("Y[1] => "+ Y[1]);
			
			chatGUI.setDisplay(Y[1]);
		}
		
	}
	
	public void setChannel(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		System.out.print("setting "+Z[0]+" channel to "+Z[1]+"\n");
		chatGUI.setUserInChannel(Z[0]);
	}
	
	public void setChangedChannel()
	{
		chatGUI.setUserInChannel(client.GetName()+": "+client.GetChannel());
	}
	
	class ClientData
	{
		public String ClientName;
		public String channel;
		
		public void SetChannel(String Chan)
		{
			channel=Chan;
		}
		public void SetName(String name)
		{
			ClientName=name;
		}
		public String GetChannel()
		{
			return channel;
		}
		public String GetName()
		{
			return ClientName;
		}
	}
	
}