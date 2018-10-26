package main;

import java.util.ArrayList;
import java.util.List;

import POJO.Birou;
import POJO.Act;
import POJO.Document;

public class Client implements Runnable{

	private Act act;
	private List<Document> documents = new ArrayList<Document>();
	private List<Birou> offices = new ArrayList<Birou>();

	public Client(Act act, List<Document> documents, List<Birou> offices) {
		this.act = act;
		this.documents.addAll(documents);
		this.offices.addAll(offices);
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<Birou> getOffices() {
		return offices;
	}

	public void setOffices(List<Birou> offices) {
		this.offices = offices;
	}


	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	private void cereDocument(Act act, List<Birou> birouri, List<Document> documente) {
		boolean gasit = false, gasitToateActeleNecesare = true, doc = false;
		System.out.println("Am nevoie de: "+act.getNume());
		for (int i = 0; i < birouri.size(); i++) {
			// PUS DOAR CA SA EVITE EROARE DE COMPILARE, INCA O PARSARE E
			// NECESARE( a ghiseelor )
			for (int l = 0; l < birouri.get(i).getNrGhisee(); l++) {
				if (!birouri.get(i).getGhisee().get(l).isClosed()) {
					if (birouri.get(i).getGhisee().get(l).cautAct(act) && !gasit) {
						// Act was found in an office
						gasit = true;
						for (int j = 0; j < documente.size(); j++) {
							if (documente.get(j).getNume().equals(act.getNume()) && documente.get(j).getLuat() == 0) {
								// Act is in fact a Document
								documente.get(j).setLuat(1);
								//TODO daca de exemplu niciun birou nu emite certif de nastere, si vrem permis de conducere
								//el nu o sa fie eliberat la final, dar in cazul buletinului necesar permisului 
								//ne spune ca a fost luat
								//desi si el are nevoie de certif de nastere, ceea ce inseamna ca daca un document are nevoie 
								//de alt document ca sa fie eliberat, pt documentul interior nu o sa se parcurga 
								//toata actele necesare
								doc = true;
								List<Act> acteNecesare = documente.get(j).getActe();
								for (int k = 0; k < acteNecesare.size(); k++) {
									cereDocument(acteNecesare.get(k), birouri, documente);
								}
								for (int k = 0; k < acteNecesare.size(); k++) {
									if (acteNecesare.get(k).getLuat() == 0) {
										gasitToateActeleNecesare = false;
									}
								}
							}
						}
						if (doc == true) {
							if (!gasitToateActeleNecesare) {
								// I am a document and I don't have all the
								// necessary acts
								System.out.println("Documentul " + act.getNume() + " nu poate fi eliberat.");
							} else {
								// I am a document and I have all the necessary
								// acts
								act.setLuat();
								System.out.println("Documentul " + act.getNume() + " a fost gasit si luat.");
								return;
							}
						} else {
							// Act doesn't need other acts
							act.setLuat();
							System.out.println("Actul " + act.getNume() + " a fost gasit si luat.");
						}
					}
				} else {
					System.out.println("Ghiseul este inchis");
				}
			}
		}
		if (!gasit) {
			// Act wasn't found in any office
			System.out.println("Actul " + act.getNume() + " nu exista.");
		}
	}

	@Override
	public void run() {
		cereDocument(act, offices, documents);
	}

}
