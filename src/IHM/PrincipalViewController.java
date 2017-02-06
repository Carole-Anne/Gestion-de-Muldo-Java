package IHM;

import java.net.URL;
import java.util.*;

import Entities.IEntities;
import Entities.Muldo;
import Services.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrincipalViewController extends AbstractController implements Initializable{
	
	private List<VBox> vbs;
	
	private List<ComboBox<IEntities>> cbosMale;
	private List<ComboBox<IEntities>> cbosFemelle;
	
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
		List<Muldo> muldosM = Main.repMuldo.getAllFecond(0);
		males = createList(muldosM, listMale);
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}
		listMale.getChildren().addAll(males);
		List<Muldo> muldosF = Main.repMuldo.getAllFecond(1);
		femelles = createList(muldosF, listFemelle);
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
					break;
				}
			}
		}		
	}

	@Override
	protected void isSelected(Object hbox) {
		int i = 0;
		int l = males.size();
		while(i<l && !hbox.equals(males.get(i))){
			i++;
		}
		if(i<l){
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
			modifieMuldoMale(males.get(i));
		}else{
			//la box n'est pas une box Male
			i = 0;
			l = femelles.size();
			while(i<l && !hbox.equals(femelles.get(i))){
				i++;
			}
			if(i<l){
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
				modifieMuldoFemelle(femelles.get(i));
			}
		}
	}

	private void modifieMuldoMale(HBox hBox) {
		Label l = ((Label)hBox.getChildren().get(1));
		Muldo m = Main.repMuldo.getByName(l.getText()).get(0);
		String im = m.getCouleur().getUrl();
		imMuldoMale.setImage(new Image(im));
		lbNbSailleMale.setText("Saillies : "+m.getNbenfant()/2+"/"+m.getNbsaillies());
		lbPropMale.setText("Propriétaire : "+m.getProprietaire());
	}
	
	private void modifieMuldoFemelle(HBox hBox) {
		Label l = ((Label)hBox.getChildren().get(1));
		Muldo m = Main.repMuldo.getByName(l.getText()).get(0);
		String im = m.getCouleur().getUrl();
		imMuldoFemelle.setImage(new Image(im));
		lbNbSailleFemelle.setText("Saillies : "+m.getNbenfant()/2+"/"+m.getNbsaillies());
		lbPropFemelle.setText("Propriétaire : "+m.getProprietaire());
	}

	protected void modifieListMuldoMale() {
		System.out.println("ICI");
		List<Muldo> muldosM = Main.repMuldo.getByGroupes(0, cbosMale);
		males = createList(muldosM, listMale);
		if(muldosM.size() != 0){
			isSelected(males.get(0));
		}
		listMale.getChildren().clear();
		listMale.getChildren().addAll(males);
		
	}
	
	protected void modifieListMuldoFemelle() {
		//TODO
		
	}

	protected void searchListMuldo(Object source) {
		//TODO problème avec la requete
		/*List<ComboBox<IEntities>> listCbo = cbosMale;
		int l = listCbo.size();
		ComboBox<IEntities> cbo;
		boolean isCboFemelle = true;
		//recherche de la comboBox dans la liste Male
		for(int i = 0; i<l ; i++){
			cbo = listCbo.get(i);
			if(source.equals(cbo)){
				isCboFemelle = false;
				modifieListMuldoMale();
				break;
			}
		}
		if(isCboFemelle){
			modifieListMuldoFemelle();
		}*/
	}
	 
}