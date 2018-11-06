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
		
		List<Birou> birouri = new ArrayList<Birou>();
		List<Document> documente = new ArrayList<Document>();
		
		Act act1 = new Act("buletin");
		Act act2 = new Act("diploma de bacalaureat");
		Act act3 = new Act("diploma de licenta");
		Act act4 = new Act("buletin");
		Act act5 = new Act("buletin");
		Act act6 = new Act("buletin");
		Act act7 = new Act("buletin");
		
		try {
			BestUtilityEVA.readConfigurationFile(birouri, documente);
			
			ExecutorService executor = Executors.newFixedThreadPool(birouri.size());
			for(Birou birou : birouri) {
				executor.execute(birou);
			}
		
			Client client1 = new Client("c1", act1, documente, birouri);
			Client client2 = new Client("c2", act2, documente, birouri);
			Client client3 = new Client("c3", act3, documente, birouri);
			Client client4 = new Client("c4", act4, documente, birouri);
			Client client5 = new Client("c5", act5, documente, birouri);
			Client client6 = new Client("c6", act6, documente, birouri);
			Client client7 = new Client("c7", act7, documente, birouri);
			Thread th1 = new Thread(client1);
			Thread th2 = new Thread(client2);
			Thread th3 = new Thread(client3);
			Thread th4 = new Thread(client4);
			Thread th5 = new Thread(client5);
			Thread th6 = new Thread(client6);
			Thread th7 = new Thread(client7);
			
			// uncomment in case of emergency
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			
//			th1.start(); // nu se apeleaza run
			th2.start();
			th3.start();
			th4.start();
			th5.start();
//			th6.start();
//			th7.start();
		
			
			
//			client1.run();
//			client2.run();
//			client3.run();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The configuration file wasn't found!");
		}
		
		
		
	}
}
