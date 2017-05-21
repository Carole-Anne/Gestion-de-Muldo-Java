package IHM;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Entities.IEntities;
import Entities.Muldo;
import Services.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectionViewController extends AbstractController implements Initializable{
	
	@FXML
	private ImageView imMuldo;
	@FXML 
	private Label lbNom;
	@FXML
	private Label lbSailliesUsed;
	@FXML
	private Label lbSaillies;
	@FXML
	private Label lbProp;
	@FXML
	private Button btChoisir;
	
	@FXML
	private VBox vbGroupe;
	@FXML
	private ComboBox<IEntities> cboGroupe;
	@FXML
	private VBox listMuldo;
	
	private List<HBox> muldos;
	private int idMuldosSelected = 0;
	private Muldo muldoSelected;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		//Initialisation des ComboBox
		ObservableList<IEntities> groupes = getListGroup();
		cboGroupe.getItems().addAll(groupes);
		
		//Initialisation des listes de muldo
		List<Muldo> listMuldos;
		if(Main.service.getFindMuldo() == 0){
			listMuldos = Main.service.getMuldoMale(Main.service.getmFemelle()); 
		}else{
			listMuldos = Main.service.getMuldoFemelle(Main.service.getmMale()); 
		}
		muldos = createList(listMuldos); 
		if(listMuldos.size() != 0){
			isSelected(muldos.get(0));
		}
		listMuldo.getChildren().addAll(muldos);
		
	}
	
	@Override
	protected void searchListMuldo(Object source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void isSelected(Object hbox) {
		int i = 0;
		int l = muldos.size();
		while(i<l && !hbox.equals(muldos.get(i))){
			i++;
		}
		if(i<l){
			if(idMuldosSelected%2==0){
				muldos.get(idMuldosSelected).getStyleClass().clear();
				muldos.get(idMuldosSelected).getStyleClass().add("pair");
			}else{
				muldos.get(idMuldosSelected).getStyleClass().clear();
				muldos.get(idMuldosSelected).getStyleClass().add("impair");
			}
			muldos.get(i).getStyleClass().clear();
			muldos.get(i).getStyleClass().add("selected");
			idMuldosSelected = i;
			modifieMuldo(muldos.get(i));
		}
	}

	private void modifieMuldo(HBox hBox) {
		Label l = ((Label)hBox.getChildren().get(1));
		Muldo m = Main.service.getRepMuldo().getByName(l.getText()).get(0);
		String im = m.getCouleur().getUrl();
		imMuldo.setImage(new Image(im));
		lbNom.setText(m.getNom());
		lbSailliesUsed.setText(m.getNbSailliesUsed()+"");
		lbProp.setText(""+m.getProprietaire());
		lbSaillies.setText(m.getNbsaillies()+"");
		muldoSelected = m;
	}

	@Override
	protected void suppGroupe(Object o) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private  synchronized void choiceMuldo(){
		if(Main.service.getFindMuldo() == 0){
			Main.service.setmMale(muldoSelected);
		}else{
			Main.service.setmFemelle(muldoSelected);
		}
		notifyAll();
		//Fermer la fenetre
		Stage stage = (Stage) btChoisir.getScene().getWindow();
		stage.close();
	}

}
