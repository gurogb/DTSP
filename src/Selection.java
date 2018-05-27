import java.util.Random;

public class Selection {
	
	
	
	
	public static Chromosome[] parentSelection(Chromosome[] chrom) {
    	//TODO: implement way to choose parents
    	
    	/*Chromosome.sortChromosomes(chrom, 100);
    	//Chromosome[] selected = new Chromosome[];
    	
        for(int i=0; i<chrom.length; i++) {
    		sum += chrom[i].getCost();
    	}
    	
    	double[] probabilities = new double[chrom.length];
    	for(int i=0; i<chrom.length; i++) {
    		probabilities[i] = chrom[i].getCost()/sum;
    	}
    	
    	double value = new Random().nextDouble();
    	
    	for(int i=0; i<chrom.length; i++) {
    		value -= probabilities[i];
    		if(value<0);
    	}*/

    	
    	/*int rouletteSelect(double[] weight) {
    		// calculate the total weight
    		double weight_sum = 0;
    		for(int i=0; i<weight.length; i++) {
    			weight_sum += weight[i];
    		}
    		// get a random value
    		double value = randUniformPositive() * weight_sum;	
    		// locate the random value based on the weights
    		for(int i=0; i<weight.length; i++) {		
    			value -= weight[i];		
    			if(value < 0) return i;
    		}
    		// when rounding errors occur, we return the last item's index 
    		return weight.length - 1;
    	}

    	// Returns a uniformly distributed double value between 0.0 and 1.0
    	double randUniformPositive() {
    		// easiest implementation
    		return new Random().nextDouble();
    	}*/
    	
   
    	
    	return chrom;
    	
    }
	
	public static Chromosome[]  survivorSelection(Chromosome[] chrom, int numSurvivors) {
    	//TODO: implement way of choosing survivors for next generation
    	//tournament med 5, ta 2 beste
    	int tournamentSize = 5;
    	int winNum = 2;
    	
    	int count = 0;
    	Chromosome[] add = new Chromosome[winNum];
    	Chromosome[] survivors = new Chromosome[numSurvivors];
    	
    	while(count<numSurvivors) {
    		add = tournament(tournamentSize, winNum, chrom);
    		for(int i = 0; i<winNum;i++) {
    			if(count== numSurvivors) {
    				return survivors;
    			}
    			survivors[count] = add[i];
    			count++;
    		}	
    	}

    	return survivors;
    }
    
    public static Chromosome[] tournament(int partNum, int winNum, Chromosome[] chrome) {
		int[] indexes = new int[partNum];
    	//System.out.println(Arrays.toString(indexes));
    	
    	//Get the indexes of the random participants in the tournament
    	for(int i=0; i<partNum;i++) {
    		int num = Utils.getRandomNumberInRange(0,chrome.length-1);
    		while(Utils.contains(indexes, num)) {
    			num = Utils.getRandomNumberInRange(0,chrome.length-1);
    		}
    		indexes[i] = num;	
    	}
    	
    	//System.out.println(Arrays.toString(indexes));
    	
    	//Get the participants
    	//System.out.println("Get the participants");
    	Chromosome[] participants = new Chromosome[partNum];
    	for(int i=0;i<indexes.length;i++) {
    		
    		//System.out.println("i: " + i);
    		//System.out.println("index: " + indexes[i]);
    		participants[i] = chrome[indexes[i]];
    	}
    	
    	//Find the winners of the tournament
    	Chromosome.sortChromosomes(participants, participants.length);
    	Chromosome[] winners = new Chromosome[winNum];
    	for(int i=0; i<winNum; i++) {
    		//System.out.println("participant "+i +participants[i].getCost());;
    		winners[i] = participants[i];
    	}
    	
    	//System.out.println(Arrays.toString(winners));
    	
    	//Return the winners
    	return winners;
    }

}
