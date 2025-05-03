package projektProgramko;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Kyberbezpecnost extends Student{

	public Kyberbezpecnost(Integer ID,String jmeno, String prijmeni, Integer rokNarozeni) {
		super(ID, jmeno, prijmeni, rokNarozeni);
	}

	@Override
	public void dovednost() {
		String jmeno = getJmeno() + getPrijmeni();
		String hashString = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte hash [] = md.digest(jmeno.getBytes());
			
			for(byte osmice:hash) {
				hashString += String.format("%02x", osmice);	
			}
			System.out.print("Jmeno a prijmeni do hashe s pouzitim SHA-256 (v hexadecimalni podobe): " + getJmeno() + " " + getPrijmeni() + 
		 			" -> " + hashString + "\t" + "\n\n");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		

	}

}
