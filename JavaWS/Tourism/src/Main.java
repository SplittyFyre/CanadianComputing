import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	private static int[] attractions;
	
	static class Day {
		final int start, end;
		
		public Day(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public int maxAttraction() {
			int retval = -1;
			for (int i = start; i <= end; i++) {
				if (attractions[i] > retval)
					retval = attractions[i];
			}
			System.out.println(retval);
			return retval;
		}
	}
	
	private static Day[] days;
	
	private static long sumScore() {
		long retval = 0;
		for (Day d : days) {
			retval += d.maxAttraction();
		}
		return retval;
	}

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));

		String[] details = jin.readLine().split(" ");
		int N = Integer.parseInt(details[0]), K = Integer.parseInt(details[1]);
		String[] toks = jin.readLine().split(" ");
		attractions = new int[N];
		for (int i = 0; i < N; i++) {
			attractions[i] = Integer.parseInt(toks[i]);
		}
		
		if (K == N) {
			System.out.println(new Day(0, N - 1).maxAttraction());
			return;
		}
		else if (K == 1) {
			long ans = 0;
			for (int i : attractions) {
				ans += i;
			}
			System.out.println(ans);
			return;
		}
		
		int daysRequired = (int) Math.ceil(N / ((float) K));
		int leftover = (daysRequired * K) - N;
		
		// assemble
		days = new Day[daysRequired];
		int st = 0;
		for (int i = 0, place = K - 1; i < daysRequired; i++, place += K) {
			if (place > N - 1) {
				place = N - 1;
			}
			days[i] = new Day(st, place);
			st = place + 1;
		}

		//if (leftover == 0) {
			System.out.println(sumScore());
		//}
		//else {
			
		//}
		
		
		jin.close();
	}

}
