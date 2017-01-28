package Repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public interface IUoW {
	
	void commit();
	
	EntityTransaction getTransaction();
	
	void setTransaction(EntityTransaction transaction);
	
	EntityManager getEntityManager();
	
	void setEntityManager(EntityManager em);
}
