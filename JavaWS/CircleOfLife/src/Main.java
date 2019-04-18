import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	private static int N, T;
	private static String cells;

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));

		
		String[] details = jin.readLine().split(" ");
		N = Integer.parseInt(details[0]);
		T = Integer.parseInt(details[1]);
		
		cells = jin.readLine();
		
		
		jin.close();
	}

}
