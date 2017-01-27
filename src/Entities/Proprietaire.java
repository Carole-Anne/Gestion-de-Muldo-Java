package Entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@SuppressWarnings("serial")
@DiscriminatorValue(value="Proprietaire")
public class Proprietaire extends Groupe {

	public Proprietaire(){
		
	}
	
	public Proprietaire(String nom) {
		super(nom);
	}
	
	

}
