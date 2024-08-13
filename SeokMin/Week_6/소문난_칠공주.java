package Combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class copy {

	static char[][] map;
	static boolean[][] visit;
	static boolean[][] visit2;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static Stack<int[]> stack;
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		map = new char[5][5];
		for (int r = 0; r < 5; r++) {
			map[r] = br.readLine().toCharArray(); 
		}
		
		visit = new boolean[5][5];
		combination(0, 0, 0);
		System.out.println(answer);
	}	// main

	private static void combination(int idx, int depth, int dasom) {
		// 종료 조건
		if (depth == 7) {
			if (dasom >= 4 && isConnected()) {
				answer++;
			}
			return;
		}
		
		for (int i = idx; i < 25; i++) {
	        int r = i / 5;
	        int c = i % 5;
	        if (!visit[r][c]) {
	            visit[r][c] = true;
	            combination(i + 1, depth + 1, map[r][c] == 'S' ? dasom + 1 : dasom);
	            visit[r][c] = false;
	        }
	    }
		
//		for (int r = idx / 5; r < 5; r++) {
//			for (int c = idx % 5; c < 5; c++) {
//				if (!visit[r][c]) {
//					visit[r][c] = true;
//					combination(5 * r + c + 1, depth + 1, map[r][c] == 'S' ? dasom + 1 : dasom);
//					visit[r][c] = false;
//				}
//			}
//		}
	}	// dfs

	private static boolean isConnected() {
		int R = -1, C = -1;
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				if (visit[r][c]) {
					R = r;
					C = c;
					break;
				}
			}
			if (R != -1) {
				break;
			}
		}
		
		stack = new Stack<int[]>();
		stack.add(new int[] {R, C});
		visit2 = new boolean[5][5];
		visit2[R][C] = true;
		int count = 1;
		
		while (!stack.isEmpty()) {
			int now[] = stack.pop();
			for (int i = 0; i < 4; i++) {
				int rNext = now[0] + dr[i];
				int cNext = now[1] + dc[i];
				if (isOk(rNext, cNext)) {
					count++;
					stack.add(new int[] {rNext, cNext});
					visit2[rNext][cNext] = true;
				}
			}
		}
		
		return count == 7;
	}	// isConnected

	private static boolean isOk(int rNext, int cNext) {
		if (rNext >= 0 && rNext < 5 && cNext >= 0 && cNext < 5 && visit[rNext][cNext] && !visit2[rNext][cNext])
			return true;
		return false;
	}	// isConnected
}