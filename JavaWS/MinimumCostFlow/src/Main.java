import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 *  The {@code UF} class represents a <em>union–find data type</em>
 *  (also known as the <em>disjoint-sets data type</em>).
 *  It supports the <em>union</em> and <em>find</em> operations,
 *  along with a <em>connected</em> operation for determining whether
 *  two sites are in the same component and a <em>count</em> operation that
 *  returns the total number of components.
 *  <p>
 *  The union–find data type models connectivity among a set of <em>n</em>
 *  sites, named 0 through <em>n</em>–1.
 *  The <em>is-connected-to</em> relation must be an 
 *  <em>equivalence relation</em>:
 *  <ul>
 *  <li> <em>Reflexive</em>: <em>p</em> is connected to <em>p</em>.
 *  <li> <em>Symmetric</em>: If <em>p</em> is connected to <em>q</em>,
 *       then <em>q</em> is connected to <em>p</em>.
 *  <li> <em>Transitive</em>: If <em>p</em> is connected to <em>q</em>
 *       and <em>q</em> is connected to <em>r</em>, then
 *       <em>p</em> is connected to <em>r</em>.
 *  </ul>
 *  <p>
 *  An equivalence relation partitions the sites into
 *  <em>equivalence classes</em> (or <em>components</em>). In this case,
 *  two sites are in the same component if and only if they are connected.
 *  Both sites and components are identified with integers between 0 and
 *  <em>n</em>–1. 
 *  Initially, there are <em>n</em> components, with each site in its
 *  own component.  The <em>component identifier</em> of a component
 *  (also known as the <em>root</em>, <em>canonical element</em>, <em>leader</em>,
 *  or <em>set representative</em>) is one of the sites in the component:
 *  two sites have the same component identifier if and only if they are
 *  in the same component.
 *  <ul>
 *  <li><em>union</em>(<em>p</em>, <em>q</em>) adds a
 *      connection between the two sites <em>p</em> and <em>q</em>.
 *      If <em>p</em> and <em>q</em> are in different components,
 *      then it replaces
 *      these two components with a new component that is the union of
 *      the two.
 *  <li><em>find</em>(<em>p</em>) returns the component
 *      identifier of the component containing <em>p</em>.
 *  <li><em>connected</em>(<em>p</em>, <em>q</em>)
 *      returns true if both <em>p</em> and <em>q</em>
 *      are in the same component, and false otherwise.
 *  <li><em>count</em>() returns the number of components.
 *  </ul>
 *  <p>
 *  The component identifier of a component can change
 *  only when the component itself changes during a call to
 *  <em>union</em>—it cannot change during a call
 *  to <em>find</em>, <em>connected</em>, or <em>count</em>.
 *  <p>
 *  This implementation uses weighted quick union by rank with path compression
 *  by halving.
 *  Initializing a data structure with <em>n</em> sites takes linear time.
 *  Afterwards, the <em>union</em>, <em>find</em>, and <em>connected</em> 
 *  operations take logarithmic time (in the worst case) and the
 *  <em>count</em> operation takes constant time.
 *  Moreover, the amortized time per <em>union</em>, <em>find</em>,
 *  and <em>connected</em> operation has inverse Ackermann complexity.
 *  For alternate implementations of the same API, see
 *  {@link QuickUnionUF}, {@link QuickFindUF}, and {@link WeightedQuickUnionUF}.
 *
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
  
    /**
     * Merges the component containing site {@code p} with the 
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }


}





// PRINCETON UF CLASS ABOVE, WILL IMPLEMENT MY OWN LATER



class Pair {
	public int one, two;
	public Pair(int one, int two) {
		this.one = one;
		this.two = two;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + one;
		result = prime * result + two;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (one != other.one)
			return false;
		if (two != other.two)
			return false;
		return true;
	}
	
}


// aka Pipe, has special field (boolean) isactive
class Edge implements Comparable<Edge> {
	
	public boolean isActive;
	
	private final int v, w, weight;
	
	public Edge(int v, int w, int weight, boolean isActive) {
		this.v = v;
		this.w = w;
		this.weight = weight;
		this.isActive = isActive;
	}
	
	public Edge(Edge e) {
		this.w = e.w;
		this.v = e.v;
		this.weight = e.weight;
		this.isActive = false; // purposely inactive
	}

	public int either() {
		return v;
	}
	
	public int other(int vtx) { // returns other vertex
		return vtx == v ? w : v;
	}

	@Override
	public int compareTo(Edge that) {
		if (this.weight < that.weight) {
			return -1;
		}
		else if (this.weight > that.weight) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int getWeight() {
		return this.weight;
	}
	
}

class Graph {
	
	public int vtxcnt;
	private List<Edge>[] adj; // adjacency matrix
	
	@SuppressWarnings("unchecked")
	public Graph(int vtxcnt) {
		this.vtxcnt = vtxcnt;
		adj = new ArrayList[vtxcnt];
		for (int i = 0; i < vtxcnt; i++) {
			adj[i] = new ArrayList<Edge>();
		}
	}
	
	public Graph(Graph that) {
		this.vtxcnt = that.vtxcnt;
		adj = new ArrayList[vtxcnt];
		List<Edge>[] theirEdges = that.getAllEdges();
		for (int i = 0; i < theirEdges.length; i++) {
			List<Edge> sublist = theirEdges[i];
			this.adj[i] = new ArrayList<Edge>(sublist.size());
			for (Edge e : sublist) {
				this.adj[i].add(new Edge(e));
			}
		}
	}
	
	public void addEdge(Edge e) {
		int v = e.either();
		int w = e.other(v);
		adj[v].add(e);
		adj[w].add(e);
	}
	
	public void addEdge(int v, int w, int weight, boolean active) {
		addEdge(new Edge(v, w, weight, active));
	}
	
	public List<Edge> getadj(int v) {
		return adj[v];
	}
	
	public List<Edge>[] getAllEdges() {
		return adj;
	}

}




class MinSpanningTree {
	
	private static void prim_visit(Graph g, int v, boolean[] markedPtr, PriorityQueue<Edge> pq) {
		markedPtr[v] = true;
		for (Edge e : g.getadj(v)) {
			if (!markedPtr[e.other(v)]) {
				pq.add(e);
			}
		}
	}
	
	public static Graph markMSTOnGraph(final Graph g) {
		
		Graph dest = new Graph(g); // all edges inactive
		
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		boolean[] marked = new boolean[g.vtxcnt];
				
		prim_visit(dest, 0, marked, pq);
		
		while (!pq.isEmpty()) {
			Edge e = pq.remove(); // pop
			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w]) {
				continue;
			}
			
			// found edge
			e.isActive = true;
			//System.out.println("thou art a saucy boy " + e.either() + " " + e.other(e.either()));

			if (!marked[v]) {
				prim_visit(dest, v, marked, pq);
			}
			if (!marked[w]) {
				prim_visit(dest, w, marked, pq);
			}
		}
		
		return dest;
	}
	
	
}







public class Main {
	
	private static int N, M, D;
	
	private static Graph assembleGraph(BufferedReader jin) throws Exception {
		
		String[] details = jin.readLine().split(" ");
		N = Integer.parseInt(details[0]);		
		M = Integer.parseInt(details[1]);
		D = Integer.parseInt(details[2]);
				
		Graph graph = new Graph(N);

		for (int i = 0; i < M; i++) {
			String[] toks = jin.readLine().split(" ");
			int a = Integer.parseInt(toks[0]), b = Integer.parseInt(toks[1]), c = Integer.parseInt(toks[2]);
			
			boolean it = false;
			if (i < N - 1) {
				it = true;
				//System.out.println("yuuup!");
			}
			
			graph.addEdge(a - 1, b - 1, c, it);
		}
		
		return graph;
	}
	
	private static HashSet<Pair> alreadyDidEdges = new HashSet<Pair>();

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
		
		Graph graph = assembleGraph(jin);
		
		Graph optimal = MinSpanningTree.markMSTOnGraph(graph);
		
		int offsNeeded = 0, onsNeeded = 0;
		for (int i = 0; i < graph.getAllEdges().length; i++) {
			List<Edge> normal = graph.getAllEdges()[i];
			List<Edge> opt = optimal.getAllEdges()[i];
			for (int j = 0; j < normal.size(); j++) {
				Edge n = normal.get(j);
				Edge o = opt.get(j);
				
		
				Pair vtxPair = new Pair(n.either(), n.other(n.either()));
				if (alreadyDidEdges.contains(vtxPair)) {
					continue;
				}
				else {
					alreadyDidEdges.add(vtxPair);
				}
				
				//System.out.printf("n:%b o:%b   %d %d\n", n.isActive, o.isActive, n.either(), n.other(n.either()));

				
				if (n.isActive != o.isActive) {
					//System.out.printf( "n:%b o:%b   %d %d\n", n.isActive, o.isActive, n.either(), n.other(n.either()));
					if (o.isActive)
						onsNeeded++;
					else
						offsNeeded++;
				}
			}
		}
		
		System.out.println(Math.max(onsNeeded, offsNeeded) - 1);
		
		jin.close();
	}

}
