package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDao {
	
	public List<Corso> getCorsiByPeriodo(int periodo){
		String sql= "SELECT * "	//***mettere spazio alla fine***
				+ "FROM corso " //***mettere spazio alla fine***
				+ "WHERE pd = ?";
		
		List <Corso> result=new ArrayList <Corso>();
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, periodo ); //posizione del parametro(parte da 1)
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),
						rs.getString("nome"),rs.getInt("pd")); //tra "" specifico nome colonna
				result.add(c);	
			}
			
			st.close();
			rs.close();
			conn.close();
			
			return result;
			
			
		}catch(SQLException e) {
			System.err.println("errore nel dao");
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public Map<Corso,Integer> getIscritti(int periodo){
		String sql="SELECT c.codins, c.crediti, c.nome, c.pd, COUNT(*) AS n "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND c.pd=? "
				+ "GROUP BY c.codins, c.crediti, c.nome, c.pd";
		
		Map<Corso,Integer> result= new HashMap <Corso,Integer>();
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, periodo ); //posizione del parametro(parte da 1)
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),
						rs.getString("nome"),rs.getInt("pd")); //tra "" specifico nome colonna
				result.put(c, rs.getInt("n"));
			}
			
			st.close();
			rs.close();
			conn.close();
			
			return result;
			
		}catch (SQLException e){
			System.err.println("errore nel dao");
			e.printStackTrace();
			return null;
		}
	}
}
