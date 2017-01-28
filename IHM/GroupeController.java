package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Proprietaire;
import Entities.Troupeau;
import Services.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GroupeController extends AbstractController  implements Initializable{
	
	@FXML
	private Button btAddGroupe;
	
	@FXML
	private Button btModifieGroupe;
	
	@FXML
	private Button btSuppGroupe;
	
	@FXML
	private Button btAddProp;
	
	@FXML
	private Button btModifieProp;
	
	@FXML
	private Button btSuppProp;
	
	@FXML
	private Button btValidate;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label lbMessage;
	
	@FXML
	private Label lbTitre;
	
	private ComboBox<Troupeau> cbo;
	private ComboBox<Proprietaire> cboProp;
	
	@FXML
	private VBox centre;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Griser Bouton btAddGroupe
		
	}
	
	@FXML
	private void addGroupe(){
		lbTitre.setText("Ajout d'un groupe");
		txtName = createTextField("Nom*");
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, txtName);
		lbMessage.setText("");
		
	}
	
	@FXML
	private void modifieGroupe(){
		lbTitre.setText("Modification du nom de groupe");
		cbo = createCombobox();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, cbo);
		txtName = createTextField("Nouveau nom");
		centre.getChildren().add(2, txtName);
		btValidate.setOnAction(e -> {
			validate_ModifieGroupe();
		});
		lbMessage.setText("");
	}
	
	private void validate_ModifieGroupe(){
		String name = txtName.getText();
		Troupeau groupe = cbo.getValue();
		if(!name.matches("[a-zA-Z]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caractères maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caractère non valable");
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			if(groupe == null){
				cbo.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Veuillez choisir un groupe");
			}else{
				cbo.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: Green;");
				lbMessage.setText("Modification du groupe");
				txtName.setText("");
				//TODO Update base
			}
		}
	}
	
	@FXML
	private void suppGroupe(){
		lbTitre.setText("Suppression d'un groupe");
		cbo = createCombobox();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, cbo);
		btValidate.setOnAction(e -> {
			validate_SuppGroupe();
		});
		lbMessage.setText("");
	}
	
	private void validate_SuppGroupe() {
		Troupeau groupe = cbo.getValue();
		if(groupe == null){
			cbo.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			lbMessage.setText("Veuillez choisir un groupe");
		}else{
			lbMessage.setStyle("-fx-text-fill: Green;");
			lbMessage.setText("Suppression du groupe");	
			//TODO Supp groupe de la base	
		}
	}

	@FXML
	private void addProp(){
		lbTitre.setText("Ajout d'un propriétaire");
		txtName = createTextField("Nom*");
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, txtName);
		btValidate.setOnAction(e -> {
			validate_AddProp();
		});
		lbMessage.setText("");
	}
	
	private void validate_AddProp() {
		String name = txtName.getText();
		if(!name.matches("[a-zA-Z]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caractères maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caractère non valable");
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: Green;");
			lbMessage.setText("Ajout du propriétaire");
			txtName.setText("");
			//TODO Ajout prop à la base
		}
	}

	@FXML
	private void modifieProp(){
		lbTitre.setText("Modification du nom du propriétaire");
		cboProp = createComboboxProp();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, cboProp);
		txtName = createTextField("Nouveau nom");
		centre.getChildren().add(2, txtName);
		btValidate.setOnAction(e -> {
			validate_ModifieProp();
		});
		lbMessage.setText("");
	}
	
	private void validate_ModifieProp() {
		String name = txtName.getText();
		Proprietaire prop = cboProp.getValue();
		if(!name.matches("[a-zA-Z]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caractères maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caractère non valable");
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			if(prop == null){
				cboProp.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Veuillez choisir un propriétaire");
			}else{
				lbMessage.setStyle("-fx-text-fill: Green;");
				lbMessage.setText("Modification du popriétaire");
				txtName.setText("");
				//TODO Update base
			}
		}
		
	}

	@FXML
	private void suppProp(){
		lbTitre.setText("Suppression d'un propriétaire");
		cboProp = createComboboxProp();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		centre.getChildren().add(1, cboProp);
		btValidate.setOnAction(e -> {
			validate_SuppProp();
		});
		lbMessage.setText("");
	}
	
	private void validate_SuppProp() {
		Proprietaire prop = cboProp.getValue();
		if(prop == null){
			cboProp.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			lbMessage.setText("Veuillez choisir un propriétaire");
		}else{
			cboProp.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: Green;");
			lbMessage.setText("Suppression du propriétaire");	
			//TODO Supp groupe de la base	
		}		
	}

	@FXML
	private void button_retour() throws IOException{
		goPrincipalView();
	}
	
	@FXML
	private void validate_AddGroupe(){
		String name = txtName.getText();
		if(!name.matches("[a-zA-Z]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caractères maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caractère non valable");
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: Green;");
			lbMessage.setText("Ajout du groupe");
			txtName.setText("");
		}
	}
	
	private TextField createTextField(String s){
		TextField newText = new TextField();
		newText.setPromptText(s);
		newText.setMinWidth(120);
		newText.setPrefWidth(120);
		newText.setMaxWidth(120);
		newText.getStyleClass().add("textField");
		return newText;
	}
	
	private ComboBox<Troupeau> createCombobox(){
		ComboBox<Troupeau> newCombo = new ComboBox<Troupeau>();
		//newCombo.getItems().addAll(Main.repoTroupeau.getAll());
		newCombo.getStyleClass().add("combobox");
		return newCombo;
	}
	
	private ComboBox<Proprietaire> createComboboxProp(){
		ComboBox<Proprietaire> newCombo = new ComboBox<Proprietaire>();
		//newCombo.getItems().addAll(Main.repoProp.getAll());
		newCombo.getStyleClass().add("combobox");
		return newCombo;
	}

	@Override
	protected void suppGroupe(Object o) {
		//Ne peut pas être appelé de la fenêtre
		
	}

}
