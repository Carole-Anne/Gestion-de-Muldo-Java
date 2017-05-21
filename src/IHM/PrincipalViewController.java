package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import Entities.IEntities;
import Entities.Muldo;
import Services.Main;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PrincipalViewController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	private List<ComboBox<IEntities>> cbosMale; //Pas mis à jours
	private List<ComboBox<IEntities>> cbosFemelle; //Pas mis à jours
	
	private Muldo mMale;
	private Muldo mFemelle;
	
	@FXML
	private ComboBox<IEntities> cboGroupeMale;
	
	@FXML
	private ComboBox<IEntities> cboGroupeFemelle;
	
	@FXML
	private VBox vbMaleGroupe;
	
	@FXML
	private VBox vbFemelleGroupe;
	
	@FXML
	private VBox listMale;
	private List<HBox> males;
	private int idMalesSelected = 0;
	
	@FXML private ImageView imMuldoMale;
	@FXML private Label lbNbSailleMale;
	@FXML private Label lbPropMale;
	
	@FXML
	private VBox listFemelle;
	private List<HBox> femelles;
	private int idFemellesSelected = 0;

	@FXML private ImageView imMuldoFemelle;
	@FXML private Label lbNbSailleFemelle;
	@FXML private Label lbPropFemelle;
	
	@FXML 
	private Label nbSaillieHypo;
	
	@FXML
	private Button btArbreMale;
	@FXML
	private Button btArbreFemelle;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<IEntities> groupes = getListGroup();
		
		//Initialisation des ComboBox
		cboGroupeMale.getItems().addAll(groupes);
		cboGroupeFemelle.getItems().addAll(groupes);
		cbosMale = new ArrayList<ComboBox<IEntities>>();
		cbosMale.add(cboGroupeMale);
		cbosFemelle = new ArrayList<ComboBox<IEntities>>();
		cbosFemelle.add(cboGroupeFemelle);
		
		vbs = new ArrayList<VBox>();
		vbs.add(vbMaleGroupe);
		vbs.add(vbFemelleGroupe);
		
		//Initialisation des listes de muldo
		//List<Muldo> muldosM = Main.service.getRepMuldo().getAllFecond(0);
		List<Muldo> muldosM = Main.service.getRepMuldo().getAll(0);
		males = createList(muldosM); 
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}else{
			btArbreMale.setDisable(true);
		}
		listMale.getChildren().addAll(males);
		//List<Muldo> muldosF = Main.service.getRepMuldo().getAllFecond(1);
		List<Muldo> muldosF = Main.service.getRepMuldo().getAll(1);
		femelles = createList(muldosF);
		if(muldosF.size() != 0){
			isSelected(femelles.get(0));
		}else{
			btArbreFemelle.setDisable(true);
		}
		listFemelle.getChildren().addAll(femelles);
	}
	
	@FXML
	private void arbreMale() throws IOException{
		Main.service.createArbre(mMale);
		goArbre();
	}
	
	@FXML
	private void arbreFemelle() throws IOException{
		Main.service.createArbre(mFemelle);
		goArbre();
	}
	
	@FXML
	private void trouverMale() throws IOException, InterruptedException{
		Main.service.selectMuldoMale(mFemelle);
		afficherList();
		mMale = Main.service.getmMale();
	}
	
	@FXML
	private void trouverFemelle() throws IOException, InterruptedException{
		Main.service.selectMuldoFemelle(mMale);
		afficherList();
		mFemelle = Main.service.getmFemelle();
	}
	
	public synchronized void findMale(Muldo m){
		modifieMuldoMale(Main.service.getmMale());
	}
	
	private void afficherList() throws IOException, InterruptedException{
		Stage s = new Stage();
		double height = 350;
		double width = 600;
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("SelectionMuldoView.fxml"));
		Scene scene = new Scene(root,width,height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		s.initOwner(Main.primaryStage);
			 
		s.setScene(scene);
		s.show();
		s.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent we) {
				notifyAll();
			}
		});
		wait();
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
	private void calculSaillie(){
		nbSaillieHypo.setText(Integer.toString(Main.service.nbSailliesHypothetique(mFemelle, mMale)));
	}
	
	@FXML
	private void feconder(){
		Main.service.feconder(mMale, mFemelle); //Le male et la femelle ne sont plus fecond
		modifieListMuldoMale(); //Mise à jour de la liste des males
		modifieListMuldoFemelle(); //Mise à jour de la liste des femelles
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
						trouve = true;
						break;
					}
				}
			}
			if(trouve){
				if(nbChildren == 2){
					HBox firstHBox = (HBox)childrens.get(0);
					firstHBox.getChildren().remove(firstHBox.getChildren().size()-1);
					if(j == 0){
						modifieListMuldoMale();
					}else{
						modifieListMuldoFemelle();
					}
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
		mMale = m;
		String im = m.getCouleur().getUrl();
		imMuldoMale.setImage(new Image(im));
		lbNbSailleMale.setText("Saillies : "+m.getNbSailliesUsed()+"/"+m.getNbsaillies());
		lbPropMale.setText("Propriétaire : "+m.getProprietaire());
		nbSaillieHypo.setText("?");
	}
	
	private void modifieMuldoFemelle(Muldo f) {
		mFemelle = f;
		String im = f.getCouleur().getUrl();
		imMuldoFemelle.setImage(new Image(im));
		lbNbSailleFemelle.setText("Saillies : "+f.getNbSailliesUsed()+"/"+f.getNbsaillies());
		lbPropFemelle.setText("Propriétaire : "+f.getProprietaire());
		nbSaillieHypo.setText("?");
	}

	private void modifieListMuldoMale() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbMaleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbMaleGroupe);
		List<Muldo> muldosM = Main.service.getRepMuldo().getByGroupes(0, listIdGroupe, idCouleur, true);
		males = createList(muldosM); //création de la liste graphiquement
		idMalesSelected = 0;
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}else{
			imMuldoMale.setImage(new Image("./image/anonyme.png"));
			lbNbSailleMale.setText("Saillies : ?/?");
			lbPropMale.setText("Propriétaire : ?");
		}
		listMale.getChildren().clear();
		listMale.getChildren().addAll(males);
	}

	private void modifieListMuldoFemelle() {
		List<Integer> listIdGroupe = VBoxGroupeToListId(vbFemelleGroupe); 
		List<Integer> idCouleur = VBoxToCouleur(vbFemelleGroupe);
		List<Muldo> muldosF = Main.service.getRepMuldo().getByGroupes(1, listIdGroupe, idCouleur, true);
		femelles = createList(muldosF); //création de la liste graphiquement
		idFemellesSelected = 0;
		if(muldosF.size() != 0){
			isSelected(femelles.get(0));
		}else{
			imMuldoFemelle.setImage(new Image("./image/anonyme.png"));
			lbPropFemelle.setText("Propriétaire : ?");
			lbNbSailleFemelle.setText("Saillies : ?/?");
		}
		listFemelle.getChildren().clear();
		listFemelle.getChildren().addAll(femelles);
	}

	@SuppressWarnings("unchecked")
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