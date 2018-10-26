package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import POJO.Act;
import POJO.Birou;
import POJO.Document;

public class Main {
	
	public static void main(String[] args) {
		
		List<Birou> offices = new ArrayList<Birou>();
		List<Document> documents = new ArrayList<Document>();
		
		Act act1 = new Act("buletin");
		Act act2 = new Act("diploma de bacalaureat");
		
		try {
			BestUtilityEVA.readConfigurationFile(offices, documents);
			Client client1 = new Client(act1, documents, offices);
			Client client2 = new Client(act2, documents, offices);
			ExecutorService executor = Executors.newFixedThreadPool(offices.size());
			for(Birou birou : offices) {
				executor.execute(birou);
			}
			// uncomment in case of emergency
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			client1.run();
			client2.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The configuration file wasn't found!");
		}
		
		
		
	}
}
