package Repositories;

import java.util.List;

import javax.persistence.EntityManager;
import Entities.Couleur;

public class RepositoryCouleur extends AbstractRepository<Couleur>{
	
	@Override 
	public List<Couleur> getAll(){
		List<Couleur> list = super.getAll();
		list.remove(0);
		return list;
	}

	public RepositoryCouleur(EntityManager em){
		setEntityManager(em);
	}

	public String getImage(String color) {
		return getByWhere("SELECT c FROM Couleur c WHERE couleur="+color).get(0).getUrl();
	}

}
