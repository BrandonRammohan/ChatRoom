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

public class SaveInFile {

	public BufferedWriter out;
	public BufferedReader in;
	public String fileGroup;
	public String filePseudo;

	public SaveInFile() {
		this.fileGroup = "messages-Général.txt" ;
		this.filePseudo = "users-Général.txt";
	}
	
	public void setFileGroup(String groupName) {
		this.fileGroup = "messages-"+groupName+".txt";
	}
	
	public void setfileUser(String groupName) {
		this.filePseudo = "user-"+groupName+".txt";
	}
	
	public String getfileUser() {
		return filePseudo;
	}
	
	public String getfileName() {
		return fileGroup;
	}

	/************* WRITE ***************/
	public void writeMessagesInFile(String message) {
		try  {
			this.out = new BufferedWriter(new FileWriter(this.getfileName(), true));
			out.write(message+"\n");
			out.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeUsersInFile(String user, String group) {
		try  {
			BufferedWriter out = new BufferedWriter(new FileWriter(this.getfileName(), true));
			out.write(user +"\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************* READ ***************/
	public String readMessagesInFile(String fileMessage) {
		InputStream is;
		try {
			is = new FileInputStream(fileMessage);
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