package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDao {
	
	
	public List<Studente> getStudentiByCorso(String codins) {
		
		String sql = "SELECT s.matricola, s.cognome,s.nome, s.CDS "
				+ "FROM iscrizione i, studente s "
				+ "WHERE s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> result = new ArrayList<Studente>();
	
		try {
			Connection conn = DbConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
						
			while(rs.next()) {
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");
				
				result.add(new Studente(matricola, cognome, nome, cds));
			}
			
			st.close();
			conn.close();
			rs.close();
			return result;
			
		} catch (SQLException e) {
			System.err.println("Errore nel DAO corso");
			e.printStackTrace();
			return null;
		}
	
	}
	
	public List<Divisione> getDivisioneStudenti(String codins) {
		
		String sql = "SELECT s.CDS, COUNT(*) AS n "
				+ "FROM iscrizione i, studente s "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.cds<>'' "
				+ "GROUP BY s.CDS";
		
		List<Divisione> result = new ArrayList<Divisione>();
	
		try {
			Connection conn = DbConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
						
			while(rs.next()) {
				int n = rs.getInt("n");
				String cds = rs.getString("CDS");
				
				result.add(new Divisione(cds,n));
			}
			
			st.close();
			conn.close();
			rs.close();
			return result;
			
		} catch (SQLException e) {
			System.err.println("Errore nel DAO corso");
			e.printStackTrace();
			return null;
		}
	
	}
}
