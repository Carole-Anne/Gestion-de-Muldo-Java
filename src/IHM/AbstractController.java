package IHM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entities.Couleur;
import Entities.Groupe;
import Entities.IEntities;
import Entities.Muldo;
import Entities.Proprietaire;
import Entities.Troupeau;
import Services.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class AbstractController {
	
	@FXML
	 private void newMuldo() throws IOException {		
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR); 
		double height = Main.primaryStage.getScene().getHeight();
		double width = Main.primaryStage.getScene().getWidth();
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("NewMuldo.fxml"));
		Scene scene = new Scene(root,width,height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT); 
	 }

	 @FXML
	 private void gestionGroupe() throws IOException {	
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR); 
		double height = Main.primaryStage.getScene().getHeight();
		double width = Main.primaryStage.getScene().getWidth();
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GroupeView.fxml"));
		Scene scene = new Scene(root,width,height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT); 
	 }
	 
	 @FXML
	 private void modifieMuldo() throws IOException{
		Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR); 
		double height = Main.primaryStage.getScene().getHeight();
		double width = Main.primaryStage.getScene().getWidth();
		BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("ModifieMuldoView.fxml"));
		Scene scene = new Scene(root,width,height);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT); 
	 }
	 
	 @FXML
	 private void suppMuldo(){
		 //TODO
	 }
	 
	 protected void goPrincipalView() throws IOException {	
		 Main.primaryStage.getScene().setCursor(Cursor.CROSSHAIR); 	
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
		 Main.primaryStage.getScene().setCursor(Cursor.DEFAULT); 
	 }
	 
	 
		protected void addGroupe(VBox vbox){
			List<Node> childrens = vbox.getChildren();
			int nbChildren = childrens.size();
			if(nbChildren == 1){
				HBox children = (HBox)childrens.get(0);
				children.getChildren().add(createButtonSupp());
				childrens.add(createNewGroupe(vbox));
			}else if(nbChildren<5){
				childrens.add(createNewGroupe(vbox));
			}
		}
		
		protected void addTroupeau(VBox vbox){
			List<Node> childrens = vbox.getChildren();
			int nbChildren = childrens.size();
			if(nbChildren == 1){
				HBox children = (HBox)childrens.get(0);
				children.setPadding(new Insets(0, 5, 0, 0));
				children.getChildren().add(createButtonSupp());
				childrens.add(createNewTroupeau(vbox));
			}else if(nbChildren<5){
				childrens.add(createNewTroupeau(vbox));
			}
		}
		
		
		private HBox createNewGroupe(VBox vb){
			HBox newChildren = new HBox();
			newChildren.setAlignment(Pos.CENTER);
			newChildren.setSpacing(5);
			ComboBox<IEntities> newCombobox = createCombobox(getListGroup());
			Button newButtonAdd = createButtonAdd(vb) ;
			Button newButtonSupp = createButtonSupp();
			newChildren.getChildren().add(newCombobox);
			newChildren.getChildren().add(newButtonAdd);
			newChildren.getChildren().add(newButtonSupp);
			return newChildren;
		}
		
		private HBox createNewTroupeau(VBox vb){
			HBox newChildren = new HBox();
			newChildren.setAlignment(Pos.CENTER);
			newChildren.setSpacing(5);
			ComboBox<Troupeau> newCombobox = createCombobobxTroup(getListTroup());
			Button newButtonAdd = createButtonAdd(vb) ;
			Button newButtonSupp = createButtonSupp();
			newChildren.getChildren().add(newCombobox);
			newChildren.getChildren().add(newButtonAdd);
			newChildren.getChildren().add(newButtonSupp);
			return newChildren;
		}
		
		protected ObservableList<IEntities> getListGroup() {
			List<Couleur> list = Main.repColor.getAll();
			list.remove(0);
			List<Groupe> groupes = Main.repGroupe.getAll();
			List<IEntities> entities = new ArrayList<IEntities>(groupes);
			entities.addAll(list);
			ObservableList<IEntities> listGroupe = FXCollections.observableList(entities);
			return listGroupe;
		}

		protected ObservableList<Proprietaire> getListProp() {
			List<Proprietaire> groupes = Main.repProp.getAll();
			List<Proprietaire> entities = new ArrayList<Proprietaire>(groupes);
			ObservableList<Proprietaire> listProp = FXCollections.observableList(entities);
			return listProp;
		}
		
		protected ObservableList<Troupeau> getListTroup() {
			List<Troupeau> groupes = Main.repTroupeau.getAll();
			List<Troupeau> entities = new ArrayList<Troupeau>(groupes);
			ObservableList<Troupeau> listTroup = FXCollections.observableList(entities);
			return listTroup;
		}
		
		private ComboBox<IEntities> createCombobox(ObservableList<IEntities> list){
			ComboBox<IEntities> newCombobox = new ComboBox<IEntities>(list);
			newCombobox.setPrefWidth(130);
			newCombobox.setMinWidth(130);
			newCombobox.setMaxWidth(130);
			newCombobox.getStyleClass().add("combobox");
			return newCombobox;
		}
		
		private ComboBox<Troupeau> createCombobobxTroup(ObservableList<Troupeau> list){
			ComboBox<Troupeau> newCombobox = new ComboBox<Troupeau>(list);
			newCombobox.setPrefWidth(130);
			newCombobox.setMinWidth(130);
			newCombobox.setMaxWidth(130);
			newCombobox.getStyleClass().add("combobox");
			return newCombobox;
		}
		
		private Button createButtonAdd(VBox vb){
			Button suppGroupe = new Button("+");
			suppGroupe.setFont(new Font(10));
			suppGroupe.setPrefWidth(21);
			suppGroupe.setPrefHeight(21);
			suppGroupe.setMaxWidth(21);
			suppGroupe.setMaxHeight(21);
			suppGroupe.setMinWidth(21);
			suppGroupe.setMinHeight(21);
			suppGroupe.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	addGroupe(vb);
	            }
	        });
			return suppGroupe;
		}
		
		
		
		private Button createButtonSupp(){
			Button suppGroupe = new Button("-");
			suppGroupe.setPrefWidth(21);
			suppGroupe.setPrefHeight(21);
			suppGroupe.setMaxWidth(21);
			suppGroupe.setMaxHeight(21);
			suppGroupe.setMinWidth(21);
			suppGroupe.setMinHeight(21);
			suppGroupe.setOnAction(s -> {
					Object o = s.getSource();
	            	suppGroupe(o);
	            }
	        );
			return suppGroupe;
		}
		
		
		protected List<HBox> createList(List<Muldo> muldos, VBox viewList) {
			List<HBox> list = new ArrayList<HBox>();
			for(int i = 0; i<muldos.size(); i++){
				Muldo m = muldos.get(i);
				HBox newMuldo = new HBox();
				newMuldo.setSpacing(5.0);
				newMuldo.setAlignment(Pos.CENTER_LEFT);
				if(i%2 == 0){
					newMuldo.getStyleClass().add("pair");
				}else{
					newMuldo.getStyleClass().add("impair");
				}
				ImageView im = new ImageView(m.getCouleur().getUrlIcon());
				Label name = new Label(m.getNom());
				name.getStyleClass().add("labelList");
				newMuldo.getChildren().addAll(im, name);
				newMuldo.setOnMouseClicked(hbox ->{
					isSelected(hbox.getSource());
				});
				
				list.add(newMuldo);
			}
			while(list.size()<8){
				HBox newMuldo = new HBox();
				newMuldo.setSpacing(5.0);
				newMuldo.setMaxHeight(25);
				newMuldo.setMinHeight(25);
				newMuldo.setPrefHeight(25);
				newMuldo.setAlignment(Pos.CENTER_LEFT);
				if(list.size()%2 == 0){
					newMuldo.getStyleClass().add("pair");
				}else{
					newMuldo.getStyleClass().add("impair");
				}
				list.add(newMuldo);
			}
			return list;
		}
		
		@FXML
		private void actionCombobox(ActionEvent e){
			searchListMuldo(e.getSource());
		}

		protected abstract void searchListMuldo(Object source);

		protected abstract void isSelected(Object hbox);

		protected abstract void suppGroupe(Object o);
	 

}
