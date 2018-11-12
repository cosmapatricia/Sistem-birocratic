package main;

public class Singleton {

	private static boolean[] checks = null;
	private static int capacity;
	
	// also this code can be called in a load() method before any other threads are started
	static {
		capacity = BestUtilityEVA.getNumberOfClients();
		checks = new boolean[capacity];
		for(int i = 0; i < capacity; ++i) {
			checks[i] = false;
		}
	}
	
	public static synchronized boolean[] getInstance() {
		if(checks == null) {
			throw new IllegalStateException("checks were not initialized");
		}
		return checks;
	}
	
}
