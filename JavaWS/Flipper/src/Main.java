
import java.util.Scanner;

public class Main {
	
	// probably not very efficient, but I was coding VERY fast
	
	private static void horizontalFlip(int[][] grid) {
		int a = grid[0][0], b = grid[0][1];
		grid[0][0] = grid[1][0];
		grid[0][1] = grid[1][1];
		
		grid[1][0] = a;
		grid[1][1] = b;
	}
	
	private static void verticalFlip(int[][] grid) {
		int a = grid[0][0], b = grid[1][0];
		grid[0][0] = grid[0][1];
		grid[1][0] = grid[1][1];
		
		grid[0][1] = a;
		grid[1][1] = b;
	}
	
	
	public static void main(String[] args) throws Exception {
		Scanner jin = new Scanner(System.in);

		int[][] grid = {
				{1, 2},
				{3, 4}
		};
		
		String str = jin.next();
		
		boolean odd = str.length() % 2 == 1;
		
		int n = odd ? 1 : 0;
		
		for (int i = 0; i < str.length() - n; i++) {
			char c = str.charAt(i);
			
			if (c == 'H') {
				if (str.charAt(++i) == 'V') {
					horizontalFlip(grid);
					verticalFlip(grid);
				}
			}
			else {
				if (str.charAt(++i) == 'H') {
					verticalFlip(grid);
					horizontalFlip(grid);
				}
			}
		}
		if (odd) {
			
			if (str.charAt(str.length() - 1) == 'V') {
				verticalFlip(grid);
			}
			else {
				horizontalFlip(grid);
			}
		
		}
		
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(grid[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
		jin.close();
	}

}
