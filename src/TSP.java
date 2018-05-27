import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Time;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.*; 

import javax.swing.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class TSP {

	private static final int cityShiftAmount = 60; //DO NOT CHANGE THIS.
	
    /**
     * How many cities to use.
     */
    protected static int cityCount;

    /**
     * How many chromosomes to use.
     */
    protected static int populationSize = 100; //DO NOT CHANGE THIS.

    /**
     * The part of the population eligable for mating.
     */
    protected static int matingPopulationSize;

    /**
     * The part of the population selected for mating.
     */
    protected static int selectedParents;

    /**
     * The current generation
     */
    protected static int generation;

    /**
     * The list of cities (with current movement applied).
     */
    protected static City[] cities;
    
    /**
     * The list of cities that will be used to determine movement.
     */
    private static City[] originalCities;

    /**
     * The list of chromosomes.
     */
    protected static Chromosome[] chromosomes;

    /**
    * Frame to display cities and paths
    */
    private static JFrame frame;

    /**
     * Integers used for statistical data
     */
    private static double min;
    private static double avg;
    private static double max;
    private static double sum;
    private static double genMin;

    /**
     * Width and Height of City Map, DO NOT CHANGE THESE VALUES!
     */
    private static int width = 600;
    private static int height = 600;


    private static Panel statsArea;
    private static TextArea statsText;


    /*
     * Writing to an output file with the costs.
     */
    private static void writeLog(String content) {
        String filename = "results.out";
        FileWriter out;

        try {
            out = new FileWriter(filename, true);
            out.write(content + "\n");
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     *  Deals with printing same content to System.out and GUI
     */
    private static void print(boolean guiEnabled, String content) {
        if(guiEnabled) {
            statsText.append(content + "\n");
        }

        System.out.println(content);
    }

    
    public static void evolve() {
        //TODO:Write evolution code here.
    	//Step 1: Parent selection
    	/*Chromosome[]  parents = parentSelection(chromosomes);*/

    	
    	//Step 2: Reecombination of parents (crossover): parre to og to foreldre, gå iterativt gjennom og parre med en random
    	/*Chromosome[] children = recombine(chromosomes);*/
    	
    	//Step 3: Mutate
    	Chromosome[] mutated = mutate(chromosomes);
    	
    	Chromosome[] childAndPar = Stream.of(mutated, chromosomes).flatMap(Stream::of).toArray(Chromosome[]::new);
    	//Step 4:Evaluate new candidates

    	
    	//Step 5:Survivor selection: tournament med 5, ta 2 beste
    	//System.out.println("Chromosomes lenght: " + chromosomes.length);
    	//System.out.println("Children length: " + mutated.length);
    	
    	
    	for(Chromosome c : childAndPar) {
        	c.calculateCost(cities);
        }

    	//System.out.println("Both length: " + childAndPar.length);
    	//System.out.println("Running survivor selection");
        Chromosome[] survivors = survivorSelection(childAndPar, 100);
        /*for (int x = 0; x < populationSize; x++) {
            survivors[x].calculateCost(cities);
        }*/
        
        
        
        //boolean b = (chromosomes == survivors);
        //System.out.println("Bool:"+ b);
        
        
        
        
        
        //System.out.println("survivors length: " + survivors.length);
        
    	//Step6: Update chromosomes variable with new generation
        chromosomes = survivors;

    }
    
    public static Chromosome[] parentSelection(Chromosome[] chrom) {
    	//TODO: implement way to choose parents
    	
    	Chromosome.sortChromosomes(chrom, 100);
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
    	}

    	
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
    
    
    public static Chromosome[]  recombine(Chromosome[] chrom) {
    	
    	//System.out.println("Before recombination:" + Arrays.toString(chrom[0].getCities()));
    	//TODO:implement crossover algortihm
    	Chromosome[] children = new Chromosome[2*chrom.length];
    	Chromosome[] c;
    	int index = 0;
    	for(int a=0; a<chrom.length;a++) {
    		int r = getRandomNumberInRange(0,chrom.length-1);
    		/*System.out.println("index: " + a);
    		System.out.println("randomIndex: " + r);
    		System.out.println("Chromosome at index 1: " + Arrays.toString(chrom[1].getCities()));
    		System.out.println("Chromosome at random 10: " + Arrays.toString(chrom[10].getCities()));*/
    		c = crossover(chrom[a], chrom[r]);
    		children[index] = c[0];
    		children[index+1] = c[1];
    		
    		index +=2;
    	}
    	//System.out.println("After recombination:" + Arrays.toString(children[0].getCities()));
    	return children;
    }
    
    public static Chromosome[] crossover(Chromosome p1, Chromosome p2) {
    	/*System.out.println("Parents:");
    	System.out.println(Arrays.toString(p1.getCities()));
    	System.out.println(Arrays.toString(p2.getCities()));*/
    	
    	
    	Chromosome[] children = new Chromosome[2];
    	int numCities = p1.getCities().length;
    	int[] cities1 = new int[numCities];
    	int[] cities2 = new int[numCities];
    	
    	//2-point
    	//select random splits
    	int split1 = getRandomNumberInRange(1,numCities-2);
    	int split2 = getRandomNumberInRange(1,numCities-2);
    	
    	
    	//making sure they are not the same
    	while(split1==split2) {
    		split2 = getRandomNumberInRange(1,numCities-2);
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
    
    public static Chromosome[]  mutate(Chromosome[] chrom) {
    	//TODO: implement mutation method
    	
    	int prob = 100;
    	int r;
    	Chromosome mutated;
    	
    	for(int i=0; i<chrom.length; i++) {
    		r = getRandomNumberInRange(0,100);
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
    	
    	int split1 = getRandomNumberInRange(0,numCities-2);//0-8
    	int split2 = getRandomNumberInRange(1,numCities);//1-10
    	
    	
    	//making sure they are in the right order
    	if(split1>split2) {
    		int temp = split2;
    		split2 = split1;
    		split1=temp;
    	}
    	
    	//making sure they are not the same, or only one apart
    	while(split2-split1 < 2) {
    		split1 = getRandomNumberInRange(0,numCities-2);//0-8
        	split2 = getRandomNumberInRange(1,numCities);//1-10
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
    	
    	int takeIndex = getRandomNumberInRange(0,numCities-1);//0-9
    	int putIndex = getRandomNumberInRange(0,numCities-1);//0-9   
    	
    	
    	//making sure they are not the same
    	while(takeIndex==putIndex) {
        	putIndex = getRandomNumberInRange(0,numCities-1);//0-9
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
    	
    	int i1 = getRandomNumberInRange(0,numCities-1);//0-9
    	int i2 = getRandomNumberInRange(0,numCities-1);//0-9   
    	
    	
    	//making sure they are not the same
    	while(i1==i2) {
        	i2 = getRandomNumberInRange(0,numCities-1);//0-9
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
    		int num = getRandomNumberInRange(0,chrome.length-1);
    		while(contains(indexes, num)) {
    			num = getRandomNumberInRange(0,chrome.length-1);
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
    
	public static boolean contains(int[] array, int num) {
		if(IntStream.of(array).anyMatch(x -> x == num)) {
			return true;
		}else {
			return false;
		}
	}

    /**
     * Update the display
     */
    public static void updateGUI() {
        Image img = frame.createImage(width, height);
        Graphics g = img.getGraphics();
        FontMetrics fm = g.getFontMetrics();

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        if (true && (cities != null)) {
            for (int i = 0; i < cityCount; i++) {
                int xpos = cities[i].getx();
                int ypos = cities[i].gety();
                g.setColor(Color.green);
                g.fillOval(xpos - 5, ypos - 5, 10, 10);
                
                //// SHOW Outline of movement boundary
                // xpos = originalCities[i].getx();
                // ypos = originalCities[i].gety();
                // g.setColor(Color.darkGray);
                // g.drawLine(xpos + cityShiftAmount, ypos, xpos, ypos + cityShiftAmount);
                // g.drawLine(xpos, ypos + cityShiftAmount, xpos - cityShiftAmount, ypos);
                // g.drawLine(xpos - cityShiftAmount, ypos, xpos, ypos - cityShiftAmount);
                // g.drawLine(xpos, ypos - cityShiftAmount, xpos + cityShiftAmount, ypos);
            }

            g.setColor(Color.gray);
            for (int i = 0; i < cityCount; i++) {
                int icity = chromosomes[0].getCity(i);
                if (i != 0) {
                    int last = chromosomes[0].getCity(i - 1);
                    g.drawLine(
                        cities[icity].getx(),
                        cities[icity].gety(),
                        cities[last].getx(),
                        cities[last].gety());
                }
            }
                        
            int homeCity = chromosomes[0].getCity(0);
            int lastCity = chromosomes[0].getCity(cityCount - 1);
                        
            //Drawing line returning home
            g.drawLine(
                    cities[homeCity].getx(),
                    cities[homeCity].gety(),
                    cities[lastCity].getx(),
                    cities[lastCity].gety());
        }
        frame.getGraphics().drawImage(img, 0, 0, frame);
    }

    private static City[] LoadCitiesFromFile(String filename, City[] citiesArray) {
        ArrayList<City> cities = new ArrayList<City>();
        try 
        {
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            while ((line = bufferReader.readLine()) != null) { 
                String [] coordinates = line.split(", ");
                cities.add(new City(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
            }

            bufferReader.close();

        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());                      
        }
        
        citiesArray = new City[cities.size()];
        return cities.toArray(citiesArray);
    }

    private static City[] MoveCities(City[]cities) {
    	City[] newPositions = new City[cities.length];
        Random randomGenerator = new Random();

        for(int i = 0; i < cities.length; i++) {
        	int x = cities[i].getx();
        	int y = cities[i].gety();
        	
            int position = randomGenerator.nextInt(5);
            
            if(position == 1) {
            	y += cityShiftAmount;
            } else if(position == 2) {
            	x += cityShiftAmount;
            } else if(position == 3) {
            	y -= cityShiftAmount;
            } else if(position == 4) {
            	x -= cityShiftAmount;
            }
            
            newPositions[i] = new City(x, y);
        }
        
        return newPositions;
    }

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
    
    public static void main(String[] args) {
    	
    	
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String currentTime  = df.format(today);

        int runs;
        boolean display = false;
        String formatMessage = "Usage: java TSP 1 [gui] \n java TSP [Runs] [gui]";

        if (args.length < 1) {
            System.out.println("Please enter the arguments");
            System.out.println(formatMessage);
            display = false;
        } else {

            if (args.length > 1) {
                display = true; 
            }

            try {
                cityCount = 50;
                populationSize = 100; //TODO:CHANGE BACK TO 100
                runs = Integer.parseInt(args[0]);

                if(display) {
                    frame = new JFrame("Traveling Salesman");
                    statsArea = new Panel();

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setSize(width + 300, height);
                    frame.setResizable(false);
                    frame.setLayout(new BorderLayout());
                    
                    statsText = new TextArea(35, 35);
                    statsText.setEditable(false);

                    statsArea.add(statsText);
                    frame.add(statsArea, BorderLayout.EAST);
                    
                    frame.setVisible(true);
                }


                min = 0;
                avg = 0;
                max = 0;
                sum = 0;

                originalCities = cities = LoadCitiesFromFile("src/CityList.txt", cities);

                writeLog("Run Stats for experiment at: " + currentTime);
                for (int y = 1; y <= runs; y++) {
                    genMin = 0;
                    print(display,  "Run " + y + "\n");

                // create the initial population of chromosomes
                    chromosomes = new Chromosome[populationSize];
                    for (int x = 0; x < populationSize; x++) {
                        chromosomes[x] = new Chromosome(cities);
                    }

                    //System.out.println("Running crossover");
                    //crossover(chromosomes[0], chromosomes[1]);

                    generation = 0;
                    double thisCost = 0.0;

                    //Run for 100 generations
                    while (generation < 100) {
    
                    	System.out.println("Before Evoolve: " + Arrays.toString(chromosomes[50].getCities()));
                        evolve();
                        System.out.println("After Evolve:   " + Arrays.toString(chromosomes[50].getCities()));
                        //moves for every 5th generation
                        if(generation % 5 == 0 ) 
                            cities = MoveCities(originalCities); //Move from original cities, so they only move by a maximum of one unit.
                        generation++;

                        Chromosome.sortChromosomes(chromosomes, populationSize);
                        double cost = chromosomes[0].getCost();
                        thisCost = cost;

                        if (thisCost < genMin || genMin == 0) {
                            genMin = thisCost;
                        }
                        
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumFractionDigits(2);
                        nf.setMinimumFractionDigits(2);

                        print(display, "Gen: " + generation + " Cost: " + (int) thisCost);
                        
                        if(display) {
                            updateGUI();
                        }
                    }

                    writeLog(genMin + "");

                    if (genMin > max) {
                        max = genMin;
                    }

                    if (genMin < min || min == 0) {
                        min = genMin;
                    }

                    sum +=  genMin;

                    print(display, "");
                }

                avg = sum / runs;
                print(display, "Statistics after " + runs + " runs");
                print(display, "Solution found after " + generation + " generations." + "\n");
                print(display, "Statistics of minimum cost from each run \n");
                print(display, "Lowest: " + min + "\nAverage: " + avg + "\nHighest: " + max + "\n");

            } catch (NumberFormatException e) {
                System.out.println("Please ensure you enter integers for cities and population size");
                System.out.println(formatMessage);
            }
        }
    }
}