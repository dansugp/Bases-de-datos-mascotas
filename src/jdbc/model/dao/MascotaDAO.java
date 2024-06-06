package jdbc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MascotaDAO {

	private Connection conn;
	
	public MascotaDAO() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:h2:~/test", "sa", "");
		} catch (SQLException e) {
			System.err.println("La conexión no se pudo establecer. "+e.getMessage());
		}
        
	}
	
	public MascotaDAO(Connection conn) {
		this.conn = conn;       
	}
	
	public ArrayList<MascotaDTO> listar(){
		ArrayList<MascotaDTO> mascotas = new ArrayList<MascotaDTO>();
		Statement statementOb = null;
		
		try {
			statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT * FROM MASCOTA");
	        
	        while (rs.next()) {
	        	MascotaDTO dto = new MascotaDTO();
	        	dto.setId(rs.getInt("ID"));
	        	dto.setNombre(rs.getString("NOMBRE"));
	        	dto.setEdad(rs.getInt("EDAD"));
	        	dto.setEspecie(rs.getString("ESPECIE"));
	        	dto.setDuenio(rs.getString("DUENIO"));
	        	mascotas.add(dto);
	        	
	        }
        
		}catch(Exception e) {
			System.err.println("Se presentó un error ejecutando la consulta. "+e.getMessage());
		}finally {
			// Close the connection            
            try {
            	statementOb.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
            
		}
		
		return mascotas;
	}
	
	public boolean agregar(String id, String nombre, String edad, String especie, String duenio) {
		boolean exito = false;
		
		try {
			
			Statement statementOb = conn.createStatement();
			
			String sqlString = "INSERT INTO MASCOTA (ID, NOMBRE, EDAD, ESPECIE, DUENIO) VALUES "
	                + "("+id+", '"+nombre+"', "+edad+", '"+especie+ "', '"+duenio+"')";
			
			statementOb.executeUpdate(sqlString);
			
			exito = true;
			
		}catch(Exception e) {
			System.err.println("Ocurrió un error insertando el registro. "+e.getMessage());
		}finally {
			// Close the connection            
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
		}
		
		return exito;
	}
	
	/**
	 * Se elimina una mascota por el número de identificación
	 * @param id
	 * @return
	 */
	public boolean eliminar(String id) {
		boolean exito = false;
		
		try {
			
			Statement statementOb = conn.createStatement();
			statementOb.executeUpdate("DELETE FROM MASCOTA WHERE ID="+id);
			
			System.out.println("Número registros afectados = "+statementOb.getUpdateCount());
			
			exito = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// Close the connection            
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
		}
		
		return exito;
	}

	public MascotaDTO buscar(String id) {
		MascotaDTO dto = null;
		
		try {
			Statement statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT * FROM MASCOTA WHERE ID = "+id);
	        
	        if(rs.next()) {
	        	dto = new MascotaDTO();
	        	dto.setId(rs.getInt("ID"));
	        	dto.setNombre(rs.getString("NOMBRE"));
	        	dto.setEdad(rs.getInt("EDAD"));
	        	dto.setEspecie(rs.getString("ESPECIE"));
	        	dto.setDuenio(rs.getString("DUENIO"));
	        		        	
	        }
        
		}catch(Exception e) {
			System.err.println("Se presentó un error ejecutando la consulta. "+e.getMessage());
		}finally {
			// Close the connection            
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
		}
		
		return dto;
	}
	
	public boolean actualizar(String id, String nombre, String edad, String especie, String duenio) {
		boolean exito = false;
		StringBuffer sb = null;
		try {
			
			Statement statementOb = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("UPDATE MASCOTA ");
			sb.append("SET NOMBRE = '"+nombre+"', EDAD = "+edad+", ESPECIE = '"+especie+ "', DUENIO = '"+duenio+"'");
			sb.append("WHERE ID = "+id);
			
			statementOb.executeUpdate(sb.toString());
			
			System.out.println("Número registros afectados = "+statementOb.getUpdateCount());
			
			exito = true;
			
		}catch(Exception e) {
			System.out.println(sb);
			e.printStackTrace();
		}finally {
			// Close the connection            
            try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
		}
		
		return exito;
	}
	
}








