
public class Recombination {
	
	public static Chromosome nPoint(int n, Chromosome p1, Chromosome p2) {
		 int[] p1List = p1.getCities();
		 int[] p2List = p2.getCities();
		
		return p1;
	}
	
	public static Chromosome[] pmx(City[] cities, Chromosome p1, Chromosome p2) {
		 int[] p1List = p1.getCities();
		 int[] p2List = p2.getCities();
		 
		 
		 int numCities = p1.getCities().length;
		 int[] childList1 = p1List;
		 int[] childList2 = p2List;
		 
		 Chromosome[] children = new Chromosome[2];
		 
		//select random splits
	    int split1 = Utils.getRandomNumberInRange(0,numCities);
	    int split2 = Utils.getRandomNumberInRange(0,numCities);
	 
	  //making sure they are not the same
    	while(split1==split2) {
    		split2 = Utils.getRandomNumberInRange(1,numCities-2);
    	}
    	
    	//making sure they are in the right order
    	if(split1>split2) {
    		int temp = split2;
    		split2 = split1;
    		split1=temp;
    	}
    	
    	int temp1;
    	int temp2;
    	int index;
    	
    	//Creating child 1 by inserting points from parent2 into parent1
    	for(int i =split1; i<split2;i++) {
    		temp1 = p2List[i];
    		temp2 = childList1[i];
    		index = Utils.findIndex(childList1, temp1);
    		childList1[index] = temp2;
    		childList1[i] = temp1;
    	}
    	
    	//Creating child 1 by inserting points from parent2 into parent1
    	for(int i =split1; i<split2;i++) {
    		temp1 = p1List[i];
    		temp2 = childList2[i];
    		index = Utils.findIndex(childList2, temp1);
    		childList2[index] = temp2;
    		childList2[i] = temp1;
    	}
		 
    	children[0] = new Chromosome(cities,childList1);
		children[1] = new Chromosome(cities,childList1);
		return children;
	}
	
	
    /*public static Chromosome[]  recombine(Chromosome[] chrom) {
    	
    	//System.out.println("Before recombination:" + Arrays.toString(chrom[0].getCities()));
    	//TODO:implement crossover algortihm
    	Chromosome[] children = new Chromosome[2*chrom.length];
    	Chromosome[] c;
    	int index = 0;
    	for(int a=0; a<chrom.length;a++) {
    		int r = Utils.getRandomNumberInRange(0,chrom.length-1);
    		c = pmx(chrom[a], chrom[r]);
    		children[index] = c[0];
    		children[index+1] = c[1];
    		
    		index +=2;
    	}
    	//System.out.println("After recombination:" + Arrays.toString(children[0].getCities()));
    	return children;
    }*/
    
    private static Chromosome[] crossover(Chromosome p1, Chromosome p2) {
    	/*System.out.println("Parents:");
    	System.out.println(Arrays.toString(p1.getCities()));
    	System.out.println(Arrays.toString(p2.getCities()));*/
    	
    	
    	Chromosome[] children = new Chromosome[2];
    	int numCities = p1.getCities().length;
    	int[] cities1 = new int[numCities];
    	int[] cities2 = new int[numCities];
    	
    	//2-point
    	//select random splits
    	int split1 = Utils.getRandomNumberInRange(1,numCities-2);
    	int split2 = Utils.getRandomNumberInRange(1,numCities-2);
    	
    	
    	//making sure they are not the same
    	while(split1==split2) {
    		split2 = Utils.getRandomNumberInRange(1,numCities-2);
    	}
    	
    	//making sure they are in the right order
    	if(split1>split2) {
    		int temp = split2;
    		split2 = split1;
    		split1=temp;
    	}

    		
    	//crossover algorithm
    	
    	
    	p1.setCities(cities1);
    	p2.setCities(cities2);
    	
    	/*System.out.println("Children:");
    	System.out.println(Arrays.toString(cities1));
    	System.out.println(Arrays.toString(cities2));*/
    	children[0] = p1;
    	children[1] = p2;
    	
    	return children;
    }
    
    
    

}
