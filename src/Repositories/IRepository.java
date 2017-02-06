package Repositories;

import java.util.List;
import Entities.IEntities;

public interface IRepository<T extends IEntities> {

	public abstract T save(T entity);
	public abstract T update(T entity);
	public abstract void remove(T entity);
	public abstract T getById(int id);
	public abstract List<T> getAll();
	public abstract List<T> getByWhere(String where);
	public abstract void add(T entity);
	public abstract boolean getNameFind(String name);
	public abstract List<T> getByName(String name);
}
