import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	
	private static int N;
	
	private static int[] ones, twos;

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));

		char type = jin.readLine().charAt(0);
		
		N = Integer.parseInt(jin.readLine());
		
		ones = new int[N];
		twos = new int[N];
		
		String[] lnOne = jin.readLine().split(" ");
		String[] lnTwo = jin.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			ones[i] = Integer.parseInt(lnOne[i]);
			twos[i] = Integer.parseInt(lnTwo[i]);
		}
		
		Arrays.sort(ones);
		Arrays.sort(twos);
		
		System.out.println((type == '1') ? minTotalSpeed() : maxTotalSpeed());
		
		jin.close();
	}
	
	// the fastest ride with the fastest, thereby losing potential speed 
	private static int minTotalSpeed() {
		int total = 0;
		for (int i = ones.length - 1; i >= 0; i--) {
			total += Math.max(ones[i], twos[i]);
		}
		return total;
	}
	
	// the fastest ride with the slowest, thereby using the most potential speed
	private static int maxTotalSpeed() {
		int total = 0;
		for (int i = 0; i < ones.length; i++) {
			total += Math.max(ones[i], twos[twos.length - 1 - i]);
		}
		return total;
	}

}
