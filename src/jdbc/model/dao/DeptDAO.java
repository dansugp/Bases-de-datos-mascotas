package jdbc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeptDAO {

	private Connection conn;
	
	public DeptDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		} catch (SQLException e) {
			System.err.println("La conexión no se pudo establecer. "+e.getMessage());
		}
        
	}
	
	public DeptDAO(Connection conn) {
		this.conn = conn;       
	}
	
	public ArrayList<DeptDTO> listar(){
		ArrayList<DeptDTO> empleados = new ArrayList<DeptDTO>();
		Statement statementOb = null;
		
		try {
			statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT * FROM DEPT");
	        
	        while (rs.next()) {
	        	DeptDTO dto = new DeptDTO();
	        	dto.setId(rs.getString("DEPTNO"));
	        	dto.setName(rs.getString("DNAME"));
	        	dto.setLoc(rs.getString("LOC"));
	        	
	        	
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
	
	public String obtenerIDDept(String nombre){
		String id = "";
		Statement statementOb = null;
		
		try {
			statementOb = conn.createStatement();
	        ResultSet rs = statementOb.executeQuery("SELECT DEPTNO FROM DEPT WHERE DNAME = '"+nombre+"' ");
	        
	        if(rs.next()) {
	        	id = rs.getString("DEPTNO");	        	
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
		
		return id;
	}
	
}
