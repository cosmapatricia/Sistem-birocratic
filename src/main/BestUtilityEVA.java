package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import POJO.Act;
import POJO.Birou;
import POJO.Document;

public class BestUtilityEVA {

	/**
	 * Removes quotes from the name
	 * 
	 * @return name without quotes
	 */
	private static String removeQuotes(String name) {
		return name.replace("\"", "");
	}

	/**
	 * Fills the given list from the separated arrays
	 * 
	 * @param acte
	 *            the list to be filled
	 * @param separated
	 *            the array from which the array is filled
	 * @param offset
	 *            the position from which the array is parsed in order to fill
	 *            the list
	 */
	private static void fillFromSeparated(List<Act> acte, String[] separated, int offset) {
		for (int i = offset; i < separated.length; ++i) {
			acte.add(new Act(removeQuotes(separated[i])));
		}
	}

	/**
	 * Returns random number between low and up
	 */
	public static int getRandInRange(int low, int up) {
		return low + (int) (Math.random() * ((up - low) + 1));
	}
	
	/**
	 * Gets the number of clients by parsing the configuration file.
	 * @return the number of clients
	 */
	public static int getNumberOfClients() {
		
		File inputFile = new File("input.txt");
		List<String> data = new LinkedList<String>();
		int numberOfClients = 0;
		
		try {
			Scanner scanner = new Scanner(inputFile);
			while(scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
			
			Collections.reverse(data);
			
			for(String line : data) {
				if(line.trim().isEmpty()) {
					break;
				}
				if(line.charAt(0) == '/' && line.charAt(1) == '/') {
					continue;
				}
				numberOfClients++;				
			}
			
			scanner.close();
			
			return numberOfClients;
			
		} catch (FileNotFoundException e) {
			System.out.println("Problem finding the configuration file!");
			e.printStackTrace();
		}
		
		return -1;
		
	}
	
	/**
	 * Checks if all the thread clients ended.
	 * @return true if all booleans are true; false otherwise
	 */
	public static boolean allClientsEnded() {
		
		try {
			int length = Singleton.getInstance().length;
			
			for(int i = 0; i < length; ++i) {
				if(!Singleton.getInstance()[i]){
					return false;
				}
			}
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}

	/**
	 * Returns number of offices from configuration file.
	 */
	private static int getNumberOfOffices(Scanner scanner) {
		String numberOfOffices = scanner.nextLine();
		return Integer.parseInt(numberOfOffices);
	}

	/**
	 * Initializes the offices, puts the documents every office can delivery in
	 * the specific office. Initializes the documents, puts the name of the
	 * document and fills the list of documents needed to obtain the final
	 * document. All documents are saved without quotes.
	 * 
	 * @param offices
	 *            list of offices to be populated
	 * @param documents
	 *            list of documents to be populated
	 * @throws FileNotFoundException
	 *             in case the configuration file does not exist
	 */
	public static void readConfigurationFile(List<Birou> offices, List<Document> documents, List<Client> clients)
			throws FileNotFoundException {

		// BufferedReader inputStream = new BufferedReader(new
		// InputStreamReader(new FileInputStream("input.txt")));
		File inputFile = new File("input.txt");
		Scanner scanner = new Scanner(inputFile);

		int numberOfOffices = getNumberOfOffices(scanner);
		List<Act> acte = new ArrayList<Act>();

		// initializing offices and assign them the documents they can delivery
		// + 1 for the comment line
		for (int i = 0; i < numberOfOffices + 1; ++i) {
			String nume = "Biroul " + i;
			String line = scanner.nextLine();
			// skipping the comment line
			if (line.charAt(0) == '/' && line.charAt(1) == '/') {
				continue;
			}
			String[] separator = line.split("\" ");
			// starting from 1 because index=0 is the no of counters
			fillFromSeparated(acte, separator, 1);
			int nrGhisee = Integer.parseInt(removeQuotes(separator[0]));
			offices.add(new Birou(nume, nrGhisee, acte));
			acte.clear();
		}

		// empty line
		scanner.nextLine();

		// initializing the documents
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.isEmpty()) {
				break;
			}
			// skipping the comment line
			if (line.charAt(0) == '/' && line.charAt(1) == '/') {
				continue;
			}
			String[] separated = line.split("\" ");
			fillFromSeparated(acte, separated, 1);
			documents.add(new Document(acte, removeQuotes(separated[0])));
			acte.clear();
		}

		int idCounter = 0;
		
		// reading the clients
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			// skipping the comment line
			if (line.charAt(0) == '/' && line.charAt(1) == '/') {
				continue;
			}
			String[] separated = line.split(" ", 2);
			clients.add(new Client(separated[0], idCounter++, new Act(removeQuotes(separated[1])), documents, offices));
		}

		scanner.close();

		printOffices(offices);
		printDocuments(documents);
		printClients(clients);

	}

	public static void printOffices(List<Birou> offices) {

		for (Birou office : offices) {
			System.out.println(
					office.getNume() + " cu " + office.getNrGhisee() + " ghisee " + "poate emite urmatoarele acte:");
			List<Act> acte = office.getGhisee().get(0).getActe();
			for (Act act : acte) {
				System.out.print(act.getNume() + ", ");
			}
			System.out.println();
		}

		System.out.println();
	}

	public static void printDocuments(List<Document> documents) {

		for (Document document : documents) {
			System.out.println("Pentru documentul " + document.getNume() + " avem nevoie de:");
			List<Act> acte = document.getActe();
			for (Act act : acte) {
				System.out.print(act.getNume() + ", ");
			}
			System.out.println();
		}

		System.out.println();
	}

	public static void printClients(List<Client> clients) {

		for (Client client : clients) {
			System.out.println("Clientul " + client.getNume() + " vrea " + client.getAct().getNume() + ".");
		}

		System.out.println();
	}
}
