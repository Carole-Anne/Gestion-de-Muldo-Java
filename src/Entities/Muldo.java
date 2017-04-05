package Entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the muldo database table.
 * 
 */
@Entity
@NamedQuery(name="Muldo.findAll", query="SELECT m FROM Muldo m")
public class Muldo implements Serializable, IEntities {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean fecond;

	private Integer nbenfant;

	private Integer nbsaillies;
	
	private int nbsailliesused;

	private String nom;

	private Integer sexe;//0 = male, 1 = femelle

	private Boolean visible;

	//bi-directional many-to-one association to Couleur
	@ManyToOne
	@JoinColumn(name="idcouleur")
	private Couleur couleur;

	//bi-directional many-to-many association to Groupe
	@ManyToMany
	@JoinTable(
		name="muldogroupe"
		, joinColumns={
			@JoinColumn(name="idmuldo")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idgroupe")
			}
		)
	private List<Groupe> groupes;

	//bi-directional many-to-one association to Muldo
	@ManyToOne
	@JoinColumn(name="idmere")
	private Muldo muldoMere;

	//bi-directional many-to-one association to Muldo
	@OneToMany(mappedBy="muldoMere")
	private List<Muldo> muldosMere;

	//bi-directional many-to-one association to Muldo
	@ManyToOne
	@JoinColumn(name="idpere")
	private Muldo muldoPere;

	//bi-directional many-to-one association to Muldo
	@OneToMany(mappedBy="muldoPere")
	private List<Muldo> muldosPere;

	public Muldo() {
		groupes = new ArrayList<Groupe>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getFecond() {
		return this.fecond;
	}

	public void setFecond(Boolean fecond) {
		this.fecond = fecond;
	}

	public Integer getNbenfant() {
		return this.nbenfant;
	}

	public void setNbenfant(Integer nbenfant) {
		this.nbenfant = nbenfant;
	}

	public Integer getNbsaillies() {
		return this.nbsaillies;
	}

	public void setNbsaillies(Integer nbsaillies) {
		this.nbsaillies = nbsaillies;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getSexe() {
		return this.sexe;
	}

	public void setSexe(Integer sexe) {
		this.sexe = sexe;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Couleur getCouleur() {
		return this.couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public List<Groupe> getGroupes() {
		return this.groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public Muldo getMuldoMere() {
		return this.muldoMere;
	}

	public void setMuldoMere(Muldo muldo1) {
		this.muldoMere = muldo1;
	}

	public List<Muldo> getMuldosMere() {
		return this.muldosMere;
	}

	public void setMuldosMere(List<Muldo> muldos1) {
		this.muldosMere = muldos1;
	}

	public Muldo addMuldosMere(Muldo muldosMere) {
		getMuldosMere().add(muldosMere);
		muldosMere.setMuldoMere(this);

		return muldosMere;
	}

	public Muldo removeMuldosMere(Muldo muldos1) {
		getMuldosMere().remove(muldos1);
		muldos1.setMuldoMere(null);

		return muldos1;
	}

	public Muldo getMuldoPere() {
		return this.muldoPere;
	}

	public void setMuldoPere(Muldo muldo2) {
		this.muldoPere = muldo2;
	}

	public List<Muldo> getMuldosPere() {
		return this.muldosPere;
	}

	public void setMuldosPere(List<Muldo> muldos2) {
		this.muldosPere = muldos2;
	}

	public Muldo addMuldosPere(Muldo muldos2) {
		getMuldosPere().add(muldos2);
		muldos2.setMuldoPere(this);

		return muldos2;
	}

	public Muldo removeMuldosPere(Muldo muldos2) {
		getMuldosPere().remove(muldos2);
		muldos2.setMuldoPere(null);

		return muldos2;
	}
	
	public String toString(){
		return nom;
	}

	public String getProprietaire() {
		int l = groupes.size();
		for(int i = 0; i<l; i++){
			if(groupes.get(i).getClass() == Entities.Proprietaire.class){
				return groupes.get(i).getNom();			
			}
		}
		return "?";
	}

	public void setProp(Proprietaire value) {
		boolean propChanged = false;
		for(int i = 0; i<groupes.size(); i++){
			if(groupes.get(i).getClass() == Entities.Proprietaire.class){
				groupes.set(i, value);		
				propChanged = true;
			}
		}
		if(groupes.size() == 0){
			groupes.add(value);
			propChanged = true;
		}
		if(!propChanged){
			groupes.add(value);
		}
		
	}

	public void setTroupeau(Groupe groupes2) {
		boolean find = false;
		for(int i = 0; i<groupes.size(); i++){
			if(groupes.get(i).getClass() == Entities.Troupeau.class && groupes.get(i).getId()==groupes2.getId()){
				find = true;		
			}
		}
		if(groupes.size() == 0 || !find){
			groupes.add(groupes2);
		}
		
	}

	public int getNbSailliesUsed() {
		return nbsailliesused;
	}

	public void setNbSailliesUsed(int nbSailliesUsed) {
		this.nbsailliesused = nbSailliesUsed;
	}

	public void addNbenfant() {
		nbenfant++;		
	}

	public void suppNbEnfants() {
		nbenfant--;
	}
	
	public boolean haveSaillie(){
		return nbsaillies > nbsailliesused;
	}

}