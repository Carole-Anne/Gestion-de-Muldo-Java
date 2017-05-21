package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import Entities.Muldo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage;
	public static Service service;

	@Override
	public void start(Stage ps) {
		try {		
			primaryStage = ps;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("./../IHM/PrincipalView.fxml"));
			Scene scene = new Scene(root,950,500);
			scene.getStylesheets().add(getClass().getResource("./../IHM/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		EntityManagerFactory emf;
		EntityManager em;
		try{
			emf = CreateEntityManagerFactory.getInstance();
			em = emf.createEntityManager();
			service = new Service(em);
			
		}catch(Exception e){
			em = createBD();
			service = new Service(em);
			service.initColor();
			service.initMuldo();
			service.initProp();
		}
		launch(args);
	}

	private static EntityManager createBD() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "formation"); 
		Statement statement = connection.createStatement(); 
		statement.execute("CREATE DATABASE muldo"); 
		
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/muldo", "postgres", "formation"); 
		statement = connection.createStatement(); 
		//Table couleur
		statement.execute("CREATE TABLE public.couleur ("
				+"id SERIAL NOT NULL,"
				+"nom character varying NOT NULL,"
				+"url character varying(80) NOT NULL,"
				+"urlicon character varying(80) NOT NULL,"
				+"nbfemelle integer NOT NULL DEFAULT 0,"
				+"nbmale integer NOT NULL DEFAULT 0,"
				+"CONSTRAINT couleur_pkey PRIMARY KEY (id)"
				+") WITH (OIDS = FALSE)"
				+"TABLESPACE pg_default");
		statement.execute("ALTER TABLE public.couleur OWNER to postgres");
		
		//Table groupe
		statement.execute("CREATE TABLE public.groupe("
				+"id SERIAL NOT NULL,"
				+"nom character varying(50) NOT NULL,"
				+"type character varying(50) NOT NULL,"
				+"CONSTRAINT groupe_pkey PRIMARY KEY (id))"
				+"WITH (OIDS = FALSE) TABLESPACE pg_default");
		statement.execute("ALTER TABLE public.couleur OWNER to postgres");
		
		//Table muldo
		statement.execute("CREATE TABLE public.muldo ("
				+"id SERIAL NOT NULL,"
				+"idcouleur integer NOT NULL,"
				+"nom character varying(30) NOT NULL,"
				+"nbenfant integer NOT NULL DEFAULT 0,"
				+"sexe integer NOT NULL,"
				+"visible boolean NOT NULL DEFAULT true,"
				+"fecond boolean NOT NULL DEFAULT false,"
				+"nbsaillies integer NOT NULL,"
				+"idmere integer,"
				+"idpere integer,"
				+"nbsailliesused integer NOT NULL DEFAULT 0,"
				+"CONSTRAINT muldo_pkey PRIMARY KEY (id),"
				+"CONSTRAINT fk_id_idcouleur FOREIGN KEY (idcouleur) "
				+"REFERENCES public.couleur (id) MATCH SIMPLE "
				+"ON UPDATE NO ACTION "
				+"ON DELETE NO ACTION,"
				+"CONSTRAINT fk_id_idmere FOREIGN KEY (idmere) "
				+"REFERENCES public.muldo (id) MATCH SIMPLE "
				+"ON UPDATE NO ACTION "
				+"ON DELETE NO ACTION, "
				+"CONSTRAINT fk_id_idpere FOREIGN KEY (idpere) "
				+"REFERENCES public.muldo (id) MATCH SIMPLE "
				+"ON UPDATE NO ACTION "
				+"ON DELETE NO ACTION) WITH (OIDS = FALSE) "
				+"TABLESPACE pg_default");
		statement.execute("ALTER TABLE public.muldo OWNER to postgres");
		statement.execute("CREATE INDEX fki_fk_id_idcouleur "
				+"ON public.muldo USING btree (idcouleur) TABLESPACE pg_default");
		statement.execute("CREATE INDEX fki_fk_id_idmere "
				+"ON public.muldo USING btree (idmere) TABLESPACE pg_default");
		statement.execute("CREATE INDEX fki_fk_id_idpere "
				+"ON public.muldo USING btree (idpere) TABLESPACE pg_default");
		
		//Table muldogroupe
		statement.execute("CREATE TABLE public.muldogroupe ("
				+"idmuldo integer NOT NULL,"
				+"idgroupe integer NOT NULL,"
				+"CONSTRAINT muldogroupe_pkey PRIMARY KEY (idgroupe, idmuldo),"
				+"CONSTRAINT fk_id_idgroupe FOREIGN KEY (idgroupe) "
				+"REFERENCES public.groupe (id) MATCH SIMPLE "
				+"ON UPDATE NO ACTION "
				+"ON DELETE NO ACTION, "
				+"CONSTRAINT fk_id_idmuldo FOREIGN KEY (idmuldo) "
				+"REFERENCES public.muldo (id) MATCH SIMPLE "
				+"ON UPDATE NO ACTION ON DELETE NO ACTION) "
				+"WITH (OIDS = FALSE) TABLESPACE pg_default");
		statement.execute("ALTER TABLE public.muldogroupe OWNER to postgres");
		statement.execute("CREATE INDEX fki_fk_id_idgroupe ON public.muldogroupe USING btree "
				+"(idgroupe) TABLESPACE pg_default");
		statement.execute("CREATE INDEX fki_fk_id_idmuldo ON public.muldogroupe USING btree "
				+"(idmuldo) TABLESPACE pg_default");
		
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		
		return em;
	}

	
}
