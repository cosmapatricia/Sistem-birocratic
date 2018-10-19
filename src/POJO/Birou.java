package POJO;

import java.util.ArrayList;
import java.util.List;

public class Birou {

	private String nume;
	private int nrGhisee;
	private List<Act> acte = new ArrayList<Act>();

	public Birou(String nume, int nrGhisee, List<Act> acte) {
		this.nume = nume;
		this.nrGhisee = nrGhisee;
		this.acte.addAll(acte);
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public int getNrGhisee() {
		return nrGhisee;
	}
	public void setNrGhisee(int nrGhisee) {
		this.nrGhisee = nrGhisee;
	}
	public List<Act> getActe() {
		return acte;
	}

	public void setActe(List<Act> acte) {
		this.acte = acte;
	}
	public boolean cautAct(Act act) {
		for(int i = 0; i < acte.size(); i ++) {
			if(acte.get(i).getNume().equals(act.getNume())) {
				return true;
			}
		}
		return false;
	}
}
