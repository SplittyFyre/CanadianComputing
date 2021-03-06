import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	//50000000
	// since getNumberOfTrees() calls itself, and is very likely to have similar trees, and stuff
	private static Map<Integer, Long> alreadyFound = new HashMap<Integer, Long>();
	//  some to speed up process
	private static long[] precomputed = {1, 1, 2, 3, 4, 6, 7, 9, 11, 13, 14, 19, 20, 22, 25, 29, 30, 36, 37, 42, 45, 47, 48, 60, 62, 64, 68, 73, 74, 84, 85, 93, 96, 98, 101, 119, 120, 122, 125, 137, 138, 148, 149, 154, 162, 164, 165, 193, 195, 201, 204, 209, 210, 226, 229, 241, 244, 246, 247, 278, 279, 281, 289, 305, 308, 318, 319, 324, 327, 337, 338, 388, 389, 391, 399, 404, 407, 417, 418, 446, 454, 456, 457, 488, 491, 493, 496, 508, 509, 545, 548, 553, 556, 558, 561, 625, 626, 632, 640, 658};

	public static void main(String[] args) {
		
		for (int i = 1; i <= 1000; i++) {
			System.out.print(getNumberOfTrees(i) + ", ");
			if (i % 50 == 0) {
				System.out.println();
			}
		}
		
		System.exit(0);
		
		Scanner jin = new Scanner(System.in);
		
		int N = jin.nextInt();
		
		long start = System.nanoTime();
		System.out.println(getNumberOfTrees(N));
		long end = System.nanoTime();
		
		System.out.println((end - start) / 1000000000f);
	
		jin.close();
	}
	
	private static long getNumberOfTrees(int weight) {
		// quoting the CCC: A perfectly balanced tree of weight 1 always consists of a single node
		if (weight == 1) { 
			return 1;
		}
		
		/*if (weight <= 100) {
			return precomputed[weight - 1];
		}*/

		if (alreadyFound.containsKey(weight)) {
			return alreadyFound.get(weight);
		}
		
		long numTrees = 0;
		// where k is number of subtrees (specified in problem)
		// for all cases of possible subtrees
		for (int k = 2; k <= weight; k++) {
			int weightOfEachSubTree = weight / k; // sum of weights of all subtrees <= weight (int division round down)
			numTrees += getNumberOfTrees(weightOfEachSubTree); // recursive call until single node
		}
		alreadyFound.put(weight, numTrees);
		return numTrees;
	}

}
