package RushHour;

import java.util.ArrayList;
import RushHour.RushHour.Direction;

public class GrapheConfiguration {
	
	private ArrayList<ArrayList<Integer>> matrice_adj; //contient les poids si poids[i][j]>=0 alors arr�te entre i et j  
	private ArrayList<Integer> indexOfSolutions; 
	private ArrayList<RushHour> configurations;
	private static final int[] all_direction = {Direction.FORWARD, Direction.BACKWARD};
	
	public ArrayList<ArrayList<Integer>> getMatrice_adj() {
		return matrice_adj;
	}

	public void setMatrice_adj(ArrayList<ArrayList<Integer>> matrice_adj) {
		this.matrice_adj = matrice_adj;
	}

	public ArrayList<RushHour> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(ArrayList<RushHour> configurations) {
		this.configurations = configurations;
	}
	
	public ArrayList<Integer> getIndexOfSolutions()
	{
		return this.indexOfSolutions;
	}
	
	public GrapheConfiguration(RushHour configDepart)
	{
		this.configurations=new ArrayList<RushHour>();
		this.matrice_adj=new ArrayList<ArrayList<Integer>>();
		this.indexOfSolutions = new ArrayList<Integer>();
		
		/*int[] arrete = {0,0};
		ArrayList<Integer> intersect = new ArrayList<Integer>();
		intersect.add(-1);
		this.matrice_adj.add(intersect);*/
		
		addSommet(configDepart);		
		creerGraphe(0);
		//System.out.println(this.configurations.size());
	}
	
	public void creerGraphe(int index){	
		//System.out.println(configurations.size());
		RushHour r = this.configurations.get(index);
		RushHour tmp = null;
		for(Vehicule v: r.getVehicules())
			for(int direction:all_direction){
				boolean changement = true;
				int taille_max = 6;
				if(v.getOrientation() == Orientation.HORIZONTAL){
					if(direction == Direction.FORWARD)
						taille_max = r.getNbColonne() - (r.getMarqueurs().get(v.getCode())%r.getNbColonne() + v.taille);
					else
						taille_max = r.getMarqueurs().get(v.getCode())%r.getNbColonne();
				}
				else{
					if(direction == Direction.FORWARD)
						taille_max = r.getNbLigne() -((int)r.getMarqueurs().get(v.getCode())/r.getNbColonne() + v.taille);
					else taille_max = (int)r.getMarqueurs().get(v.getCode())/r.getNbColonne();
				}
				for(int j = 1; j <=taille_max; j++){
					if(changement)
						tmp = (RushHour) r.clone();
					changement = tmp.deplacement_multiple(v, direction, v.getOrientation(), j);
					if(changement){
						if(!this.configurations.contains(tmp)){
							addSommet(tmp);	
						}
						if(index!=this.configurations.size()-1)
						{
							setSuccesseur(index, this.configurations.indexOf(tmp),j);
							setSuccesseur(this.configurations.indexOf(tmp),index,j);
						}
					}
					else
						j = 50;
				}
			}
		
		//Si on a ajout� une config
		if(index+1<this.configurations.size())
			creerGraphe(index+1);
		
		return;
	}
	
	public void addSommet(RushHour r)
	{
		

		if(r.isSolution())
			indexOfSolutions.add(this.configurations.size());
		
		this.configurations.add(r);
		
		/*System.out.println("NV TABLEAU :");
		
		for(RushHour r0 : this.configurations)
			System.out.println(r0);*/
		
		ArrayList<Integer> newSommet = new ArrayList<Integer>();
		this.matrice_adj.add(newSommet);
		
		
		for(int i=0;i<this.matrice_adj.size();i++)
		{				
			 // ajout nv colonne correspondant au nouveau sommet pour tous les sommets existants
			this.matrice_adj.get(i).add(-1);			
			
			//ajout chaque cellule pour la ligne du nouveau sommet sauf la derni�re qui est faite avant
			if(i!=this.matrice_adj.size()-1)
				newSommet.add(-1);
		}		
	}
	
	public void setSuccesseur(int sommetPere, int sommetFils, int cout)
	{
		//int[] arrete = {1,cout};
		this.matrice_adj.get(sommetPere).set(sommetFils, cout);
	}
	
	public boolean isSuccesseur(int sommetPere,int sommetFils)
	{
		if(this.matrice_adj.get(sommetPere).get(sommetFils)>=0)
			return true;
			
		return false;
	}
	
	/*public int getCout(int sommetPere,int sommetFils)
	{
		if(!isSuccesseur(sommetPere, sommetFils))
			return -1;
		
		return this.matrice_adj.get(sommetPere).get(sommetFils);
	}*/
	
	public void afficherMatrice()
	{		
		for(int i=0;i<this.matrice_adj.size();i++)
		{
			for(int j=0;j<this.matrice_adj.get(i).size();j++)
			{
				System.out.print(String.format("%d\t", this.matrice_adj.get(i).get(j)));
			}
			
			System.out.println();
		}
	}
	
	public int getNBofIntInMatrice(int a)
	{
		int compteur=0;
		
		for(int i=0;i<this.matrice_adj.size();i++)
		{
			for(int j=0;j<this.matrice_adj.get(i).size();j++)
			{
				if(matrice_adj.get(i).get(j).equals(a))
					compteur++;
			}
		}
		
		return compteur;
	}
	
	public static void main(String[] args)
	{
		//RushHour r1 = new RushHour("puzzles/d�butant/jam1.txt");
		RushHour r1 = new RushHour("puzzles/expert/jam40.txt"); 		
		GrapheConfiguration g1 = new GrapheConfiguration(r1);
		System.out.println(g1.getIndexOfSolutions().get(0));
		DijkstraSolver.resolveRHC(g1.getMatrice_adj(), g1.getConfigurations(), g1.getIndexOfSolutions().get(0));
		g1.afficherMatrice();
		System.out.println(g1.getNBofIntInMatrice(1));
	}
	
	
}
