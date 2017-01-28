package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import Entities.Couleur;
import Entities.Groupe;
import Entities.IEntities;
import Services.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PrincipalViewController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	@FXML
	private ComboBox<IEntities> cboGroupeMale;
	@FXML 
	private ComboBox<IEntities> cboGroupeMale2 = null;
	
	@FXML
	private ComboBox<IEntities> cboGroupeFemelle;
	
	@FXML
	private VBox vbMaleGroupe;
	
	@FXML
	private VBox vbFemelleGroupe;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*ObservableList<IEntities> groupes = Main.repoGroupe.getAllGroupe();
		cboGroupeMale.getItems().addAll(groupes);
		cboGroupeFemelle.getItems().addAll(groupes);*/
		vbs = new ArrayList<VBox>();
		vbs.add(vbMaleGroupe);
		vbs.add(vbFemelleGroupe);
	}
	
	@FXML
	private void addGroupeMale(){
		addGroupe(vbMaleGroupe);
	}
	
	@FXML
	private void addGroupeFemelle(){
		addGroupe(vbFemelleGroupe);
	}
	
	@Override
	protected void suppGroupe(Object o){
		boolean trouve = false;
		for(int j = 0; j<vbs.size(); j++){
			List<Node> childrens = vbs.get(j).getChildren();
			int nbChildren = childrens.size();
			for(int i = 0; i<nbChildren; i++){
				if(((HBox)childrens.get(i)).getChildren().size()>2){
					Object obj = ((HBox)childrens.get(i)).getChildren().get(2);
					if(o.equals(obj)){
						childrens.remove(childrens.get(i));
						System.out.println("Trouver");
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