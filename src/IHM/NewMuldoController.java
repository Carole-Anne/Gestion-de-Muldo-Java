package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Entities.Couleur;
import Entities.Groupe;
import Entities.IEntities;
import Services.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class NewMuldoController implements Initializable{
	
	@FXML
	 private ComboBox<Couleur> cboChoiceColor;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtNbSaillies;
	
	@FXML 
	private ComboBox<IEntities> cboGroupeMale;
	
	@FXML 
	private ComboBox<IEntities> cboGroupe;
	 
	@FXML
	private ImageView imNewMuldo;
	
	@FXML
	private ToggleGroup tgSexe;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Couleur> list = Main.repColor.getAll();
		ObservableList<Couleur> value = FXCollections.observableList(list);
		cboChoiceColor.getItems().addAll(value);

		List<Groupe> groupes = Main.repGroupe.getAll();
		List<IEntities> entities = new ArrayList<IEntities>(groupes);
		entities.addAll(list);
		ObservableList<IEntities> listGroupe = FXCollections.observableList(entities);
		cboGroupe.getItems().addAll(listGroupe);
	}
	
	@FXML
	 private void choiceColor(){
		Couleur color = (Couleur)cboChoiceColor.getValue();
		Image im = new Image(color.getUrl());
		imNewMuldo.setImage(im);
	 }
	
	@FXML
	 private void cancelNewMuldo() throws IOException {		
		goPrincipalView();
	 }
	
	@FXML 
	private void validateNewMuldo() throws IOException{	
		boolean goodFormul = true;
		String name = txtName.getText();
		String nbSaillie = txtNbSaillies.getText();
		if(!name.matches("[a-zA-Z]{1,16}")){
			goodFormul = false;
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		if(!nbSaillie.matches("[1-4]{1}")){
			goodFormul = false;
			txtNbSaillies.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			txtNbSaillies.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		if(cboChoiceColor.getValue()==null){
			goodFormul = false;
			cboChoiceColor.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			cboChoiceColor.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		if(goodFormul){
			//TODO enregistrer newMuldo
			goPrincipalView();
		}
	}
	
	private void goPrincipalView() throws IOException {		
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }

}
