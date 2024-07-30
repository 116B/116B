package DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2098_외판원_순회 {

	static int N;	// 도시의 수
	static int[][] cost;	// 비용
	static int[][] DP;	// 순회에 필요한 최소 비용
	static final int INF = 987654321;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		cost = new int[N][N];		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				cost[r][c] = Integer.parseInt(st.nextToken());
			}
		}	// cost
		
		DP = new int[N][(1 << N)];
		for (int i = 0; i < N; i++)
			Arrays.fill(DP[i], -1);
		
		// 0번 도시에서 출발, 0번 도시 방문처리
		System.out.println(DFS(0, (1 << 0)));
	}	// main

	
	// 현재 위치한 도시, 현재까지 방문한 도시
	private static int DFS(int curr, int visit) {
		if (visit == (1 << N) - 1) {	// 모든 도시를 방문한 경우
			if (cost[curr][0] == 0) {	// 현재 도시에서 출발 지역으로 가는 경로가 없다면
				return INF;
			}
			
			return cost[curr][0];
		}
	
		if (DP[curr][visit] != -1)
			return DP[curr][visit];
		
		DP[curr][visit] = INF;
		
		for (int i = 1; i < N; i++) {
			// 현재까지 i번째 도시에 방문한 적이 없고 && 현재 도시에서 i번째 도시로 가는 경로가 있는 경우
			if ((visit & (1 << i)) == 0 && cost[curr][i] != 0) {
				// 현재 도시에서 출발 도시로 가는 최소 비용은
				// 이전에 저장된 비용과 현재 도시에서 i번째 도시로 이동 후 i번째 도시에서 출발 도시로 이동하는 비용 중 작은 값이다.
				DP[curr][visit] = Math.min(DP[curr][visit], cost[curr][i] + DFS(i, visit | (1 << i)));
			}
		}
		
		return DP[curr][visit];
	}	// DFS
}