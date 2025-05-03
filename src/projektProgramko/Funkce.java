package projektProgramko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Funkce {
	
	public static void vypisPrumeruStudentu(Integer cislo) {
		Double soucetVsechZnamek = 0d;
		Integer pocetVsechZnamek = 0;
		Student student;
		Class<?> skupina;
		switch(cislo) {		//nastavi se pozadovana skupina, kterou chceme vypsat
		case 1:
			skupina = Telekomunikace.class;
			System.out.println("Prumer studentu ze skupiny Telekomunikace: ");
			break;
		case 2:
			skupina = Kyberbezpecnost.class;
			System.out.println("Prumer studentu ze skupiny Kyberbezpecnost: ");
			break;
		case 3:
			skupina = Student.class;
			System.out.println("Prumer vsech studentu: ");
			break;
		default:
			System.out.println("Cislo je mimo povoleny rozsah");
			return;
		}
		
		for(int i = 0; i < Databaze.ID; i++) {
			student = Databaze.databaze.get(i);
			if (skupina.isInstance(student)) {
				if(Double.isNaN(student.getPrumer())) {
					continue;
				}
				soucetVsechZnamek += student.soucetZnamek();
				pocetVsechZnamek += student.getZnamkyPocet();
				
				
			}
			else {
				continue;
			}
		}
		Double vyslednyPrumer = soucetVsechZnamek / pocetVsechZnamek;
		if(Double.isNaN(vyslednyPrumer)) {
			System.out.println("Ve skupine neni zadny student, nebo zadny student jeste nema znamku");
		}
		else{
			System.out.println(vyslednyPrumer);
		}
		
	}
	
	
	
	public static void vypisStudentu(Integer cislo) {
		List<Student> sortedStudentsbysurname = new ArrayList<Student>();
		Student student;
		Integer nTyStudent = 1;
		Class<?> skupina;
		switch(cislo) {		//nastavi se pozadovana skupina, kterou chceme vypsat
			case 1:
				skupina = Telekomunikace.class;
				break;
			case 2:
				skupina = Kyberbezpecnost.class;;
				break;
			case 3:
				skupina = Student.class;;
				break;
			default:
				System.out.println("Cislo je mimo povoleny rozsah");
				return;
		}

		for(int i = 0; i < Databaze.ID; i++) { //naplneni seznamu sortedStudentsbysurname
			student = Databaze.databaze.get(i);
			if(student != null) {
				sortedStudentsbysurname.add(student);				
			}
			else {
				continue;
			}
		}
		Locale czLocale = Locale.of("cs", "CZ");	
		Collator czCollator = Collator.getInstance(czLocale);	
		czCollator.setStrength(Collator.PRIMARY);	
		sortedStudentsbysurname.sort(Comparator.comparing(Student::getPrijmeni, czCollator)); 
		
		for(int i = 0; i < sortedStudentsbysurname.size(); i++) { 
			student = sortedStudentsbysurname.get(i);
			
				if(skupina.isInstance(student)) {
					//porovna, zda je student z prave zvolene tridy
					System.out.println( "\n" + nTyStudent + ".Student " + student.getPrijmeni() + ":");
					System.out.println(vypisInfoOStudentovi(student));
					nTyStudent++;
				}
				else {
					continue;
				}	
			}
			
			
		
	}
	
	
	
	public static void pocetStudentuVeSkupine() {
		Student student;
		Integer counterTelekomunikace = 0;
		Integer counterKyberbezpecnost = 0;
		for(int i = 0; i < Databaze.ID; i++) {
			student = Databaze.databaze.get(i);
			if (student instanceof Telekomunikace) {
				counterTelekomunikace++;
			}
			else if (student instanceof Kyberbezpecnost){
				counterKyberbezpecnost++;
			}
		}
		System.out.println("Ve skupine Telekomunikace je studentu: " + counterTelekomunikace);
		System.out.println("Ve skupine Kyberbezpecnost je studentu: " + counterKyberbezpecnost);
		System.out.println("Celkove je studentu: " + (counterTelekomunikace + counterKyberbezpecnost) + "\n");
	}


	
	public static void ulozeniStudentaDoSouboru(Integer ID) {
		
		FileWriter fw = null;
		try {
			String nazevSouboru = "ID_" + ID + ".txt";
			fw = new FileWriter(nazevSouboru);
			String vypis = vypisInfoOStudentovi(ID);
			fw.write(vypis);
			
			System.out.println("Student byl uspesne ulozen do souboru " + nazevSouboru);
		} catch (IOException e) {
			System.out.println("Student nelze ulozit: " + Vyjimky.doesStudentExist(ID));
		}
		finally {
			try {
				fw.close();
			} catch (IOException e) {		
				System.out.print("Soubor nejde zavrit: ");
				e.printStackTrace();
			}
		}
	}

	
	
	public static String nacteniStudentaZeSouboru(Integer ID) {
		
		String nazevSouboru = "ID_" + ID + ".txt";
		String textovyVystup = "";		
		
		FileReader fr = null;
		BufferedReader in = null;
		try {
			 fr = new FileReader(nazevSouboru);
			 in = new BufferedReader(fr);
			 String radek;
			 while((radek = in.readLine()) != null) {
				 textovyVystup += radek + "\n";
			 }
			 return textovyVystup;
			
		} catch (Exception e) {
			return "Soubor nelze precist, nebo neexistuje: " + e.getMessage();
		}
		finally {
			try {
				fr.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}

	
	
	public static String vypisInfoOStudentovi(Object klicNeboObjekt) {
	    StringBuilder vystup = new StringBuilder();

	    if (klicNeboObjekt instanceof Integer) {
	        Integer ID = (Integer) klicNeboObjekt;
	        Student student = Databaze.databaze.get(ID);

	        

	        vystup.append("\nID: ").append(student.getID());
	        vystup.append("\nSkupina: ").append(student.getSkupina());
	        vystup.append("\nJméno: ").append(student.getJmeno());
	        vystup.append("\nPříjmení: ").append(student.getPrijmeni());
	        vystup.append("\nRok narození: ").append(student.getRokNarozeni());
	        vystup.append("\nZnámky: ").append(student.vypisZnamek());
	        vystup.append("      Průměr: ").append(student.getPrumer()).append("\n");
	    } 
	    else if (klicNeboObjekt instanceof Student student) {
	        vystup.append("\nID: ").append(student.getID());
	        vystup.append("\nSkupina: ").append(student.getSkupina());
	        vystup.append("\nJméno: ").append(student.getJmeno());
	        vystup.append("\nPříjmení: ").append(student.getPrijmeni());
	        vystup.append("\nRok narození: ").append(student.getRokNarozeni());
	        vystup.append("\nZnámky: ").append(student.vypisZnamek());
	        vystup.append("      Průměr: ").append(student.getPrumer()).append("\n");
	    } 
	    else {
	        return "Do metody byl zadán parametr, se kterým metoda nedokáže pracovat.";
	    }
	    return vystup.toString();
	}
	
	
	public static void vytvoreniStudenta(Integer ID,String jmeno, String prijmeni, Integer rokNarozeni, Integer skupina) {
		
		if (skupina == 1) {
			Student novyStudent = new Telekomunikace(ID, jmeno, prijmeni, rokNarozeni);
			Databaze.addStudent(novyStudent);
			novyStudent.setSkupina("1 - Telekomunikace");
		}
		else if (skupina == 2) {
			Student novyStudent = new Kyberbezpecnost(ID ,jmeno, prijmeni, rokNarozeni);
			Databaze.addStudent(novyStudent);
			novyStudent.setSkupina("2 - Kyberbezpecnost"); 
		}
		else {
			System.out.println("Zadali jste cislo mimo rozsah");
		}
		
	}
	
	
}

