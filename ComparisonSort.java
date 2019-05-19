////////////////////////////////////////////////////////////////////////////
// Title:            Assignment5
// Files:            ComparisonSort.java
//					 SortObject.java
//					 TestSort.java
// Semester:         CS367 Spring 2018
//
// Author:           Ruokun Xu
// Email:            rxu83@wisc.edu
// CS Login:         ruokun
// Lecturer's Name:  Charles Fischer
// Lab Section:      lecture 1
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION /////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Xuetong Du
// Email:            xdu49@wisc.edu
// CS Login:         xuetong
// Lecturer's Name:  Charles Fischer
// Lab Section:      Lecture 1
/////////////////////////////////////////////////////////////////////////////////
import java.util.Arrays;

/**
 * This class implements six different comparison sorts (and an optional seventh
 * sort for extra credit):
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort</li>
 * <li>heap sort</li>
 * <li>selection2 sort</li>
 * <li>(extra credit) insertion2 sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {

	private static int moves = 0;

	/**
	 * Sorts the given array using the selection sort algorithm. You may use either
	 * the algorithm discussed in the on-line reading or the algorithm discussed in
	 * lecture (which does fewer data moves than the one from the on-line reading).
	 * Note: after this method finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void selectionSort(E[] A) {
		// TODO: implement this sorting algorithm
		int minIndex;
		for (int i = 0; i < A.length; i++) {
			minIndex = i;
			for (int j = i + 1; j < A.length; j++) {
				if (A[j].compareTo(A[minIndex]) < 0) {
					minIndex = j;
				}
			}
			swap(A, minIndex, i);
		}
	}

	/**
	 * Swap elements in the array
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param index1
	 *            the first element involved in swapping
	 * @param index2
	 *            the second element involved in swapping
	 */
	private static <E extends Comparable<E>> void swap(E[] A, int index1, int index2) {
		if (index1 != index2) {
			moves += 3;
			E temp = A[index1];
			A[index1] = A[index2];
			A[index2] = temp;
		}
	}

	/**
	 * Sorts the given array using the insertion sort algorithm. Note: after this
	 * method finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void insertionSort(E[] A) {
		// TODO: implement this sorting algorithm
		for (int i = 1; i < A.length; i++) {
			E temp = A[i];
			moves++;
			int j = i - 1;
			while ((j >= 0 && (A[j].compareTo(temp) > 0))) {
				A[j + 1] = A[j];
				moves++;
				j--;
			}
			A[j + 1] = temp;
			moves++;
		}
		if (check(A) == false) {
			System.out.println("incorrect");
		}

	}

	/**
	 * Sorts the given array using the merge sort algorithm. Note: after this method
	 * finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void mergeSort(E[] A) {
		// TODO: implement this sorting algorithm
		mergeAux(A, 0, A.length - 1);
	}

	/**
	 * Divide the unsorted list into n sublists, and do mergeAux recursively
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the left most position in the sublist
	 * @param high
	 *            the right most position in the sublist
	 */
	private static <E extends Comparable<E>> void mergeAux(E[] A, int low, int high) {
		// base case
		if (low == high)
			return;
		// recursive case
		// Step 1: Find the middle of the array (conceptually, divide it in half)
		int mid = (low + high) / 2;
		// Steps 2 and 3: Sort the 2 halves of A
		mergeAux(A, low, mid);
		mergeAux(A, mid + 1, high);
		// Step 4: Merge sorted halves into an auxiliary array
		E[] temp = (E[]) (new Comparable[high - low + 1]);
		int left = low; // index into left half
		int right = mid + 1; // index into right half
		int pos = 0; // index into temp
		while ((left <= mid) && (right <= high)) {
			// choose the smaller of the two values "pointed to" by left, right
			// copy that value into temp[pos]
			// increment either left or right as appropriate
			// increment pos
			if (A[left].compareTo(A[right]) <= 0) {
				moves++;
				temp[pos] = A[left];
				left++;
			} else {
				moves++;
				temp[pos] = A[right];
				right++;
			}
			pos++;
		}

		// when one of the two sorted halves has "run out" of values, but
		// there are still some in the other half, copy all the remaining
		// values to temp
		// Note: only 1 of the next 2 loops will be actually executed
		while (left <= mid) {
			temp[pos] = A[left];
			moves++;
			left++;
			pos++;
		}
		while (right <= high) {
			temp[pos] = A[right];
			moves++;
			right++;
			pos++;
		}
		// all values are in temp; copy them back into A
		System.arraycopy(temp, 0, A, low, temp.length);
		for (int i = 0; i < temp.length; i++) {
			moves++;
		}
	}

	/**
	 * Sorts the given array using the quick sort algorithm, using the median of the
	 * first, last, and middle values in each segment of the array as the pivot
	 * value. Note: after this method finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void quickSort(E[] A) {
		// TODO: implement this sorting algorithm
		quickAux(A, 0, A.length - 1);
	}

	/**
	 * Switch the value in low point and high point to the corresponding positions.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the left most position in the sublist
	 * @param high
	 *            the right most position in the sublist
	 */
	private static <E extends Comparable<E>> void quickAux(E[] A, int low, int high) {
		if (high - low < 4) {
			if (high - low == 1) {
				if (A[low].compareTo(A[high]) > 0) {
					swap(A, low, high);
				}
			}
		} else {
			int right = partition(A, low, high);
			quickAux(A, low, right);
			quickAux(A, right + 2, high);
		}
	}

	/**
	 * Move all elements that are less than the pivot point to the left side of the
	 * partition, and move all elements that are greater than the pivot point to the
	 * right side of the partition
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the left most position in the sublist
	 * @param high
	 *            the right most position in the sublist
	 */
	private static <E extends Comparable<E>> int partition(E[] A, int low, int high) {
		int left = low + 1;
		int right = high - 2;
		E pivot = medianOfThree(A, low, high);
		while (left <= right) {

			while (A[left].compareTo(pivot) < 0) {
				left++;
			}

			while (A[right].compareTo(pivot) > 0) {
				right--;

			}
			if (left <= right) {
				swap(A, left, right);
				left++;
				right--;
			}
		}

		swap(A, right + 1, high - 1);
		return right;
	}

	/**
	 * Perform the median of three by the median of the first, middle, and last
	 * values of the array. Swaps the values when appropriate.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to compare
	 * @param high
	 *            the index of the high end of array to compare
	 * @return the median object
	 */
	private static <E extends Comparable<E>> E medianOfThree(E[] A, int low, int high) {
		if (A[low].compareTo(A[(low + high) / 2]) > 0) {
			swap(A, low, (low + high) / 2);
		}
		if (A[high].compareTo(A[low]) < 0) {
			swap(A, high, low);
		}
		if (A[high].compareTo(A[(low + high) / 2]) < 0) {
			swap(A, high, (low + high) / 2);
		}
		swap(A, (low + high) / 2, high - 1);
		return A[high - 1];
	}

	/**
	 * Sorts the given array using the heap sort algorithm outlined below. Note:
	 * after this method finishes the array is in sorted order.
	 * <p>
	 * The heap sort algorithm is:
	 * </p>
	 * 
	 * <pre>
	 * for each i from 1 to the end of the array
	 *     insert A[i] into the heap (contained in A[0]...A[i-1])
	 *     
	 * for each i from the end of the array up to 1
	 *     remove the max element from the heap and put it in A[i]
	 * </pre>
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void heapSort(E[] A) {
		// TODO: implement this sorting algorithm
		int i = A.length;
		while (i >= 0) {
			heapAux(A, i--, A.length - 1);
		}
		i = A.length - 1;
		while (i > 0) {
			swap(A, 0, i);
			heapAux(A, 0, --i);
		}
	}

	/**
	 * Move the value at index1 and index2 to the right position
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @index1 the first index that involved in sorting
	 * @index2 the second index that involved in sorting
	 */
	private static <E extends Comparable<E>> void heapAux(E[] A, int index1, int index2) {

		int leftChild = index1 * 2 + 1;
		int rightChild = index1 * 2 + 2;
		int largestIndex = leftChild;

		if (leftChild > index2 && rightChild > index2) {
			return;
		}
		if (rightChild <= index2 && A[rightChild].compareTo(A[largestIndex]) > 0) {
			largestIndex = rightChild;
		}
		if (A[largestIndex].compareTo(A[index1]) > 0) {
			swap(A, largestIndex, index1);
			heapAux(A, largestIndex, index2);
		}
	}

	/**
	 * Sorts the given array using the selection2 sort algorithm outlined below.
	 * Note: after this method finishes the array is in sorted order.
	 * <p>
	 * The selection2 sort is a bi-directional selection sort that sorts the array
	 * from the two ends towards the center. The selection2 sort algorithm is:
	 * </p>
	 * 
	 * <pre>
	 * begin = 0, end = A.length-1
	 * 
	 * // At the beginning of every iteration of this loop, we know that the 
	 * // elements in A are in their final sorted positions from A[0] to A[begin-1]
	 * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
	 * // still to be sorted.
	 * do
	 *     use the MinMax algorithm (described below) to find the minimum and maximum 
	 *     values between A[begin] and A[end]
	 *     
	 *     swap the maximum value and A[end]
	 *     swap the minimum value and A[begin]
	 *     
	 *     ++begin, --end
	 * until the middle of the array is reached
	 * </pre>
	 * <p>
	 * The MinMax algorithm allows you to find the minimum and maximum of N elements
	 * in 3N/2 comparisons (instead of 2N comparisons). The way to do this is to
	 * keep the current min and max; then
	 * </p>
	 * <ul>
	 * <li>take two more elements and compare them against each other</li>
	 * <li>compare the current max and the larger of the two elements</li>
	 * <li>compare the current min and the smaller of the two elements</li>
	 * </ul>
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> void selection2Sort(E[] A) {
		// TODO: implement this sorting algorithm
		int index1 = 0;
		int index2 = A.length - 1;
		int tempMax = A.length - 1;
		int tempMin = 0;
		while (index1 <= index2) {
			int left = index1;
			int right = index2;
			while (left <= right) {
				if (A[left].compareTo(A[right]) > 0) {
					// left is larger than right
					// See if left is the new max
					if (A[left].compareTo(A[tempMax]) > 0) {
						tempMax = left;
					}
					if (A[right].compareTo(A[tempMin]) < 0) {
						tempMin = right;
					}
				} // See if right is the new min

				// Right is larger than left
				// See if right is the new max
				else if (A[left].equals(A[right])) {
					if (A[left].compareTo(A[tempMin]) < 0) {

						tempMin = left;
					}
					if (A[right].compareTo(A[tempMax]) > 0) {
						tempMax = right;
					}
				} else { // See if left is the new min
					if (A[right].compareTo(A[tempMax]) > 0) {
						tempMax = right;
					}
					if (A[left].compareTo(A[tempMin]) < 0) {
						tempMin = left;
					}
				}
				// increment counters
				left++;
				right--;
			}
			if (index1 != tempMax && index2 != tempMin) {
				swap(A, tempMin, index1);
				swap(A, tempMax, index2);
			} else if (index1 == tempMax && index2 != tempMin) {
				swap(A, tempMax, index2);
				swap(A, tempMin, index1);
			} else if (index1 != tempMax && index2 == tempMin) {
				swap(A, tempMin, index1);
				swap(A, tempMax, index2);
			} else if (index1 == tempMin && index2 != tempMax) {
				swap(A, tempMax, index2);
			} else if (index1 != tempMin && index2 == tempMax) {
				swap(A, tempMin, index1);
			} else if (index1 == tempMin && index2 == tempMax) {
				swap(A, tempMin, index1);
				swap(A, tempMax, index2);
			} else {
				E tmp = A[tempMin];
				moves++;
				swap(A, tempMax, index2);
				A[index1] = tmp;
				moves++;
			}
			// increment pointers
			index1++;
			tempMin = index1;
			index2--;
			tempMax = index2;
		}
	}

	/**
	 * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort
	 * algorithm outlined below. Note: after this method finishes the array is in
	 * sorted order.
	 * <p>
	 * The insertion2 sort is a bi-directional insertion sort that sorts the array
	 * from the center out towards the ends. The insertion2 sort algorithm is:
	 * </p>
	 * 
	 * <pre>
	 * precondition: A has an even length
	 * left = element immediately to the left of the center of A
	 * right = element immediately to the right of the center of A
	 * if A[left] > A[right]
	 *     swap A[left] and A[right]
	 * left--, right++ 
	 *  
	 * // At the beginning of every iteration of this loop, we know that the elements
	 * // in A from A[left+1] to A[right-1] are in relative sorted order.
	 * do
	 *     if (A[left] > A[right])
	 *         swap A[left] and A[right]
	 *  
	 *     starting with with A[right] and moving to the left, use insertion sort 
	 *     algorithm to insert the element at A[right] into the correct location 
	 *     between A[left+1] and A[right-1]
	 *     
	 *     starting with A[left] and moving to the right, use the insertion sort 
	 *     algorithm to insert the element at A[left] into the correct location 
	 *     between A[left+1] and A[right-1]
	 *  
	 *     left--, right++
	 * until left has gone off the left edge of A and right has gone off the right 
	 *       edge of A
	 * </pre>
	 * <p>
	 * This sorting algorithm described above only works on arrays of even length.
	 * If the array passed in as a parameter is not even, the method throws an
	 * IllegalArgumentException
	 * </p>
	 *
	 * @param A
	 *            the array to sort
	 * @throws IllegalArgumentException
	 *             if the length or A is not even
	 */
	public static <E extends Comparable<E>> void insertion2Sort(E[] A) {
		// TODO: implement this sorting algorithm
		if (A.length % 2 == 1) {
			throw new IllegalArgumentException();
		}
		if (A.length == 0) {
			return;
		}

		int left = (A.length - 1) / 2;
		int right = left + 1;
		if (A[left].compareTo(A[right]) > 0) {
			swap(A, left, right);
		}
		left--;
		right++;
		while (left >= 0 && right < A.length) {
			if (A[left].compareTo(A[right]) > 0) {
				swap(A, left, right);
			}
			E temp = A[right];
			moves++;
			int j = right - 1;
			while ((j >= left + 1 && (A[j].compareTo(temp) > 0))) {
				A[j + 1] = A[j];
				moves++;
				j--;
			}
			if (j != right - 1) {
				A[j + 1] = temp;
				moves++;
			}
			temp = A[left];
			moves++;
			j = left + 1;
			while ((j <= right - 1 && (A[j].compareTo(temp) < 0))) {
				A[j - 1] = A[j];
				moves++;
				j++;
			}
			if (j != left + 1) {
				A[j - 1] = temp;
				moves++;
			}
			left--;
			right++;
		}
	}

	/**
	 * Internal helper for printing rows of the output table.
	 * 
	 * @param sort
	 *            name of the sorting algorithm
	 * @param compares
	 *            number of comparisons performed during sort
	 * @param moves
	 *            number of data moves performed during sort
	 * @param milliseconds
	 *            time taken to sort, in milliseconds
	 */
	private static void printStatistics(String sort, int compares, int moves, long milliseconds) {
		System.out.format("%-23s%,15d%,15d%,15d\n", sort, compares, moves, milliseconds);
	}

	/**
	 * A method that checks if the sorting methods work
	 * 
	 * @param A
	 *            the array to sort
	 */
	public static <E extends Comparable<E>> boolean check(E[] A) {
		for (int i = 0; i < A.length - 1; i++) {
			if (A[i].compareTo(A[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sorts the given array using the six (seven with the extra credit) different
	 * sorting algorithms and prints out statistics. The sorts performed are:
	 * <ul>
	 * <li>selection sort</li>
	 * <li>insertion sort</li>
	 * <li>merge sort</li>
	 * <li>quick sort</li>
	 * <li>heap sort</li>
	 * <li>selection2 sort</li>
	 * <li>(extra credit) insertion2 sort</li>
	 * </ul>
	 * <p>
	 * The statistics displayed for each sort are: number of comparisons, number of
	 * data moves, and time (in milliseconds).
	 * </p>
	 * <p>
	 * Note: each sort is given the same array (i.e., in the original order) and the
	 * input array A is not changed by this method.
	 * </p>
	 * 
	 * @param A
	 *            the array to sort
	 */
	static public void runAllSorts(SortObject[] A) {

		System.out.format("%-23s%15s%15s%15s\n", "algorithm", "data compares", "data moves", "milliseconds");
		System.out.format("%-23s%15s%15s%15s\n", "---------", "-------------", "----------", "------------");

		// TODO: run each sort and print statistics about what it did
		long before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		selectionSort(Arrays.copyOf(A, A.length));

		long after = System.currentTimeMillis() - before;
		printStatistics("selection", SortObject.getCompares(), ComparisonSort.moves, after);

		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		insertionSort(Arrays.copyOf(A, A.length));

		after = System.currentTimeMillis() - before;
		printStatistics("insertion", SortObject.getCompares(), moves, after);

		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		mergeSort(Arrays.copyOf(A, A.length));
		after = System.currentTimeMillis() - before;
		printStatistics("merge", SortObject.getCompares(), moves, after);

		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		quickSort(Arrays.copyOf(A, A.length));

		after = System.currentTimeMillis() - before;
		printStatistics("quick", SortObject.getCompares(), moves, after);

		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		heapSort(Arrays.copyOf(A, A.length));

		after = System.currentTimeMillis() - before;
		printStatistics("heap", SortObject.getCompares(), moves, after);

		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		selection2Sort(Arrays.copyOf(A, A.length));

		after = System.currentTimeMillis() - before;
		printStatistics("selection2", SortObject.getCompares(), moves, after);
		before = System.currentTimeMillis();
		moves = 0;
		SortObject.resetCompares();
		insertion2Sort(Arrays.copyOf(A, A.length));

		after = System.currentTimeMillis() - before;
		printStatistics("insertion2", SortObject.getCompares(), moves, after);
	}
}
