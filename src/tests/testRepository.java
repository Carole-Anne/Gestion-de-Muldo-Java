package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.Test;
import Entities.Couleur;
import Entities.Muldo;
import Entities.Proprietaire;
import Entities.Troupeau;
import Repositories.RepositoryCouleur;
import Repositories.RepositoryGroupe;
import Repositories.RepositoryMuldo;
import Services.CreateEntityManagerFactory;
import Services.Service;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class testRepository {
	
	RepositoryCouleur repColor;
	RepositoryGroupe repGroupe;
	RepositoryMuldo repMuldo;
	Service service;
	
	
	
	@Before
	public void init(){
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstanceTest();
		EntityManager em = emf.createEntityManager();
		service = new Service(em);
		repColor = service.getRepColor();
		repGroupe = service.getRepGroupe();
		repMuldo = service.getRepMuldo();
	}

	@Test
	public void testGetAllCouleur() {
		List<Couleur> colors = repColor.getAll();
		assertEquals(62, colors.size());
	}
	
	@Test
	public void testGetFirstCouleur() {
		List<Couleur> colors = repColor.getAll();
		Couleur c = colors.get(0);
		String couleur = c.getNom();
		assertEquals("Amande", couleur);
	}
	
	@Test
	public void testIsNotFind(){
		Troupeau t = new Troupeau();
		t.setNom("Doré");
		repGroupe.add(t);
		repMuldo.commit();
		assertTrue(repGroupe.getNameFind("Doré"));
		repGroupe.remove(t);
		repGroupe.commit();
		assertFalse(repGroupe.getNameFind("Doré"));
	}
	
	@Test
	public void testAddRemoveProp(){		
		Proprietaire prop = new Proprietaire();
		prop.setNom("Test");
		
		repGroupe.add(prop);
		repMuldo.commit();
		//Groupe "Test" existe
		assertTrue(repGroupe.getNameFind("Test"));
		
		repGroupe.remove(prop);
		repGroupe.commit();
		//Groupe "Test" n'existe pas
		assertFalse(repGroupe.getNameFind("Test"));
	}
	
	  @Test
	  public void testDeleteMuldo(){
		//Ajout muldo
			Couleur c = repColor.getById(2);
			Muldo m = new Muldo();
			m.setNom("GrandParent");
			m.setCouleur(c);
			m.setNbsaillies(4);
			m.setSexe(0);
			m.setMuldoPere(repMuldo.getById(1));
			m.setMuldoMere(repMuldo.getById(2));
			m.setFecond(false);
			m.setNbenfant(2);
			m.setVisible(true);
			repMuldo.add(m);
			
			Muldo m2 =new Muldo();
			m2.setNom("Parent");
			m2.setCouleur(c);
			m2.setNbsaillies(4);
			m2.setSexe(0);
			m2.setMuldoPere(m);
			m2.setMuldoMere(repMuldo.getById(2));
			m2.setFecond(false);
			m2.setNbenfant(1);
			m2.setVisible(true);
			repMuldo.add(m2);
			
			Muldo m3 =new Muldo();
			m3.setNom("Enfant");
			m3.setCouleur(c);
			m3.setNbsaillies(4);
			m3.setSexe(0);
			m3.setMuldoPere(m2);
			m3.setMuldoMere(repMuldo.getById(2));
			m3.setMuldoPere(m2);
			m3.setFecond(false);
			m3.setNbenfant(0);
			m3.setVisible(true);
			repMuldo.add(m3);
		  
		  List<Muldo> muldos = repMuldo.getAll();
		  
		  assertEquals(5, muldos.size());
		  
		  Muldo Parent = repMuldo.getByName("GrandParent").get(0);
		  service.delete(Parent);
		  muldos = repMuldo.getAll();
		  assertEquals(5, muldos.size());
		  
		  Parent = repMuldo.getByName("Parent").get(0);
		  service.delete(Parent);
		  muldos = repMuldo.getAll();
		  assertEquals(5, muldos.size());
		  
		  Parent = repMuldo.getByName("Enfant").get(0);
		  service.delete(Parent);
		  muldos = repMuldo.getAll();
		  assertEquals(2, muldos.size());
	  }
	  
	  @Test
	  public void testAddMuldo(){
		  Couleur c = repColor.getById(2);
		  service.addMuldo("test", "4", 0, 2, 1, c, null, new ArrayList<Node>());
		  
		  Muldo grandParent = repMuldo.getByName("test").get(0);
		  
		  service.addMuldo("parent", "4", 0, 2, grandParent.getId(), c, null, new ArrayList<Node>());
		  
		  Muldo parent = repMuldo.getByName("parent").get(0);
		  
		  service.addMuldo("enfant", "2", 0, 2, parent.getId(), c, null, new ArrayList<Node>());
		  
		  Muldo enfant = repMuldo.getByName("enfant").get(0);
		  
		  assertEquals(new Integer(2), grandParent.getNbenfant());
		  
		  service.delete(grandParent);
		  service.delete(parent);
		  service.delete(enfant);
		  
		  List<Muldo> muldos = repMuldo.getAll();
		  assertEquals(2, muldos.size());
	  }
	  
}
