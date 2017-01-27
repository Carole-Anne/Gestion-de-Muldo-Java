package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import Services.Main;

public class PrincipalViewController implements Initializable{
	 
	 @FXML
	 private void newMuldo() throws IOException {		 
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("NewMuldo.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	 
}