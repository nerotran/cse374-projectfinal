import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask<Integer> {
	int[] arr;
	int l,r;
	
	public ParallelMergeSort(int l, int r, int[] arr)
	{
    	this.arr = arr;
		this.l = l;
		this.r = r;
	}
	
    public static void merge(int arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
 
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    public static void mergeSort(int arr[], int l, int r)
    {
        if (l < r) {
            int m =l+(r-l)/2;
 
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
 
            merge(arr, l, m, r);
        }
    }

	@Override
	protected Integer compute() {
		if (l < r) {
			int m =l+(r-l)/2;
			  
	        ParallelMergeSort left = new ParallelMergeSort(l,m,arr);
	        ParallelMergeSort right = new ParallelMergeSort(m+1,r,arr);

	        invokeAll(left,right);
	  
	        merge(arr,l,m,r);
		}
        return null;
	}
	
	public static void pMergeSort(int[] array)
	{
		int n = array.length;
		ForkJoinPool pool = ForkJoinPool.commonPool();
		pool.invoke(new ParallelMergeSort(0, n - 1, array));
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 99; i++) {
			int[] data1 = RunTime.generateRandomArray(10);
			int[] data2 = RunTime.generateRandomArray(10);
			
			System.out.println("Iteration " + (i+1));
			System.out.println("QuickSort:");
			System.out.println("Unsorted: " + Arrays.toString(data1));
			mergeSort(data1,0,9);
			System.out.println("sorted: " + Arrays.toString(data1));
			
			System.out.println("Paralel QuickSort:");
			System.out.println("Unsorted: " + Arrays.toString(data2));
			pMergeSort(data2);
			System.out.println("sorted: " + Arrays.toString(data2));
			System.out.println();	
		}
	}
}
