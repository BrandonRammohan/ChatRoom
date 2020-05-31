package Client;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.Scanner;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class View_ChatRoom extends JFrame {

	private JPanel panel;
	private JTextField pseudo;
	private JTextField message;
	private JTextField groupe;
	private JTextArea conv;
	private JTextArea participants;
	//private JTextArea conversations;
	private Container chat;
	Controller_Thread clientThread;
	public Model_ClientData client;
	private JLabel l_groupe;
	private JLabel l_pseudo;
	private JButton buttonGeneric;
	private JButton buttonGroup;

	public View_ChatRoom() {	
		super("Chat");
		
		getContentPane().setLayout(new FlowLayout());
		
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
//		this.conversations = new JTextArea(30, 10);
//		JScrollPane scrollPane4 = new JScrollPane(this.conversations); 
//		conversations.setEditable(false);
//		conversations.append("channel0"+ newline);
//		
//		getContentPane().add(scrollPane4);
		
		this.conv = new JTextArea(30, 30);
		JScrollPane scrollPane = new JScrollPane(conv); 
		conv.setEditable(false);
		
		getContentPane().add(scrollPane);
		
		this.participants = new JTextArea(30, 10);
		JScrollPane scrollPane3 = new JScrollPane(this.participants); 
		participants.setEditable(false);
		
		getContentPane().add(scrollPane3);
		
		this.message = new JTextField(20);
		message.setEditable(false);
		getContentPane().add(message);

        this.pseudo = new JTextField(20);
        pseudo.setEditable(true);
		getContentPane().add(pseudo);

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
			Socket socket = new Socket("localhost",3333);
			this.clientThread = new Controller_Thread(socket,this);
			clientThread.start();
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
			thehandler handlerButton = new thehandler();

			if(event.getSource()==message)
			{
				string=String.format("%s", event.getActionCommand());
				String text= message.getText();
				clientThread.sendDataClientToServer(text);
				message.setText("");
			}
			else if(event.getSource()==pseudo) {
				string=String.format("%s", event.getActionCommand());
				if(string.matches("[0-9]*"))
				{
					JOptionPane.showMessageDialog(null,"formate not allowed");
					pseudo.setText("");
				}
				else
				{
					//Set the NAME of the THREAD
					clientThread.setName(string);
					
					clientThread.setClient("channel0",string);
										
					clientThread.call_setChannelSelected("channel0");
					
					JOptionPane.showMessageDialog(null, "name has been set: "+string);
					
					buttonGeneric = new JButton("channel0");
					buttonGeneric.addActionListener(handlerButton);
					panel.add(buttonGeneric);
					
					//pseudo.setText("");
					pseudo.setEditable(false);
					message.setEditable(true);
					groupe.setEditable(true);
					clientThread.sendDataClientToServer("new user");
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
					clientThread.call_setChannel("channel"+string);
					
					clientThread.call_setChannelSelected("channel"+string);

					clientThread.call_addChannels("channel"+string);
					
					JOptionPane.showMessageDialog(null, "Channel has been set: channel"+string);
					
					buttonGroup = new JButton("channel"+string);
					buttonGroup.addActionListener(handlerButton);
					panel.add(buttonGroup);
					
					panel.revalidate();
					groupe.setText("");
					
					clientThread.sendDataClientToServer("change channel");
				}
			}
			
			else if((event.getSource() == buttonGroup) || (event.getSource() == buttonGeneric)) {
			    Object source = event.getSource();
		        JButton btn = (JButton)source;
				string = btn.getText();
				System.out.println("String -> " + string);

				clientThread.call_setChannelSelected(string);

				clientThread.sendDataClientToServer("button selected : "+string);
			}
		}
	}
	
	public void setDisplay(String x) {
		conv.append(x + "\n"); 
	}
	
	public void displaySavedMessaged(String recordedMessages) {
		conv.append(recordedMessages);
		panel.revalidate();
	}
	
	public void displaySavedUsers(String oldUsers) {
		participants.append(oldUsers);
	}
	
	public void setUserInChannel(String x) {
		participants.append(x + "\n");
	}
	
	public void clearChat() {
		conv.setText("");
	}
	
	public void clearDisplay() {
		participants.setText("");
	}

}
