package Client;

import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
			else if(Text.matches("button selected : (.*)"))
			{
				dataOut.writeUTF(Text);
				dataOut.flush();
				changeFileName(Text);
				displayMessagesPerChannel(Text);
			}
			else
			{
				String message = client.GetChannel()+"="+this.getName()+": "+Text;
				dataOut.writeUTF(message);
				dataOut.flush();
				sauvegardeFichier(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void SetClient(String channel,String Name) {
		client.SetName(Name);
		client.SetChannel(channel);
		client.addChannels(channel);
	}
	
	public void run() {
		try {
			
			System.out.println("In Run");
			//chatGUI.autoCreateUser();
			
			displayGenericChat();
			//getSavedMessageAndDisplay();
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
					
					if(reply.matches("button selected : (.*)")) {
						System.out.println("in button server : " + reply);
					}
					
					if(name.equals("new user")) {
						System.out.print("new user in body: "+reply+"\n");
						//GUI.ClearDisplay();
						setChannel(reply);
					}
					
					else {
						PrintReply(Chan,reply);
						//System.out.print("Reply ==> "+reply+"\n");
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
	
	public void changeFileName(String text) {
		String[]Y=text.split(": ");
		sauvegarde.setfileName(Y[1]);
	}
	
	public void sauvegardeFichier(String Rep) {
		if(!Rep.contentEquals("change channel")) {
			String []Y=Rep.split("=");
			sauvegarde.writeMessagesInFile(Y[1]);
		}
	}
	
	public void displayGenericChat() {
		String oldMessages = sauvegarde.readMessagesInFile("save_channel0.txt");
		chatGUI.displaySavedMessaged(oldMessages);
	}
	
	public void getSavedMessageAndDisplay() {
		//String oldMessages = sauvegarde.readMessagesInFile();
		//chatGUI.displaySavedMessaged(oldMessages);
	}
	
	private void getSavedUsersAndDisplay() {
		String oldUsers = sauvegarde.readUsersInFile();
		chatGUI.displaySavedUsers(oldUsers);
	}
	
	private void displayMessagesPerChannel(String text) {
		chatGUI.clearChat();
		String[]Y=text.split(": ");
		String messages =sauvegarde.readMessagesInFile("save_"+Y[1]+".txt");
		chatGUI.displaySavedMessaged(messages);
	}
	
	public void PrintReply(String Chan,String Rep)
	{
//		if(client.GetChannel().equals(Chan))
//		{
//			System.out.println("Rep => "+ Rep);
//			String []Y=Rep.split("=");
//			System.out.println("Y[1] => "+ Y[1]);
//			
//			chatGUI.setDisplay(Y[1]);
//		}
		if(Chan.contentEquals(client.getChannelSelected())) {
			System.out.println("Rep => "+ Rep);
			String []Y=Rep.split("=");
			System.out.println("Y[1] => "+ Y[1]);
			
			chatGUI.setDisplay(Y[1]);
		}
		
//		for(String channel : client.channels) {
//			if(Chan.contentEquals(channel)) {
//				System.out.println("Rep => "+ Rep);
//				String []Y=Rep.split("=");
//				System.out.println("Y[1] => "+ Y[1]);
//				
//				chatGUI.setDisplay(Y[1]);
//			}
//		}	
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
		public ArrayList<String> channels = new ArrayList<String>();
		private String channelSelected = ""; 


		
		public void SetChannel(String Chan) {
			channel=Chan;
		}
		
		public void SetName(String name) {
			ClientName=name;
		}
		
		public String GetChannel() {
			return channel;
		}
		
		public String GetName() {
			return ClientName;
		}
		
		public void setChannelSelected(String channel) {
			channelSelected = channel;
		}
		
		public String getChannelSelected() {
			return channelSelected;
		}
		
		public void addChannels(String newChannel) {
			channels.add(newChannel);
			System.out.println("Channels : ");
			for(String chan : channels) {
				System.out.println(chan);
			}
		}
		
		public void removeChannels(String newChannel) {
			channels.remove(newChannel);
			System.out.println("Channels de " + this.GetName() + ": ");
			for(String chan : channels) {
				System.out.println(chan + "\n");
			}
		}
	}
	
}