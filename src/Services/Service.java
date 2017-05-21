package Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import Entities.Arbre;
import Entities.Couleur;
import Entities.Groupe;
import Entities.Muldo;
import Entities.Proprietaire;
import Repositories.RepositoryCouleur;
import Repositories.RepositoryGroupe;
import Repositories.RepositoryMuldo;
import Repositories.RepositoryProprietaire;
import Repositories.RepositoryTroupeau;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class Service {
	
	EntityManager em;
	
	private Muldo[] arbreMuldo;
	private Muldo mFemelle;
	private Muldo mMale;
	private int findMuldo; //d�finit le sexe des muldos � trouver
	
	private final int muldoAnonymeMale = 2;
	private final int muldoAnonymeFemelle = 3;

	private RepositoryCouleur repColor;
	private RepositoryMuldo repMuldo;
	private RepositoryGroupe repGroupe;
	private RepositoryTroupeau repTroupeau;
	private RepositoryProprietaire repProp;
	
	public Service(EntityManager em){
		this.em = em;
		repMuldo = new RepositoryMuldo(em);
		repColor = new RepositoryCouleur(em);
		repGroupe = new RepositoryGroupe(em);
		repTroupeau = new RepositoryTroupeau(em);
		repProp = new RepositoryProprietaire(em);
	}
	
	public Muldo getmFemelle() {
		return mFemelle;
	}
	
	public void setmFemelle(Muldo f){
		mFemelle = f;
	}

	public Muldo getmMale() {
		return mMale;
	}
	
	public void setmMale(Muldo m){
		mMale = m;
		
	}

	public int getFindMuldo() {
		return findMuldo;
	}

	public RepositoryCouleur getRepColor() {
		return repColor;
	}

	public RepositoryMuldo getRepMuldo() {
		return repMuldo;
	}

	public RepositoryGroupe getRepGroupe() {
		return repGroupe;
	}

	public RepositoryTroupeau getRepTroupeau() {
		return repTroupeau;
	}

	public RepositoryProprietaire getRepProp() {
		return repProp;
	}
	
	@SuppressWarnings("unchecked")
	public void addMuldo(String name, String nbSaillie, int sexe, int idMere, int idPere, Couleur color, Proprietaire prop, List<Node> listGroupes){
		Muldo m = new Muldo();
		m.setSexe(sexe);
		m.setCouleur(color);
		m.setNom(name);
		m.setNbenfant(0);
		m.setNbsaillies(Integer.parseInt(nbSaillie));
		m.setFecond(false);
		Muldo mere = repMuldo.getById(idMere);
		m.setMuldoMere(mere);
		Muldo pere = repMuldo.getById(idPere);
		m.setMuldoPere(pere);
		m.setProp(prop);
		for(int i = 0; i<listGroupes.size(); i++){
			Node cbo = ( (HBox)listGroupes.get(i)).getChildren().get(0);
			m.setTroupeau(((ComboBox<Groupe>)cbo).getValue());
		}
		m.setVisible(true);
		
		repMuldo.add(m);
		updateNbEnfant(m, 0);
		updateColor(m.getSexe(), m.getCouleur());
		repMuldo.commit();
	}
	
	private void updateNbEnfant(Muldo m, int i){
		Muldo mere = m.getMuldoMere();
		Muldo pere = m.getMuldoPere();
		if(mere.getId() != muldoAnonymeFemelle){
			mere.addNbenfant();
			if(i<3){
				updateNbEnfant(mere, i+1);
			}
		}
		if(pere.getId() != muldoAnonymeMale){
			pere.addNbenfant();
			if(i<3){
				updateNbEnfant(pere, i+1);
			}
		}
		repMuldo.commit();
	}

	/**
	 * Supprime le muldo pour l'�leveur mais pas dans la base
	 * @param m
	 */
	public void delete(Muldo m){
		m.setVisible(false);
		repMuldo.update(m);
		
		Muldo pere = m.getMuldoPere();
		Muldo mere = m.getMuldoMere();
		
		if(pere.getId() != muldoAnonymeMale){
			pere.setNbenfant(pere.getNbenfant()-1);
			if(supp(pere,0)){
				removeBD(pere);
			}
		}
		if(mere.getId() != muldoAnonymeFemelle){
			mere.setNbenfant(mere.getNbenfant()-1);
			if(supp(mere,0)){
				removeBD(mere);
			}
		}
		if(m.getNbenfant() == 0){
			repMuldo.remove(m);
		}

		repMuldo.commit();
	}
	
	private boolean supp(Muldo m, int i){
		int j = i+1;
		Muldo pere = m.getMuldoPere();
		Muldo mere = m.getMuldoMere();
		if(pere.getId() != muldoAnonymeMale){
			if(j<4){
				pere.setNbenfant(pere.getNbenfant()-1);
			}
			if(supp(pere,j)){
				removeBD(pere);
				pere = repMuldo.getById(muldoAnonymeMale);
				m.setMuldoPere(pere);
			}
		}
		if(mere.getId() != muldoAnonymeFemelle){
			if(j<4){
				mere.setNbenfant(mere.getNbenfant()-1);
			}
			if(supp(mere,j)){
				removeBD(mere);
				mere = repMuldo.getById(muldoAnonymeFemelle);
				m.setMuldoMere(mere);
			}
		}
		if(mere.getId() == muldoAnonymeFemelle && pere.getId() == muldoAnonymeMale && m.getNbenfant() == 0 && !m.getVisible()){
			return true;
		}
		return false;
	}
	
	private void removeBD(Muldo m){
		List<Muldo> muldos;
		Muldo anonyme;
		if(m.getSexe()==0){
			anonyme = repMuldo.getById(muldoAnonymeMale);
			muldos = repMuldo.getByFather(m.getId());
			muldos.forEach(muldo -> muldo.setMuldoPere(anonyme));
		}else{
			anonyme = repMuldo.getById(muldoAnonymeFemelle);
			muldos = repMuldo.getByMother(m.getId());
			muldos.forEach(muldo -> muldo.setMuldoMere(anonyme));
		}
		repMuldo.remove(m);
		repMuldo.commit();
	}
	
	public Muldo[] getArbre(){
		return arbreMuldo;
	}
	
	public int nbSailliesHypothetique(Muldo mere, Muldo pere){		
		List<Arbre> arbreGenealogique = createArbreGenealogique(mere, pere);
		arbreGenealogique.sort(new Comparator<Arbre>() {
			public int compare(Arbre o1, Arbre o2) {
				if(o1.getMuldo().getId() < o2.getMuldo().getId()){
					return -1;
				}
				return 0;
			}
		});
		//TODO Verifie uniquement si les arbres des parents non pas colision /!\ Manque peut �tre pour la derni�re
		//On v�rifie s'il y a colision
		int colision = 6; //Pas de colision sur 5 g�n�rations
		Arbre a = arbreGenealogique.get(0);
		int l = arbreGenealogique.size();
		for(int i = 1; i<l; i++){
			Arbre b = arbreGenealogique.get(i);
			Muldo m = a.getMuldo();
			Muldo m2 = b.getMuldo();
			if(m.getId() == m2.getId() && m.getId()!=muldoAnonymeMale && m.getId()!=muldoAnonymeFemelle){
				//Colision
				if(a.getGeneration() <= b.getGeneration() && a.getGeneration()<=colision){
					colision = a.getGeneration();
				}else if(a.getGeneration() > b.getGeneration() && b.getGeneration()<colision){
					colision = b.getGeneration();
				}
			}
			a = b;
		}
		
		return colision<=4 ? colision : 4;
	}

	private List<Arbre> createArbreGenealogique(Muldo m1, Muldo m2) {
		
		List<Arbre> l = new ArrayList<Arbre>();
		l = arbreRecursif(m1, 0, l);
		l = arbreRecursif(m2, 0, l);
		
		return l;
	}
	
	private List<Arbre> arbreRecursif(Muldo m, int gene, List<Arbre> l){
		System.out.println("Muldo : "+m.getNom());
		gene++;
		if(gene>5){
			return l;
		}
		Arbre a = new Arbre(m, gene);
		l.add(a);
		Muldo pere = m.getMuldoPere();
		System.out.println("Pere : "+pere.getNom()+"  id : "+pere.getId());
		if(pere.getId() != muldoAnonymeMale){
			System.out.println("Non anonyme");
			l = arbreRecursif(pere, gene, l);
		}
		Muldo mere = m.getMuldoMere();
		System.out.println("Mere : "+mere.getNom()+"  id : "+mere.getId());
		if(mere.getId() != muldoAnonymeFemelle){
			System.out.println("Non anonyme");
			l = arbreRecursif(mere, gene, l);
		}
		return l;
		
	}

	public void createArbre(Muldo m){
		arbreMuldo = new Muldo[15];
		arbreMuldo[0] = m;
		Muldo pere = m.getMuldoPere();
		Muldo mere = m.getMuldoMere();
		arbreMuldo[1] = pere;
		arbreMuldo[2] = mere;
		if(pere.getId() != muldoAnonymeMale){
			//si le pere n'est pas anonyme
			Muldo perePere = pere.getMuldoPere();
			Muldo merePere = pere.getMuldoMere();
			arbreMuldo[3] = perePere;
			arbreMuldo[4] = merePere;
			if(perePere.getId() != muldoAnonymeMale){
				//si le perePere n'est pas anonyme
				Muldo perePerePere = perePere.getMuldoPere();
				Muldo merePerePere = perePere.getMuldoMere();
				arbreMuldo[7] = perePerePere;
				arbreMuldo[8] = merePerePere;
			}
			if(merePere.getId() != muldoAnonymeFemelle){
				//si le merePere n'est pas anonyme
				Muldo pereMerePere = merePere.getMuldoPere();
				Muldo mereMerePere = merePere.getMuldoMere();
				arbreMuldo[9] = pereMerePere;
				arbreMuldo[10] = mereMerePere;
			}
		}
		if(mere.getId() != muldoAnonymeFemelle){
			//si le mere n'est pas anonyme
			Muldo pereMere = mere.getMuldoPere();
			Muldo mereMere = mere.getMuldoMere();
			arbreMuldo[5] = pereMere;
			arbreMuldo[6] = mereMere;
			if(pereMere.getId() != muldoAnonymeMale){
				//si le perePere n'est pas anonyme
				Muldo perePereMere = pereMere.getMuldoPere();
				Muldo merePereMere = pereMere.getMuldoMere();
				arbreMuldo[11] = perePereMere;
				arbreMuldo[12] = merePereMere;
			}
			if(mereMere.getId() != muldoAnonymeFemelle){
				//si le merePere n'est pas anonyme
				Muldo pereMereMere = mereMere.getMuldoPere();
				Muldo mereMereMere = mereMere.getMuldoMere();
				arbreMuldo[13] = pereMereMere;
				arbreMuldo[14] = mereMereMere;
			}
		}
	}
	
	public void feconder(Muldo mMale, Muldo mFemelle){
		if(mMale.haveSaillie() && mFemelle.haveSaillie()){
			mMale.setFecond(false);
			mMale.setNbSailliesUsed(mMale.getNbSailliesUsed()+1);
			repMuldo.update(mMale);
		
			mFemelle.setFecond(false);
			mFemelle.setNbSailliesUsed(mFemelle.getNbSailliesUsed()+1);
			repMuldo.update(mFemelle);
			
			repMuldo.commit();
		}
	}
	
	public void selectMuldoMale(Muldo femelle){
		mFemelle = femelle;
		mMale = null;
		findMuldo = 0;
	}
	
	public void selectMuldoFemelle(Muldo male){
		mMale = male;
		mFemelle = null;
		findMuldo = 1;
	}
	
	public List<Muldo> getMuldoMale(Muldo femelle){
		//TODO A v�rifi�
		List<Muldo> goodMuldo = new ArrayList<Muldo>();
		List<Muldo> list = repMuldo.getAllFecond(0);
		int i = 0;
		while(i<list.size() && goodMuldo.size()<10){
			if(nbSailliesHypothetique(list.get(i), femelle) == 4){
				goodMuldo.add(list.get(i));
			}
			i++;
		}
		return goodMuldo;
	}
	
	public List<Muldo> getMuldoFemelle(Muldo male){
		//TODO A v�rifi�
		List<Muldo> goodMuldo = new ArrayList<Muldo>();
		List<Muldo> list = repMuldo.getAllFecond(1);
		int i = 0;
		while(i<list.size() && goodMuldo.size()<10){
			if(nbSailliesHypothetique(male, list.get(i)) == 4){
				goodMuldo.add(list.get(i));
			}
			i++;
		}
		return goodMuldo;
	}
	
	public void initMuldo() {
		Couleur couleur = repColor.getByName("Anonyme").get(0);
		
		Muldo m1 = new Muldo();
		m1.setCouleur(couleur);
		m1.setFecond(false);
		m1.setNbenfant(0);
		m1.setNbsaillies(0);
		m1.setSexe(0);
		m1.setVisible(true);
		m1.setNom("Anonyme");
		repMuldo.save(m1);
		
		Muldo m2 = new Muldo();
		m2.setCouleur(couleur);
		m2.setFecond(false);
		m2.setNbenfant(0);
		m2.setNbsaillies(0);
		m2.setSexe(1);
		m2.setVisible(true);
		m2.setNom("Anonyme");
		repMuldo.save(m2);
		
		repMuldo.commit();
	}
	
	public void initProp(){
		Proprietaire p = new Proprietaire("Personnage principal");
		repGroupe.save(p);
		repGroupe.commit();
	}

	/**
	 * Initialise/Remplit la table Couleur et cr�ation d'un propri�taire
	 */
	public void initColor() {
		
		Couleur c0 = new Couleur();
		c0.setNom("Anonyme");
		c0.setUrl("./image/anonyme.png");
		c0.setUrlIcon("./image/icon/anonyme.png");
		repColor.save(c0);
		
		Couleur c = new Couleur();
		c.setNom("Amande");
		c.setUrl("./image/amande.png");
		c.setUrlIcon("./image/icon/amande.png");
		repColor.save(c);
		
		Couleur c1 = new Couleur();
		c1.setNom("Amande Emeraude");
		c1.setUrl("./image/amandeEmeraude.png");
		c1.setUrlIcon("./image/icon/amandeEmeraude.png");
		repColor.save(c1);
		
		Couleur c2 = new Couleur();
		c2.setNom("Amande Ivoire");
		c2.setUrl("./image/amandeIvoire.png");
		c2.setUrlIcon("./image/icon/amandeIvoire.png");
		repColor.save(c2);
		
		Couleur c3 = new Couleur();
		c3.setNom("Dor�");
		c3.setUrl("./image/dore.png");
		c3.setUrlIcon("./image/icon/dore.png");
		repColor.save(c3);
		
		Couleur c4 = new Couleur();
		c4.setNom("Dor� Amande");
		c4.setUrl("./image/doreAmande.png");
		c4.setUrlIcon("./image/icon/doreAmande.png");
		repColor.save(c4);

		Couleur c5 = new Couleur();
		c5.setNom("Dor� Eb�ne");
		c5.setUrl("./image/doreEbene.png");
		c5.setUrlIcon("./image/icon/doreEbene.png");
		repColor.save(c5);

		Couleur c6 = new Couleur();
		c6.setNom("Dor� Emeraude");
		c6.setUrl("./image/doreEmeraude.png");
		c6.setUrlIcon("./image/icon/doreEmeraude.png");
		repColor.save(c6);

		Couleur c7 = new Couleur();
		c7.setNom("Dor� Indigo");
		c7.setUrl("./image/doreIndigo.png");
		c7.setUrlIcon("./image/icon/doreIndigo.png");
		repColor.save(c7);

		Couleur c8 = new Couleur();
		c8.setNom("Dor� Ivoire");
		c8.setUrl("./image/doreIvoire.png");
		c8.setUrlIcon("./image/icon/doreIvoire.png");
		repColor.save(c8);

		Couleur c9 = new Couleur();
		c9.setNom("Dor� Orchid�");
		c9.setUrl("./image/doreOrchi.png");
		c9.setUrlIcon("./image/icon/doreOrchi.png");
		repColor.save(c9);

		Couleur c10 = new Couleur();
		c10.setNom("Dor� Pourpre");
		c10.setUrl("./image/dorePourpre.png");
		c10.setUrlIcon("./image/icon/dorePourpre.png");
		repColor.save(c10);

		Couleur c11 = new Couleur();
		c11.setNom("Eb�ne");
		c11.setUrl("./image/ebene.png");
		c11.setUrlIcon("./image/icon/ebene.png");
		repColor.save(c11);

		Couleur c12 = new Couleur();
		c12.setNom("Eb�ne Amande");
		c12.setUrl("./image/ebeneAmande.png");
		c12.setUrlIcon("./image/icon/ebeneAmande.png");
		repColor.save(c12);

		Couleur c13 = new Couleur();
		c13.setNom("Eb�ne Emeraude");
		c13.setUrl("./image/ebeneEmeraude.png");
		c13.setUrlIcon("./image/icon/ebeneEmeraude.png");
		repColor.save(c13);

		Couleur c14 = new Couleur();
		c14.setNom("Eb�ne Indigo");
		c14.setUrl("./image/ebeneIndigo.png");
		c14.setUrlIcon("./image/icon/ebeneIndigo.png");
		repColor.save(c14);

		Couleur c15 = new Couleur();
		c15.setNom("Eb�ne Ivoire");
		c15.setUrl("./image/ebeneIvoire.png");
		c15.setUrlIcon("./image/icon/ebeneIvoire.png");
		repColor.save(c15);

		Couleur c16 = new Couleur();
		c16.setNom("Eb�ne Orchid�");
		c16.setUrl("./image/ebeneOrchi.png");
		c16.setUrlIcon("./image/icon/ebeneOrchi.png");
		repColor.save(c16);

		Couleur c17 = new Couleur();
		c17.setNom("Eb�ne Pourpre");
		c17.setUrl("./image/ebenePourpre.png");
		c17.setUrlIcon("./image/icon/ebenePourpre.png");
		repColor.save(c17);

		Couleur c18 = new Couleur();
		c18.setNom("Emeraude");
		c18.setUrl("./image/emeraude.png");
		c18.setUrlIcon("./image/icon/emeraude.png");
		repColor.save(c18);

		Couleur c19 = new Couleur();
		c19.setNom("Indigo");
		c19.setUrl("./image/indigo.png");
		c19.setUrlIcon("./image/icon/indigo.png");
		repColor.save(c19);

		Couleur c20 = new Couleur();
		c20.setNom("Indigo Amande");
		c20.setUrl("./image/indigoAmande.png");
		c20.setUrlIcon("./image/icon/indigoAmande.png");
		repColor.save(c20);

		Couleur c21 = new Couleur();
		c21.setNom("Indigo Emeraude");
		c21.setUrl("./image/indigoEmeraude.png");
		c21.setUrlIcon("./image/icon/indigoEmeraude.png");
		repColor.save(c21);

		Couleur c22 = new Couleur();
		c22.setNom("Indigo Ivoire");
		c22.setUrl("./image/indigoIvoire.png");
		c22.setUrlIcon("./image/icon/indigoIvoire.png");
		repColor.save(c22);

		Couleur c23 = new Couleur();
		c23.setNom("Indigo Orchid�");
		c23.setUrl("./image/indigoOrchi.png");
		c23.setUrlIcon("./image/icon/indigoOrchi.png");
		repColor.save(c23);

		Couleur c24 = new Couleur();
		c24.setNom("Indigo Pourpre");
		c24.setUrl("./image/indigoPourpre.png");
		c24.setUrlIcon("./image/icon/indigoPourpre.png");
		repColor.save(c24);

		Couleur c25 = new Couleur();
		c25.setNom("Ivoire");
		c25.setUrl("./image/ivoire.png");
		c25.setUrlIcon("./image/icon/ivoire.png");
		repColor.save(c25);

		Couleur c29 = new Couleur();
		c29.setNom("Ivoire Emeraude");
		c29.setUrl("./image/ivoireEmeraude.png");
		c29.setUrlIcon("./image/icon/ivoireEmeraude.png");
		repColor.save(c29);

		Couleur c26 = new Couleur();
		c26.setNom("Orchid�");
		c26.setUrl("./image/orchidee.png");
		c26.setUrlIcon("./image/icon/orchidee.png");
		repColor.save(c26);

		Couleur c27 = new Couleur();
		c27.setNom("Orchid� Amande");
		c27.setUrl("./image/orchiAmande.png");
		c27.setUrlIcon("./image/icon/orchiAmande.png");
		repColor.save(c27);

		Couleur c28 = new Couleur();
		c28.setNom("Orchid� Emeraude");
		c28.setUrl("./image/orchiEmeraude.png");
		c28.setUrlIcon("./image/icon/orchiEmeraude.png");
		repColor.save(c28);

		Couleur c30 = new Couleur();
		c30.setNom("Orchid� Ivoire");
		c30.setUrl("./image/orchiIvoire.png");
		c30.setUrlIcon("./image/icon/orchiIvoire.png");
		repColor.save(c30);

		Couleur c31 = new Couleur();
		c31.setNom("Orchid� Pourpre");
		c31.setUrl("./image/orchiPourpre.png");
		c31.setUrlIcon("./image/icon/orchiPourpre.png");
		repColor.save(c31);

		Couleur c32 = new Couleur();
		c32.setNom("Pourpre");
		c32.setUrl("./image/pourpre.png");
		c32.setUrlIcon("./image/icon/pourpre.png");
		repColor.save(c32);

		Couleur c33 = new Couleur();
		c33.setNom("Pourpre Amande");
		c33.setUrl("./image/pourpreAmande.png");
		c33.setUrlIcon("./image/icon/pourpreAmande.png");
		repColor.save(c33);

		Couleur c34 = new Couleur();
		c34.setNom("Pourpre Emeraude");
		c34.setUrl("./image/pourpreEmeraude.png");
		c34.setUrlIcon("./image/icon/pourpreEmeraude.png");
		repColor.save(c34);

		Couleur c35 = new Couleur();
		c35.setNom("Pourpre Ivoire");
		c35.setUrl("./image/pourpreIvoire.png");
		c35.setUrlIcon("./image/icon/pourpreIvoire.png");
		repColor.save(c35);

		Couleur c36 = new Couleur();
		c36.setNom("Prune");
		c36.setUrl("./image/prune.png");
		c36.setUrlIcon("./image/icon/prune.png");
		repColor.save(c36);

		Couleur c37 = new Couleur();
		c37.setNom("Prune Dor�");
		c37.setUrl("./image/pruneDore.png");
		c37.setUrlIcon("./image/icon/pruneDore.png");
		repColor.save(c37);

		Couleur c38 = new Couleur();
		c38.setNom("Prune Eb�ne");
		c38.setUrl("./image/pruneEbene.png");
		c38.setUrlIcon("./image/icon/pruneEbene.png");
		repColor.save(c38);

		Couleur c39 = new Couleur();
		c39.setNom("Prune Emeraude");
		c39.setUrl("./image/pruneEmeraude.png");
		c39.setUrlIcon("./image/icon/pruneEmeraude.png");
		repColor.save(c39);

		Couleur c40 = new Couleur();
		c40.setNom("Prune Indigo");
		c40.setUrl("./image/pruneIndigo.png");
		c40.setUrlIcon("./image/icon/pruneIndigo.png");
		repColor.save(c40);
		
		Couleur c63 = new Couleur();
		c63.setNom("Prune Ivoire");
		c63.setUrl("./image/pruneIvoire.png");
		c63.setUrlIcon("./image/icon/pruneIvoire.png");
		repColor.save(c63);

		Couleur c41 = new Couleur();
		c41.setNom("Prune Orchid�");
		c41.setUrl("./image/pruneOrchi.png");
		c41.setUrlIcon("./image/icon/pruneOrchi.png");
		repColor.save(c41);

		Couleur c42 = new Couleur();
		c42.setNom("Prune Pourpre");
		c42.setUrl("./image/prunePourpre.png");
		c42.setUrlIcon("./image/icon/prunePourpre.png");
		repColor.save(c42);
		
		Couleur c61 = new Couleur();
		c61.setNom("Prune Pourpre");
		c61.setUrl("./image/pruneTurquoise.png");
		c61.setUrlIcon("./image/icon/pruneTurquoise.png");
		repColor.save(c61);

		Couleur c43 = new Couleur();
		c43.setNom("Roux");
		c43.setUrl("./image/roux.png");
		c43.setUrlIcon("./image/icon/roux.png");
		repColor.save(c43);

		Couleur c44 = new Couleur();
		c44.setNom("Roux Amande");
		c44.setUrl("./image/rouxAmande.png");
		c44.setUrlIcon("./image/icon/rouxAmande.png");
		repColor.save(c44);

		Couleur c45 = new Couleur();
		c45.setNom("Roux Dor�");
		c45.setUrl("./image/rouxDore.png");
		c45.setUrlIcon("./image/icon/rouxDore.png");
		repColor.save(c45);

		Couleur c46 = new Couleur();
		c46.setNom("Roux Eb�ne");
		c46.setUrl("./image/rouxEbene.png");
		c46.setUrlIcon("./image/icon/rouxEbene.png");
		repColor.save(c46);

		Couleur c47 = new Couleur();
		c47.setNom("Roux Emeraude");
		c47.setUrl("./image/rouxEmeraude.png");
		c47.setUrlIcon("./image/icon/rouxEmeraude.png");
		repColor.save(c47);

		Couleur c48 = new Couleur();
		c48.setNom("Roux Indigo");
		c48.setUrl("./image/rouxIndigo.png");
		c48.setUrlIcon("./image/icon/rouxIndigo.png");
		repColor.save(c48);

		Couleur c62 = new Couleur();
		c62.setNom("Roux Ivoire");
		c62.setUrl("./image/rouxIvoire.png");
		c62.setUrlIcon("./image/icon/rouxIvoire.png");
		repColor.save(c62);
		
		Couleur c49 = new Couleur();
		c49.setNom("Roux Orchid�");
		c49.setUrl("./image/rouxOrchi.png");
		c49.setUrlIcon("./image/icon/rouxOrchi.png");
		repColor.save(c49);

		Couleur c50 = new Couleur();
		c50.setNom("Roux Pourpre");
		c50.setUrl("./image/rouxPourpre.png");
		c50.setUrlIcon("./image/icon/rouxPourpre.png");
		repColor.save(c50);

		Couleur c51 = new Couleur();
		c51.setNom("Turquoise");
		c51.setUrl("./image/turquoise.png");
		c51.setUrlIcon("./image/icon/turquoise.png");
		repColor.save(c51);

		Couleur c52 = new Couleur();
		c52.setNom("Turquoise Amande");
		c52.setUrl("./image/turquoiseAmande.png");
		c52.setUrlIcon("./image/icon/turquoiseAmande.png");
		repColor.save(c52);

		Couleur c53 = new Couleur();
		c53.setNom("Turquoise Dor�");
		c53.setUrl("./image/turquoiseDore.png");
		c53.setUrlIcon("./image/icon/turquoiseDore.png");
		repColor.save(c53);

		Couleur c54 = new Couleur();
		c54.setNom("Turquoise Eb�ne");
		c54.setUrl("./image/turquoiseEbene.png");
		c54.setUrlIcon("./image/icon/turquoiseEbene.png");
		repColor.save(c54);

		Couleur c55 = new Couleur();
		c55.setNom("Turquoise Emeraude");
		c55.setUrl("./image/turquoiseEmeraude.png");
		c55.setUrlIcon("./image/icon/turquoiseEmeraude.png");
		repColor.save(c55);

		Couleur c56 = new Couleur();
		c56.setNom("Turquoise Indigo");
		c56.setUrl("./image/turquoiseIndigo.png");
		c56.setUrlIcon("./image/icon/turquoiseIndigo.png");
		repColor.save(c56);

		Couleur c57 = new Couleur();
		c57.setNom("Turquoise Ivoire");
		c57.setUrl("./image/turquoiseIvoire.png");
		c57.setUrlIcon("./image/icon/turquoiseIvoire.png");
		repColor.save(c57);

		Couleur c58 = new Couleur();
		c58.setNom("Turquoise Orchid�");
		c58.setUrl("./image/turquoiseOrchi.png");
		c58.setUrlIcon("./image/icon/turquoiseOrchi.png");
		repColor.save(c58);

		Couleur c59 = new Couleur();
		c59.setNom("Turquoise Pourpre");
		c59.setUrl("./image/turquoisePourpre.png");
		c59.setUrlIcon("./image/icon/turquoisePourpre.png");
		repColor.save(c59);

		Couleur c60 = new Couleur();
		c60.setNom("Turquoise Roux");
		c60.setUrl("./image/turquoiseRoux.png");
		c60.setUrlIcon("./image/icon/turquoiseRoux.png");
		repColor.save(c60);
		
		repColor.commit();
	}

	private void updateColor(Integer sexe, Couleur couleur) {
		if(sexe == 0){
			couleur.setNbMale(couleur.getNbMale()+1);
		}else{
			couleur.setNbFemelle(couleur.getNbFemelle()+1);
		}
		repColor.commit();
	}

	
}
