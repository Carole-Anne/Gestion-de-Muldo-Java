package tests;

import static org.junit.Assert.*;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import Entities.Couleur;
import Entities.Proprietaire;
import Repositories.RepositoryCouleur;
import Repositories.RepositoryGroupe;
import Services.CreateEntityManagerFactory;

public class testRepository {

	@Test
	public void testGetAllCouleur() {
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		RepositoryCouleur repColor = new RepositoryCouleur(em);
		List<Couleur> colors = repColor.getAll();
		assertEquals(61, colors.size());
	}
	
	@Test
	public void testGetFirstCouleur() {
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		RepositoryCouleur repColor = new RepositoryCouleur(em);
		List<Couleur> colors = repColor.getAll();
		Couleur c = colors.get(0);
		String couleur = c.getNom();
		assertEquals("Amande", couleur);
	}
	
	@Test
	public void testIsNotFind(){
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		RepositoryGroupe repGroupe = new RepositoryGroupe(em);
		assertTrue(repGroupe.getNameFind("Doré"));
		assertFalse(repGroupe.getNameFind("Pure Doré"));
	}
	
	@Test
	public void testAddRemoveProp(){
		EntityManagerFactory emf = CreateEntityManagerFactory.getInstance();
		EntityManager em = emf.createEntityManager();
		RepositoryGroupe repGroupe = new RepositoryGroupe(em);
		
		Proprietaire prop = new Proprietaire();
		prop.setNom("Test");
		
		repGroupe.add(prop);
		//Groupe "Test" existe
		assertFalse(repGroupe.getNameFind("Test"));
		
		repGroupe.remove(prop);
		//Groupe "Test" n'existe pas
		assertTrue(repGroupe.getNameFind("Test"));
	}

}
