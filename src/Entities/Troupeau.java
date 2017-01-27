package Entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@SuppressWarnings("serial")
@DiscriminatorValue(value="Troupeau")
public class Troupeau extends Groupe {

	public Troupeau(){
		
	}
	
	public Troupeau(String nom) {
		super(nom);
	}
}
