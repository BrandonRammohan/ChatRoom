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
	
	
	public String getPseudo() {
		return pseudo;
	}
	
	public String getGroup() {
		return group;
	}
	
	public String getGroupSelected() {
		return groupSelected;
	}
	
	public ArrayList<String> getListGroup() {
		return listGroup;
	}
	
	public void addInListGroup(String newGroup) {
		this.listGroup.add(newGroup);
	}
	
	public Boolean checkExistingGroup(String group) {
		for(String existingGroup : this.listGroup) {
			if(existingGroup.equals(group)) {
				return true;
			}
		}
		return false;
	}
	
}

