package tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;

import Entities.Couleur;
import Repositories.RepositoryCouleur;
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
		String couleur = c.getCouleur();
		assertEquals("Amande", couleur);
	}

}
