package POJO;

public class Act {

	private String nume;
	private int luat = 0;
	
	public Act (String nume) {
		this.nume = nume;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}
	
	public int getLuat() {
		return luat;
	}
	
	public void setLuat(int luat) {
		this.luat = luat;
	}
}
