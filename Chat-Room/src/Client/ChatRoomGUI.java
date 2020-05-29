package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;//this is the socket package
/*dont under any circumstance remove this import XD*/
import java.net.UnknownHostException;
///////////////////////////////////////
//our scanner import
import java.util.Scanner;
///////////////////////////////////////

@SuppressWarnings("serial")
public class ChatRoomGUI extends JFrame{

	private JTextField message;
	private JTextField pseudo;
	private JTextField groupe;
	private JTextArea conv;
	private JTextArea participants;
	private Container chat;
	MultiThreads ClientThread;
	private JLabel l_groupe;
	private JLabel l_pseudo;
	private final static String newline = "\n";

	public ChatRoomGUI(){
		super("WOW");
		
		setLayout(new FlowLayout());
		
		this.conv = new JTextArea(30, 30);
		JScrollPane scrollPane = new JScrollPane(conv); 
		conv.setEditable(false);
		
		add(scrollPane);
		
		this.participants = new JTextArea(30, 10);
		JScrollPane scrollPane3 = new JScrollPane(this.participants); 
		participants.setEditable(false);
		
		add(scrollPane3);
		
		this.message = new JTextField(20);
		message.setEditable(false);
		add(message);

        this.pseudo = new JTextField(20);
        pseudo.setEditable(true);
		add(pseudo);

		this.l_groupe=new JLabel("channel number");
		this.l_pseudo=new JLabel("Name");
		
		chat = this.getContentPane(); 
		chat.setLayout(new FlowLayout());
		chat.add(l_pseudo);
		chat.add(pseudo);
		
		this.groupe = new JTextField(20);
		groupe.setEditable(false);
			
		chat = this.getContentPane(); 
		chat.setLayout(new FlowLayout());
		chat.add(l_groupe);
		chat.add(groupe);
		
		thehandler handler = new thehandler();
		message.addActionListener(handler);
		pseudo.addActionListener(handler); 
		groupe.addActionListener(handler);
		try {
			Socket s = new Socket("localhost",3333);
			this.ClientThread = new MultiThreads(s,this);
			ClientThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent event){

			String string = "";

			if(event.getSource()==message)
			{
				string=String.format("%s", event.getActionCommand());
				String text= message.getText();
				ClientThread.ClientOutServerIn(text);
				message.setText("");
			}
			else if(event.getSource()==pseudo) {
				System.out.println('1');
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[0-9]*"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					pseudo.setText("");
				}
				else
				{
					System.out.println(ClientThread);
					ClientThread.setName(string);
					System.out.println('3');
					ClientThread.SetClient("channel0",string);
					JOptionPane.showMessageDialog(null, "name has been set: "+string);
					pseudo.setText("");
					pseudo.setEditable(false);
					message.setEditable(true);
					groupe.setEditable(true);
					ClientThread.ClientOutServerIn("new user");
					l_pseudo.setVisible(false);
				}
			}
			else if(event.getSource()==groupe) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[a-z A-Z]"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					groupe.setText("");
				}
				else
				{
					ClientThread.client.SetChannel("channel"+string);
					JOptionPane.showMessageDialog(null, "Channel has been set: channel"+string);
					groupe.setText("");
					ClientThread.ClientOutServerIn("change channel");
				}
			}
			//JOptionPane.showMessageDialog(null, string);
		}
	}
	public void setDisplay(String x)
	{
		conv.append(x + newline); 
	}
	
	public void displaySavedMessaged(String oldMessages) {
		conv.append(oldMessages);
	}
	
	public void displaySavedUsers(String oldUsers) {
		participants.append(oldUsers);
	}
	
	public void setUserInChannel(String x)
	{
		participants.append(x + newline);
	}
	
	public void ClearDisplay()
	{
		participants.setText("");
	}
}
