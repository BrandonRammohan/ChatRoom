package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Sauvegarde {

	public FileOutputStream fos ;
	public ChatRoomGUI GUI;
	public BufferedWriter out;
	public BufferedReader in;
	public String fileName;
	public String fileUser ;



	public Sauvegarde() {
		this.fileName = "save_channel0.txt" ;
		this.fileUser = "users-channel0.txt";

	}
	
	public void setfileName(String name) {
		this.fileName = "save_"+name+".txt";
	}
	
	public void setfileUser(String user) {
		this.fileUser = "user_"+user+".txt";
	}
	
	public String getfileUser() {
		return fileUser;
	}
	
	
	public String getfileName() {
		return fileName;
	}

	/************* WRITE ***************/
	public void writeMessagesInFile(String results) {
		try  {
			this.out = new BufferedWriter(new FileWriter(this.getfileName(), true));
			out.write(results+"\n");
			out.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeUsersInFile(String user, String group) {
		try  {
			System.out.print("String group = " + group);
			BufferedWriter out = new BufferedWriter(new FileWriter("users-" +group+".txt", true));
			out.write(user +"\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************* READ ***************/
	public String readMessagesInFile(String file) {
		String readFile = file;
		InputStream is;
		try {
			is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line;
			try {
				line = buf.readLine();
				StringBuilder sb = new StringBuilder(); 
				while(line != null){ 
					sb.append(line).append("\n"); 
					line = buf.readLine(); 
				} 
				String fileAsString = sb.toString(); 
				return fileAsString ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return "" ;
	}
	
	public String readUsersInFile(String fileUser) {
		InputStream is;
		try {
			is = new FileInputStream(fileUser);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
			String line;
			try {
				line = buf.readLine();
				StringBuilder sb = new StringBuilder(); 
				while(line != null){ 
					sb.append(line).append("\n"); 
					line = buf.readLine(); 
				} 
				String fileAsString = sb.toString(); 
				//GUI.setDisplay("bonjour");
				return fileAsString ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return "" ;
	}
	
} 