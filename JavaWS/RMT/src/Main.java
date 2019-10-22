import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

class Pair {
	public int first, second;
	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}
	public void set(int f, int s) {
		this.first = f;
		this.second = s;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first;
		result = prime * result + second;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		//if (getClass() != obj.getClass())
		//	return false;
		Pair other = (Pair) obj;
		return (this.first == other.first) && (this.second == other.second);
	}
}

class PassengerSet {
	public List<Integer> stationPassengers = new ArrayList<Integer>();
	public int offset = 0;
	
	public PassengerSet() {
		
	}
	public void moveLineTrains(int shift) {
		this.offset += shift;
		offset %= stationPassengers.size();
	}
	public int getPassengersAtStationIndex(int index) {
		int i = index - this.offset;
		return stationPassengers.get((i < 0) ? i + (this.stationPassengers.size()) : i);
	}
}

class Station {
	public PassengerSet passengerSetPtr;
	public final int indexIn;
	public Station(PassengerSet passengerSetPtr, int initialPassengers) {
		this.passengerSetPtr = passengerSetPtr;
		int sizern = passengerSetPtr.stationPassengers.size();
		this.indexIn = sizern;
		passengerSetPtr.stationPassengers.add(initialPassengers);
	}
	public int getNumPassengers() {
		return passengerSetPtr.getPassengersAtStationIndex(indexIn);
	}
}

/*class MLine {
	
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
		}
		int[] copy = new int[len];
		for (int i = 0; i < len; i++) {
			copy[i] = stations.get(i).numPassengers;
		}
		
		for (int i = 0; i < len; i++) {
			stations.get((i + shift) % len).numPassengers = copy[i];
		}
	}
}*/


public class Main {
	
	private static int N, M, Q;
	
	private static List<Station> stationRefs = new ArrayList<Station>();
	private static Map<Pair, Integer> alreadyCalculated = new HashMap<Pair, Integer>();
	
	
	private static List<PassengerSet> readInStructure(BufferedReader jin) throws Exception {
		
		String[] details = jin.readLine().split(" ");
		N = Integer.parseInt(details[0]); M = Integer.parseInt(details[1]); Q = Integer.parseInt(details[2]);
		
		List<PassengerSet> lines = new ArrayList<PassengerSet>(M);
		for (int i = 0; i < M; i++) {
			lines.add(new PassengerSet());
		}
		
		String[] stationLines = jin.readLine().split(" ");
		String[] initialPassengers = jin.readLine().split(" ");
		//StringTokenizer stationLines = new StringTokenizer(jin.readLine(), " ");
		//StringTokenizer initialPassengers = new StringTokenizer(jin.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int lineNum = Integer.parseInt(stationLines[i]) - 1;
			PassengerSet current = lines.get(lineNum);
			Station station = new Station(current, Integer.parseInt(initialPassengers[i]));
			stationRefs.add(station);
		}
		
		return lines;
	}
	

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		List<PassengerSet> lines = readInStructure(jin);
		
		Map<Integer, Integer> shifts = new HashMap<Integer, Integer>();
		boolean workQueued = false;
		
		Pair checkingPair = new Pair(0, 0);
		
		for (int i = 0; i < Q; i++) {
			String[] command = jin.readLine().split(" ");
			if (command.length == 3) { // survey
				if (workQueued) {
					// process queued up shifts
					for (Entry<Integer, Integer> entry : shifts.entrySet()) {
						lines.get(entry.getKey()).moveLineTrains(entry.getValue());
					}
					workQueued = false;
					shifts.clear();
					alreadyCalculated.clear();
				}
				
				// from l -> r
				int l = Integer.parseInt(command[1]) - 1, r = Integer.parseInt(command[2]) - 1;
				int total = 0;
				
				checkingPair.set(l, r);
				Integer val = alreadyCalculated.get(checkingPair);
				if (val != null) {
					total = val;
				}
				else {
					for (int j = l; j <= r; j++) {
						Station station = stationRefs.get(j);
						total += station.getNumPassengers();
					}
					alreadyCalculated.put(new Pair(l, r), total);
				}
				
				sb.append(total);
				sb.append('\n');
			}
			else { // operate line
				workQueued = true;
				int x = Integer.parseInt(command[1]) - 1;
				Integer val = shifts.get(x);
				if (val != null) {
					shifts.put(x, val + 1);
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
