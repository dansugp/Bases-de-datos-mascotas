package jdbc.simple.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdbc.model.Fachada;
import jdbc.model.dao.EmpleadoDTO;

public class EmpleadoController implements Initializable {

	private Fachada fac;
	private ObservableList<String> listaEmpleados;
	
	@FXML
	private ListView<String> lvEmpleados;
	@FXML
	private TextField txtID, txtNombre, txtEdad, txtSalario;
	@FXML
	private TextField txtIDA, txtNombreA, txtEdadA, txtSalarioA;
	@FXML
	private Button btnGuardar;
	
	public EmpleadoController() {
		fac = new Fachada();
		listaEmpleados = FXCollections.observableArrayList();
	}
	
	@FXML
	public void handleListarEmpleadosBtn(ActionEvent event) {
		listaEmpleados.clear();
		
		ArrayList<EmpleadoDTO> empleados = fac.listarEmpleados();
		
		for(EmpleadoDTO dto : empleados) {
			listaEmpleados.add(dto.getId() + " - " + dto.getNombre());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lvEmpleados.setItems(listaEmpleados);
	}
	
	@FXML
	public void handleAgregarBtn(ActionEvent event) {
		String id = txtID.getText();
		String nombre = txtNombre.getText();
		String edad = txtEdad.getText();
		String salario = txtSalario.getText();
		
		if(!id.isBlank() && !nombre.isBlank() && !edad.isBlank() && !salario.isBlank()) {
		
			boolean exito = fac.agregarEmpleado(id, nombre, edad, salario);
			if(exito) {
				
				txtID.clear();
				txtNombre.clear();
				txtEdad.clear();
				txtSalario.clear();
				
				handleListarEmpleadosBtn(event);
			}
		
		}else {
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("Por favor diligencie todos los campos");
			al.show();
		}
		
	}
	
	@FXML
	public void handleEliminarBtn(ActionEvent event) {
		// Se retorna como ID - nombre
		// 8888 - Julian
		String empleadoSel = obtenerEmpleadoSeleccionado(lvEmpleados);
		
		if(empleadoSel!=null && !empleadoSel.isBlank()) {
		
			String id1 = obtenerIDEmpleadoListView(empleadoSel);
			
			boolean exito = fac.eliminarEmpleado(id1);
			if(exito) {
				handleListarEmpleadosBtn(event);
			}
		}else {
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("Seleccione un empleado.");
			al.show();
		}
	}
	
	@FXML
	public void handleActualizarBtn(ActionEvent eve) {
		// Hacer consulta para traer los datos
		// empleadoSel = "1004 - Yurley"
		String empleadoSel = obtenerEmpleadoSeleccionado(lvEmpleados);
		
		if(empleadoSel!=null && !empleadoSel.isBlank()) {
		
			// id = "1004"
			String id = obtenerIDEmpleadoListView(empleadoSel);
			
			// Se le pide a la fachada que traiga los datos del empleado
			EmpleadoDTO dto = fac.obtenerEmpleado(id);
			
			if(dto!=null) {
				txtNombreA.setEditable(true);
				txtEdadA.setEditable(true);
				txtSalarioA.setEditable(true);
				
				btnGuardar.setDisable(false);
				
				txtIDA.setText(String.valueOf(dto.getId()));
				txtNombreA.setText(dto.getNombre());
				txtEdadA.setText(String.valueOf(dto.getEdad()));
				txtSalarioA.setText(String.valueOf(dto.getSalario()));
				
			}
		}
	}
	
	@FXML
	public void handleGuardarBtn(ActionEvent eve) {
		
		String id = txtIDA.getText();
		String nombre = txtNombreA.getText();
		String edad = txtEdadA.getText();
		String salario = txtSalarioA.getText();
		
		if(!nombre.isBlank() && !edad.isBlank() && !salario.isBlank()) {
		
			boolean exito = fac.actualizarEmpleado(id, nombre, edad, salario);
			if(exito) {
				
				txtIDA.clear();
				txtNombreA.clear();
				txtEdadA.clear();
				txtSalarioA.clear();
				
				txtNombreA.setEditable(false);
				txtEdadA.setEditable(false);
				txtSalarioA.setEditable(false);
				
				btnGuardar.setDisable(true);
				
				handleListarEmpleadosBtn(eve);
			}
		
		}else {
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("Por favor diligencie todos los campos");
			al.show();
		}
		
	}
	
	@FXML
	public void handleDetallesBtn(ActionEvent eve) {
		
		// empleadoSel = "1002 - Julian"
		String empleadoSel = obtenerEmpleadoSeleccionado(lvEmpleados);
		
		if(empleadoSel!=null && !empleadoSel.isBlank()) {
		
			// id = "1002"
			String id = obtenerIDEmpleadoListView(empleadoSel);
			
			// Se le pide a la fachada que traiga los datos del empleado
			EmpleadoDTO dto = fac.obtenerEmpleado(id);
			
			if(dto!=null) {
				txtIDA.setText(String.valueOf(dto.getId()));
				txtNombreA.setText(dto.getNombre());
				txtEdadA.setText(String.valueOf(dto.getEdad()));
				txtSalarioA.setText(String.valueOf(dto.getSalario()));
				
			}
		}
	}
	
	// ************************************************************
	// ******** METODOS PRIVADOS PARA USO DEL CONTROLADOR *********
	// ************************************************************
	
	private String obtenerIDEmpleadoListView(String empleadoSeleccionado) {
		// indexOF + substring
		int posGuion = empleadoSeleccionado.indexOf("-");
		String id1 = empleadoSeleccionado.substring(0, posGuion).trim();
		
		return id1;
	}
	
	private String obtenerEmpleadoSeleccionado(ListView<String> listView) {
		String empleadoSel = lvEmpleados.getSelectionModel().getSelectedItem();
		return empleadoSel;
	}
	
}






