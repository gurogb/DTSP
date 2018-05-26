import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyClass {
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static boolean contains(int[] array, int num) {
		if(IntStream.of(array).anyMatch(x -> x == num)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		 String[] s1 = new String[]{"a", "b", "c"};
	        String[] s2 = new String[]{"d", "e", "f"};
	        String[] s3 = new String[]{"g", "h", "i"};

			//join object type array
	        String[] result = Stream.of(s1, s2, s3).flatMap(Stream::of).toArray(String[]::new);
	        System.out.println(Arrays.toString(result));
	        
	        String[] t1 = new String[]{"a", "b", "c"};
	        String[] t2 = new String[]{"d", "e", "f"};
	        String[] t3 = new String[]{"g", "a", "i"};

			//join object type array
	        String[] result2 = Stream.of(t1, t2, t3).flatMap(Stream::of).toArray(String[]::new);
	        System.out.println(Arrays.toString(result2));
		
		/*int[] indexes = new int[20];
		System.out.println(Arrays.toString(indexes));
    	
    	for(int i=0; i<20;i++) {
    		int num = getRandomNumberInRange(0,20);
    		while(contains(indexes, num)) {
    			num = getRandomNumberInRange(0,20);
    		}
    		indexes[i] = num;
    		
    	}
    	System.out.println(Arrays.toString(indexes));*/
	}

}
