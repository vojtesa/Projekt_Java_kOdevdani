package projektProgramko;
import java.text.Normalizer;

public class Telekomunikace extends Student{

	public Telekomunikace(Integer ID,String jmeno, String prijmeni, Integer rokNarozeni) {
		super(ID, jmeno, prijmeni, rokNarozeni);
		
	}

	public void dovednost() {
		char[] english = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
                'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                ',', '.', '?' };

      String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
              ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
              "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
              "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
              "-----", "--..--", ".-.-.-", "..--.." };
      String normalizerJmeno = Normalizer.normalize(getJmeno().toLowerCase(), Normalizer.Form.NFD) ;		
      String normalizerPrijmeni = Normalizer.normalize(getPrijmeni().toLowerCase(), Normalizer.Form.NFD); 	
      String jmeno = normalizerJmeno.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");  		
      String prijmeni = normalizerPrijmeni.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");  	
      String morseJmeno = "";
      String morsePrijmeni = "";
      char char_array [];
      for (int i = 0; i < jmeno.length(); i++) {	
    	  char_array= jmeno.toCharArray();
    	  for(int j = 0; j < english.length; j++) {
    		  if(char_array[i] == english[j]) {
    			  morseJmeno += morse[j] + " ";
    		  }
    		  else {
    			  continue;
    		  }
    	  }
      }
      for (int i = 0; i < prijmeni.length(); i++) {	
    	  char_array = prijmeni.toCharArray();
    	  for(int j = 0; j < english.length; j++) {
    		  if(char_array[i] == english[j]) {
    			  morsePrijmeni += morse[j] + " ";
    		  }
    		  else {
    			  continue;
    		  }
    	  }
      }
     System.out.print("Jmeno a prijmeni do morseovy abecedy: " + getJmeno() + " " + getPrijmeni() + 
    		 			" -> " + morseJmeno + "\t" + morsePrijmeni + "\n\n");
	}

}
