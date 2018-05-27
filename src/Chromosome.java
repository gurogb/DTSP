import java.util.ArrayList;
import java.util.Random;

class Chromosome {

    /**
     * The list of cities, which are the genes of this chromosome.
     */
    protected int[] cityList;

    /**
     * The cost of following the cityList order of this chromosome.
     */
    protected double cost;
    
    protected City[] cities;

    /**
     * @param cities The order that this chromosome would visit the cities.
     */
    Chromosome(City[] cities) {
        Random generator = new Random();
        this.cityList = new int[cities.length];
        //cities are visited based on the order of an integer representation [o,n] of each of the n cities.
        for (int x = 0; x < cities.length; x++) {
            this.cityList[x] = x;
        }

        //shuffle the order so we have a random initial order
        for (int y = 0; y < this.cityList.length; y++) {
            int temp = this.cityList[y];
            int randomNum = generator.nextInt(cityList.length);
            this.cityList[y] = this.cityList[randomNum];
            this.cityList[randomNum] = temp;
        }
        this.cities = cities;
        calculateCost(cities);
        
    }
    
    Chromosome(City[] cities, int[] cityList){
    	this.cityList = cityList;
    	this.cities = cities;
    	
    	calculateCost(cities);
    	
    }

    /**
     * Calculate the cost of the specified list of cities.
     *
     * @param cities A list of cities.
     */
    void calculateCost(City[] cities) {
        this.cost = 0;
        for (int i = 0; i < cityList.length - 1; i++) {
            double dist = cities[this.cityList[i]].proximity(cities[this.cityList[i + 1]]);
            cost += dist;
        }

        this.cost += cities[this.cityList[0]].proximity(cities[this.cityList[this.cityList.length - 1]]); //Adding return home
    }

    /**
     * Get the cost for this chromosome. This is the amount of distance that
     * must be traveled.
     */
    double getCost() {
        return cost;
    }

    /**
     * @param i The city you want.
     * @return The ith city.
     */
    int getCity(int i) {
        return cityList[i];
    }
    
    int[] getCities() {
        return cityList;
    }

    /**
     * Set the order of cities that this chromosome would visit.
     *
     * @param list A list of cities.
     */
    void setCities(int[] list) {
        for (int i = 0; i < cityList.length; i++) {
            this.cityList[i] = list[i];
        }
        
        this.calculateCost(this.cities);
    }

    /**
     * Set the index'th city in the city list.
     *
     * @param index The city index to change
     * @param value The city number to place into the index.
     */
    void setCity(int index, int value) {
        cityList[index] = value;
    }

    /**
     * Sort the chromosomes by their cost.
     *
     * @param chromosomes An array of chromosomes to sort.
     * @param num         How much of the chromosome list to sort.
     */
    public static void sortChromosomes(Chromosome chromosomes[], int num) {
        Chromosome ctemp;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < num - 1; i++) {
                if (chromosomes[i].getCost() > chromosomes[i + 1].getCost()) {
                    ctemp = chromosomes[i];
                    chromosomes[i] = chromosomes[i + 1];
                    chromosomes[i + 1] = ctemp;
                    swapped = true;
                }
            }
        }
    }
}
