package projektProgramko;

import java.util.HashMap;
import java.util.Map;

public class Databaze {
	public static Integer ID = 0;
	
	public static Map<Integer, Student> databaze = new HashMap<Integer, Student>();
	
	public static void addStudent(Student studentKzarazeni) {
			try {
				if(studentKzarazeni.getID() == null) {
					databaze.put(ID, studentKzarazeni);
					studentKzarazeni.setID(ID);
					ID++;
				}
				else {
					databaze.put(studentKzarazeni.getID(), studentKzarazeni);
				}
			}
			catch(Exception e) {
				System.out.println("Student nebyl pridan do databaze, chyba: " + e.getMessage());
			}
			
	}
	
	
}
