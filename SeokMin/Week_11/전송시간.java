import java.util.*;

import Pro.UserSolution.Node;

class UserSolution {
	
	class Node implements Comparable<Node> {
		int NEXT;
		int TIME;
		
		public Node (int NEXT, int TIME) {
			this.NEXT = NEXT;
			this.TIME = TIME;
		}
		
		@Override
		public int compareTo(Node N) {
			return this.TIME - N.TIME;
		}
	}	// Node
	
	int N, K;						// N : 소규모 그룹의 개수, K : 라인의 개수
	int[] mNodeA, mNodeB, mTime;	// 한쪽, 반대쪽, 전송 시간
	List<Node>[] nodes;
	int[] time;
	
	// N : 네트워크를 구성하는 소규모 그룹의 개수 (3 ≤ N ≤ 300)
	// K : 네트워크를 구성하는 라인의 개수 (10 ≤ K ≤ 30,000) 
	public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[])
	{
		this.N = N;
		this.K = K;
		this.mNodeA = mNodeA;
		this.mNodeB = mNodeB;
		this.mTime = mTime;
		
		nodes = new ArrayList[100 * N + 31];		
		for (int i = 0; i < K; i++) {
			if (nodes[mNodeA[i]] == null) {
				nodes[mNodeA[i]] = new LinkedList<>();
			}
			
			if (nodes[mNodeB[i]] == null) {
				nodes[mNodeB[i]] = new LinkedList<>();
			}
			
			nodes[mNodeA[i]].add(new Node(mNodeB[i], mTime[i]));
			nodes[mNodeB[i]].add(new Node(mNodeA[i], mTime[i]));
		}
	}
	
	// 호출 횟수 최대 200
	public void addLine(int mNodeA, int mNodeB, int mTime)
	{
		if (nodes[mNodeA] == null) {
			nodes[mNodeA] = new LinkedList<>();
		}
		
		if (nodes[mNodeB] == null) {
			nodes[mNodeB] = new LinkedList<>();
		}
		
		nodes[mNodeA].add(new Node(mNodeB, mTime));
		nodes[mNodeB].add(new Node(mNodeA, mTime));
	}

	// 호출 횟수 최대 200
	public void removeLine(int mNodeA, int mNodeB)
	{
		if (nodes[mNodeA] == null) return;
		if (nodes[mNodeB] == null) return;
		
		int index = 0;
		for (Node node : nodes[mNodeA]) {
			if (node.NEXT == mNodeB) {
				nodes[mNodeA].remove(index);
				break;
			}
			
			index++;
		}
		
		index = 0;
		for (Node node : nodes[mNodeB]) {
			if (node.NEXT == mNodeA) {
				nodes[mNodeB].remove(index);
				break;
			}
			
			index++;
		}
	}

	// 호출 횟수 최대 700
	public int checkTime(int mNodeA, int mNodeB)
	{
		time = new int[100 * N + 31];
		Arrays.fill(time, 987654321);
		time[mNodeA] = 0;
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(mNodeA, 0));
		while (!pq.isEmpty()) {
			Node CURR = pq.poll();
			
			if (CURR.TIME > time[CURR.NEXT]) continue;
			if (CURR.NEXT == mNodeB) break;
			
			for (Node node : nodes[CURR.NEXT]) {
				if (time[node.NEXT] > CURR.TIME + node.TIME) {
					time[node.NEXT] = CURR.TIME + node.TIME;
					pq.add(new Node(node.NEXT, time[node.NEXT]));
				}
			}
		}
		
		return time[mNodeB];
	}
}