package Client;
//////////////////////////////////////
import java.io.IOException;
import java.net.Socket;//this is the socket package
/*dont under any circumstance remove this import XD*/
import java.net.UnknownHostException;
///////////////////////////////////////
//our scanner import
import java.util.Scanner;

import javax.swing.JFrame;

public class ClientLaunch {
	
	public ClientLaunch()
	{	
		View_ChatRoom crape = new View_ChatRoom();
		crape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crape.setSize(600,600);
		crape.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClientLaunch();
	}
	
}