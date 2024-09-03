import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 순서대로_방문하기 {
	
	static class Point {
		int r, c;
		
		public Point() {}
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
		
		boolean equals (Point p) {
			return this.r == p.r && this.c == p.c;
		}
	}	// Point

	static int N, M;
	static int[][] grid;
	static boolean[][] visit;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static Point[] destination;
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		grid = new int[N][N];
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				grid[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		destination = new Point[M];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			destination[i] = new Point(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
		}
        
		visit = new boolean[N][N];
		dfs(destination[0], 1);
		
		System.out.println(answer);
	}	// main

	private static void dfs(Point pnt, int idx) {
		if (pnt.equals(destination[idx])) {
			if (idx == M - 1) {
				answer++;
				return;
			}
			
			idx++;
		}

		visit[pnt.r][pnt.c] = true;
		for (int i = 0; i < 4; i++) {
			int nr = pnt.r + dr[i];
			int nc = pnt.c + dc[i];
			
			if (isOk(nr, nc)) {
				dfs(new Point(nr, nc), idx);
			}
		}
		
		visit[pnt.r][pnt.c] = false;
	}	// dfs

	private static boolean isOk(int nr, int nc) {
		return !(nr < 0 || nr >= N || nc < 0 || nc >= N || grid[nr][nc] == 1 || visit[nr][nc]);
	}
}