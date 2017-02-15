package IHM;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.Muldo;
import Services.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ArbreMuldoController extends AbstractController implements Initializable{
	
	@FXML
	private BorderPane rootPane;
	@FXML
	private HBox muldo;
	@FXML
	private HBox parents;
	@FXML
	private HBox grandParents;
	@FXML
	private HBox ArriereGrandParents;
	
	@FXML
	private ImageView imMuldo;
	@FXML
	private Label lbMuldo;
	@FXML
	private ImageView imParents1;
	@FXML
	private Label lbParents1;
	@FXML
	private ImageView imParents2;
	@FXML
	private Label lbParents2;
	@FXML
	private ImageView imGrandParents1;
	@FXML
	private Label lbGrandParents1;
	@FXML
	private ImageView imGrandParents2;
	@FXML
	private Label lbGrandParents2;
	@FXML
	private ImageView imGrandParents3;
	@FXML
	private Label lbGrandParents3;
	@FXML
	private ImageView imGrandParents4;
	@FXML
	private Label lbGrandParents4;
	@FXML
	private ImageView imArriereGrandParents1;
	@FXML
	private Label lbArriereGrandParents1;
	@FXML
	private ImageView imArriereGrandParents2;
	@FXML
	private Label lbArriereGrandParents2;
	@FXML
	private ImageView imArriereGrandParents3;
	@FXML
	private Label lbArriereGrandParents3;
	@FXML
	private ImageView imArriereGrandParents4;
	@FXML
	private Label lbArriereGrandParents4;
	@FXML
	private ImageView imArriereGrandParents5;
	@FXML
	private Label lbArriereGrandParents5;
	@FXML
	private ImageView imArriereGrandParents6;
	@FXML
	private Label lbArriereGrandParents6;
	@FXML
	private ImageView imArriereGrandParents7;
	@FXML
	private Label lbArriereGrandParents7;
	@FXML
	private ImageView imArriereGrandParents8;
	@FXML
	private Label lbArriereGrandParents8;

	@Override
	protected void searchListMuldo(Object source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void isSelected(Object hbox) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void suppGroupe(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		System.out.println(rootPane.widthProperty());
		Muldo[] arbreMuldo = Main.service.getArbre();
		imMuldo.setImage(new Image(arbreMuldo[0].getCouleur().getUrl()));
		lbMuldo.setText(arbreMuldo[0].getNom());
		imParents1.setImage(new Image(arbreMuldo[1].getCouleur().getUrl()));
		lbParents1.setText(arbreMuldo[1].getNom());
		imParents2.setImage(new Image(arbreMuldo[2].getCouleur().getUrl()));
		lbParents2.setText(arbreMuldo[2].getNom());
		if(arbreMuldo[3] != null){
			imGrandParents1.setImage(new Image(arbreMuldo[3].getCouleur().getUrl()));
			lbGrandParents1.setText(arbreMuldo[3].getNom());
			if(arbreMuldo[7] != null){
				imArriereGrandParents1.setImage(new Image(arbreMuldo[7].getCouleur().getUrl()));
				lbArriereGrandParents1.setText(arbreMuldo[7].getNom());
			}
			if(arbreMuldo[8] != null){
				imArriereGrandParents2.setImage(new Image(arbreMuldo[8].getCouleur().getUrl()));
				lbArriereGrandParents2.setText(arbreMuldo[8].getNom());
			}
		}
		if(arbreMuldo[4] != null){
			imGrandParents2.setImage(new Image(arbreMuldo[4].getCouleur().getUrl()));
			lbGrandParents2.setText(arbreMuldo[4].getNom());
			if(arbreMuldo[9] != null){
				imArriereGrandParents3.setImage(new Image(arbreMuldo[9].getCouleur().getUrl()));
				lbArriereGrandParents3.setText(arbreMuldo[9].getNom());
			}
			if(arbreMuldo[10] != null){
				imArriereGrandParents4.setImage(new Image(arbreMuldo[10].getCouleur().getUrl()));
				lbArriereGrandParents4.setText(arbreMuldo[10].getNom());
			}
		}
		if(arbreMuldo[5] != null){
			imGrandParents3.setImage(new Image(arbreMuldo[5].getCouleur().getUrl()));
			lbGrandParents3.setText(arbreMuldo[5].getNom());
			if(arbreMuldo[11] != null){
				imArriereGrandParents5.setImage(new Image(arbreMuldo[11].getCouleur().getUrl()));
				lbArriereGrandParents5.setText(arbreMuldo[11].getNom());
			}
			if(arbreMuldo[12] != null){
				imArriereGrandParents6.setImage(new Image(arbreMuldo[12].getCouleur().getUrl()));
				lbArriereGrandParents6.setText(arbreMuldo[12].getNom());
			}
		}
		if(arbreMuldo[6] != null){
			imGrandParents4.setImage(new Image(arbreMuldo[6].getCouleur().getUrl()));
			lbGrandParents4.setText(arbreMuldo[6].getNom());
			if(arbreMuldo[13] != null){
				imArriereGrandParents7.setImage(new Image(arbreMuldo[13].getCouleur().getUrl()));
				lbArriereGrandParents7.setText(arbreMuldo[13].getNom());
			}
			if(arbreMuldo[14] != null){
				imArriereGrandParents8.setImage(new Image(arbreMuldo[14].getCouleur().getUrl()));
				lbArriereGrandParents8.setText(arbreMuldo[14].getNom());
			}
		}
		
	}

}
