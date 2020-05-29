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


	public Sauvegarde() {
		this.fos =null ;
	}

	public void writeMessagesInFile(String results) {
		System.out.println("message "+results);
		try  {
			this.out = new BufferedWriter(new FileWriter("saveChat.txt", true));
			out.write(results+"\n");
			out.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeUsersInFile(String user) {
		System.out.println("user "+user);
		try  {
			this.out = new BufferedWriter(new FileWriter("saveUsers.txt", true));
			out.write(user+"\n");
			out.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readMessagesInFile() {
		InputStream is;
		try {
			is = new FileInputStream("saveChat.txt");
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
				System.out.println("Contents (before Java 7) : " + fileAsString);
				return fileAsString ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return "echec" ;
	}
	
	public String readUsersInFile() {
		InputStream is;
		try {
			is = new FileInputStream("saveUsers.txt");
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
				System.out.println("Contents (before Java 7) : " + fileAsString);
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

		return "echec" ;
	}
	
} 