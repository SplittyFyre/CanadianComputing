import java.util.Scanner;

public class Main {
	
	private static int[] tmp = new int[3];
	
	private static int square[][] = new int[3][3];
	private static boolean known[][] = new boolean[3][3];

	public static void main(String[] args) {
		
		Scanner jin = new Scanner(System.in);

		for (int i = 0; i < 3; i++) {
			String[] toks = jin.nextLine().split(" ");
			for (int j = 0; j < 3; j++) {
				if (toks[j].charAt(0) == 'X') {
					known[i][j] = false;
				} 
				else {
					square[i][j] = Integer.parseInt(toks[j]);
					known[i][j] = true;
				}
			}
		}
		
		checkContingencies();
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(square[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
		
		jin.close();
	}
	
	private static void checkContingencies() {
		if (checkRowFilledIn()) 
			return;
		if (checkColumnFilledIn())
			return;
	}
	
	private static boolean checkRowFilledIn() {
		for (int i = 0; i < 3; i++) {
			if (known[i][0] && known[i][1] && known[i][2]) {
				for (int j = 0; j < 3; j++) {
					tmp[j] = square[i][j];
				}
				// fill in the entire square with the row
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 3; k++) {
						square[j][k] = tmp[k];
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkColumnFilledIn() {
		for (int i = 0; i < 3; i++) {
			if (known[0][i] && known[1][i] && known[2][i]) {
				for (int j = 0; j < 3; j++) {
					tmp[j] = square[j][i];
				}
				// fill in the entire square with the row
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < 3; k++) {
						square[k][j] = tmp[k];
					}
				}
				return true;
			}
		}
		return false;
	}

}
