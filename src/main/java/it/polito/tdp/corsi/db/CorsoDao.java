package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDao {

	public List<Corso> getCorsiByPeriodo(int periodo) {
		
		String sql = "SELECT * FROM corso WHERE pd = ?";
		List<Corso> result = new ArrayList<Corso>();
	
		try {
			Connection conn = DbConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
						
			while(rs.next()) {
				String codins = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int pd = rs.getInt("pd");
				
				result.add(new Corso(codins, crediti, nome, pd));
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
	
	public Map<Corso,Integer> getIscritti(int periodo) {
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd, COUNT(*) AS n "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins AND c.pd = ? "
				+ "GROUP BY c.codins, c.crediti, c.nome, c.pd";

		Map<Corso, Integer> result = new HashMap<Corso,Integer>();
	
		try {
			Connection conn = DbConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();
						
			while(rs.next()) {
				String codins = rs.getString("codins");
				int crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int pd = rs.getInt("pd");
				int iscritti = rs.getInt("n");
				result.put(new Corso(codins, crediti, nome, pd), iscritti);
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
