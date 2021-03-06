
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Cell {
	int i, j, dist;
	public Cell(int i, int j, int dist) {
		this.i = i;
		this.j = j;
		this.dist = dist;
	}
}

public class Main {
	
	private static char[][] grid;
	private static boolean[][] invalids, rndinvalids;
	
	
	private static LinkedList<Cell> queue = new LinkedList<Cell>();
	
	private static void enqueue(Cell cell) {
		queue.addLast(cell);
	}
	private static Cell dequeue() {
		return queue.removeFirst();
	}
	
	
	
	
	private static int stI, stJ;
	private static int n, m;
		
	private static List<Point> targets = new ArrayList<Point>();
	
	private static boolean startCoveredByCamera = false;
	

	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
	
		Scanner jin = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		
		n = jin.nextInt();
		m = jin.nextInt();
		// n = rows
		// m = columns (cells)
		grid = new char[n][m];
		invalids = new boolean[n][m];
		rndinvalids = new boolean[n][m];
		
		/*for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				invalids[i][j] = false;
			}
		}*/
		
		for (int i = 0; i < n; i++) {
			String buf = jin.next();
			for (int j = 0; j < m; j++) {
				char c = buf.charAt(j);
				grid[i][j] = c;
				markInitialInvalids(c, i, j);
			}
		}
		
		markCameraRayInvalids();
		
		// EVERYTHING ABOVE THIS STILL WORKS
		int i = 0;
		for (Point target : targets) {
			i++;
			sb.append(i);
			sb.append(" : ");
			sb.append(evaluateSolution(target));
			sb.append('\n');
		}
		
		System.out.println(sb.toString());
		
		jin.close();
		
	}
	
	
	
	private static int evaluateSolution(Point target) {
		
		int i = target.x, j = target.y;
		
		if (startCoveredByCamera) {
			return -1;
		}
		
		// if target surrounded by cells we cannot enter
		if (invalids[i + 1][j] &&
			invalids[i - 1][j] &&
			invalids[i][j + 1] &&
			invalids[i][j - 1]) {
			return -1;
		}
		
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < m; b++) {
				rndinvalids[a][b] = invalids[a][b];
			}
		}
		
		return bfs(stI, stJ, target);
	}
	
	
	
	private static int bfs(int startI, int startJ, Point target) {
		
		queue.clear();
		
		enqueue(new Cell(startI, startJ, 0));
		rndinvalids[startI][startJ] = true;
		
		while (!queue.isEmpty()) {
			
			Cell cell = dequeue();
			int i = cell.i, j = cell.j, nextDist = cell.dist + 1;
			
			switch (grid[i][j]) {
			case 'U':
				if (invalids[i - 1][j])
					continue;
				else
					i--;
				break;
			case 'D':
				if (invalids[i + 1][j])
					continue;
				else
					i++;
				break;
			case 'L':
				if (invalids[i][j - 1])
					continue;
				else
					j--;
				break;
			case 'R':
				if (invalids[i][j + 1])
					continue;
				else
					j++;
				break;
			}
			
			// if target found
			if (i == target.x && j == target.y) {
				return cell.dist;
			}
			
			if (!rndinvalids[i + 1][j]) {
				enqueue(new Cell(i + 1, j, nextDist));
				rndinvalids[i + 1][j] = true;
			}
				
			if (!rndinvalids[i - 1][j]) {
				enqueue(new Cell(i - 1, j, nextDist));
				rndinvalids[i - 1][j] = true;
			}
			
			if (!rndinvalids[i][j + 1]) {
				enqueue(new Cell(i, j + 1, nextDist));
				rndinvalids[i][j + 1] = true;
			}
			
			if (!rndinvalids[i][j - 1]) {
				enqueue(new Cell(i, j - 1, nextDist));
				rndinvalids[i][j - 1] = true;
			}
			
		}
		
		// out of search, and no path
		return -1;
		
	}
	
	private static void markInitialInvalids(char c, int i, int j) {
		
		switch (c) {
		
		case 'W':
			invalids[i][j] = true;
			break;
			
		case '.':
			targets.add(new Point(i, j));
			break;
			
		case 'S':
			stI = i;
			stJ = j;
			break;
			
		case 'C':
			
			// mark initial invalid, do ray marking crap later after full grid assembled
			invalids[i][j] = true;
			
			break;
		
		}
		
	}
	
	
	
	private static void markCameraRayInvalids() {
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				char c = grid[i][j];
				
				
				if (c == 'C') {
					
					boolean flag = true;
					int a = 1;
					
					while (flag) {
						switch (grid[i + a][j]) {
						case '.':
							invalids[i + a][j] = true;
							break;
						case 'S':
							startCoveredByCamera = true;
							flag = false;
							break;
						case 'W':
							// 'ray' blocked by wall
							flag = false;
							break;
						case 'C':
							// no need to continue, save some time
							flag = false;
							break;
						}
						a++;
					}
					a = 1;
					flag = true;
					
					while (flag) {
						switch (grid[i - a][j]) {
						case '.':
							invalids[i - a][j] = true;
							break;
						case 'S':
							startCoveredByCamera = true;
							flag = false;
							break;
						case 'W':
							// 'ray' blocked by wall
							flag = false;
							break;
						case 'C':
							// no need to continue, save some time
							flag = false;
							break;
						}
						a++;
					}
					a = 1;
					flag = true;
					
					while (flag) {
						switch (grid[i][j + a]) {
						case '.':
							invalids[i][j + a] = true;
							break;
						case 'S':
							startCoveredByCamera = true;
							flag = false;
							break;
						case 'W':
							// 'ray' blocked by wall
							flag = false;
							break;
						case 'C':
							// no need to continue, save some time
							flag = false;
							break;
						}
						a++;
					}
					a = 1;
					flag = true;
					
					while (flag) {
						switch (grid[i][j - a]) {
						case '.':
							invalids[i][j - a] = true;
							break;
						case 'S':
							startCoveredByCamera = true;
							flag = false;
							break;
						case 'W':
							// 'ray' blocked by wall
							flag = false;
							break;
						case 'C':
							// no need to continue, save some time
							flag = false;
							break;
						}
						a++;
					}
					
				}
				
			}
		}
		
	}

}
