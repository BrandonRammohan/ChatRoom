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


	public Sauvegarde() {
		this.fileName = "save_channel0.txt" ;
	}
	
	public void setfileName(String name) {
		this.fileName = "save_"+name+".txt";
	}
	
	public String getfileName() {
		return fileName;
	}

	/************* WRITE ***************/
	public void writeMessagesInFile(String results) {
		
		System.out.println("message "+results);
		try  {
			System.out.println("in file");
			this.out = new BufferedWriter(new FileWriter(this.getfileName(), true));
			System.out.println("file created");
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
	
	/************* READ ***************/
	public String readMessagesInFile(String file) {
		System.out.println("file is 2 : " + file);
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

		return "echec lors du rechargement de la conversation...\n \n" ;
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