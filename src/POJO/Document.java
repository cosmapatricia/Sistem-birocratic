package POJO;

import java.util.ArrayList;
import java.util.List;

public class Document {
	
	// the name of the document
	private String nume;
	// the other documents we need to have in order to get this document
	private List<Act> acte = new ArrayList<Act>();
	private int luat = 0;

	public int getLuat() {
		return luat;
	}

	public void setLuat(int luat) {
		this.luat = luat;
	}

	public Document(List<Act> acte, String nume) {
		this.acte.addAll(acte);
		this.nume = nume;
	}
	
	public List<Act> getActe() {
		return acte;
	}

	public void setActe(List<Act> acte) {
		this.acte = acte;
	}
	public String getNume() {
		return nume;
	}
	
	public void setNume(String nume) {
		this.nume = nume;
	}
}
