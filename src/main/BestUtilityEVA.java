package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import POJO.Act;
import POJO.Birou;
import POJO.Document;

public class BestUtilityEVA {

	private static String removeQuotes(String name) {
		return name.replace("\"", "");
	}
	
	private static void fillFromSeparated(List<Act> acte, String[] separated, int offset) {
		for(int i = offset; i < separated.length; ++i) {
			acte.add(new Act(removeQuotes(separated[i])));
		}
	}
	
	public static int getRandInRange(int low, int up) {
		return low + (int)(Math.random() * ((up - low) + 1));
	}
	
	private static int getNumberOfOffices(Scanner scanner) {
		String numberOfOffices = scanner.nextLine();
		return Integer.parseInt(numberOfOffices);
	}
	
	/**
	 * Initializes the offices, puts the documents every office can delivery in the specific office.
	 * Initializes the documents, puts the name of the document and fills the list of documents needed
	 * to obtain the final document.
	 * All documents are saved without quotes.
	 * 
	 * @param offices list of offices to be populated
	 * @param documents list of documents to be populated
	 * @throws FileNotFoundException in case the configuration file does not exist
	 */
	public static void readConfigurationFile(List<Birou> offices, List<Document> documents) throws FileNotFoundException {
		
		//BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		File inputFile = new File("input.txt");
		Scanner scanner = new Scanner(inputFile);
		
		int numberOfOffices = getNumberOfOffices(scanner);
		List<Act> acte = new ArrayList<Act>();
		
		// initializing offices and assign them the documents they can delivery
		for(int i = 0; i < numberOfOffices; ++i) {
			String nume = "Biroul "+ (i+1);
			String line = scanner.nextLine();
			String[] separator = line.split("\" ");
			//starting from 1 because index=0 is the no of counters
			fillFromSeparated(acte, separator, 1);
			int nrGhisee = Integer.parseInt(removeQuotes(separator[0]));
			offices.add(new Birou(nume, nrGhisee, acte));
			acte.clear();
		}
		
		// the empty line
		scanner.nextLine();
		
		// initializing the documents
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// if we accidentally add empty lines in the configuration file
			if(line.isEmpty()) {
				continue;
			}
			String[] separated = line.split("\" ");
			fillFromSeparated(acte, separated, 1);
			documents.add(new Document(acte, removeQuotes(separated[0])));
			acte.clear();
		}
		
		scanner.close();
		
		printOffices(offices);
		printDocuments(documents);
		
	}
	
	public static void printOffices(List<Birou> offices) {
		
		for(Birou office : offices) {
			System.out.println(office.getNume() + " cu " + office.getNrGhisee() + " ghisee " + "poate emite urmatoarele acte:");
			List<Act> acte = office.getGhisee().get(0).getActe();
			for(Act act : acte) {
				System.out.print(act.getNume() + ", ");
			}
			System.out.println();
		}
		
		System.out.println("\n");
	}
	
	public static void printDocuments(List<Document> documents) {
		
		for(Document document : documents) {
			System.out.println("Pentru documentul "+ document.getNume() + " avem nevoie de:");
			List<Act> acte = document.getActe();
			for(Act act : acte) {
				System.out.print(act.getNume() + ", ");
			}
			System.out.println();
		}
		
		System.out.println("\n");
	}
}
