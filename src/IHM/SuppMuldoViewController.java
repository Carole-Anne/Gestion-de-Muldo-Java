package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Entities.Groupe;
import Entities.IEntities;
import Entities.Muldo;
import Entities.Troupeau;
import Services.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SuppMuldoViewController extends AbstractController  implements Initializable{

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
	private TextField txtProp;
	@FXML
	private TextField txtTroup;
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
	private int sexeMuldoSelected;
	private Muldo muldoSelected;
	
	@FXML
	private VBox listFemelle;
	private List<HBox> femelles;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		
		if(muldosM.size()!=0){
			isSelected(males.get(0));
		}else if(muldosF.size()!=0){
			isSelected(femelles.get(0));
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
	private void returnPrincipaleView() throws IOException{
		goPrincipalView();
	}
	
	@FXML
	private void supprimerMuldo() throws IOException{
		//TODO à vérifier
		Main.service.delete(muldoSelected);
		goPrincipalView();
	}
	
	@Override
	protected void searchListMuldo(Object source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void isSelected(Object hbox) {
		HBox hb = (HBox)hbox;
		int id = Integer.parseInt(hb.getId());
		List<HBox> muldoSelected;
		boolean isMale;
		System.out.println(Integer.parseInt(hb.getId()));
		Muldo m = Main.service.getRepMuldo().getById(id);
		if(m.getSexe() == 0){
			isMale = true;
			muldoSelected = males;
		}else{
			isMale = false;
			muldoSelected = femelles;
		}
		modifieMuldo(m);
		
		//On modifie la selection dans la fenetre
		int i = 0;
		while(i<muldoSelected.size() && id!=Integer.parseInt(muldoSelected.get(i).getId())){
			i++;
		}
		if(sexeMuldoSelected == 0){
			if(idMuldoSelected%2==0){
				males.get(idMuldoSelected).getStyleClass().clear();
				males.get(idMuldoSelected).getStyleClass().add("pair");
			}else{
				males.get(idMuldoSelected).getStyleClass().clear();
				males.get(idMuldoSelected).getStyleClass().add("impair");
			}
		}else{
			if(idMuldoSelected%2==0){
				femelles.get(idMuldoSelected).getStyleClass().clear();
				femelles.get(idMuldoSelected).getStyleClass().add("pair");
			}else{
				femelles.get(idMuldoSelected).getStyleClass().clear();
				femelles.get(idMuldoSelected).getStyleClass().add("impair");
			}
		}

		idMuldoSelected = i;
		
		if(isMale){
			males.get(i).getStyleClass().clear();
			males.get(i).getStyleClass().add("selected");
			sexeMuldoSelected = 0;
		}else{
			femelles.get(i).getStyleClass().clear();
			femelles.get(i).getStyleClass().add("selected");
			sexeMuldoSelected = 1;
		}
	}
	
	private void modifieMuldo(Muldo m) {
		muldoSelected = m;
		String im = m.getCouleur().getUrl();
		imMuldo.setImage(new Image(im));
		txtSailliesUse.setText(m.getNbSailliesUsed()+"");
		txtName.setText(m.getNom());
		lbNbSaille.setText("/"+m.getNbsaillies());
		String s = m.getProprietaire();
		if(s.equals("?")){
			txtProp.setText(Main.service.getRepProp().getAll().get(0).getNom());
		}else{
			txtProp.setText(Main.service.getRepProp().getByName(m.getProprietaire()).get(0).getNom());
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
			vbGroupe.getChildren().add(createHBoxGroupeEmpty());
		}
	}

	private HBox createHBoxGroupeEmpty() {
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(5);
		TextField txt = new TextField();
		txt.getStyleClass().add("textField");
		txt.setEditable(false);
		hb.getChildren().add(txt);
		return hb;
	}

	private void afficherTroupeau(Troupeau g) {
		List<Node> childrens = vbGroupe.getChildren();
		HBox hb = createHBoxGroupeEmpty();
		TextField t = (TextField)hb.getChildren().get(0);
		t.setText(g.getNom());
		childrens.add(hb);
	}

	@Override
	protected void suppGroupe(Object o) {
		// Impossible à appeler
		
	}



}
