package Entities;

public class Arbre {

	private Muldo muldo;
	private int generation;
	
	public Arbre(){
		generation = 0;
	}
	
	public Arbre(Muldo m, int gene){
		this.muldo = m;
		this.generation = gene;
	}

	public Muldo getMuldo() {
		return muldo;
	}

	public int getGeneration() {
		return generation;
	}
	
}
