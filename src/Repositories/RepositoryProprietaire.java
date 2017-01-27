package Repositories;

import java.util.List;

import javax.persistence.EntityManager;
import Entities.Proprietaire;

public class RepositoryProprietaire extends AbstractRepository<Proprietaire>{
	
	public RepositoryProprietaire(EntityManager em){
		setEntityManager(em);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Proprietaire> getAll(){
		return getEntityManager().createQuery("SELECT g FROM Groupe g WHERE type="+1).getResultList();
	}

}
