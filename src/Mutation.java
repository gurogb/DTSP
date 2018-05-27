import java.util.Arrays;

public class Mutation {
	
	
	
	public static Chromosome[]  mutate(Chromosome[] chrom) {
    	//TODO: implement mutation method
    	
    	int prob = 100;
    	int r;
    	Chromosome mutated;
    	
    	for(int i=0; i<chrom.length; i++) {
    		r = Utils.getRandomNumberInRange(0,100);
    		if(r>prob) {
    			mutated = inverseMutation(chrom[i]);
    			chrom[i] = mutated;
    		}
    	}
    	
    	
    	//ES - adaptive mutation step sizes
    	
    	return chrom;
    }
    
    public static Chromosome inverseMutation(Chromosome c) {
    	//Cut out random segment, inserts it in opposite direction
    	int[] segment = c.getCities();
    	int numCities = segment.length;
    	
    	int split1 = Utils.getRandomNumberInRange(0,numCities-2);//0-8
    	int split2 = Utils.getRandomNumberInRange(1,numCities);//1-10
    	
    	
    	//making sure they are in the right order
    	if(split1>split2) {
    		int temp = split2;
    		split2 = split1;
    		split1=temp;
    	}
    	
    	//making sure they are not the same, or only one apart
    	while(split2-split1 < 2) {
    		split1 = Utils.getRandomNumberInRange(0,numCities-2);//0-8
        	split2 = Utils.getRandomNumberInRange(1,numCities);//1-10
    	}

    	int[] inversed = new int[split2-split1];
    	
    	//creating inversed segment
    	for(int i=0; i< inversed.length;i++ ){
    		inversed[i] = segment[split2-i-1];
    	}
    	//System.out.println(Arrays.toString(inversed));
    	
    	//inserting inversed segment
    	for(int i=0; i< inversed.length;i++) {
    		segment[split1+i] = inversed[i];
    	}
    	
    	/*System.out.println(Arrays.toString(original));
    	System.out.println(split1);
    	System.out.println(split2);*/
    	c.setCities(segment);
    	
    	return c;
    }
    
    public static Chromosome insertionMutation(Chromosome c) {
    	//Selects random position, insert gene on that position into another random position
    	int[] cities = c.getCities();
    	System.out.println(Arrays.toString(cities));
    	int numCities = cities.length;
    	int[] result = new int[numCities];
    	
    	int takeIndex = Utils.getRandomNumberInRange(0,numCities-1);//0-9
    	int putIndex = Utils.getRandomNumberInRange(0,numCities-1);//0-9   
    	
    	
    	//making sure they are not the same
    	while(takeIndex==putIndex) {
        	putIndex = Utils.getRandomNumberInRange(0,numCities-1);//0-9
    	}

    	
    	//System.out.println("Take:" +takeIndex);
    	//System.out.println("Put; " + putIndex);

    	int take = cities[takeIndex];
    	
    	
    	if(takeIndex<putIndex) {      	
        	for(int i=0; i<numCities;i++) {
        		if(i<takeIndex) {
        			//same as original
        			result[i] = cities[i];
        		}else if(takeIndex<=i&&i<putIndex) {
        			//put number from index before
        			result[i] = cities[i+1];
        		}else if(i>putIndex) {
        			//same as original
        			result[i] = cities[i];
        		}else if(i==putIndex) {
        			result[putIndex] = take;
        		}
        	}        	
    	}else {
        	for(int i=0; i<numCities;i++) {
        		if(i<putIndex) {
        			//same as original
        			result[i] = cities[i];
        		}else if(putIndex<i&&i<=takeIndex) {
        			//put number from index before
        			result[i] = cities[i-1];
        		}else if(i>takeIndex) {
        			//same as original
        			result[i] = cities[i];
        		}else if(i==putIndex) {
        			result[putIndex] = take;
        		}
        	}
    	}
    	
    	//System.out.println(Arrays.toString(result));

    	c.setCities(result);
    	
    	
    	return c;
    }
    
    public static Chromosome shiftingMutation(Chromosome c) {
    	//TODO: Implement as mix of inverse and insert
    	return c;
    }
    
    public static Chromosome transpositionMutation(Chromosome c) {
    	int[] cities = c.getCities();
    	System.out.println(Arrays.toString(cities));
    	int numCities = cities.length;
    	int[] result = new int[numCities];
    	
    	int i1 = Utils.getRandomNumberInRange(0,numCities-1);//0-9
    	int i2 = Utils.getRandomNumberInRange(0,numCities-1);//0-9   
    	
    	
    	//making sure they are not the same
    	while(i1==i2) {
        	i2 = Utils.getRandomNumberInRange(0,numCities-1);//0-9
    	}
    	
    	for(int i=0; i<numCities;i++) {
    		if(i==i1) {
    			result[i] = cities[i2];
    		}else if(i==i2) {
    			result[i] = cities[i1];
    		}else {
    			result[i] = cities[i];
    		}
    	}
    	
    	
    	return c;
    }

}
