import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner jin = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int N = jin.nextInt();
		
		int swiftCnt = 0, semaphoreCnt = 0;
		
		int[] swifts = new int[N];
		
		int k = -1;
		
		for (int i = 0; i < N; i++) {
			swifts[i] = jin.nextInt();
		}
		for (int i = 0; i < N; i++) {
			swiftCnt += swifts[i];
			semaphoreCnt += jin.nextInt();
			if (swiftCnt == semaphoreCnt) {
				if (i > k) {
					k = i;
				}
			}
		}
		
		System.out.println(k + 1);
		
		jin.close();
	}

}
