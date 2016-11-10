import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RushHour {

	private ArrayList<ArrayList<String>> grille;
	private HashMap<String,Integer> marqueurs;
	private int nbLigne;
	private int nbColonne;
	
	public RushHour(String filename)
	{
		BufferedReader buffer;
		String x;
		this.grille = new ArrayList<ArrayList<String>>();
		this.marqueurs = new HashMap<String,Integer>();
		
        try 
        {
        	int i=0;
            buffer = new BufferedReader(new FileReader(filename));
            
            if((x = buffer.readLine()) != null)	
            {
            	//System.out.println(x);
            	
            	Scanner scanner = new Scanner(x);
            	this.nbLigne = scanner.nextInt();
            	this.nbColonne = scanner.nextInt();
            	scanner.close();
            }
            
            while ( (x = buffer.readLine()) != null ) {
                // printing out each line in the file
                //System.out.println(x);
            	
            	ArrayList<String> ar = new ArrayList<String>();
            	
            	StringTokenizer st = new StringTokenizer(x," ");
            	while(st.hasMoreElements())
            	{
            		String s = (String)st.nextElement();
            		ar.add(s);
            		i++;
            		if(!this.marqueurs.containsKey(s) && !s.equals("0"))
            		{
            			this.marqueurs.put(s,i);
            		}
            	}
            	
            	this.grille.add(ar);
            }
            
        }catch(Exception e){e.printStackTrace();}
	}
	
	public void afficher()
	{
		for(int i=0;i<this.nbLigne;i++)
		{			
			for(int j=0;j<this.nbColonne;j++)
			{
				System.out.print(this.grille.get(i).get(j)+"\t");
			}
			
			System.out.println();
		}
		
		//DEBUG MARQUEUR
		System.out.println();
		Iterator<String> ite = marqueurs.keySet().iterator();
		 
		while (ite.hasNext())
		{
		    String key = ite.next();
		    System.out.println(key+" :" + this.marqueurs.get(key));
		}
	}
	
	public String toString()
	{
		String s ="";
		
		for(int i=0;i<this.nbLigne;i++)
		{			
			for(int j=0;j<this.nbColonne;j++)
			{
				s+=this.grille.get(i).get(j)+"\t";
			}
			
			s+="\n";
		}	
		
		return s;
	}
	
	public boolean equals(RushHour r2)
	{
		for(int i=0;i<this.nbLigne;i++)
		{			
			for(int j=0;j<this.nbColonne;j++)
			{
				if(!this.grille.get(i).get(j).equals(r2.grille.get(i).get(j)))
					return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<ArrayList<String>> getGrille()
	{
		return this.grille;
	}
	
	public HashMap<String,Integer> getMarqueurs()
	{
		return this.marqueurs;
	}
	
	public static void main(String[] args)
	{
		//RushHour r1 = new RushHour("puzzles/d�butant/jam1.txt");
		RushHour r1 = new RushHour("puzzles/debug.txt"); 
		r1.afficher();
	}

	
}