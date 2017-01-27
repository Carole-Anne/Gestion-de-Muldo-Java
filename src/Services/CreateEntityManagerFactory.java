package Services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateEntityManagerFactory{

	private static EntityManagerFactory emf = null;
	
	private CreateEntityManagerFactory(String s){
		emf = Persistence.createEntityManagerFactory(s);
	}
	
	public static synchronized EntityManagerFactory getInstance(){
		if (emf == null){
			new CreateEntityManagerFactory("Muldo");
		}
		return emf;
		
	}
	
}
