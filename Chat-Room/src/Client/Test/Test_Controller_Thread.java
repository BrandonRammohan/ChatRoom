package Client.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

//import org.junit.Test;
import org.junit.jupiter.api.Test;

import Client.Controller_Thread;
import Client.Model_ClientData;
import Client.View_ChatRoom;

public class Test_Controller_Thread {
	

	@Test
	void test_setClient() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String pseudo = "user1";
		String groupe = "Général";
		testController.setClient(groupe, pseudo);
		
		ArrayList<String> list = testController.call_getListGroup();
		
		assertEquals("user1", testController.call_getPseudo());
		assertEquals("Général", testController.call_getGroup());
		assertEquals("Général", list.get(list.size() - 1));
	}
	
	@Test
	void test_call_setChannelSelected() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String newGroupSelected = "Général";
		testController.call_setChannelSelected(newGroupSelected);
				
		assertEquals("Général", testController.call_getGroupSelected());
	}
	
	@Test
	void test_call_setChannel() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String groupe = "Other";
		testController.call_setChannel(groupe);
				
		assertEquals("Other", testController.call_getGroup());
	}
	
	@Test
	void test_call_addChannels() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String groupe = "Other";
		testController.call_addChannels(groupe);
		
		ArrayList<String> list = testController.call_getListGroup();
				
		assertEquals("Other", list.get(list.size() - 1));
	}
	
	@Test
	void test_call_checkExistingGroup() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String groupe1 = "Général";
		String groupe2 = "Other";
		
		testController.call_addChannels(groupe1);
		testController.call_addChannels(groupe2);
				
		String checkExistingGroup = "Other";
		String checkNonExistingGroup = "Nope";
		
		
		assertTrue(testController.call_checkExistingGroup(checkExistingGroup));
		assertFalse(testController.call_checkExistingGroup(checkNonExistingGroup));
		
	}
	
	@Test
	void test_sendDataClientToServer() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String data1 = "switch group";
		testController.sendDataClientToServer(data1);
		
		String data2 = "new user";
		testController.sendDataClientToServer(data2);

		String data3 = "group selected : Général";
		testController.sendDataClientToServer(data3);
		
		String data4 = "message de user1";
		testController.sendDataClientToServer(data4);
		
	}
	
	@Test
	void test_run() {
		View_ChatRoom testView = new View_ChatRoom();
		testView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testView.setSize(600,600);
		testView.setVisible(true);
		
		Socket socket = null;
		try {
			socket = new Socket("localhost",3333);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Controller_Thread testController = new Controller_Thread(socket, testView);
		
		String data1 = "switch group";
		testController.sendDataClientToServer(data1);
		
		String data2 = "new user";
		testController.sendDataClientToServer(data2);

		String data3 = "group selected : Général";
		testController.sendDataClientToServer(data3);
		
		String data4 = "message de user1";
		testController.sendDataClientToServer(data4);
		
	}

}
