package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entities.*;
import Services.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModifieMuldoViewController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	@FXML
	private VBox vbMaleGroupe;
	@FXML
	private ComboBox<IEntities> cboGroupeMale;
	
	@FXML
	private VBox vbGroupe;
	
	@FXML
	private VBox vbFemelleGroupe;
	@FXML
	private ComboBox<IEntities> cboGroupeFemelle;
	
	@FXML
	private ImageView imMuldo;
	@FXML
	private ComboBox<Proprietaire> cbProp;
	@FXML
	private ComboBox<Troupeau> cbTroup;
	@FXML
	private CheckBox isFecond;
	@FXML
	private Label message;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSailliesUse;
	@FXML 
	private Label lbNbSaille;
	
	@FXML
	private VBox listMale;
	private List<HBox> males;
	private int idMuldoSelected;
	private Muldo muldoSelected;
	
	@FXML
	private VBox listFemelle;
	private List<HBox> femelles;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		vbs = new ArrayList<VBox>();
		vbs.add(vbMaleGroupe);
		vbs.add(vbFemelleGroupe);
		vbs.add(vbGroupe);
		//Initialisation des listes de muldo
		List<Muldo> muldosM = Main.repMuldo.getAllModifie(0);
		males = createList(muldosM, listMale);
		listMale.getChildren().addAll(males);
		List<Muldo> muldosF = Main.repMuldo.getAllModifie(1);
		femelles = createList(muldosF, listFemelle);
		listFemelle.getChildren().addAll(femelles);
		
		//Initialisation des combobox
		ObservableList<IEntities> groupes = getListGroup();
		cboGroupeMale.getItems().addAll(groupes);
		cboGroupeFemelle.getItems().addAll(groupes);
		ObservableList<Proprietaire> props = getListProp();
		cbProp.getItems().addAll(props);
		cbProp.setValue(props.get(0));
		ObservableList<Troupeau> troupeaux = getListTroup();
		cbTroup.getItems().addAll(troupeaux);
		
		if(muldosM.size()!=0){
			isSelected(males.get(0));
		}else if(muldosF.size()!=0){
			isSelected(femelles.get(0));
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
		addTroupeau(vbGroupe);
	}

	@FXML
	private void mofifieMuldo() throws IOException{
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR);
		boolean goodFormul = true;
		String name = txtName.getText();
		String saillie = txtSailliesUse.getText();
		if(!name.matches("[a-zA-Z]{1,16}")){
			goodFormul = false;
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		if(!saillie.matches("[0-4]{1}")){
			goodFormul = false;
			txtSailliesUse.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			txtSailliesUse.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		
		if(goodFormul){
			muldoSelected.setNom(name);
			muldoSelected.setProp(cbProp.getValue());
			muldoSelected.setFecond(isFecond.isSelected());
			Main.repMuldo.update(muldoSelected);
			Main.repMuldo.commit();
			goPrincipalView();
			//TODO modifie le nombre de saillies restantes // Les groupes
		}
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}
	
	@FXML
	private void returnPrincipaleView() throws IOException{
		goPrincipalView();
	}

	@Override
	protected void isSelected(Object hbox) {
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR);
		int i = 0;
		int l = males.size();
		while(i<l && !hbox.equals(males.get(i))){
			i++;
		}
		if(i<l){
			if(idMuldoSelected<0){
				int idFemelle = -idMuldoSelected - 1;
				if(idFemelle%2==0){
					femelles.get(idFemelle).getStyleClass().clear();
					femelles.get(idFemelle).getStyleClass().add("pair");
				}else{
					System.out.println("Clear femelle : "+idFemelle);
					femelles.get(idFemelle).getStyleClass().clear();
					femelles.get(idFemelle).getStyleClass().add("impair");
				}
			}else{
				if(idMuldoSelected%2==0){
					males.get(idMuldoSelected).getStyleClass().clear();
					males.get(idMuldoSelected).getStyleClass().add("pair");
				}else{
					males.get(idMuldoSelected).getStyleClass().clear();
					males.get(idMuldoSelected).getStyleClass().add("impair");
				}
			}
			males.get(i).getStyleClass().clear();
			males.get(i).getStyleClass().add("selected");
			idMuldoSelected = i;
			modifieMuldo(males.get(i));
		}else{
			//la box n'est pas une box Male
			i = 0;
			l = femelles.size();
			while(i<l && !hbox.equals(femelles.get(i))){
				i++;
			}
			if(i<l){
				if(idMuldoSelected<0){
					int idFemelle = -idMuldoSelected - 1;
					if(idFemelle%2==0){
						femelles.get(idFemelle).getStyleClass().clear();
						femelles.get(idFemelle).getStyleClass().add("pair");
					}else{
						femelles.get(idFemelle).getStyleClass().clear();
						femelles.get(idFemelle).getStyleClass().add("impair");
					}
				}else{
					if(idMuldoSelected%2==0){
						males.get(idMuldoSelected).getStyleClass().clear();
						males.get(idMuldoSelected).getStyleClass().add("pair");
					}else{
						males.get(idMuldoSelected).getStyleClass().clear();
						males.get(idMuldoSelected).getStyleClass().add("impair");
					}
				}
				femelles.get(i).getStyleClass().clear();
				femelles.get(i).getStyleClass().add("selected");
				idMuldoSelected = -i-1;
				modifieMuldo(femelles.get(i));
			}
		}
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
		
	}
	
	private void modifieMuldo(HBox hBox) {
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR);
		//TODO A modifier
		Label l = ((Label)hBox.getChildren().get(1));
		Muldo m = Main.repMuldo.getByName(l.getText()).get(0);
		muldoSelected = m;
		String im = m.getCouleur().getUrl();
		imMuldo.setImage(new Image(im));
		txtSailliesUse.setText((m.getNbenfant()/2)+"");
		txtName.setText(m.getNom());
		lbNbSaille.setText("/"+m.getNbsaillies());
		if(m.getFecond()){
			isFecond.setSelected(true);
		}else{
			isFecond.setSelected(false);
		}
		String s = m.getProprietaire();
		if(s.equals("?")){
			cbProp.setValue(Main.repProp.getAll().get(0));
		}else{
			cbProp.setValue(Main.repProp.getByName(m.getProprietaire()).get(0));
		}
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}


	@Override
	protected void searchListMuldo(Object source) {
		// TODO Auto-generated method stub
		
	}

}
