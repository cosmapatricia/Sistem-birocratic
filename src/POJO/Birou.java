package POJO;

import java.util.ArrayList;
import java.util.List;

import main.BestUtilityEVA;

public class Birou implements Runnable {

	private String nume;
	private int nrGhisee;
	private List<Ghiseu> ghisee = new ArrayList<Ghiseu>();

	public Birou(String nume, int nrGhisee, List<Act> acte) {
		this.nume = nume;
		this.nrGhisee = nrGhisee;
		for(int j = 0; j < nrGhisee; j++) {
			ghisee.add(new Ghiseu(acte));
		}
	}
	public List<Ghiseu> getGhisee() {
		return ghisee;
	}
	public void setGhisee(List<Ghiseu> ghisee) {
		this.ghisee = ghisee;
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
	
	@Override
	public void run() {

		while(true) {
			try{
				int index = BestUtilityEVA.getRandInRange(0, ghisee.size() - 1);
				System.out.println("Ghiseul " + (index+1) + " de la "+ nume + " o sa fie inchis");
				ghisee.get(index).setClosed(true);
				Thread.sleep(30000);
				ghisee.get(index).setClosed(false);
				System.out.println("Ghiseul " + (index+1) + " de la "+ nume + " o sa fie deschis");
				
				/*if(BestUtilityEVA.allClientsEnded()) {
					System.out.println("Biroul " + this.nume + " se opreste.");
					break;
				}*/
			} catch(InterruptedException e) {
				System.out.println("Biroul " + this.nume + " a fost oprit.");
				break;
			}
		}
		
	}
}
