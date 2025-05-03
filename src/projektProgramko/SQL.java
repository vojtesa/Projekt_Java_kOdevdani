package projektProgramko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement ppstmt;
	private static PreparedStatement ppstmt2;
	private static ResultSet studentiRS;
	private static ResultSet znamkyRS;
	private static ResultSet nejvyssiIDpouziteRS; 
	
	public static boolean connect() {	
		conn = null;					
		try {
			
			conn = DriverManager.getConnection("jdbc:sqlite:databaze_studentu.db");
			vlozit_strukturu();
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage() + "chyba");
			return false;
		}
		
	}
	
	
	
	public static void disconnect() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e){
				System.out.println(e.getMessage());				
			}
		}
	}
	
	
	
	public static boolean vlozit_strukturu() {	
		try {
			stmt = conn.createStatement();
			String[] sqlSoubory= { "src/projektProgramko/studenti.sql", 
					"src/projektProgramko/znamky.sql",
					"src/projektProgramko/nejvyssi_pridelene_ID.sql"};	
			String strukturaJednohoSouboru = "";
			FileReader fr = null;
			BufferedReader in = null;
			try {
				for(String a:sqlSoubory) {	
					fr = new FileReader(a);
					in = new BufferedReader(fr);
					String obsah;
					while((obsah = in.readLine()) != null) {
						strukturaJednohoSouboru += obsah + "\n";					
					}
					stmt.executeUpdate(strukturaJednohoSouboru);
					strukturaJednohoSouboru = "";
				}
				 return true;
				
			} catch (Exception e) {
				System.out.println("Chyba pri nacitani souboru: " + e.getMessage());
				return false;
			}
			finally {
				try {
					fr.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	
	public static void naplneniSQLdatabaze() throws SQLException {
		String vymazaniDatabaze = "DELETE FROM studenti";
		ppstmt = conn.prepareStatement(vymazaniDatabaze);
		ppstmt.executeUpdate();
		ppstmt.close();
		String vymazaniZnamek = "DELETE FROM znamky";
		ppstmt = conn.prepareStatement(vymazaniZnamek);
		ppstmt.executeUpdate();
		ppstmt.close();
		
		
		for (int i = 0; i < Databaze.ID; i++) {
			Student student = Databaze.databaze.get(i);
			if (student == null) {
				continue;
			}
			String studentiChart = "INSERT INTO studenti (ID, jmeno, prijmeni, rokNarozeni, skupina) VALUES (?,?,?,?,?)";
			String znamkyChart = "INSERT INTO znamky (znamka, student_id) VALUES (?,?)";
			
			ppstmt = conn.prepareStatement(studentiChart);
			ppstmt.setInt(1, student.getID());
			ppstmt.setString(2, student.getJmeno());
			ppstmt.setString(3, student.getPrijmeni());
			ppstmt.setInt(4, student.getRokNarozeni());
			ppstmt.setInt(5, student.getSkupinaInteger());
			ppstmt.executeUpdate();
			
			ppstmt = conn.prepareStatement(znamkyChart);
			for(int j = 0; j < student.getZnamkyPocet(); j++) {
				ppstmt.setDouble(1, student.vypisZnamkaPoZnamce(j));
				ppstmt.setInt(2, student.getID());	
				ppstmt.executeUpdate();
			}
			ppstmt.close();
			
			String ulozeni_nejvyssi_ID = "INSERT OR REPLACE INTO nejvyssi_pridelene_ID (ID, MAX_used_ID) VALUES (1, ?)";
			ppstmt = conn.prepareStatement(ulozeni_nejvyssi_ID);
			ppstmt.setInt(1, Databaze.ID);
			ppstmt.executeUpdate();
			ppstmt.close();
			
				
			
		}
	}
	
	
	public static void nacteniSQLdatabazeDoProgramu() {
		
		
		String studentiQuery = "SELECT * FROM studenti";
		String znamkyQuery = "SELECT * FROM znamky WHERE student_id = ?";
		String nejvyssiVytvoreneID = "SELECT MAX_used_ID FROM nejvyssi_pridelene_ID WHERE ID = 1";
		

		try {
			ppstmt = conn.prepareStatement(studentiQuery);
			ppstmt2 = conn.prepareStatement(znamkyQuery);
			
			studentiRS = ppstmt.executeQuery();
			
			while (studentiRS.next()) {
				
				Funkce.vytvoreniStudenta(studentiRS.getInt("ID"), studentiRS.getString("jmeno"), studentiRS.getString("prijmeni"), 
						studentiRS.getInt("rokNarozeni"), studentiRS.getInt("skupina"));
				
				ppstmt2.setInt(1, (studentiRS.getInt("ID")));
				znamkyRS = ppstmt2.executeQuery();
				Student noveVytvorenyStudent = Databaze.databaze.get(studentiRS.getInt("ID"));
				while(znamkyRS.next()) {
					noveVytvorenyStudent.setZnamka(znamkyRS.getDouble("znamka"));
				}
				znamkyRS.close();
						
			}
			studentiRS.close();
			ppstmt.close();
			ppstmt2.close();
					
			
			ppstmt = conn.prepareStatement(nejvyssiVytvoreneID);
			nejvyssiIDpouziteRS = ppstmt.executeQuery();
			
			if(nejvyssiIDpouziteRS.next()) {	
				Databaze.ID = nejvyssiIDpouziteRS.getInt("MAX_used_ID");
			}
			
			ppstmt.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage() + "\tChyba u metody nacteniSQLdatabazeDoProgramu" + "\tDatabaze jeste nebyla vytvorena");
		}
	}
}
