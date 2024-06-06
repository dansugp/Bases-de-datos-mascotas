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
import jdbc.model.MascotaFachada;
import jdbc.model.dao.MascotaDTO;

public class MascotaController implements Initializable {

	private MascotaFachada fac;
	private ObservableList<String> listaMascotas;
	
	@FXML
	private ListView<String> lvMascotas;
	@FXML
	private TextField txtID, txtNombre, txtEdad, txtDueño, txtEspecie;
	@FXML
	private TextField txtIDA, txtNombreA, txtEdadA, txtDueñoA, txtEspecieA;
	@FXML
	private Button btnGuardar;
	
	public MascotaController() {
		fac = new MascotaFachada();
		listaMascotas= FXCollections.observableArrayList();
	}
	
	@FXML
	public void handleListarMascotasBtn(ActionEvent event) {
		listaMascotas.clear();
		
		ArrayList<MascotaDTO> mascotas = fac.listarMascotas();
		
		for(MascotaDTO dto : mascotas) {
			listaMascotas.add(dto.getId() + " - " + dto.getNombre());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lvMascotas.setItems(listaMascotas);
	}
	
	@FXML
	public void handleAgregarBtn(ActionEvent event) {
		String id = txtID.getText();
		String nombre = txtNombre.getText();
		String edad = txtEdad.getText();
		String especie = txtEspecie.getText();
		String duenio = txtDueño.getText();
		
		if(!id.isBlank() && !nombre.isBlank() && !edad.isBlank() && !especie.isBlank() && !duenio.isBlank()) {
		
			boolean exito = fac.agregarMascota(id, nombre, edad, especie, duenio);
			if(exito) {
				
				txtID.clear();
				txtNombre.clear();
				txtEdad.clear();
				txtEspecie.clear();
				txtDueño.clear();
				
				handleListarMascotasBtn(event);
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
		// 8888 - Munieca
		String mascotaSel = obtenerMascotaSeleccionado(lvMascotas);
		
		if(mascotaSel!=null && !mascotaSel.isBlank()) {
		
			String id1 = obtenerIDMascotaListView(mascotaSel);
			
			boolean exito = fac.eliminarMascota(id1);
			if(exito) {
				handleListarMascotasBtn(event);
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
		// mascotaSel = "1004 - Princesa"
		String mascotaSel = obtenerMascotaSeleccionado(lvMascotas);
		
		if(mascotaSel!=null && !mascotaSel.isBlank()) {
		
			// id = "1004"
			String id = obtenerIDMascotaListView(mascotaSel);
			
			// Se le pide a la fachada que traiga los datos del empleado
			MascotaDTO dto = fac.obtenerMascota(id);
			if(dto!=null) {
				txtNombreA.setEditable(true);
				txtEdadA.setEditable(true);
				txtEspecieA.setEditable(true);
				txtDueñoA.setEditable(true);
				
				btnGuardar.setDisable(false);
				
				txtIDA.setText(String.valueOf(dto.getId()));
				txtNombreA.setText(dto.getNombre());
				txtEdadA.setText(String.valueOf(dto.getEdad()));
				txtEspecieA.setText(String.valueOf(dto.getEspecie()));
				txtDueñoA.setText(String.valueOf(dto.getDuenio()));
				
			}
		}
	}
	
	@FXML
	public void handleGuardarBtn(ActionEvent eve) {
		
		String id = txtIDA.getText();
		String nombre = txtNombreA.getText();
		String edad = txtEdadA.getText();
		String especie = txtEspecieA.getText();
		String duenio = txtDueñoA.getText();
		
		if(!nombre.isBlank() && !edad.isBlank() && !especie.isBlank() && !duenio.isBlank()) {
		
			boolean exito = fac.actualizarMascota(id, nombre, edad, especie, duenio);
			if(exito) {
				
				txtIDA.clear();
				txtNombreA.clear();
				txtEdadA.clear();
				txtEspecieA.clear();
				txtDueñoA.clear();
				
				txtNombreA.setEditable(false);
				txtEdadA.setEditable(false);
				txtEspecieA.setEditable(false);
				txtDueñoA.setEditable(false);
				
				btnGuardar.setDisable(true);
				
				handleListarMascotasBtn(eve);
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
		String mascotaSel = obtenerMascotaSeleccionado(lvMascotas);
		
		if(mascotaSel!=null && !mascotaSel.isBlank()) {
		
			// id = "1002"
			String id = obtenerIDMascotaListView(mascotaSel);
			
			// Se le pide a la fachada que traiga los datos del empleado
			   MascotaDTO dto = fac.obtenerMascota(id);
			   if(dto!=null) {
				txtIDA.setText(String.valueOf(dto.getId()));
				txtNombreA.setText(dto.getNombre());
				txtEdadA.setText(String.valueOf(dto.getEdad()));
				txtEspecieA.setText(dto.getEspecie());
				txtDueñoA.setText(dto.getDuenio());
				
			}
		}
	}
	
	// ************************************************************
	// ******** METODOS PRIVADOS PARA USO DEL CONTROLADOR *********
	// ************************************************************
	
	private String obtenerIDMascotaListView(String mascotaSeleccionado) {
		// indexOF + substring
		int posGuion = mascotaSeleccionado.indexOf("-");
		String id1 = mascotaSeleccionado.substring(0, posGuion).trim();
		
		return id1;
	}
	
	private String obtenerMascotaSeleccionado(ListView<String> listView) {
		String mascotaSel = lvMascotas.getSelectionModel().getSelectedItem();
		return mascotaSel;
	}
	
}






