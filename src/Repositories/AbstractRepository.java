package Repositories;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.*;
import Entities.IEntities;

public abstract class AbstractRepository<T extends IEntities> implements IRepository<T>, IUoW {
	
	@PersistenceContext
	private EntityManager em;
	
	private EntityTransaction transaction;
	
	private Class<T> entityClass;
	
	@Override
	public EntityManager getEntityManager(){
		return em;
	}
	
	@Override
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	@Override
	public EntityTransaction getTransaction(){
		if(transaction == null){
			transaction = em.getTransaction();
		}
		if(!transaction.isActive()){
			transaction.begin();
		}
		return transaction;
	}
	
	@SuppressWarnings("unchecked")
	public AbstractRepository(){
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];		
	}
	
	@Override
	public T save(T entity){
		em.persist(entity);
		return entity;
	}
	
	@Override
	public T update(T entity){
		return em.merge(entity);
	}

	@Override
	public void remove(T entity){
		em.remove(em.merge(entity));
	}
	
	@Override
	public T getById(int id){
		return em.find(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(){
		return em.createNamedQuery(entityClass.getSimpleName()+".findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByWhere(String where) {
		return em.createQuery(where).getResultList();
	}
	
	@Override
	public void setTransaction(EntityTransaction transaction) {
		this.transaction = transaction;		
	}
	
	@Override
	public void commit(){
		getTransaction().commit();
	}
	
	@Override
	public void add(T entity) {
		save(entity);		
		commit();
	}
	
	/***
	 * Vérifie que le nom n'hésiste pas déjà la base de données
	 * @param name
	 * @return true si name n'hésiste pas dans la base de données
	 */
	@Override
	public boolean getNameFind(String name){
		boolean isFind = false;
		int lenghtName = name.length();
		String request = "FROM "+entityClass.getSimpleName()+" WHERE nom LIKE '"+name+"'";
		List<T> listContainerName = getByWhere(request);
		int l = listContainerName.size();
		for (int i = 0; i<l; i++){
			if(listContainerName.get(i).getNom().length() == lenghtName){
				isFind = true;
				break;
			}
		}
		return isFind;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByName(String name){
		return em.createQuery("FROM "+entityClass.getSimpleName()+" WHERE nom='"+name+"'").getResultList();
	}
	
}
