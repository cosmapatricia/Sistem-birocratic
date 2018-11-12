package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import POJO.Birou;
import POJO.Document;

public class Main {
	
	public static void main(String[] args) {
		
		/*
		 * TO BE DELETED
		 * nu am modificat algoritmul de parsare, nimic din partea aia
		 * am pus clientii in input.txt, pot fi comentati
		 * in clasa Singleton, e un array de boolene, fiecare client seteaza cate un boolean pe true
		 * cand toate boolenele sunt pe true, birourile se opresc(dupa ce isi termina sleep)
		 */
		
		List<Birou> birouri = new ArrayList<Birou>();
		List<Document> documente = new ArrayList<Document>();
		List<Client> clients = new ArrayList<Client>();
		
		try {
			BestUtilityEVA.readConfigurationFile(birouri, documente, clients);
			
			List<Thread> officeThreads = new ArrayList<Thread>();
			int currentThread = 0;
			for(Birou birou : birouri) {
				officeThreads.add(new Thread(birou));
				officeThreads.get(currentThread++).start();
			}
			
			// maybe we are not allowed to use executorService
			/*ExecutorService executor = Executors.newFixedThreadPool(birouri.size());
			for(Birou birou : birouri) {
				executor.execute(birou);
			}*/
		
			List<Thread> clientThreads = new ArrayList<Thread>();
			currentThread = 0;
			for(Client client : clients) {
				clientThreads.add(new Thread(client));
				clientThreads.get(currentThread++).start();
			}
			
			// works just as fine with the same instance starting all threads
			/*Thread thread;
			for(Client client : clients) {
				thread = new Thread(client);
				thread.start();
			}*/
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The configuration file wasn't found!");
		}
	}
}
