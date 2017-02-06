package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.*;
import Services.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
		btValidate.setOnAction(e -> {
			validate_AddGroupe();
		});
		
	}
	
	@FXML
	private void validate_AddGroupe(){
		String name = txtName.getText();
		if(!name.matches("[A-Za-z\' -����]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caract�res maximum)");	
			}else{
				if(Main.repGroupe.getNameFind(name)){
					lbMessage.setText("Le nom du groupe h�siste d�j� (dans groupe ou propri�taire)");
				}else{
					lbMessage.setText("Le nom contient au moins un caract�re non valable");
				}
			}
		}else{
			System.out.println("nom valide");
			if(Main.repGroupe.getNameFind(name)){
				System.out.println("D�j� dans la base");
				txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Le nom du groupe h�siste d�j� (dans groupe ou propri�taire)");
			}else{
				System.out.println("Ajout dans la base");
				txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: Green;");
				lbMessage.setText("Ajout du groupe");
				txtName.setText("");
	
				Troupeau newTroupeau = new Troupeau();
				newTroupeau.setNom(name);
				
				Main.repTroupeau.add(newTroupeau);
			}
		}
	}
	
	@FXML
	private void modifieGroupe(){
		lbTitre.setText("Modification du nom de groupe");
		cbo = createCombobox();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		Label l = new Label("Groupe : ");
		l.getStyleClass().add("labelFenetre");
		h.getChildren().addAll(l, cbo);
		centre.getChildren().add(1, h);
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
		//On v�rifie le champ Text
		if(!name.matches("[a-zA-Z]{1,20}")){
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				lbMessage.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				lbMessage.setText("Le nom est trop grand (20 caract�res maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caract�re non valable");
			}
		}else{
		//On v�rifie la ComboBox
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			if(groupe == null){
				cbo.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Veuillez choisir un groupe");
			}else{
				if(Main.repGroupe.getNameFind(name)){
					txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
					lbMessage.setStyle("-fx-text-fill: #e81010;");
					lbMessage.setText("Le nom du groupe h�siste d�j� (dans groupe ou propri�taire)");
				}else{
					cbo.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
					lbMessage.setStyle("-fx-text-fill: Green;");
					lbMessage.setText("Modification du groupe");
					txtName.setText("");
	
					groupe.setNom(name);
					Main.repTroupeau.update(groupe);
					Main.repTroupeau.commit();
					//TODO mettre � jours la cbo
				}
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
		HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		Label l = new Label("Groupe : ");
		l.getStyleClass().add("labelFenetre");
		h.getChildren().addAll(l, cbo);
		centre.getChildren().add(1, h);
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
			suppInMuldo(groupe);
			Main.repTroupeau.remove(groupe);
			Main.repTroupeau.commit();
			//TODO Mettre � jour la combobox
		}
	}

	@FXML
	private void addProp(){
		lbTitre.setText("Ajout d'un propri�taire");
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
				lbMessage.setText("Le nom est trop grand (20 caract�res maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caract�re non valable");
			}
		}else{
			if(Main.repGroupe.getNameFind(name)){
				txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Le nom du groupe h�siste d�j� (dans groupe ou propri�taire)");
			}else{
				txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: Green;");
				lbMessage.setText("Ajout du propri�taire");
				txtName.setText("");
	
				Proprietaire newProp = new Proprietaire();
				newProp.setNom(name);
				
				Main.repProp.add(newProp);
			}
		}
	}

	@FXML
	private void modifieProp(){
		lbTitre.setText("Modification du nom du propri�taire");
		cboProp = createComboboxProp();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		Label l = new Label("Propri�taire : ");
		l.getStyleClass().add("labelFenetre");
		h.getChildren().addAll(l, cboProp);
		centre.getChildren().add(1, h);
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
				lbMessage.setText("Le nom est trop grand (20 caract�res maximum)");	
			}else{
				lbMessage.setText("Le nom contient au moins un caract�re non valable");
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			if(prop == null){
				cboProp.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
				lbMessage.setStyle("-fx-text-fill: #e81010;");
				lbMessage.setText("Veuillez choisir un propri�taire");
			}else{
				if(Main.repGroupe.getNameFind(name)){
					txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
					lbMessage.setStyle("-fx-text-fill: #e81010;");
					lbMessage.setText("Le nom du groupe h�siste d�j� (dans groupe ou propri�taire)");
				}else{
					lbMessage.setStyle("-fx-text-fill: Green;");
					lbMessage.setText("Modification du popri�taire");
					txtName.setText("");
	
					prop.setNom(name);
					Main.repProp.update(prop);
					Main.repProp.commit();
				}
			}
		}
		
	}

	@FXML
	private void suppProp(){
		lbTitre.setText("Suppression d'un propri�taire");
		cboProp = createComboboxProp();
		centre.getChildren().remove(1);
		if(centre.getChildren().size()>3){
			centre.getChildren().remove(1);
		}
		HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		Label l = new Label("Propri�taire : ");
		l.getStyleClass().add("labelFenetre");
		h.getChildren().addAll(l, cboProp);
		centre.getChildren().add(1, h);
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
			lbMessage.setText("Veuillez choisir un propri�taire");
		}else{
			suppInMuldo(prop);
			Main.repProp.remove(prop);
			Main.repProp.commit();
		
			cboProp.setStyle("-fx-background-color: #574F4D; -fx-border-color: black; -fx-text-fill: white ;");
			lbMessage.setStyle("-fx-text-fill: Green;");
			lbMessage.setText("Suppression du propri�taire");	
		}		
	}

	@FXML
	private void button_retour() throws IOException{
		goPrincipalView();
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
		newCombo.getItems().addAll(Main.repTroupeau.getAll());
		newCombo.setPrefWidth(130);
		newCombo.setMinWidth(130);
		newCombo.setMaxWidth(130);
		newCombo.getStyleClass().add("combobox");
		return newCombo;
	}
	
	private ComboBox<Proprietaire> createComboboxProp(){
		ComboBox<Proprietaire> newCombo = new ComboBox<Proprietaire>();
		newCombo.getItems().addAll(Main.repProp.getAll());
		newCombo.setPrefWidth(130);
		newCombo.setMinWidth(130);
		newCombo.setMaxWidth(130);
		newCombo.getStyleClass().add("combobox");
		return newCombo;
	}
	
	private void suppInMuldo(Groupe groupe) {
		List<Muldo> muldos = groupe.getMuldos();
		int l = muldos.size();
		Muldo m;
		List<Groupe> muldoGroupes;
		int length;
		for (int i = 0; i<l; i++){
			m = muldos.get(i);
			muldoGroupes = m.getGroupes();
			length = muldoGroupes.size();
			for(int j = 0; j<length; j++){
				if(muldoGroupes.get(j).getId() == groupe.getId()){
					muldoGroupes.remove(j);
					break;
				}
			}
		}
		
	}

	@Override
	protected void suppGroupe(Object o) {
		//Ne peut pas �tre appel� de la fen�tre
		
	}

	@Override
	protected void isSelected(Object hbox) {
		//Ne peut pas �tre appel� de la fen�tre
		
	}

	@Override
	protected void searchListMuldo(Object source) {
		// TODO Auto-generated method stub
		
	}

}
