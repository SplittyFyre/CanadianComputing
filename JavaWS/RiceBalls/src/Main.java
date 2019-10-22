import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	
	private static int maxRiceBallSize = -42069;
	private static int[] riceBalls;
	
	private static int sumRiceBallResult;

	public static void main(String[] args) throws Exception {
		
		Scanner jin = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int N = jin.nextInt();
		riceBalls = new int[N];
		
		for (int i = 0; i < N; i++) {
			riceBalls[i] = jin.nextInt();
		}
		
		jin.close();
	}
	
	// returns if it can sum the riceball, if it can, it does, and stores the result in sumRiceBallResult
	private boolean sumRiceBalls(int start, int end) {
		
		int dist = end - start;
		
		
		return false;
	}

}
