package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import POJO.Birou;
import POJO.Act;
import POJO.Document;

public class Client implements Runnable{
	
	private String nume;
	private Act act;
	private List<Document> documente = new ArrayList<Document>();
	private List<Birou> birouri = new ArrayList<Birou>();
	private final Semaphore mutex = new Semaphore(1);

	public Client(String nume, Act act, List<Document> documente, List<Birou> birouri) {
		this.nume = nume;
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
		System.out.println("\\Clientul "+this.nume+"/ "+"Am nevoie de: "+act.getNume());
		for (int i = 0; i < birouri.size(); i++) {
			for (int l = 0; l < birouri.get(i).getNrGhisee(); l++) {
				if (!birouri.get(i).getGhisee().get(l).isClosed()) {
					System.out.println("\\Clientul "+this.nume+"/ "+"Ghiseul " + (l+1) + " de la Biroul " + (i+1) + " este deschis");
					if (!gasit && birouri.get(i).getGhisee().get(l).cautAct(act)) { //pt gasit=1 o sa se parcurga birouri in cont inutil, trebuie mutat if(!gasit) dupa primul for
						// Act was found in an office
						gasit = true;
						for (int j = 0; j < documente.size(); j++) {
							//System.out.println("\\Clientul "+this.nume+"/ "+"Nume document: " + documente.get(j).getNume() + "| Luat: " + documente.get(j).getLuat());
							if (documente.get(j).getNume().equals(act.getNume())) {
								// Act is in fact a Document
								doc = true;
								//if(documente.get(j).getLuat() == 0) {
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
//										try {
//											mutex.acquire();
											//documente.get(j).setLuat(1);
//										} catch (InterruptedException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										} finally {
//											mutex.release();
//										}
										// I am a document and I have all the necessary acts
										act.setLuat(1);  //necesar? eventual pt cazul cand pt diploma bac buletin se considera act
										System.out.println("\\Clientul "+this.nume+"/ "+"Documentul " + act.getNume() + " a fost gasit si luat.");
									}
									else{
										//I am a document and I don't have all the necessary acts
										System.out.println("\\Clientul "+this.nume+"/ "+"Documentul " + act.getNume() + " nu poate fi eliberat.");
									}
								//}
//								else {
//									act.setLuat(1);
//									System.out.println("\\Clientul "+this.nume+"/ "+"Documentul "+documente.get(j).getNume()+" a fost luat deja.");
//								}
								//break;
							}
						}
//						if (doc == true) {
//							if (!gasitToateActeleNecesare) {
//								// I am a document and I don't have all the necessary acts
//								System.out.println("\\Clientul "+this.nume+"/ "+"Documentul " + act.getNume() + " nu poate fi eliberat.");
//								return; 
//							} 
//							else {
//								// I am a document and I have all the necessary acts
//								act.setLuat(1);  //necesar? eventual pt cazul cand pt diploma bac buletin se considera act
//								System.out.println("\\Clientul "+this.nume+"/ "+"Documentul " + act.getNume() + " a fost gasit si luat.");
//								return; 
//							}
//						} 
//						else {
//							// Act doesn't need other acts
//							act.setLuat(1);
//							System.out.println("\\Clientul "+this.nume+"/ "+"Actul " + act.getNume() + " a fost gasit si luat.");
//							return; 
//						}
						if(!doc){
							// Act doesn't need other acts
							act.setLuat(1);
							System.out.println("\\Clientul "+this.nume+"/ "+"Actul " + act.getNume() + " a fost gasit si luat.");
						}
					}
					else {
						break;//continue; //nu e bun, sare la urm ghiseu, trebuie break ca sa iasa din for-ul cu ghisee si
						//sa treaca la urm birou
					}
				} 
				else {
					System.out.println("\\Clientul "+this.nume+"/ "+"Ghiseul " + (l+1) + " de la Biroul " + (i+1) + " este inchis");
				}
			}
		}
		if (!gasit) {
			// Act wasn't found in any office
			System.out.println("\\Clientul "+this.nume+"/ "+"Actul " + act.getNume() + " nu exista.");
		}
	}
	
	//se repune luat=0 pt. toate documentele din lista dupa ce aceasta a fost consultata de un client
	private void resetListaDocumente() {
		for(Document d : documente)
			d.setLuat(0);
	}
	
	@Override
	public void run() {
//		try {
//			mutex.acquire();
		//apar probleme cand de ex un client cere diploma de bac, unul cere diploma de licenta, si primul obtine diploma
		//de bac, se reseteaza luat pe lista de doc, si chiar daca s-a luat buletinul pt diploma de bac necesara
		//diplomei de licenta, se mai ia o data si ca act necesar separat pt diploma de licenta (cand e pus semaforul)
			cereDocument(act, birouri, documente);
			resetListaDocumente();	
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			mutex.release();
//		}
						
	}

}
