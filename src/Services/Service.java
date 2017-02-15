package Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Entities.Arbre;
import Entities.Muldo;

public class Service {
	
	Muldo[] arbreMuldo;
	
	/**
	 * Supprime le muldo pour l'éleveur mais pas dans la base
	 * @param m
	 */
	public void delete(Muldo m){
		m.setVisible(false);
		Main.repMuldo.update(m);
		Main.repMuldo.commit();
		deleteMuldoOfBD(m);
	}
	
	public Muldo[] getArbre(){
		return arbreMuldo;
	}

	/**
	 * Suppression de muldo dans la base
	 * @param m
	 */
	private void deleteMuldoOfBD(Muldo m) {
		supp(m.getMuldoMere());
		supp(m.getMuldoPere());
		
	}
	
	private void supp(Muldo muldo){
		muldo.suppNbEnfants();
		Main.repMuldo.update(muldo);
		if(muldo.getNbenfant() < 1){
			if(muldo.getId() != 1 && muldo.getId() != 2){
				Main.repMuldo.remove(muldo);
			}
		}else{
			supp(muldo.getMuldoPere());
			supp(muldo.getMuldoMere());
		}
		Main.repMuldo.commit();
	}
	
	public int nbSailliesHypothetique(Muldo mere, Muldo pere){		
		List<Arbre> arbreGenealogique = createArbreGenealogique(mere, pere);
		arbreGenealogique.sort(new Comparator<Arbre>() {
			@Override
			public int compare(Arbre o1, Arbre o2) {
				if(o1.getMuldo().getId() < o2.getMuldo().getId()){
					return -1;
				}
				return 0;
			}
		});
		//TODO Verifie uniquement si les arbres des parents non pas colision /!\ Manque peut être pour la dernière
		//On vérifie s'il y a colision
		int colision = 6; //Pas de colision sur 5 générations
		Arbre a = arbreGenealogique.get(0);
		int l = arbreGenealogique.size();
		for(int i = 1; i<l; i++){
			Arbre b = arbreGenealogique.get(i);
			Muldo m = a.getMuldo();
			Muldo m2 = b.getMuldo();
			if(m.getId() == m2.getId() && !m.getNom().equals("Anonyme")){
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
		gene++;
		if(gene>5){
			return l;
		}
		Arbre a = new Arbre(m, gene);
		l.add(a);
		Muldo pere = m.getMuldoPere();
		if(pere.getNom().equals("Anonyme")){
			l.add(new Arbre(pere, gene));
		}else{
			l = arbreRecursif(pere, gene, l);
		}
		Muldo mere = m.getMuldoMere();
		if(mere.getNom().equals("Anonyme")){
			l.add(new Arbre(mere, gene));
		}else{
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
		if(pere.getId() != 1 && pere.getId() != 2){
			//si le pere n'est pas anonyme
			Muldo perePere = pere.getMuldoPere();
			Muldo merePere = pere.getMuldoMere();
			arbreMuldo[3] = perePere;
			arbreMuldo[4] = merePere;
			if(perePere.getId() != 1 && perePere.getId() != 2){
				//si le perePere n'est pas anonyme
				Muldo perePerePere = perePere.getMuldoPere();
				Muldo merePerePere = perePere.getMuldoMere();
				arbreMuldo[7] = perePerePere;
				arbreMuldo[8] = merePerePere;
			}
			if(merePere.getId() != 1 && merePere.getId() != 2){
				//si le merePere n'est pas anonyme
				Muldo pereMerePere = merePere.getMuldoPere();
				Muldo mereMerePere = merePere.getMuldoMere();
				arbreMuldo[9] = pereMerePere;
				arbreMuldo[10] = mereMerePere;
			}
		}
		if(mere.getId() != 1 && mere.getId() != 2){
			//si le mere n'est pas anonyme
			Muldo pereMere = mere.getMuldoPere();
			Muldo mereMere = mere.getMuldoMere();
			arbreMuldo[5] = pereMere;
			arbreMuldo[6] = mereMere;
			if(pereMere.getId() != 1 && pereMere.getId() != 2){
				//si le perePere n'est pas anonyme
				Muldo perePereMere = pereMere.getMuldoPere();
				Muldo merePereMere = pereMere.getMuldoMere();
				arbreMuldo[11] = perePereMere;
				arbreMuldo[12] = merePereMere;
			}
			if(mereMere.getId() != 1 && mereMere.getId() != 2){
				//si le merePere n'est pas anonyme
				Muldo pereMereMere = mereMere.getMuldoPere();
				Muldo mereMereMere = mereMere.getMuldoMere();
				arbreMuldo[13] = pereMereMere;
				arbreMuldo[14] = mereMereMere;
			}
		}
	}
	
}
