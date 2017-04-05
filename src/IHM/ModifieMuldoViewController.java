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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
		List<Muldo> muldosM = Main.service.getRepMuldo().getAllModifie(0);
		males = createList(muldosM);
		listMale.getChildren().addAll(males);
		List<Muldo> muldosF = Main.service.getRepMuldo().getAllModifie(1);
		femelles = createList(muldosF);
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

	@SuppressWarnings("unchecked")
	@FXML
	private void modifieMuldo() throws IOException{
		boolean goodFormul = true;
		String name = txtName.getText();
		String saillie = txtSailliesUse.getText();
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
		if(!saillie.matches("[0-4]{1}")){
			goodFormul = false;
			txtSailliesUse.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			message.setText("Le nombre de saillies doit être compris entre 1 et 4 saillies.");
		}else if(Integer.parseInt(saillie)>muldoSelected.getNbsaillies()){
			goodFormul = false;
			txtSailliesUse.setStyle("-fx-background-color: #574F4D; -fx-border-color: red; -fx-text-fill: white ;");
			message.setText("Le nombre de saillies utilisés est supérieur au nombre de saillies.");
		}else{
			txtSailliesUse.setStyle("-fx-background-color: #574F4D; -fx-border-color: green; -fx-text-fill: white ;");
		}
		
		if(goodFormul){
			message.setText("");
			muldoSelected.setNom(name);
			muldoSelected.setProp(cbProp.getValue());
			muldoSelected.setFecond(isFecond.isSelected());
			//Récupérer les groupes
			Troupeau troup;
			for(Node hb: vbGroupe.getChildren()){
				troup = ((ComboBox<Troupeau>)((HBox)hb).getChildren().get(0)).getValue();
				if(troup != null){
					muldoSelected.setTroupeau(troup);
				}
			}
			muldoSelected.setNbSailliesUsed(Integer.parseInt(saillie));
			Main.service.getRepMuldo().update(muldoSelected);
			Main.service.getRepMuldo().commit();
			goPrincipalView();
		}
	}
	
	@FXML
	private void returnPrincipaleView() throws IOException{
		goPrincipalView();
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
			modifieMuldo(m);
		}else{
			isMale = false;
			muldoSelected = femelles;
			modifieMuldo(m);
		}
		
		//On modifie la selection dans la fenetre
		int i = 0;
		while(i<muldoSelected.size() && id!=Integer.parseInt(muldoSelected.get(i).getId())){
			i++;
		}
		if(isMale){
			if(idMuldoSelected%2==0){
				males.get(idMuldoSelected).getStyleClass().clear();
				males.get(idMuldoSelected).getStyleClass().add("pair");
			}else{
				males.get(idMuldoSelected).getStyleClass().clear();
				males.get(idMuldoSelected).getStyleClass().add("impair");
			}
			males.get(i).getStyleClass().clear();
			males.get(i).getStyleClass().add("selected");
			idMuldoSelected = i;
		}else{
			if(idMuldoSelected%2==0){
				femelles.get(idMuldoSelected).getStyleClass().clear();
				femelles.get(idMuldoSelected).getStyleClass().add("pair");
			}else{
				femelles.get(idMuldoSelected).getStyleClass().clear();
				femelles.get(idMuldoSelected).getStyleClass().add("impair");
			}
			femelles.get(i).getStyleClass().clear();
			femelles.get(i).getStyleClass().add("selected");
			idMuldoSelected = i;
		}
	}
	
	private void modifieMuldo(Muldo m) {
		muldoSelected = m;
		String im = m.getCouleur().getUrl();
		imMuldo.setImage(new Image(im));
		txtSailliesUse.setText(m.getNbSailliesUsed()+"");
		txtName.setText(m.getNom());
		lbNbSaille.setText("/"+m.getNbsaillies());
		if(m.getFecond()){
			isFecond.setSelected(true);
		}else{
			isFecond.setSelected(false);
		}
		String s = m.getProprietaire();
		if(s.equals("?")){
			cbProp.setValue(Main.service.getRepProp().getAll().get(0));
		}else{
			cbProp.setValue(Main.service.getRepProp().getByName(m.getProprietaire()).get(0));
		}
		//Clean vbGroupe qui contient toutes les HBox de groupes
		vbGroupe.getChildren().clear();
		
		//Affiche les groupes du muldo
		List<Groupe> groupes = m.getGroupes();
		boolean haveTroupeau = false;
		for(Groupe g : groupes){
			if(g.getClass() == Troupeau.class){
				afficherTroupeau((Troupeau)g);
				haveTroupeau = true;
			}
		}
		if(!haveTroupeau){
			vbGroupe.getChildren().add(createHBoxGroupeEmpty(vbGroupe));
		}
	}
	
	private HBox createHBoxGroupeEmpty(VBox vb) {
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(5);
		ComboBox<Troupeau> cbo = createCombobobxTroup(getListTroup());
		hb.getChildren().add(cbo);
		Button bAdd = createButtonAdd(vb);
		hb.getChildren().add(bAdd);
		return hb;
	}
	
	private HBox createHBoxGroupe(VBox vb, Troupeau t) {
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(5);
		ComboBox<Troupeau> cbo = createCombobobxTroup(getListTroup());
		cbo.setValue(t);
		hb.getChildren().add(cbo);
		Button bAdd = createButtonAdd(vb);
		hb.getChildren().add(bAdd);
		return hb;
	}


	private void afficherTroupeau(Troupeau t) {
		List<Node> childrens = vbGroupe.getChildren();
		int nbChildren = childrens.size();
		if(nbChildren == 0){
			childrens.add(createHBoxGroupe(vbGroupe, t));
		}else if(nbChildren == 1){
			HBox children = (HBox)childrens.get(0);
			children.setPadding(new Insets(0, 5, 0, 0));
			children.getChildren().add(createButtonSupp());
			childrens.add(createNewTroupeau(vbGroupe, t));
		}else if(nbChildren<5){
			childrens.add(createNewTroupeau(vbGroupe, t));
		}
	}
	
	private HBox createNewTroupeau(VBox vb, Troupeau t){
		HBox newChildren = new HBox();
		newChildren.setAlignment(Pos.CENTER);
		newChildren.setSpacing(5);
		ComboBox<Troupeau> newCombobox = createCombobobxTroup(getListTroup());
		newCombobox.setValue(t);
		Button newButtonAdd = createButtonAdd(vb) ;
		Button newButtonSupp = createButtonSupp();
		newChildren.getChildren().add(newCombobox);
		newChildren.getChildren().add(newButtonAdd);
		newChildren.getChildren().add(newButtonSupp);
		return newChildren;
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
	
	private void modifieListMuldoMale() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbMaleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbMaleGroupe);
		List<Muldo> muldosM = Main.service.getRepMuldo().getByGroupes(0, listIdGroupe, idCouleur, false);
		males = createList(muldosM); //création de la liste graphiquement
		idMuldoSelected = 0;
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}else{
			imMuldo.setImage(new Image("./image/anonyme.png"));
			lbNbSaille.setText("/?");
			txtName.setText("?");
			txtSailliesUse.setText("?");
			cbProp.setValue(null);
		}
		listMale.getChildren().clear();
		listMale.getChildren().addAll(males);
	}

	private void modifieListMuldoFemelle() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbFemelleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbFemelleGroupe);
		List<Muldo> muldosF = Main.service.getRepMuldo().getByGroupes(1, listIdGroupe, idCouleur, false);
		femelles = createList(muldosF); //création de la liste graphiquement
		idMuldoSelected = 0;
		if(muldosF.size() != 0){
			isSelected(femelles.get(0));
		}else{
			imMuldo.setImage(new Image("./image/anonyme.png"));
			lbNbSaille.setText("/?");
			txtName.setText("?");
			txtSailliesUse.setText("?");
			cbProp.setValue(null);
		}
		listFemelle.getChildren().clear();
		listFemelle.getChildren().addAll(femelles);
	}

}
