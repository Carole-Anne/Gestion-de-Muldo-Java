package Repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import Entities.Muldo;

public class RepositoryMuldo extends AbstractRepository<Muldo>{
	
	public RepositoryMuldo(EntityManager em){
		setEntityManager(em);
	}

	@SuppressWarnings("unchecked")
	public List<Muldo> getAllFecond(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true and fecond=true Order By nom").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getAll(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true Order By nom").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getAllModifie(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true and nom!='Anonyme' Order By nom").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getByGroupes(int sexe, List<Integer> listGroupe, List<Integer> idCouleur, boolean fecond){
		if(idCouleur.size()>1){ //Si la liste a plus de une couleur alors c'est impossible
			return new ArrayList<Muldo>();
		}		
		String sql = "SELECT m FROM Muldo m";
		String where = " WHERE m.sexe="+sexe+" and m.visible=true ";
		if (fecond){
			where = where + "and m.fecond=true ";
		}
		where = where +" and m.nom!='Anonyme'";
		for(int i = 0; i<listGroupe.size(); i++){
				sql = sql +" JOIN m.groupes g"+i;
				where = where + " and g"+i+".id="+ listGroupe.get(i);
			
		}
		if(idCouleur.size()==1){
			sql = sql + where +" and m.couleur="+idCouleur.get(0);
		}else{
			sql = sql + where +" Order By m.couleur.id, m.nom";
		}
		return getEntityManager().createQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getByFather(Integer id){
		List<Muldo> muldos;
		String sql = "SELECT m FROM Muldo m WHERE m.muldoPere.id = :id";
		muldos = getEntityManager().createQuery(sql).setParameter("id", id).getResultList();
		return muldos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getByMother(Integer id){
		List<Muldo> muldos;
		String sql = "SELECT m FROM Muldo m WHERE m.muldoMere.id = :id";
		muldos = getEntityManager().createQuery(sql).setParameter("id", id).getResultList();
		return muldos;
	}
}
