import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.jws.soap.SOAPBinding.Use;

public class Main {
	
	private static int N, M;
	
	private static int vtxcnt;
	private static List<Integer>[] adj;
	
	private static boolean[] isPho, marked, wasTrimmed;
	private static int nodesTrimmed = 0;
	private static int furthestIndex, furthestDistance, depth;

	private static int aPhoNode;
	
	private static Queue<Integer> q = new LinkedList<Integer>();

	
	private static List<Integer> leaves = new ArrayList<Integer>();
	
	@SuppressWarnings("unchecked")
	private static void initGraph(int vc) {
		vtxcnt = vc;
		isPho = new boolean[vtxcnt]; // default all false
		marked = new boolean[vtxcnt];
		wasTrimmed = new boolean[vtxcnt];
		//childrenCntOfVtx = new int[vtxcnt]; // default all to zero, which is good
		adj = new ArrayList[vtxcnt];
		for (int i = 0; i < vtxcnt; i++) {
			adj[i] = new ArrayList<Integer>();
		}
	}
	
	private static void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}
	
	private static List<Integer> getadj(int v) {
		return adj[v];
	}
	
	/*private static void indexLeavesDFS(int v) {
		marked[v] = true;
		boolean isLeaf = true;
		for (int i : getadj(v)) {
			if (!marked[i]) {
				indexLeavesDFS(i);
				isLeaf = false;
			}
		}
		if (isLeaf) {
			leaves.add(v);
		}
	}*/
	
	private static void indexLeaves() {
		for (int i = 0; i < vtxcnt; i++) {
			if (getadj(i).size() == 1) {
				leaves.add(i);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
		
		String[] details = jin.readLine().split(" ");
		N = Integer.parseInt(details[0]);
		M = Integer.parseInt(details[1]);
		
		initGraph(N);
		
		String[] toks = jin.readLine().split(" ");
		for (int i = 0; i < M; i++) {
			int j = Integer.parseInt(toks[i]);
			isPho[j] = true;
			if (i == 0) {
				aPhoNode = j;
			}
		}
		
		for (int i = 0; i < N - 1; i++) {
			toks = jin.readLine().split(" ");
			int v = Integer.parseInt(toks[0]), w = Integer.parseInt(toks[1]);
			addEdge(v, w);
		}
		
		indexLeaves();
		trimNodes();
		
		int trimmedGraphNumEdges = N - nodesTrimmed - 1;
		int diameter = getDiameterOfTree();
		
		System.out.println((trimmedGraphNumEdges * 2 - diameter));
			
		jin.close();
	}
	
	private static void trimNodes() {
		Arrays.fill(marked, false);
		
		for (int v : leaves) {
			if (!isPho[v])
				trimBFS(v); // same marked can be used for all these bfs
		}
	}
	
	private static void trimBFS(int s) {
		q.add(s);
		while (!q.isEmpty()) {
			int v = q.remove();
			for (int i : getadj(v)) {
				if (!marked[i] && !isPho[i] && !wasTrimmed[i]) {
					q.add(i);
					wasTrimmed[i] = true;
					nodesTrimmed++;
					marked[i] = true;
				}
			}
		}
		// q should be empty, and can be reused
	}
	
	private static int getDiameterOfTree() {
		int arbitraryPho = aPhoNode;
		getFurthestFrom(arbitraryPho);
		int y = furthestIndex;
		getFurthestFrom(y);
		int ytoz = furthestDistance;
		return ytoz;
	}
	
	private static void getFurthestFrom(int v) {
		depth = -1;
		furthestDistance = -9999;
		Arrays.fill(marked, false);
		findFurthestFromDFS(v);
	}
	
	private static void findFurthestFromDFS(int v) {
		depth++;
		marked[v] = true;
		
		if (depth > furthestDistance) {
			furthestDistance = depth;
			furthestIndex = v;
		}
		
		for (int i : getadj(v)) {
			if (!marked[i] && !wasTrimmed[i]) {
				findFurthestFromDFS(i);
			}
		}
		depth--;
	}

}
