package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Entities.Couleur;
import Entities.Groupe;
import Entities.IEntities;
import Entities.Muldo;
import Entities.Proprietaire;
import Services.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewMuldoController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	@FXML
	private Label message;
	
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
	private ComboBox<IEntities> cboGroupeFemelle;
	
	@FXML 
	private ComboBox<IEntities> cboGroupe;
	
	@FXML 
	private ComboBox<Proprietaire> cboProp;
	 
	@FXML
	private ImageView imNewMuldo;
	
	@FXML
	private ToggleGroup tgSexe;
	
	@FXML
	private RadioButton sexeM;
	@FXML
	private RadioButton sexeF;
	
	@FXML
	private VBox listMale;
	private List<HBox> males;
	private int idMalesSelected = 0;
	
	@FXML private ImageView imMuldoMale;
	
	@FXML
	private VBox listFemelle;
	private List<HBox> femelles;
	private int idFemellesSelected = 0;

	@FXML private ImageView imMuldoFemelle;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<Couleur> list = Main.service.getRepColor().getAll();
		ObservableList<Couleur> value = FXCollections.observableList(list);
		cboChoiceColor.getItems().addAll(value);
		cboChoiceColor.setValue(list.get(0));
		imNewMuldo.setImage(new Image(list.get(0).getUrl()));
		
		ObservableList<IEntities> groupes = getListGroup();
		cboGroupeMale.getItems().addAll(groupes);
		cboGroupeFemelle.getItems().addAll(groupes);
		ObservableList<Proprietaire> listProp = getListProp();
		cboProp.getItems().addAll(listProp);
		cboProp.setValue(listProp.get(0));
		cboGroupe.getItems().addAll(getListTroup());
		
		vbs = new ArrayList<VBox>();
		vbs.add(vbMaleGroupe);
		vbs.add(vbFemelleGroupe);
		vbs.add(vbGroupe);
		
		sexeM.setGraphic(new ImageView(new Image("./image/male.png")));
		sexeM.setUserData(0);
		sexeF.setGraphic(new ImageView(new Image("./image/femelle.png")));
		sexeF.setUserData(1);
		
		//Initialisation des listes de muldo
		List<Muldo> muldosM = Main.service.getRepMuldo().getAll(0); //TODO manque anonyme
		males = createList(muldosM);
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}
		listMale.getChildren().addAll(males);
		List<Muldo> muldosF = Main.service.getRepMuldo().getAll(1);
		femelles = createList(muldosF);
		if(muldosF.size() != 0){
			isSelected(femelles.get(0));
		}
		listFemelle.getChildren().addAll(femelles);
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
	
	@SuppressWarnings("unchecked")
	@FXML 
	private void validateNewMuldo() throws IOException{	
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR); 
		boolean goodFormul = true;
		String name = txtName.getText();
		String nbSaillie = txtNbSaillies.getText();
		Muldo mere = Main.service.getRepMuldo().getById(Integer.parseInt(femelles.get(idFemellesSelected).getId()));
		Muldo pere = Main.service.getRepMuldo().getById(Integer.parseInt(males.get(idMalesSelected).getId()));
		if(!name.matches("[a-zA-Z]{1,16}")){
			goodFormul = false;
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			message.setStyle("-fx-text-fill: #e81010;");
			if(name.equals("")){
				message.setText("Veuillez rentrer un nom");				
			}else if(name.length() > 20){
				message.setText("Le nom est trop grand (20 caractères maximum)");	
			}else{
				if(Main.service.getRepMuldo().getNameFind(name)){
					message.setText("Le nom du muldo hésiste déjà (dans groupe ou propriétaire)");
				}else{
					message.setText("Le nom du muldo contient au moins un caractère non valable");
				}
			}
		}else{
			txtName.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		if(!nbSaillie.matches("[1-4]{1}")){
			goodFormul = false;
			txtNbSaillies.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			message.setText("Le nombre de saillies doit être compris entre 1 et 4.");
		}else{
			txtNbSaillies.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		/*if(cboChoiceColor.getValue()==null){
			goodFormul = false;
			cboChoiceColor.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
		}else{
			cboChoiceColor.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}*/
		if(goodFormul){	
			message.setText("");
			Muldo m = new Muldo();
			m.setSexe(Integer.parseInt(tgSexe.getSelectedToggle().getUserData().toString()));
			m.setCouleur(cboChoiceColor.getValue());
			m.setNom(name);
			m.setNbenfant(0);
			m.setNbsaillies(Integer.parseInt(nbSaillie));
			m.setFecond(false);
			m.setMuldoMere(mere);
			if(mere.getId() != 2){
				mere.addNbenfant();
			}
			m.setMuldoPere(pere);
			if(pere.getId() != 1){
				pere.addNbenfant();
			}
			m.setProp(cboProp.getValue());
			ObservableList<Node> listGroupes = vbGroupe.getChildren();
			for(int i = 0; i<listGroupes.size(); i++){
				Node cbo = ( (HBox)listGroupes.get(i)).getChildren().get(0);
				m.setTroupeau(((ComboBox<Groupe>)cbo).getValue());
			}
			m.setVisible(true);
			
			Main.service.getRepMuldo().add(m);
			goPrincipalView();
		}
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT); 
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


	@Override
	protected void isSelected(Object hbox) {
		HBox hb = (HBox)hbox;
		int id = Integer.parseInt(hb.getId());
		boolean isMale;
		List<HBox> muldoSelected;
		System.out.println(Integer.parseInt(hb.getId()));
		Muldo m = Main.service.getRepMuldo().getById(id);
		if(m.getSexe() == 0){
			isMale = true;
			muldoSelected = males;
			modifieMuldoMale(m);
		}else{
			isMale = false;
			muldoSelected = femelles;
			modifieMuldoFemelle(m);
		}
		
		//On modifie la selection dans la fenetre
		int i = 0;
		while(i<muldoSelected.size() && id!=Integer.parseInt(muldoSelected.get(i).getId())){
			i++;
		}
		if(isMale){
			if(idMalesSelected%2==0){
				males.get(idMalesSelected).getStyleClass().clear();
				males.get(idMalesSelected).getStyleClass().add("pair");
			}else{
				males.get(idMalesSelected).getStyleClass().clear();
				males.get(idMalesSelected).getStyleClass().add("impair");
			}
			males.get(i).getStyleClass().clear();
			males.get(i).getStyleClass().add("selected");
			idMalesSelected = i;
		}else{
			if(idFemellesSelected%2==0){
				femelles.get(idFemellesSelected).getStyleClass().clear();
				femelles.get(idFemellesSelected).getStyleClass().add("pair");
			}else{
				femelles.get(idFemellesSelected).getStyleClass().clear();
				femelles.get(idFemellesSelected).getStyleClass().add("impair");
			}
			femelles.get(i).getStyleClass().clear();
			femelles.get(i).getStyleClass().add("selected");
			idFemellesSelected = i;
		}
		
	}
	
	private void modifieMuldoMale(Muldo m) {
		String im = m.getCouleur().getUrl();
		imMuldoMale.setImage(new Image(im));
	}
	
	private void modifieMuldoFemelle(Muldo m) {
		String im = m.getCouleur().getUrl();
		imMuldoFemelle.setImage(new Image(im));
	}
	
	private void modifieListMuldoMale() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbMaleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbMaleGroupe);
		List<Muldo> muldosM = Main.service.getRepMuldo().getByGroupes(0, listIdGroupe, idCouleur, false);
		muldosM.add(0,Main.service.getRepMuldo().getById(2));
		males = createList(muldosM); //création de la liste graphiquement
		idMalesSelected = 0;
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}else{
			imMuldoMale.setImage(new Image("./image/anonyme.png"));
		}
		listMale.getChildren().clear();
		listMale.getChildren().addAll(males);
	}

	private void modifieListMuldoFemelle() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbFemelleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbFemelleGroupe);
		List<Muldo> muldosF = Main.service.getRepMuldo().getByGroupes(1, listIdGroupe, idCouleur, false);
		muldosF.add(0,Main.service.getRepMuldo().getById(3));
		femelles = createList(muldosF); //création de la liste graphiquement
		idFemellesSelected = 0;
		if(muldosF.size() != 0){
			isSelected(femelles.get(0));
		}else{
			imMuldoFemelle.setImage(new Image("./image/anonyme.png"));
		}
		listFemelle.getChildren().clear();
		listFemelle.getChildren().addAll(femelles);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void searchListMuldo(Object source) {
		List<Node> chil = vbMaleGroupe.getChildren(); //Liste de HBox
		int l = chil.size();
		ComboBox<IEntities> cbo;
		boolean isCboFemelle = true;
		//recherche de la comboBox dans la liste Male
		for(int i = 0; i<l ; i++){
			cbo = (ComboBox<IEntities>)((HBox)chil.get(i)).getChildren().get(0);
			if(source.equals(cbo)){
				isCboFemelle = false;
				modifieListMuldoMale();
				break;
			}
		}
		if(isCboFemelle){
			modifieListMuldoFemelle();
		}		
		
	}

}
