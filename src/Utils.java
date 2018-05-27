import java.util.Random;
import java.util.stream.IntStream;

public class Utils {
	
	public static boolean contains(int[] array, int num) {
		if(IntStream.of(array).anyMatch(x -> x == num)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static int  findIndex (int[] my_array, int t) {
        if (my_array == null) return -1;
        int len = my_array.length;
        int i = 0;
        while (i < len) {
            if (my_array[i] == t) return i;
            else i=i+1;
        }
        return -1;
    }
	
	public static double calculateAvgCost(Chromosome[] pop) {
		double sum = 0;
		
		for(Chromosome c : pop) {
			sum += c.getCost();
		}

		double avg = sum/pop.length;
		return avg;
	}

}
