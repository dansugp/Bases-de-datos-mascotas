package jdbc.complex.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainJDBCComplex extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().
            		getResource("EmpleadoView.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}