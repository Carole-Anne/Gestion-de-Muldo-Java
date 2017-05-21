package Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the couleur database table.
 * 
 */
@Entity
@NamedQuery(name="Couleur.findAll", query="SELECT c FROM Couleur c Order By c.nom")
public class Couleur implements Serializable, IEntities{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nom;

	private String url;
	
	private String urlicon;
	
	private int nbfemelle;
	
	private int nbmale;

	//bi-directional many-to-one association to Muldo
	@OneToMany(mappedBy="couleur")
	private List<Muldo> muldos;

	public Couleur() {
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

	public void setNom(String couleur) {
		this.nom = couleur;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Muldo> getMuldos() {
		return this.muldos;
	}

	public void setMuldos(List<Muldo> muldos) {
		this.muldos = muldos;
	}

	public Muldo addMuldo(Muldo muldo) {
		getMuldos().add(muldo);
		muldo.setCouleur(this);

		return muldo;
	}

	public Muldo removeMuldo(Muldo muldo) {
		getMuldos().remove(muldo);
		muldo.setCouleur(null);

		return muldo;
	}
	
	public String getUrlIcon(){
		return urlicon;
	}

	public void setUrlIcon(String urlIcone) {
		this.urlicon = urlIcone;		
	}
	
	public String toString(){
		return nom;
		
	}

	public int getNbFemelle() {
		return nbfemelle;
	}

	public void setNbFemelle(int nbfemelle) {
		this.nbfemelle = nbfemelle;
	}

	public int getNbMale() {
		return nbmale;
	}

	public void setNbMale(int nbmale) {
		this.nbmale = nbmale;
	}

}