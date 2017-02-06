package Repositories;

import java.util.List;
import javax.persistence.EntityManager;
import Entities.Troupeau;

public class RepositoryTroupeau  extends AbstractRepository<Troupeau>{
	
	public RepositoryTroupeau(EntityManager em){
		setEntityManager(em);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Troupeau> getAll(){
		return getEntityManager().createQuery("SELECT g FROM Groupe g WHERE type=Troupeau").getResultList();
	}

}
