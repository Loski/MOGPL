package RushHour;

import java.util.ArrayList;
import java.util.Collections;

public abstract class DijkstraSolver {
	
	private static int[][] resolve(ArrayList<ArrayList<Integer>> matrice_adj, ArrayList<RushHour> configurations)
	{
		int[] distances = new int[matrice_adj.size()];
		int[] predecesseurs = new int[matrice_adj.size()];
		ArrayList<Integer> sommetsNonMarques = new ArrayList<Integer>();
		
		for(int i=0;i<distances.length;i++)
		{
			sommetsNonMarques.add(i);
			distances[i] = Integer.MAX_VALUE;
			predecesseurs[i] = i;
		}
		
		distances[0]=0;
		
		boolean finished = false;
		
		int nvSommetMarque=0;
		
		while((!finished) && sommetsNonMarques.size()>0)
		{		
			int minDistance=Integer.MAX_VALUE;
			
			for(Integer sommet : sommetsNonMarques)
			{
				if(distances[sommet.intValue()]<=minDistance)
				{
					nvSommetMarque=sommet.intValue();
					minDistance=distances[sommet.intValue()];					
				}
			}
			
			if(configurations.get(nvSommetMarque).isSolution())
			{
				finished=true;
			}
			
			else
			{
				sommetsNonMarques.remove(Integer.valueOf(nvSommetMarque));
				int i=0;
				
				
				for(Integer poids : matrice_adj.get(nvSommetMarque))
				{
					//si le poids � [i][j]>=0 alors j est voisin de i			
					if(poids.intValue()>=0)
					{						
						if(distances[i]> distances[nvSommetMarque] + poids.intValue())
							predecesseurs[i]=nvSommetMarque;
						
						distances[i]=Math.min(distances[i],distances[nvSommetMarque] + poids.intValue());					

					}
					
					i++;
				}
			}
			
			/*System.out.println("DISTANCE + sommet( "+ nvSommetMarque +" )");
			for(int i=0;i<distances.length;i++)
			{
				System.out.print(distances[i]+"\t");
			}
			
			System.out.println();
			
			System.out.println("SUCCESEUR + sommet( "+ nvSommetMarque +" )");
			for(int i=0;i<distances.length;i++)
			{
				System.out.print(predecesseurs[i]+"\t");
			}
			
			System.out.println("\n");*/
			
		}		
		
		int i=0;
		
		int[][] result = new int[2][];
		result[0] = distances; 
		result[1] = predecesseurs;
		
		return result;
	}
	
	private static ArrayList<RushHour> createSequence(int[] predecesseurs,ArrayList<RushHour> configurations,int indexOfSolution)
	{
		boolean configDepart=false;
		ArrayList<RushHour> sequence = new ArrayList<RushHour>();
		sequence.add(configurations.get(indexOfSolution));
		int currentRushHour=indexOfSolution;
		
		while(!configDepart)
		{
			currentRushHour=predecesseurs[currentRushHour];
			sequence.add(configurations.get(currentRushHour));
			
			if(configurations.get(currentRushHour).equals(configurations.get(0)))
				configDepart=true;		
			
		}
		
		//System.out.println("\n"+sequence.size());
		
		Collections.reverse(sequence);
		
		return sequence;
	}
	
	
	public static Object[] resolveRHC(ArrayList<ArrayList<Integer>> matrice_adj, ArrayList<RushHour> configurations,int indexOfSolution)
	{
		long startTime = System.nanoTime();
		
		int[][] resultDij = resolve(matrice_adj, configurations);
		
		int[] predecesseurs = resultDij[1];
		int[] distance = resultDij[0];
		
		int nbCaseDeplace = distance[indexOfSolution];
			
		ArrayList<RushHour> sequence = createSequence(predecesseurs, configurations, indexOfSolution);
		
		Object[] result = new Object[2];
		result[0] = nbCaseDeplace;
		result[1] = sequence;
		
		long endTime = System.nanoTime();

		long duration = (endTime - startTime); 
		
		System.out.println("DIJKSTRA WAS DONE IN "+duration/1000000+" ms");
		System.out.println("Nombre de Sommet = " + configurations.size());
		
		return result;
	}
	
	public static Object[] resolveRHM(ArrayList<ArrayList<Integer>> matrice_adj, ArrayList<RushHour> configurations,int indexOfSolution)
	{
		ArrayList<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> line : matrice_adj)
		{
			ArrayList<Integer> lineCopy = new ArrayList<Integer>();
			
			for(Integer poids : line)
			{
				if(poids.intValue()>=0)
					lineCopy.add(1);	
				else
					lineCopy.add(-1);
			}
			
			copy.add(lineCopy);
		}
		
		
		int[][] resultDij = resolve(matrice_adj, configurations);
		
		int[] predecesseurs = resultDij[1];
		int[] distance = resultDij[0];
		
		int nbCaseDeplace = distance[indexOfSolution];
			
		ArrayList<RushHour> sequence = createSequence(predecesseurs, configurations, indexOfSolution);
		
		Object[] result = new Object[2];
		result[0] = nbCaseDeplace;
		result[1] = sequence;
		
		return result;
	}
	
	
}
