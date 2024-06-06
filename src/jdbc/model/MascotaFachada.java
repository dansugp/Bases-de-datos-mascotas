package jdbc.model;

import java.util.ArrayList;

import jdbc.model.dao.DeptDAO;
import jdbc.model.dao.DeptDTO;
import jdbc.model.dao.EmpDAO;
import jdbc.model.dao.EmpDTO;
import jdbc.model.dao.MascotaDAO;
import jdbc.model.dao.MascotaDTO;

public class MascotaFachada {

	public ArrayList<MascotaDTO> listarMascotas(){
		
		MascotaDAO dao = new MascotaDAO();
		ArrayList<MascotaDTO> mascotas = dao.listar();
		return mascotas;
		
	}
	
	public boolean eliminarMascota(String id) {
		boolean exito = false;
		MascotaDAO dao = new MascotaDAO();
		exito = dao.eliminar(id);
		
		return exito;
	}

	public boolean agregarMascota(String id, String nombre, 
			String edad, String especie, String duenio) {
		
		boolean exito = false;
		MascotaDAO dao = new MascotaDAO();
		exito = dao.agregar(id, nombre, edad, especie, duenio);
		
		return exito;
	}

	public MascotaDTO obtenerMascota(String id) {
		
		MascotaDAO dao = new MascotaDAO();
		MascotaDTO dto = dao.buscar(id);
		
		return dto;
	}
	
	public boolean actualizarMascota(String id, String nombre, 
			String edad, String especie, String duenio) {
		
		boolean exito = false;
		MascotaDAO dao = new MascotaDAO();
		exito = dao.actualizar(id, nombre, edad, especie, duenio);
		
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
	
	public boolean agregarMascota(String nombre, String fechaContrata, String nombreDepto) {
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
