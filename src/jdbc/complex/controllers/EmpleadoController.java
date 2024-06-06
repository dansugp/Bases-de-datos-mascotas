package jdbc.complex.controllers;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jdbc.model.Fachada;
import jdbc.model.dao.DeptDTO;
import jdbc.model.dao.EmpDTO;

public class EmpleadoController implements Initializable {

	@FXML
	private TableView<Employee> tableEmpleados;
	@FXML
	private TableColumn<Employee, String> colNo, colName, colDepartment, colHireDate;
	@FXML 
	private ComboBox<String> cmbDeptos;
	@FXML
	private TextField txtNombre, txtFechaContratacion;
	
	private ObservableList<String> dataCombo;
	private ObservableList<Employee> dataTable;
	
	private Fachada fac;
	
	public EmpleadoController() {
		fac = new Fachada();
		
		dataTable = FXCollections.observableArrayList();
		dataCombo = FXCollections.observableArrayList();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Configuracion de las columnas del TableView.
		colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
		
		// Asociación del ObservableList al TableView.
		tableEmpleados.setItems(dataTable);
		
		// Asociación del ObservableList al ComboBox.
		cmbDeptos.setItems(dataCombo);
		
		// Poblado del combo de departamentos
		ArrayList<DeptDTO> depts = fac.listarDepartments();
		if(depts!=null) {
			for(DeptDTO dto : depts) {
				dataCombo.add(dto.getName());
			}
		}
		
		// Poblado del tableview de empleados (por si hay ya registrados) 
		listarEmpleados();
	}
	
	@FXML
	public void handleAgregarEmpleado(ActionEvent ev) {
		String nombre = txtNombre.getText();
		String fechaContratacion = txtFechaContratacion.getText();
		String nombreDepto = cmbDeptos.getValue();
		
		// Se envia a guardar a la Fachada
		boolean exito = fac.agregarEmp(nombre, fechaContratacion, nombreDepto);
		if(exito) {
			listarEmpleados();
			txtNombre.clear();
			txtFechaContratacion.clear();
		}else {
			Alert al = new Alert(AlertType.ERROR);
			al.setContentText("Ocurrió un error inesperado");
			al.show();
		}
		
	}
	
	private ArrayList<EmpDTO> listarEmpleados(){
		
		dataTable.clear();
		
		ArrayList<EmpDTO> emps = fac.listarEmployees();
		if(emps!=null) {
			for(EmpDTO dto : emps) {
				
				Employee em = new Employee();
				em.setNo(dto.getNo());
				em.setName(dto.getName());
				em.setDepartment(dto.getDeptname());
				em.setHireDate(dto.getHiredate());
				
				dataTable.add(em);
			}
		}
		return emps;
	}
	
}
