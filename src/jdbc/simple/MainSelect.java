package jdbc.simple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainSelect {

//	public static void main1(String[] args) throws Exception {
//		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//		DriverManager.getConnection("jdbc:derby://localhost:1527/poo2_local", "poo2", "poo2");
//		
//	}
	
	public static void main(String[] args) throws Exception {
        System.out.println("***Demonstration-1.Connecting to the Derby DB server.***");
        
        Connection connectionOb = null;
        try {
        	// ****************************************
            // Lista los registros
            connectionOb = DriverManager.getConnection(
            		"jdbc:derby://localhost:1527/poo2_local", "poo2", "poo2");
            
            Statement statementOb = connectionOb.createStatement();
            ResultSet queryResult = statementOb.executeQuery("SELECT * FROM MASCOTA");
            
            
            System.out.println(" ID\t" + "NOMBRE\t\t" + "EDAD\t" + "SALARIO\t" + "ESPECIE\t" + "DUENIO\t");
            System.out.println("------------------------------------------");
            
            
            while (queryResult.next()) {
                System.out.print(queryResult.getString("ID") + "\t" + 
                        queryResult.getString("NOMBRE") + "\t" + 
                        queryResult.getInt("EDAD") + "\t" + 
                        queryResult.getString("ESPECIE") + "\t" + 
                        queryResult.getString("DUENIO") + "\t");
                System.out.println();
            }
            
            // ****************************************
            // Insertando 1 registro
            System.out.println("Insertando un registro");
            statementOb.executeUpdate("INSERT INTO EMPLEADO (ID, NOMBRE, EDAD, SALARIO) VALUES "
                    + "(9453, 'CAMI CASTELL', 43, 2500000)");
            
            
            // ****************************************
            // Lista los registros
            statementOb = connectionOb.createStatement();
            queryResult = statementOb.executeQuery("SELECT * FROM EMPLEADO");
            
            
            System.out.println(" ID\t" + "NOMBRE\t\t" + "EDAD\t" + "SALARIO");
            System.out.println("------------------------------------------");
            while (queryResult.next()) {
                System.out.print(queryResult.getString("ID") + "\t" + 
                        queryResult.getString("NOMBRE") + "\t" + 
                        queryResult.getInt("EDAD") + "\t" + 
                        queryResult.getDouble("SALARIO"));
                System.out.println();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } // To catch any other exception
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Close the connection
            if (connectionOb != null) {
                connectionOb.close();
            }
        }
    }

}
