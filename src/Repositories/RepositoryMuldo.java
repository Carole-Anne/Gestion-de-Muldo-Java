package Repositories;

import javax.persistence.EntityManager;
import Entities.Muldo;

public class RepositoryMuldo extends AbstractRepository<Muldo>{
	
	public RepositoryMuldo(EntityManager em){
		setEntityManager(em);
	}
	
}
