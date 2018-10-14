package POJO;

import java.util.ArrayList;
import java.util.List;

public class Birou {

	private List<Act> acte = new ArrayList<Act>();

	public Birou(List<Act> acte) {
		this.acte.addAll(acte);
	}
	
	public List<Act> getActe() {
		return acte;
	}

	public void setActe(List<Act> acte) {
		this.acte = acte;
	}
}
