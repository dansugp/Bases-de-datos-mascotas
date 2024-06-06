package jdbc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmpDAO {

	private Connection conn;
	
	public EmpDAO() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:h2:~/test", "sa", "");
		} catch (SQLException e) {
			System.err.println("La conexión no se pudo establecer. "+e.getMessage());
		}
        
	}
	
	public EmpDAO(Connection conn) {
		this.conn = conn;       
	}
	
	public ArrayList<EmpDTO> listar(){
		ArrayList<EmpDTO> empleados = new ArrayList<EmpDTO>();
		Statement statementOb = null;
		
		try {
			statementOb = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT EMPNO, ENAME, DNAME, HIREDATE ");
			sb.append("FROM DEPT D, EMP E ");
			sb.append("WHERE D.DEPTNO = E.DEPTNO ");
			sb.append("ORDER BY ENAME");
			
	        ResultSet rs = statementOb.executeQuery(sb.toString());
	        
	        while (rs.next()) {
	        	EmpDTO dto = new EmpDTO();
	        	dto.setNo(rs.getString("EMPNO"));
	        	dto.setName(rs.getString("ENAME"));
	        	dto.setHiredate(rs.getString("HIREDATE"));
	        	dto.setDeptname(rs.getString("DNAME"));
	        	
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
	
	public boolean agregar(String nombre, String idDepto, String fechaContrata) {
		boolean exito = false;
		
		try {
			
			Statement statementOb = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO EMP(ENAME, DEPTNO, HIREDATE) ");
			sb.append("VALUES ('"+nombre+"', "+idDepto+", DATE('"+fechaContrata+"'))");
			
			statementOb.executeUpdate(sb.toString());
			
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
	
}
