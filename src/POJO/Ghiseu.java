package POJO;

import java.util.ArrayList;
import java.util.List;

public class Ghiseu {

	private boolean closed = false;  //ar trebui setat pe ceva de la inceput
	private List<Act> acte = new ArrayList<Act>();
	
	public List<Act> getActe() {
		return acte;
	}

	public void setActe(List<Act> acte) {
		this.acte = acte;
	}

	public Ghiseu(List<Act> acte) {
		this.acte.addAll(acte);
	}
	
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
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
