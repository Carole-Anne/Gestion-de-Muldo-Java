package Services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import Entities.*;
import Repositories.RepositoryCouleur;
import Repositories.RepositoryGroupe;
import Repositories.RepositoryMuldo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage;
	public static RepositoryCouleur repColor;
	public static RepositoryMuldo repMuldo;
	public static RepositoryGroupe repGroupe;
	
	
	@Override
	public void start(Stage ps) {
		try {
			primaryStage = ps;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("./../IHM/PrincipalView.fxml"));
			Scene scene = new Scene(root,850,500);
			scene.getStylesheets().add(getClass().getResource("./../IHM/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		repColor = new RepositoryCouleur(em);
		List<Couleur> colors = repColor.getAll();
		if(colors.isEmpty()){
			initColor();
		}
		repGroupe = new RepositoryGroupe(em);
		repMuldo = new RepositoryMuldo(em);
		
		launch(args);
	}

	/**
	 * Initialise/Remplit la table Couleur et cr�ation d'un propri�taire
	 */
	private static void initColor() {
		Couleur c = new Couleur();
		c.setCouleur("Amande");
		c.setUrl("./image/amande.png");
		c.setUrlIcon("./image/icon/amande.png");
		repColor.save(c);
		
		Couleur c1 = new Couleur();
		c1.setCouleur("Amande Emeraude");
		c1.setUrl("./image/amandeEmeraude.png");
		c1.setUrlIcon("./image/icon/amandeEmeraude.png");
		repColor.save(c1);
		
		Couleur c2 = new Couleur();
		c2.setCouleur("Amande Ivoire");
		c2.setUrl("./image/amandeIvoire.png");
		c2.setUrlIcon("./image/icon/amandeIvoire.png");
		repColor.save(c2);
		
		Couleur c3 = new Couleur();
		c3.setCouleur("Dor�");
		c3.setUrl("./image/dore.png");
		c3.setUrlIcon("./image/icon/dore.png");
		repColor.save(c3);
		
		Couleur c4 = new Couleur();
		c4.setCouleur("Dor� Amande");
		c4.setUrl("./image/doreAmande.png");
		c4.setUrlIcon("./image/icon/doreAmande.png");
		repColor.save(c4);

		Couleur c5 = new Couleur();
		c5.setCouleur("Dor� Eb�ne");
		c5.setUrl("./image/doreEbene.png");
		c5.setUrlIcon("./image/icon/doreEbene.png");
		repColor.save(c5);

		Couleur c6 = new Couleur();
		c6.setCouleur("Dor� Emeraude");
		c6.setUrl("./image/doreEmeraude.png");
		c6.setUrlIcon("./image/icon/doreEmeraude.png");
		repColor.save(c6);

		Couleur c7 = new Couleur();
		c7.setCouleur("Dor� Indigo");
		c7.setUrl("./image/doreIndigo.png");
		c7.setUrlIcon("./image/icon/doreIndigo.png");
		repColor.save(c7);

		Couleur c8 = new Couleur();
		c8.setCouleur("Dor� Ivoire");
		c8.setUrl("./image/doreIvoire.png");
		c8.setUrlIcon("./image/icon/doreIvoire.png");
		repColor.save(c8);

		Couleur c9 = new Couleur();
		c9.setCouleur("Dor� Orchid�");
		c9.setUrl("./image/doreOrchi.png");
		c9.setUrlIcon("./image/icon/doreOrchi.png");
		repColor.save(c9);

		Couleur c10 = new Couleur();
		c10.setCouleur("Dor� Pourpre");
		c10.setUrl("./image/dorePourpre.png");
		c10.setUrlIcon("./image/icon/dorePourpre.png");
		repColor.save(c10);

		Couleur c11 = new Couleur();
		c11.setCouleur("Eb�ne");
		c11.setUrl("./image/ebene.png");
		c11.setUrlIcon("./image/icon/ebene.png");
		repColor.save(c11);

		Couleur c12 = new Couleur();
		c12.setCouleur("Eb�ne Amande");
		c12.setUrl("./image/ebeneAmande.png");
		c12.setUrlIcon("./image/icon/ebeneAmande.png");
		repColor.save(c12);

		Couleur c13 = new Couleur();
		c13.setCouleur("Eb�ne Emeraude");
		c13.setUrl("./image/ebeneEmeraude.png");
		c13.setUrlIcon("./image/icon/ebeneEmeraude.png");
		repColor.save(c13);

		Couleur c14 = new Couleur();
		c14.setCouleur("Eb�ne Indigo");
		c14.setUrl("./image/ebeneIndigo.png");
		c14.setUrlIcon("./image/icon/ebeneIndigo.png");
		repColor.save(c14);

		Couleur c15 = new Couleur();
		c15.setCouleur("Eb�ne Ivoire");
		c15.setUrl("./image/ebeneIvoire.png");
		c15.setUrlIcon("./image/icon/ebeneIvoire.png");
		repColor.save(c15);

		Couleur c16 = new Couleur();
		c16.setCouleur("Eb�ne Orchid�");
		c16.setUrl("./image/ebeneOrchi.png");
		c16.setUrlIcon("./image/icon/ebeneOrchi.png");
		repColor.save(c16);

		Couleur c17 = new Couleur();
		c17.setCouleur("Eb�ne Pourpre");
		c17.setUrl("./image/ebenePourpre.png");
		c17.setUrlIcon("./image/icon/ebenePourpre.png");
		repColor.save(c17);

		Couleur c18 = new Couleur();
		c18.setCouleur("Emeraude");
		c18.setUrl("./image/emeraude.png");
		c18.setUrlIcon("./image/icon/emeraude.png");
		repColor.save(c18);

		Couleur c19 = new Couleur();
		c19.setCouleur("Indigo");
		c19.setUrl("./image/indigo.png");
		c19.setUrlIcon("./image/icon/indigo.png");
		repColor.save(c19);

		Couleur c20 = new Couleur();
		c20.setCouleur("Indigo Amande");
		c20.setUrl("./image/indigoAmande.png");
		c20.setUrlIcon("./image/icon/indigoAmande.png");
		repColor.save(c20);

		Couleur c21 = new Couleur();
		c21.setCouleur("Indigo Emeraude");
		c21.setUrl("./image/indigoEmeraude.png");
		c21.setUrlIcon("./image/icon/indigoEmeraude.png");
		repColor.save(c21);

		Couleur c22 = new Couleur();
		c22.setCouleur("Indigo Ivoire");
		c22.setUrl("./image/indigoIvoire.png");
		c22.setUrlIcon("./image/icon/indigoIvoire.png");
		repColor.save(c22);

		Couleur c23 = new Couleur();
		c23.setCouleur("Indigo Orchid�");
		c23.setUrl("./image/indigoOrchi.png");
		c23.setUrlIcon("./image/icon/indigoOrchi.png");
		repColor.save(c23);

		Couleur c24 = new Couleur();
		c24.setCouleur("Indigo Pourpre");
		c24.setUrl("./image/indigoPourpre.png");
		c24.setUrlIcon("./image/icon/indigoPourpre.png");
		repColor.save(c24);

		Couleur c25 = new Couleur();
		c25.setCouleur("Ivoire");
		c25.setUrl("./image/ivoire.png");
		c25.setUrlIcon("./image/icon/ivoire.png");
		repColor.save(c25);

		Couleur c29 = new Couleur();
		c29.setCouleur("Ivoire Emeraude");
		c29.setUrl("./image/ivoireEmeraude.png");
		c29.setUrlIcon("./image/icon/ivoireEmeraude.png");
		repColor.save(c29);

		Couleur c26 = new Couleur();
		c26.setCouleur("Orchid�");
		c26.setUrl("./image/orchidee.png");
		c26.setUrlIcon("./image/icon/orchidee.png");
		repColor.save(c26);

		Couleur c27 = new Couleur();
		c27.setCouleur("Orchid� Amande");
		c27.setUrl("./image/orchiAmande.png");
		c27.setUrlIcon("./image/icon/orchiAmande.png");
		repColor.save(c27);

		Couleur c28 = new Couleur();
		c28.setCouleur("Orchid� Emeraude");
		c28.setUrl("./image/orchiEmeraude.png");
		c28.setUrlIcon("./image/icon/orchiEmeraude.png");
		repColor.save(c28);

		Couleur c30 = new Couleur();
		c30.setCouleur("Orchid� Ivoire");
		c30.setUrl("./image/orchiIvoire.png");
		c30.setUrlIcon("./image/icon/orchiIvoire.png");
		repColor.save(c30);

		Couleur c31 = new Couleur();
		c31.setCouleur("Orchid� Pourpre");
		c31.setUrl("./image/orchiPourpre.png");
		c31.setUrlIcon("./image/icon/orchiPourpre.png");
		repColor.save(c31);

		Couleur c32 = new Couleur();
		c32.setCouleur("Pourpre");
		c32.setUrl("./image/pourpre.png");
		c32.setUrlIcon("./image/icon/pourpre.png");
		repColor.save(c32);

		Couleur c33 = new Couleur();
		c33.setCouleur("Pourpre Amande");
		c33.setUrl("./image/pourpreAmande.png");
		c33.setUrlIcon("./image/icon/pourpreAmande.png");
		repColor.save(c33);

		Couleur c34 = new Couleur();
		c34.setCouleur("Pourpre Emeraude");
		c34.setUrl("./image/pourpreEmeraude.png");
		c34.setUrlIcon("./image/icon/pourpreEmeraude.png");
		repColor.save(c34);

		Couleur c35 = new Couleur();
		c35.setCouleur("Pourpre Ivoire");
		c35.setUrl("./image/pourpreIvoire.png");
		c35.setUrlIcon("./image/icon/pourpreIvoire.png");
		repColor.save(c35);

		Couleur c36 = new Couleur();
		c36.setCouleur("Prune");
		c36.setUrl("./image/prune.png");
		c36.setUrlIcon("./image/icon/prune.png");
		repColor.save(c36);

		Couleur c37 = new Couleur();
		c37.setCouleur("Prune Dor�");
		c37.setUrl("./image/pruneDore.png");
		c37.setUrlIcon("./image/icon/pruneDore.png");
		repColor.save(c37);

		Couleur c38 = new Couleur();
		c38.setCouleur("Prune Eb�ne");
		c38.setUrl("./image/pruneEbene.png");
		c38.setUrlIcon("./image/icon/pruneEbene.png");
		repColor.save(c38);

		Couleur c39 = new Couleur();
		c39.setCouleur("Prune Emeraude");
		c39.setUrl("./image/pruneEmeraude.png");
		c39.setUrlIcon("./image/icon/pruneEmeraude.png");
		repColor.save(c39);

		Couleur c40 = new Couleur();
		c40.setCouleur("Prune Indigo");
		c40.setUrl("./image/pruneIndigo.png");
		c40.setUrlIcon("./image/icon/pruneIndigo.png");
		repColor.save(c40);

		Couleur c41 = new Couleur();
		c41.setCouleur("Prune Orchid�");
		c41.setUrl("./image/pruneOrchi.png");
		c41.setUrlIcon("./image/icon/pruneOrchi.png");
		repColor.save(c41);

		Couleur c42 = new Couleur();
		c42.setCouleur("Prune Pourpre");
		c42.setUrl("./image/prunePourpre.png");
		c42.setUrlIcon("./image/icon/prunePourpre.png");
		repColor.save(c42);

		Couleur c43 = new Couleur();
		c43.setCouleur("Roux");
		c43.setUrl("./image/roux.png");
		c43.setUrlIcon("./image/icon/roux.png");
		repColor.save(c43);

		Couleur c44 = new Couleur();
		c44.setCouleur("Roux Amande");
		c44.setUrl("./image/rouxAmande.png");
		c44.setUrlIcon("./image/icon/rouxAmande.png");
		repColor.save(c44);

		Couleur c45 = new Couleur();
		c45.setCouleur("Roux Dor�");
		c45.setUrl("./image/rouxDore.png");
		c45.setUrlIcon("./image/icon/rouxDore.png");
		repColor.save(c45);

		Couleur c46 = new Couleur();
		c46.setCouleur("Roux Eb�ne");
		c46.setUrl("./image/rouxEbene.png");
		c46.setUrlIcon("./image/icon/rouxEbene.png");
		repColor.save(c46);

		Couleur c47 = new Couleur();
		c47.setCouleur("Roux Emeraude");
		c47.setUrl("./image/rouxEmeraude.png");
		c47.setUrlIcon("./image/icon/rouxEmeraude.png");
		repColor.save(c47);

		Couleur c48 = new Couleur();
		c48.setCouleur("Roux Indigo");
		c48.setUrl("./image/rouxIndigo.png");
		c48.setUrlIcon("./image/icon/rouxIndigo.png");
		repColor.save(c48);

		Couleur c49 = new Couleur();
		c49.setCouleur("Roux Orchid�");
		c49.setUrl("./image/rouxOrchi.png");
		c49.setUrlIcon("./image/icon/rouxOrchi.png");
		repColor.save(c49);

		Couleur c50 = new Couleur();
		c50.setCouleur("Roux Pourpre");
		c50.setUrl("./image/rouxPourpre.png");
		c50.setUrlIcon("./image/icon/rouxPourpre.png");
		repColor.save(c50);

		Couleur c51 = new Couleur();
		c51.setCouleur("Turquoise");
		c51.setUrl("./image/turquoise.png");
		c51.setUrlIcon("./image/icon/turquoise.png");
		repColor.save(c51);

		Couleur c52 = new Couleur();
		c52.setCouleur("Turquoise Amande");
		c52.setUrl("./image/turquoiseAmande.png");
		c52.setUrlIcon("./image/icon/turquoiseAmande.png");
		repColor.save(c52);

		Couleur c53 = new Couleur();
		c53.setCouleur("Turquoise Dor�");
		c53.setUrl("./image/turquoiseDore.png");
		c53.setUrlIcon("./image/icon/turquoiseDore.png");
		repColor.save(c53);

		Couleur c54 = new Couleur();
		c54.setCouleur("Turquoise Eb�ne");
		c54.setUrl("./image/turquoiseEbene.png");
		c54.setUrlIcon("./image/icon/turquoiseEbene.png");
		repColor.save(c54);

		Couleur c55 = new Couleur();
		c55.setCouleur("Turquoise Emeraude");
		c55.setUrl("./image/turquoiseEmeraude.png");
		c55.setUrlIcon("./image/icon/turquoiseEmeraude.png");
		repColor.save(c55);

		Couleur c56 = new Couleur();
		c56.setCouleur("Turquoise Indigo");
		c56.setUrl("./image/turquoiseIndigo.png");
		c56.setUrlIcon("./image/icon/turquoise.png");
		repColor.save(c56);

		Couleur c57 = new Couleur();
		c57.setCouleur("Turquoise Ivoire");
		c57.setUrl("./image/turquoiseIvoire.png");
		c57.setUrlIcon("./image/icon/turquoiseIvoire.png");
		repColor.save(c57);

		Couleur c58 = new Couleur();
		c58.setCouleur("Turquoise Orchid�");
		c58.setUrl("./image/turquoiseOrchi.png");
		c58.setUrlIcon("./image/icon/turquoiseOrchi.png");
		repColor.save(c58);

		Couleur c59 = new Couleur();
		c59.setCouleur("Turquoise Pourpre");
		c59.setUrl("./image/turquoisePourpre.png");
		c59.setUrlIcon("./image/icon/turquoisePourpre.png");
		repColor.save(c59);

		Couleur c60 = new Couleur();
		c60.setCouleur("Turquoise Roux");
		c60.setUrl("./image/turquoiseRoux.png");
		c60.setUrlIcon("./image/icon/turquoiseRoux.png");
		repColor.save(c60);
		
		repColor.commit();
	}

}