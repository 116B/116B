package Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2211_네트워크_복구 {
	
	static class Edge implements Comparable<Edge> {
		int num, dist;
		
		public Edge (int num, int dist) {
			this.num = num;
			this.dist = dist;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.dist - o.dist;
		}
	}	// Edge
	
	static int N, M;
	static int[] distance, connect;
	static final int INF = (int)1e9;
	static ArrayList<Edge>[] list;
	static int count;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N + 1];
		distance = new int[N + 1];
		connect = new int[N + 1];
		for (int i = 0; i <= N; i++) {
			list[i] = new ArrayList<Edge>();
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			
			list[A].add(new Edge(B, dist));
			list[B].add(new Edge(A, dist));
		}
		
		Arrays.fill(distance, INF);
		dijkstra();
		
		for(int i = 2; i <= N; i++){
            if(connect[i] == 0) {
            	continue;
            }
            count++;
            sb.append(i + " " + connect[i] + "\n");
        }
        System.out.println(count);
        System.out.println(sb);
	}	// main

	private static void dijkstra() {
		distance[1] = 0;	// 시작 지점
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		pq.add(new Edge(1, 0));
		while (!pq.isEmpty()) {
			Edge edge = pq.poll();
			
			if (edge.dist > distance[edge.num]) {
				continue;
			}
			
			for (Edge e : list[edge.num]) {
				if (distance[e.num] > e.dist + edge.dist) {
					distance[e.num] = e.dist + edge.dist;
					connect[e.num] = edge.num;
					pq.add(new Edge(e.num, distance[e.num]));
				}
			}
		}
	}	// dijkstra
}















































