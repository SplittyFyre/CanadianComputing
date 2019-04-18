import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner jin = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();

		List<Integer> heights = new ArrayList<Integer>();
		
		int N = jin.nextInt();
		for (int i = 0; i < N; i++) {
			heights.add(jin.nextInt());
		}
				
		Collections.sort(heights);
		
		int middle = heights.size() / 2;
		int subby = 1;
		
		if (N % 2 != 0) {
			middle++;
			subby = 0;
		}
		
		for (int i = middle; i < heights.size(); i++) {
			sb.append(heights.get(heights.size() - subby - i));
			sb.append(' ');
			sb.append(heights.get(i));
			sb.append(' ');
		}
		
		if (N % 2 != 0) sb.append(heights.get(0));
				
		System.out.println(sb.toString());
		
		jin.close();
	}

}
