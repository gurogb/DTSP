import java.util.Arrays;
import java.util.stream.Stream;

//import java.util.Arrays;


class Evolution{
	
	/**
     * The part of the population eligable for mating.
     */
    protected static int matingPopulationSize;

    /**
     * The part of the population selected for mating.
     */
    protected static int selectedParents;
    
    protected static double mutationRate = 80;

	public static Chromosome[] evolve(Chromosome[] population, City[] cities) {
		//TODO:Write evolution code here.
		Chromosome[] mutatedPopulation = new Chromosome[population.length];
		Chromosome[] newPopulation = new Chromosome[population.length*2];
		Arrays.sort(population, (x,y) -> Double.valueOf(x.getCost()).compareTo(Double.valueOf(y.getCost()))); //Sort old population by cost
		
		for(Chromosome c : population) { c.calculateCost(cities);}
		double avg = Utils.calculateAvgCost(population);
		System.out.println("Avg cost before evolving: " + avg);
    	
		//Step 1: Parent selection
    	/*Chromosome[]  parents = parentSelection(chromosomes);*/
		
    	//Step 2: Mutate
		int index = 0;
		for(int i=0; i<population.length; i++) {
			boolean mutate = Utils.getRandomNumberInRange(0, 1) < mutationRate;
			mutatedPopulation[i] = mutate ? Mutation.inverseMutation(population[i]) : new Chromosome(cities, population[i].getCities());
	    	
	    	//Step 3: Reecombination of parents (crossover): parre to og to foreldre, gå iterativt gjennom og parre med en random
	    	/*Chromosome[] children = recombine(chromosomes);*/
			//boolean crossover = Utils.getRandomNumberInRange(0, population.length-1 )>  20 ? true : false;
			//Chromosome[] children = crossover ? Recombination.pmx(cities,mutatedPopulation[i], population[ Utils.getRandomNumberInRange(0, mutatedPopulation.length-1)]) : new Chromosome(cities, mutatedPopulation[i].getCities());;
	    	Chromosome[] children = Recombination.pmx(cities,mutatedPopulation[i], population[ Utils.getRandomNumberInRange(0, mutatedPopulation.length-1)]);
	    	newPopulation[index] = children[0];
	    	newPopulation[index+1] = children[0];
			
			
			newPopulation[index].calculateCost(cities);
			newPopulation[index+1].calculateCost(cities);
			
			index +=2;
    	}
		
		for(Chromosome c : newPopulation) { c.calculateCost(cities);}
		double avg1 = Utils.calculateAvgCost(newPopulation);
		System.out.println("Avg cost of newPop after evolving: " + avg1 + ", " + newPopulation.length);
    	
		/*Chromosome[] childAndPar = Stream.of(newPopulation, population).flatMap(Stream::of).toArray(Chromosome[]::new);
		
		
		for(Chromosome c : childAndPar) { c.calculateCost(cities);}
		double avg2 = Utils.calculateAvgCost(childAndPar);
		System.out.println("Avg cost of new and old pop after evolving: " + avg2);

    	//Step 4:Evaluate new candidates
    	//for(Chromosome c : newPopulation) { c.calculateCost(cities);}
		Arrays.sort(childAndPar, (x,y) -> Double.valueOf(x.getCost()).compareTo(Double.valueOf(y.getCost())));*/

    	//Step 5:Survivor selection: tournament med 5, ta 2 beste
		Chromosome[] survivors = Selection.survivorSelection(newPopulation, population.length);
		
		for(Chromosome c : survivors) { c.calculateCost(cities);}
		double avg3 = Utils.calculateAvgCost(survivors);
		System.out.println("Avg cost of survivors after selection: " + avg3 +", " + survivors.length);
		
		return survivors;

        

	}
	
	
	
	/**
	 * The method used to generate a mutant of a chromosome
	 * @param original The chromosome to mutate.
	 * @param cityList list of cities, needed to instantiate the new Chromosome.
	 * @return Mutated chromosome.
	 */
	/*public static Chromosome Mutate(Chromosome original, City [] cityList){
      int [] cityIndexes = original.getCities();
      int [] newCityIndexes = Arrays.copyOf(cityIndexes, cityIndexes.length);
      
      return new Chromosome(newCityIndexes, cityList);
   }*/
	
	/**
	 * Breed two chromosomes to create a offspring
	 * @param parent1 First parent.
	 * @param parent2 Second parent.
	 * @param cityList list of cities, needed to instantiate the new Chromosome.
	 * @return Chromosome resuling from breeding parent.
	 */
	/*public static Chromosome Breed(Chromosome parent1, Chromosome parent2, City [] cityList){
	      int [] cityIndexes = parent1.getCities();
	      int [] newCityIndexes = Arrays.copyOf(cityIndexes, cityIndexes.length);
	      
	      return new Chromosome(newCityIndexes, cityList);
	   }*/

	/**
	 * Evolve given population to produce the next generation.
	 * @param population The population to evolve.
	 * @param cityList List of ciies, needed for the Chromosome constructor calls you will be doing when mutating and breeding Chromosome instances
	 * @return The new generation of individuals.
	 */
   /*public static Chromosome [] Evolve(Chromosome [] population, City [] cityList){
      Chromosome [] newPopulation = new Chromosome [population.length];
      for (int i = 0; i<population.length; ++i){
         newPopulation[i] = Mutate(population[i], cityList);
      }
      
      return newPopulation;
   }*/
   
}