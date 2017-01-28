package Repositories;

import java.util.List;

import javax.persistence.EntityManager;
import Entities.Groupe;

public class RepositoryGroupe extends AbstractRepository<Groupe>{
	
	public RepositoryGroupe(EntityManager em){
		setEntityManager(em);
	}
	
	@SuppressWarnings("unchecked")
	public List<Groupe> getAllGroupe(){
		return getEntityManager().createNamedQuery("SELECT g FROM Groupe g").getResultList();
	}

}
