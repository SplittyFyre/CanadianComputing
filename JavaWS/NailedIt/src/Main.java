import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
	
	// uniqueWoodPieceLengths -> number of woods with this length
	private static Map<Integer, Integer> numWoodWithLength = new HashMap<Integer, Integer>();
	
	// uniqueBoardLengths -> number of boards with this length
	private static Map<Integer, Integer> numBoardsWithLength = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));

		
		getCountForEachLengthOfWood(jin);
		getCountForEachLengthOfBoard();
		
		Set<Integer> uniqueBoardLens = numBoardsWithLength.keySet();
		
		int maxFenceLength = 0;
		int possibleFenceHeights = 0;
		
		for (int len : uniqueBoardLens) {
			int numberOf = numBoardsWithLength.get(len);
			if (numberOf > maxFenceLength) {
				maxFenceLength = numberOf;
				possibleFenceHeights = 1; // since we have new max, reset possibleHeights to 1
			}
			else if (numberOf == maxFenceLength) {
				possibleFenceHeights++;
			}
		}
		
		System.out.printf("%d %d\n", maxFenceLength, possibleFenceHeights);
		
		jin.close();
	}
		
	private static void getCountForEachLengthOfBoard() {
		
		Set<Integer> uselessSet = numWoodWithLength.keySet();
		int size = uselessSet.size();
		Integer[] uniqueWoodLengths = uselessSet.toArray(new Integer[size]);
		
		// compare each piece of wood against all others
		for (int i = 0; i < size; i++) {
			int lenWood = uniqueWoodLengths[i];
			for (int j = i; j < size; j++) {
				int lenOtherWood = uniqueWoodLengths[j];
				
				int boardLen = lenWood + lenOtherWood;
				
				int totalPossibleBoards;
				if (lenWood == lenOtherWood) { // two equal woods fit together
					totalPossibleBoards = numWoodWithLength.get(lenWood) / 2;
				}
				else { // whichever length of wood runs out first
					totalPossibleBoards = Math.min(numWoodWithLength.get(lenOtherWood), numWoodWithLength.get(lenWood));
				}
				
				if (numBoardsWithLength.containsKey(boardLen)) {
					numBoardsWithLength.put(boardLen, numBoardsWithLength.get(boardLen) + totalPossibleBoards);
				}
				else {
					numBoardsWithLength.put(boardLen, totalPossibleBoards);
				}
			}
		}
		
	}
	
	private static void getCountForEachLengthOfWood(BufferedReader jin) throws Exception {
		int N = Integer.parseInt(jin.readLine());
		String[] toks = jin.readLine().split(" ");
		
		for (int i = 0; i < N; i++) {
			int woodLen = Integer.parseInt(toks[i]);
			
			if (numWoodWithLength.containsKey(woodLen)) {
				numWoodWithLength.put(woodLen, numWoodWithLength.get(woodLen) + 1);
			} 
			else {
				numWoodWithLength.put(woodLen, 1);
			}
		}
	}

}
