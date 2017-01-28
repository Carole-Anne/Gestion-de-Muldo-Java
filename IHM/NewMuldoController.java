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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewMuldoController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	@FXML
	private VBox vbMaleGroupe;
	
	@FXML
	private VBox vbGroupe;
	
	@FXML
	private VBox vbFemelleGroupe;
	
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
		/*List<Couleur> list = Main.repColor.getAll();
		ObservableList<Couleur> value = FXCollections.observableList(list);
		cboChoiceColor.getItems().addAll(value);

		List<Groupe> groupes = Main.repGroupe.getAll();
		List<IEntities> entities = new ArrayList<IEntities>(groupes);
		entities.addAll(list);
		ObservableList<IEntities> listGroupe = FXCollections.observableList(entities);
		cboGroupe.getItems().addAll(listGroupe);*/
		
		vbs = new ArrayList<VBox>();
		vbs.add(vbMaleGroupe);
		vbs.add(vbFemelleGroupe);
		vbs.add(vbGroupe);
	}
	

	@FXML
	private void addGroupeMale(){
		addGroupe(vbMaleGroupe);
	}
	
	@FXML
	private void addGroupeFemelle(){
		addGroupe(vbFemelleGroupe);
	}
	
	@FXML
	private void addGroupeCenter(){
		addGroupe(vbGroupe);
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

	@Override
	protected void suppGroupe(Object o) {
		boolean trouve = false;
		for(int j = 0; j<vbs.size(); j++){
			List<Node> childrens = vbs.get(j).getChildren();
			int nbChildren = childrens.size();
			for(int i = 0; i<nbChildren; i++){
				if(((HBox)childrens.get(i)).getChildren().size()>2){
					Object obj = ((HBox)childrens.get(i)).getChildren().get(2);
					if(o.equals(obj)){
						childrens.remove(childrens.get(i));
						trouve = true;
						break;
					}
				}
			}
			if(trouve){
				if(nbChildren == 2){
					HBox firstHBox = (HBox)childrens.get(0);
					firstHBox.getChildren().remove(firstHBox.getChildren().size()-1);
					break;
				}
			}
		}
	}

}
