package Client;

import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Controller_Thread extends Thread {
	
	private Socket socket;
	private DataInputStream dataIn;
	public DataOutputStream dataOut;
	private Boolean exit = false;
	private Model_ClientData modelClient;
	private View_ChatRoom viewChat;
	private SaveInFile sauvegarde;
	
	
	public Controller_Thread(Socket clientSocket, View_ChatRoom view) {
		this.socket = clientSocket;
		this.modelClient = new Model_ClientData();
		this.viewChat = view;
		this.sauvegarde = new SaveInFile() ;	
	}
	
/**************** Sends data to the server about user's interaction with the chat ****************/
	
	public void sendDataClientToServer(String data) {
		try {
			
			if(data.equals("change channel"))
			{
				dataOut.writeUTF(data);
				dataOut.flush();
				sauvegarde.writeUsersInFile(modelClient.getName(), modelClient.getGroupSelected());
			}
			else if(data.equals("new user"))
			{
				dataOut.writeUTF(data+":"+modelClient.getName()+"="+modelClient.getChannel());
				dataOut.flush();
				sauvegarde.writeUsersInFile(modelClient.getName(), modelClient.getGroupSelected());
			}
			else if(data.matches("button selected : (.*)"))
			{
				dataOut.writeUTF(data);
				dataOut.flush();
				
				changeFileMessage(data);
				displayMessagesPerGroup(data);
				
				changeFileUser(data);
				displayUsernamePerChannel(modelClient.getGroupSelected());
			}
			else
			{
				String message = modelClient.getChannel()+"="+this.getName()+": "+data;
				dataOut.writeUTF(message);
				dataOut.flush();
				saveMessageInFile(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
/**************** Gets data from above function and display messages to other users ****************/
	
	public void run() {
		try {
			
			System.out.println("In Run");
			
			displayGenericChat();
			displayGenericUser();
			
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
					
					String reply = dataIn.readUTF();
					String pseudoReply = extractPseudoFromReply(reply);
					String groupReply = extractGroupFromReply(reply);
					
					if(reply.matches("button selected : (.*)")) {
						System.out.println("in button server : " + reply);
					}
					
					if(pseudoReply.equals("new user")) {
						addNewUserinGroup(reply);
					}
					
					else {
						displayReply(groupReply,reply);
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
	
/**************** Call from View to Model ****************/
	
	public void setClient(String newGroup,String newPseudo) {
		modelClient.setPseudo(newPseudo);
		modelClient.setGroup(newGroup);
		modelClient.addInListGroup(newGroup);
	}

	public void call_setChannelSelected(String newChannelSelected) {
		modelClient.setGroupSelected(newChannelSelected);
	}
	
	public void call_setChannel(String newChannel) {
		modelClient.setGroup(newChannel);
	}
	
	public void call_addChannels(String newChannel) {
		modelClient.addInListGroup(newChannel);
	}
	
/**********************************************************/

	public void closeClient()
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
	
/**************** Extract data from the reply  ****************/

	public String extractPseudoFromReply(String reply)
	{
		String[] splitReply = reply.split(":");
		return splitReply[0];
	}
	
	public String extractGroupFromReply(String reply)
	{
		String[] splitReply = reply.split("=");
		return splitReply[0];
	}

/**************** Interaction with SaveInFile.java ****************/
	
	public void changeFileMessage(String data) {
		String[] splitData = data.split(": ");
		sauvegarde.setFileGroup(splitData[1]);
	}
	
	public void changeFileUser(String data) {
		String[] splitData = data.split(": ");
		sauvegarde.setfileUser(splitData[1]);
	}
	
	public void saveMessageInFile(String newMessage) {
		if(!newMessage.contentEquals("change channel")) {
			String[] splitMessage = newMessage.split("=");
			sauvegarde.writeMessagesInFile(splitMessage[1]);
		}
	}
	
	// Display Messages from the General group
	public void displayGenericChat() {
		String oldMessages = sauvegarde.readMessagesInFile("save_channel0.txt");
		viewChat.displaySavedMessaged(oldMessages);
	}
	
	// Display the list of Users from the General group
	private void displayGenericUser() {
		String oldUsers = sauvegarde.readUsersInFile("users-channel0.txt");
		viewChat.displaySavedUsers(oldUsers);
	}
	
	// Display Messages of a group when the user switches from a group to another
	private void displayMessagesPerGroup(String groupSelected) {
		viewChat.clearChat();
		String[] splitGroup = groupSelected.split(": ");
		String messages = sauvegarde.readMessagesInFile("save_"+splitGroup[1]+".txt");
		viewChat.displaySavedMessaged(messages);
	}
	
	// Display the list of Users of a group when the user switches from a group to another *********/
	public void displayUsernamePerChannel(String groupSelected){
		viewChat.clearDisplay();
		String users = sauvegarde.readUsersInFile("users-"+groupSelected+".txt");
		viewChat.displaySavedUsers(users);
	}

	// Display Messages from the other users of the same group
	public void displayReply(String groupOfOtherUser,String reply)
	{
		if(groupOfOtherUser.contentEquals(modelClient.getGroupSelected())) {
			String[] splitReply=reply.split("=");
			
			viewChat.setDisplay(splitReply[1]);
		}
	}
	
/**************** Display the New User in the list of Users of the group ****************/
	public void addNewUserinGroup(String x)
	{
		String []Y=x.split(":");
		String []Z=Y[1].split("=");
		viewChat.setUserInChannel(Z[0]);
	}
	
}