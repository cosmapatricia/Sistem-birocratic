package da;

import java.util.ArrayList;
import java.util.List;

public class Document {

	public List<Act> acte = new ArrayList<>();

	public List<Act> getActe() {
		return acte;
	}

	public void setActe(List<Act> acte) {
		this.acte = acte;
	}
}
