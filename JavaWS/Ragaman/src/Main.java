import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
	
	private static Map<Character, Integer> charsContained = new HashMap<Character, Integer>();

	public static void main(String[] args) throws Exception {
		
		BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
		
		String line1 = jin.readLine();
		String line2 = jin.readLine();
		
		for (int i = 0; i < line1.length(); i++) {
			char c = line1.charAt(i);
			if (charsContained.containsKey(c)) {
				charsContained.put(c, charsContained.get(c) + 1);
			}
			else {
				charsContained.put(c, 1);
			}
		}
		
		int recognized = 0;
		int asteriskCnt = 0;
		boolean isAnagram = false;
		
		for (int i = 0; i < line2.length(); i++) {
			char c = line2.charAt(i);
			if (c == '*') {
				asteriskCnt++;
				continue;
			}
			
			if (charsContained.containsKey(c)) {
				recognized++;
				int num = charsContained.get(c);
				if (num == 1) {
					charsContained.remove(c);
					continue;
				}
				charsContained.put(c, num - 1);
			}
			else { // not an asterisk or 
				break;
			}
		}
		
		if (line1.length() - recognized == asteriskCnt) {
			isAnagram = true;
		}
		
		System.out.println(isAnagram ? 'A' : 'N');
		
		jin.close();
	}

}
