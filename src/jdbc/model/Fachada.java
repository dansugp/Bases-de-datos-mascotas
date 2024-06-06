package jdbc.model;

import java.util.ArrayList;

import jdbc.model.dao.DeptDAO;
import jdbc.model.dao.DeptDTO;
import jdbc.model.dao.EmpDAO;
import jdbc.model.dao.EmpDTO;
import jdbc.model.dao.EmpleadoDAO;
import jdbc.model.dao.EmpleadoDTO;

public class Fachada {

	public ArrayList<EmpleadoDTO> listarEmpleados(){
		
		EmpleadoDAO dao = new EmpleadoDAO();
		ArrayList<EmpleadoDTO> empleados = dao.listar();
		return empleados;
		
	}
	
	public boolean eliminarEmpleado(String id) {
		boolean exito = false;
		EmpleadoDAO dao = new EmpleadoDAO();
		exito = dao.eliminar(id);
		
		return exito;
	}

	public boolean agregarEmpleado(String id, String nombre, 
			String edad, String salario) {
		
		boolean exito = false;
		EmpleadoDAO dao = new EmpleadoDAO();
		exito = dao.agregar(id, nombre, edad, salario);
		
		return exito;
	}

	public EmpleadoDTO obtenerEmpleado(String id) {
		
		EmpleadoDAO dao = new EmpleadoDAO();
		EmpleadoDTO dto = dao.buscar(id);
		
		return dto;
	}
	
	public boolean actualizarEmpleado(String id, String nombre, 
			String edad, String salario) {
		
		boolean exito = false;
		EmpleadoDAO dao = new EmpleadoDAO();
		exito = dao.actualizar(id, nombre, edad, salario);
		
		return exito;
		
	}
	
	public ArrayList<DeptDTO> listarDepartments(){
		
		DeptDAO dao = new DeptDAO();
		ArrayList<DeptDTO> depts = dao.listar();
		return depts;
		
	}

	public ArrayList<EmpDTO> listarEmployees() {
		EmpDAO dao = new EmpDAO();
		
		ArrayList<EmpDTO> depts = dao.listar();
		return depts;
	}
	
	public boolean agregarEmp(String nombre, String fechaContrata, String nombreDepto) {
		boolean exito = false;
		
		// 1. Obtener el ID del departamento
		DeptDAO dDao = new DeptDAO();
		String idDepto = dDao.obtenerIDDept(nombreDepto);
		
		// 2. Guardar al Empleado con el ID 
		if(!idDepto.isBlank()) {
			EmpDAO eDao = new EmpDAO();
			exito = eDao.agregar(nombre, idDepto, fechaContrata);
		}
		
		return exito;
	}
	
	
	
}
