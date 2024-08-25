import java.io.*;
import java.util.*;

public class Main {
	
	static String correct = "123456780";
	static Map<String, Integer> map = new HashMap<>();
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		String str = "";
		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				int num = Integer.parseInt(st.nextToken());
				str += num;
			}
		}
	
		map.put(str, 0);
		System.out.println(bfs(str));
	}
	
	static int bfs(String str) {
		Queue<String> q = new LinkedList<>();
		q.add(str);
		
		while(!q.isEmpty()) {
			String pos = q.poll();
			int move = map.get(pos);
			int empty = pos.indexOf('0');
			int px = empty % 3;
			int py = empty / 3;
			
			if(pos.equals(correct)) {
				return move;
			}
			
			for(int i = 0; i < 4; i++) {
				int nx = px + dx[i];
				int ny = py + dy[i];
				
				if(nx < 0 || ny < 0 || nx > 2 || ny > 2) continue;
				
				int nPos = ny*3 + nx;
				char ch = pos.charAt(nPos);
				String next = pos.replace(ch, 'c');
				next = next.replace('0', ch);
				next = next.replace('c', '0');
				
				if(!map.containsKey(next)) {
					q.add(next);
					map.put(next, move + 1);
				}
			}
		}
		return -1;
	}
}