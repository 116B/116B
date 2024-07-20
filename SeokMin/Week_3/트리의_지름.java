package BFS_DFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_1167_트리의_지름 {
	static class Node {

		int end, dist;

		public Node(int end, int dist) {
			this.end = end;
			this.dist = dist;
		}
	} // Node class

	static int V; // 정점의 개수
	static List<Node>[] nodes; // 정점을 담을 리스트
	static boolean[] visit; // 방문처리
	static int lastNode;
	static long maxDist = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		V = Integer.parseInt(br.readLine());

		nodes = new ArrayList[V + 1];
		for (int i = 1; i < V + 1; i++)
			nodes[i] = new ArrayList<>();
		
		for (int i = 1; i < V + 1; i++) {

			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			while (true) {

				int end = Integer.parseInt(st.nextToken());
				if (end == -1)
					break;

				int dist = Integer.parseInt(st.nextToken());

				nodes[start].add(new Node(end, dist)); // i번째 정점에 연결된 정점과 정점까지의 거리 저장
			} // while
		} // for

		visit = new boolean[V + 1];
		DFS(1, 0);
		
		visit = new boolean[V + 1];
		DFS(lastNode, 0);
		
		System.out.println(maxDist);
	} // main

	private static void DFS(int num, int 길이) {
		
		if (maxDist < 길이) {	// 현재 루트까지의 거리가 maxDist보다 멀다면
			
			maxDist = 길이;	// maxDist 갱신
			lastNode = num;	// 끝 노드 번호 갱신
		}
		
		visit[num] = true;
		
		for (int i = 0; i < nodes[num].size(); i++) {
			if (!visit[nodes[num].get(i).end]) {	// 뒤로 돌아가는 탐색을 막기 위해 방문처리 체크
				
				DFS(nodes[num].get(i).end, 길이 + nodes[num].get(i).dist);
			}
		}	// for
	}	// DFS
}