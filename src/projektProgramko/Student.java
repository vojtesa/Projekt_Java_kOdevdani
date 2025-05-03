package projektProgramko;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {
	private String jmeno;
	private String prijmeni;
	private Integer rokNarozeni;
	private Integer ID;
	private String skupina; //1 - telekomunikace, 2 - kyberbezpecnost
	private List<Double> znamky = new ArrayList<Double>();
	
	
	public Student(Integer ID, String jmeno, String prijmeni, Integer rokNarozeni) {
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.rokNarozeni = rokNarozeni;
		this.ID = ID;
	}
	
	
	public void setZnamka(double znamka) {
		if(znamka >= 1 && znamka <= 5) {
			znamky.add(znamka);
		}
		else {
			System.out.println("Zadali jste cislo mimo rozsah 1 az 5");
		}
	} 
	
	public String vypisZnamek() {
		StringBuilder vystup = new StringBuilder();
		for (Double jednaZnamka:znamky) {
			vystup.append(jednaZnamka).append( ", ");
		}
		return vystup.toString();
	}
	
	public Double getPrumer() {
		Integer counter = 0;
		Double soucet = 0d;
		for(Double znamka:znamky) {
			soucet += znamka;
			counter++;
		}
		return (soucet/counter);
	}
	
	
	
	public abstract void dovednost();

	
	public void setID(Integer IDcouter) {
		this.ID = IDcouter;
	}
	
	public Integer getID() {
		return ID;
	}
	
	public String getJmeno() {
		return jmeno;
	}
	public String getPrijmeni() {
		return prijmeni;
	}
	
	public Integer getRokNarozeni() {
		return rokNarozeni;
	}


	public String getSkupina() {
		return skupina;
	}
	
	public Integer getSkupinaInteger() {
		if(skupina == "1 - Telekomunikace") {
			return 1;
		}
		else if(skupina == "2 - Kyberbezpecnost") {
			return 2;
		}
		return null;
	}

	public void setSkupina(String skupina) {
		this.skupina = skupina;
	}
	
	public Integer getZnamkyPocet() {
		return znamky.size();
	}
	
	public Double vypisZnamkaPoZnamce(Integer j) {
		return znamky.get(j);
	}
	
	public Double soucetZnamek() {
		Double soucet = 0d;
		for(Double znamka:znamky) {
			soucet += znamka;
		}
		return soucet;
	}
	
}
