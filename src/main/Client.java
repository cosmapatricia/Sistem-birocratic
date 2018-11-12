package main;

import java.util.ArrayList;
import java.util.List;

import POJO.Birou;
import POJO.Act;
import POJO.Document;

public class Client implements Runnable{
	
	private String nume;
	private Act act;
	private List<Document> documente = new ArrayList<Document>();
	private List<Birou> birouri = new ArrayList<Birou>();
	private int id;

	public Client(String nume, int id, Act act, List<Document> documente, List<Birou> birouri) {
		this.nume = nume;
		this.id = id;
		this.act = act;
		this.documente.addAll(documente);
		this.birouri.addAll(birouri);
	}
	
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}


	public List<Document> getDocumente() {
		return documente;
	}

	public void setDocumente(List<Document> documente) {
		this.documente = documente;
	}

	public List<Birou> getBirouri() {
		return birouri;
	}

	public void setBirouri(List<Birou> birouri) {
		this.birouri = birouri;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	private void cereDocument(Act act, List<Birou> birouri, List<Document> documente) {
		boolean gasit = false, gasitToateActeleNecesare = true, doc = false;
		System.out.println("Clientul " + this.nume + ": " + " Am nevoie de: " + act.getNume());
		for (int i = 0; i < birouri.size(); i++) {
			for (int l = 0; l < birouri.get(i).getNrGhisee(); l++) {
				if (!birouri.get(i).getGhisee().get(l).isClosed()) {
					//System.out.println("Clientul " + this.nume+": " + " Ghiseul " + (l+1) + " de la Biroul " + (i+1) + " este deschis");
					if (!gasit && birouri.get(i).getGhisee().get(l).cautAct(act)) { 
						// Act was found in an office
						synchronized(birouri.get(i).getGhisee().get(l)) {
							gasit = true;
							/*try {
								System.out.println(this.nume + " waits 2 s");
								Thread.sleep(2000);
							} catch(InterruptedException e) {
								e.printStackTrace();
							}*/
							for (int j = 0; j < documente.size(); j++) {
								if (documente.get(j).getNume().equals(act.getNume())) {
									// Act is in fact a Document
									doc = true;
									List<Act> acteNecesare = documente.get(j).getActe();
									for (int k = 0; k < acteNecesare.size(); k++) {
										cereDocument(acteNecesare.get(k), birouri, documente);
									}
									for (int k = 0; k < acteNecesare.size(); k++) {
										if (acteNecesare.get(k).getLuat() == 0) {
											gasitToateActeleNecesare = false;
											break;
										}
									}
									if(gasitToateActeleNecesare) {
										// I am a document and I have all the necessary acts
										act.setLuat(1);  
										System.out.println("Clientul " + this.nume + ": " + " Documentul " + act.getNume() + " a fost gasit si luat.");
									}
									else {
										//I am a document and I don't have all the necessary acts
										System.out.println("Clientul " + this.nume + ": " + " Documentul " + act.getNume() + " nu poate fi eliberat.");
									}
								}
							}
							if(!doc){
								// Act doesn't need other acts
								act.setLuat(1);
								System.out.println("Clientul " + this.nume+": " + " Actul " + act.getNume() + " a fost gasit si luat.");
							}
						}
					}
					else {
						break;
					}
				} 
				else {
					System.out.println("Clientul " + this.nume + ": " + " Ghiseul " + (l+1) + " de la Biroul " + (i+1) + " este inchis");
				}
			}
		}
		if (!gasit) {
			// Act wasn't found in any office
			System.out.println("Clientul " + this.nume + ": " + " Actul " + act.getNume() + " nu exista.");
		}
	}
	
	@Override
	public void run() {
		cereDocument(act, birouri, documente);		
		System.out.println("<----- CLIENTUL " + this.nume + " A OBTINUT DOCUMENTUL DORIT. ------>");
		try{
			Singleton.getInstance()[id] = true;
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}

}
