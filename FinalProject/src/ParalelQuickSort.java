import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParalelQuickSort extends RecursiveTask<Integer> {
	int start, end;
    int[] array;
    
    public ParalelQuickSort(int start, int end, int[] array)
	{
    	this.array = array;
		this.start = start;
		this.end = end;
	}
	
	public static void quickSort(int[] array) {
		quickSort(array, 0, array.length - 1);
		
	} 
	private static void quickSort(int[] array, int begin, int end) {
		
		if (begin < end) {
			int p = partition(array, begin, end);
			quickSort(array, begin, p - 1);
			quickSort(array, p+1, end);
		}
	}
	
	private static int partition(int[] array, int begin, int end) {
		int pivot = array[end];
	   
	    int i = (begin - 1);
	 
	    for(int j = begin; j <= end - 1; j++)
	    {
	        if (array[j] < pivot)
	        {
	            i++;
	            swap(array, i, j);
	        }
	    }
	    swap(array, i + 1, end);
	    return (i + 1);
		
	} 

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		
	} 

	public static void main(String[] args) {
		for (int i = 0; i < 99; i++) {
			int[] data1 = RunTime.generateRandomArray(10);
			int[] data2 = RunTime.generateRandomArray(10);
			
			System.out.println("Iteration " + (i+1));
			System.out.println("QuickSort:");
			System.out.println("Unsorted: " + Arrays.toString(data1));
			quickSort(data1);
			System.out.println("sorted: " + Arrays.toString(data1));
			
			System.out.println("Paralel QuickSort:");
			System.out.println("Unsorted: " + Arrays.toString(data2));
			pQuickSort(data2);
			System.out.println("sorted: " + Arrays.toString(data2));
			System.out.println();	
		}
	}
	
	@Override
	protected Integer compute() {
		if (start >= end)
            return null;
  
        int p = partition(array, start, end);
  
        ParalelQuickSort left
            = new ParalelQuickSort(start, p - 1, array);
  
        ParalelQuickSort right
            = new ParalelQuickSort(p + 1, end, array);
        left.fork();
        right.compute();
  
        left.join();
  
        return null;
	}
	
	public static void pQuickSort(int[] array)
	{
		int n = array.length;
		ForkJoinPool pool = ForkJoinPool.commonPool();
		pool.invoke(new ParalelQuickSort(0, n - 1, array));
	}
	
}