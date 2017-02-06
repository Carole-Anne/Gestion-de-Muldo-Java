package Repositories;

import java.util.List;

import javax.persistence.EntityManager;

import Entities.IEntities;
import Entities.Muldo;
import javafx.scene.control.ComboBox;

public class RepositoryMuldo extends AbstractRepository<Muldo>{
	
	public RepositoryMuldo(EntityManager em){
		setEntityManager(em);
	}

	@SuppressWarnings("unchecked")
	public List<Muldo> getAllFecond(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true and fecond=true").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getAll(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getAllModifie(int sexe) {
		return getEntityManager().createQuery("FROM Muldo WHERE sexe="+sexe+" and visible=true and nom!='Anonyme'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Muldo> getByGroupes(int sexe, List<ComboBox<IEntities>> list){
		//TODO A verifier
		String sql = "Select m ";
		String sqlFrom = "FROM Muldo m";
		String sqlWhere = " WHERE m.sexe = "+sexe+" and m.visible = true";
		for(int i = 0; i<list.size(); i++){
			IEntities e = list.get(i).getValue();
			sqlFrom = sqlFrom + ", muldogroupe mg"+i;
			sqlWhere = sqlWhere + " and mg"+i+".idmuldo = m.id and mg"+i+".idgroupe = "+e.getId();
		}
		sql = sqlFrom + sqlWhere;
		System.out.println(sql);
		return getEntityManager().createQuery(sql).getResultList();
		
	}
}
