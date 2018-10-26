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
			int index = BestUtilityEVA.getRandInRange(0, ghisee.size() - 1);
			System.out.println("Ghiseul "+index+ " de la Biroul "+nume+" o sa fie inchis");
			ghisee.get(index).setClosed(true);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Problems mit dem Threads, m8.");
			}
			ghisee.get(index).setClosed(false);
			//System.out.println("Works");
			
		}
		
	}
}
