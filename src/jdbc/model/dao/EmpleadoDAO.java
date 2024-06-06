package jdbc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmpleadoDAO {

	private Connection conn;
	
	public EmpleadoDAO() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:h2:~/test", "sa", "");
		} catch (SQLException e) {
			System.err.println("La conexión no se pudo establecer. "+e.getMessage());
		}
        
	}
	
	public EmpleadoDAO(Connection conn) {
		this.conn = conn;       
	}
	
	public ArrayList<EmpleadoDTO> listar(){
		ArrayList<EmpleadoDTO> empleados = new ArrayList<EmpleadoDTO>();
		Statement statementOb = null;
		
		try {
			statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT * FROM EMPLEADO");
	        
	        while (rs.next()) {
	        	EmpleadoDTO dto = new EmpleadoDTO();
	        	dto.setId(rs.getInt("ID"));
	        	dto.setNombre(rs.getString("NOMBRE"));
	        	dto.setEdad(rs.getInt("EDAD"));
	        	dto.setSalario(rs.getDouble("SALARIO"));
	        	
	        	empleados.add(dto);
	        	
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
		
		return empleados;
	}
	
	public boolean agregar(String id, String nombre, String edad, String salario) {
		boolean exito = false;
		
		try {
			
			Statement statementOb = conn.createStatement();
			
			String sqlString = "INSERT INTO EMPLEADO (ID, NOMBRE, EDAD, SALARIO) VALUES "
	                + "("+id+", '"+nombre+"', "+edad+", "+salario+")";
			
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
	 * Se elimina un empleado por el número de identificación
	 * @param id
	 * @return
	 */
	public boolean eliminar(String id) {
		boolean exito = false;
		
		try {
			
			Statement statementOb = conn.createStatement();
			statementOb.executeUpdate("DELETE FROM EMPLEADO WHERE ID="+id);
			
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

	public EmpleadoDTO buscar(String id) {
		EmpleadoDTO dto = null;
		
		try {
			Statement statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT * FROM EMPLEADO WHERE ID = "+id);
	        
	        if(rs.next()) {
	        	dto = new EmpleadoDTO();
	        	dto.setId(rs.getInt("ID"));
	        	dto.setNombre(rs.getString("NOMBRE"));
	        	dto.setEdad(rs.getInt("EDAD"));
	        	dto.setSalario(rs.getDouble("SALARIO"));	        	
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
	
	public boolean actualizar(String id, String nombre, String edad, String salario) {
		boolean exito = false;
		StringBuffer sb = null;
		try {
			
			Statement statementOb = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append("UPDATE EMPLEADO ");
			sb.append("SET NOMBRE = '"+nombre+"', EDAD = "+edad+", SALARIO = "+salario+" ");
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








