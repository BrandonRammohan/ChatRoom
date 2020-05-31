package Client;

import java.util.ArrayList;

public class Model_ClientData
{
	private String pseudo;
	private String group;
	private ArrayList<String> listGroup = new ArrayList<String>();
	private String groupSelected = ""; 
	

	public void setPseudo(String newPseudo) {
		this.pseudo = newPseudo;
	}
	
	public void setGroup(String newGroup) {
		this.group = newGroup;
	}
	
	public void setGroupSelected(String newGroupSelected) {
		this.groupSelected = newGroupSelected;
	}
	
	public String getChannel() {
		return group;
	}
	
	public String getName() {
		return pseudo;
	}
	
	public String getGroupSelected() {
		return groupSelected;
	}
	
	public void addInListGroup(String newChannel) {
		listGroup.add(newChannel);
	}
	
	public void removeInListGroup(String newChannel) {
		listGroup.remove(newChannel);
	}
}

