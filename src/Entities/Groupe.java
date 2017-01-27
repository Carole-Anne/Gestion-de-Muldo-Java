package Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the groupe database table.
 * 
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@NamedQuery(name="Groupe.findAll", query="SELECT g FROM Groupe g")
public abstract class Groupe implements Serializable, IEntities {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nom;

	//bi-directional many-to-many association to Muldo
	@ManyToMany(mappedBy="groupes")
	private List<Muldo> muldos;

	public Groupe(){
		
	}
	
	public Groupe(String nom) {
		this.nom = nom;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Muldo> getMuldos() {
		return this.muldos;
	}

	public void setMuldos(List<Muldo> muldos) {
		this.muldos = muldos;
	}

}