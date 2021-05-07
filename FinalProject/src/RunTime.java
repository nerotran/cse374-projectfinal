/**
 * This program is to compute and report the execution times
 * for the sorting algorithms, we defined in Sorting class,
 * for different size arrays. 
 */
 
import java.util.*;

public class RunTime {
    private static int[] SAMPLE_SIZES = new int[] { 10, 100, 1000, 10000, 100000, 1000000, 2000000, 3000000, 4000000, 5000000};

    public static void main(final String[] args) {
        executionTimeReport();
    }

    public static int[] generateRandomArray(int size)
	{
		int [] rarr = new int[size];
		Random rand = new Random();
		for (int c= 0; c< rarr.length; c++)
		{				
			rarr[c] = rand.nextInt(1000);
		}
		
	    return rarr;
	}
    
    private static void executionTimeReport() {
    	 long startTime;
    	 long endTime;
         
    	 System.out.println("Arrays size     |  QuickSort  |   Parallel Quicksort   |");
    	 System.out.print("========================================================\n");
    	 
    	 for (int i =0; i< SAMPLE_SIZES.length;i++) {
        	int size = SAMPLE_SIZES[i]; 
            
            System.out.print(String.format("  %10d    |",size));

            int[] randomArray = generateRandomArray(size);
            startTime = System.currentTimeMillis();
    		ParalelQuickSort.quickSort(randomArray);
    		endTime = System.currentTimeMillis();

    		System.out.format("%7d      |%n", endTime - startTime);
    		


        }
    	System.out.print("========================================================\n");
     }

}
