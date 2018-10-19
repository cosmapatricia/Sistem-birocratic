package main;

import java.util.List;

import POJO.Birou;
import POJO.Act;
import POJO.Document;

public class Client {

	private Act act;
	
	public Client(Act act) {
		this.act = act;
	}
	
	public Act getAct() {
		return act;
	}
	
	public void setAct(Act act) {
		this.act = act;
	}
	
	public void cereDocument(Act act, List<Birou> birouri,List<Document> documente) {
		boolean gasit = false, gasitToateActeleNecesare = true, doc = false;
		for(int i = 0; i < birouri.size(); i++) {
			if(birouri.get(i).cautAct(act) && !gasit) {
				//Act was found in an office
				gasit = true;
				for(int j = 0; j < documente.size(); j++) {
					if(documente.get(j).getNume().equals(act.getNume())) {
						//Act is in fact a Document
						doc = true;
						List<Act> acteNecesare = documente.get(j).getActe();
						for(int k = 0; k < acteNecesare.size(); k++) {
							cereDocument(acteNecesare.get(k),birouri,documente);
						}
						for(int k = 0; k < acteNecesare.size(); k++) {
							if(acteNecesare.get(k).getLuat() == 0) {
								gasitToateActeleNecesare = false;
							}
						}
					}
				}
				if(doc == true) {
					if(!gasitToateActeleNecesare) {
						//I am a document and I don't have all the necessary acts
						System.out.println("Documentul " + act.getNume() + " nu poate fi eliberat.");
					}
					else {
						//I am a document and I have all the necessary acts
						act.setLuat();
						System.out.println("Documentul " + act.getNume() + " a fost gasit si luat.");
					}
				}
				else {
					//Act doesn't need other acts
					act.setLuat();
					System.out.println("Actul " + act.getNume() + " a fost gasit si luat.");
				}
			}
		}
		if(!gasit) {
			//Act wasn't found in any office
			System.out.println("Actul " + act.getNume() + " nu exista.");
		}
	}
	
	
}
