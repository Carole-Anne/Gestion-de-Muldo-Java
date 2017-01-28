package IHM;

import java.io.IOException;
import java.util.List;

import Entities.IEntities;
import Services.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class AbstractController {
	
	@FXML
	 private void newMuldo() throws IOException {		 
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("NewMuldo.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }

	 @FXML
	 private void gestionGroupe() throws IOException {	
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GroupeView.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }
	 
	 @FXML
	 private void modifieMuldo() throws IOException{
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("ModifieMuldoView.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }
	 
	 @FXML
	 private void suppMuldo(){
		 //TODO
	 }
	 
	 protected void goPrincipalView() throws IOException {		
		 double height = Main.primaryStage.getScene().getHeight();
		 double width = Main.primaryStage.getScene().getWidth();
		 BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PrincipalView.fxml"));
		 Scene scene = new Scene(root,width,height);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 Main.primaryStage.setScene(scene);
		 Main.primaryStage.show();
	 }
	 
	 
		protected void addGroupe(VBox vbox){
			List<Node> childrens = vbox.getChildren();
			int nbChildren = childrens.size();
			if(nbChildren == 1){
				HBox children = (HBox)childrens.get(0);
				children.setPadding(new Insets(0, 5, 0, 0));
				children.getChildren().add(createButtonSupp());
				childrens.add(createNewGroupe(vbox));
			}else if(nbChildren<5){
				childrens.add(createNewGroupe(vbox));
			}
		}
		
		private HBox createNewGroupe(VBox vb){
			HBox newChildren = new HBox();
			newChildren.setAlignment(Pos.CENTER);
			newChildren.setSpacing(5);
			ComboBox<IEntities> newCombobox = new ComboBox<IEntities>();
			//ComboBox<IEntities> newCombobox = createCombobobx(getListGroup());
			Button newButtonAdd = createButtonAdd(vb) ;
			Button newButtonSupp = createButtonSupp();
			newChildren.getChildren().add(newCombobox);
			newChildren.getChildren().add(newButtonAdd);
			newChildren.getChildren().add(newButtonSupp);
			return newChildren;
		}
		
		private ComboBox<IEntities> createCombobobx(ObservableList<IEntities> list){
			ComboBox<IEntities> newCombobox = new ComboBox<IEntities>(list);
			newCombobox.setPrefWidth(100);
			newCombobox.setMinWidth(100);
			newCombobox.setMaxWidth(100);
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
		
		protected abstract void suppGroupe(Object o);
	 

}
