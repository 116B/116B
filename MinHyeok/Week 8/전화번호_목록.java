import java.io.*;
import java.util.*;

class Node {
	Node[] next = new Node[10];
	
	Node(){}
}

public class Main {
	static Node node;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int tc = Integer.parseInt(br.readLine());
		for (int t = 0; t < tc; t++) {
			Node[] nodes = new Node[10];
			
			int n = Integer.parseInt(br.readLine());
			boolean isSeparate = true;
			String[] strs = new String[n];
			for (int i = 0; i < n; i++) {
				strs[i] = br.readLine();
			}
			Arrays.sort(strs, new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o2.length() - o1.length();
				}
			});
			
			for (int i = 0; i < n; i++) {
				String number = strs[i];
				boolean check = false;
				int num = Character.getNumericValue(number.charAt(0));
				if (nodes[num] == null) {
					nodes[num] = new Node();
					check = true;
				}
				node = nodes[num];
				for (int j = 1; j < number.length(); j++) {
					num = Character.getNumericValue(number.charAt(j));
					if (node.next[num] == null) {
						node.next[num] = new Node();
						check = true;
					}
					node = node.next[num];
				}
				if (!check) {
					isSeparate = false;
				}
			}
			if (isSeparate) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}
		System.out.println(sb);
	}
}