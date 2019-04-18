import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Station {
	public int numPassengers;
	public Station(int passengers) {
		this.numPassengers = passengers;
	}
}

class MLine {
	
	private int gcd(int a, int b) {
		return (b == 0) ? a : gcd(b, a % b);
	}
	
	private List<Station> stations = new ArrayList<Station>();
	public void addStation(Station station) {
		this.stations.add(station);
	}
	public void moveTrains(int shift) {
		int len = stations.size();
		shift %= len;
		if (shift == 0) {
			return;
		}
		/*int shift = 1;
		int len = stations.size();
		
		for (int i = 0; i < gcd(shift, len); i++) {
			int tmp = stations.get(i).numPassengers;
			int j = i;
			while (true) {
				int k = (j + shift) % len;
				if (k == i) {
					break;
				}
				stations.get(j).numPassengers = stations.get(k).numPassengers;
				j = k;
			}
			stations.get(j).numPassengers = tmp;
		}*/
		int[] copy = new int[len];
		for (int i = 0; i < len; i++) {
			copy[i] = stations.get(i).numPassengers;
		}
		
		for (int i = 0; i < len; i++) {
			stations.get((i + shift) % len).numPassengers = copy[i];
		}
	}
}


public class Main {
	
	private static int N, M, Q;
	
	private static List<Station> stationRefs = new ArrayList<Station>();
	
	
	private static List<MLine> readInStructure(BufferedReader jin) throws Exception {
		
		String[] details = jin.readLine().split(" ");
		N = Integer.parseInt(details[0]); M = Integer.parseInt(details[1]); Q = Integer.parseInt(details[2]);
		
		List<MLine> lines = new ArrayList<MLine>(M);
		for (int i = 0; i < M; i++) {
			lines.add(new MLine());
		}
		
		String[] stationLines = jin.readLine().split(" ");
		String[] initialPassengers = jin.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			int lineNum = Integer.parseInt(stationLines[i]);
			Station station = new Station(Integer.parseInt(initialPassengers[i]));
			stationRefs.add(station);
			lines.get(lineNum - 1).addStation(station); 
		}
		
		return lines;
	}
	

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		List<MLine> lines = readInStructure(jin);
		
		Map<Integer, Integer> shifts = new HashMap<Integer, Integer>();
		boolean workQueued = false;
		
		for (int i = 0; i < Q; i++) {
			String[] command = jin.readLine().split(" ");
			if (command.length == 3) { // survey
				
				if (workQueued) {
					// process queued up shifts
					for (Integer line : shifts.keySet()) {
						lines.get(line).moveTrains(shifts.get(line));
					}
					workQueued = false;
					shifts.clear();
				}
				
				// from l -> r
				int l = Integer.parseInt(command[1]), r = Integer.parseInt(command[2]);
				int total = 0;
				for (int j = l - 1; j <= r - 1; j++) {
					total += stationRefs.get(j).numPassengers;
				}
				sb.append(total);
				sb.append('\n');
			}
			else { // operate line
				workQueued = true;
				int x = Integer.parseInt(command[1]) - 1;
				if (shifts.containsKey(x)) {
					shifts.put(x, shifts.get(x) + 1);
				}
				else {
					shifts.put(x, 1);
				}
			}
		}
		
		System.out.println(sb.toString());
		
		jin.close();
	}

}