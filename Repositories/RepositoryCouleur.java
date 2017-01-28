package Repositories;

import javax.persistence.EntityManager;
import Entities.Couleur;

public class RepositoryCouleur extends AbstractRepository<Couleur>{

	public RepositoryCouleur(EntityManager em){
		setEntityManager(em);
	}

	public String getImage(String color) {
		return getByWhere("SELECT c FROM Couleur c WHERE couleur="+color).get(0).getUrl();
	}


}
