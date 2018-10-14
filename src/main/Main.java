package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import POJO.Birou;
import POJO.Document;

public class Main {

	public static void main(String[] args) {
		
		List<Birou> offices = new ArrayList<Birou>();
		List<Document> documents = new ArrayList<Document>();
		
		try {
			BestUtilityEVA.readConfigurationFile(offices, documents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The configuration file wasn't found!");
		}
		
	}
}
