package Pro;

import java.util.*;

class Solution {
    private static Scanner sc;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT   = 0;
    private final static int CMD_ADD    = 1;
	private final static int CMD_REMOVE = 2;
	private final static int CMD_CHECK  = 3;
	
	private final static int MAX_LINE = 30000;
	
	private static int nodeA[] = new int[MAX_LINE];
	private static int nodeB[] = new int[MAX_LINE];
	private static int Time[] = new int[MAX_LINE];	
	
    private static boolean run() throws Exception
    {
    	int cmd, N, K;
    	int ans, ret;
 
        boolean ok = false;

        int Q = sc.nextInt();
        for (int q = 0; q < Q; q++) {
            cmd = sc.nextInt();

            if (cmd == CMD_INIT) {
            	N = sc.nextInt();
            	K = sc.nextInt();
    			for (int i = 0; i < K; i++) {
    				nodeA[i] = sc.nextInt();
    				nodeB[i] = sc.nextInt();
    				Time[i] = sc.nextInt();
    			}
    			usersolution.init(N, K, nodeA, nodeB, Time);
    			ok = true;
            } else if (cmd == CMD_ADD) {
            	nodeA[0] = sc.nextInt();
            	nodeB[0] = sc.nextInt();
            	Time[0] = sc.nextInt();
				usersolution.addLine(nodeA[0], nodeB[0], Time[0]);
			} else if (cmd == CMD_REMOVE) {
				nodeA[0] = sc.nextInt();
            	nodeB[0] = sc.nextInt();
				usersolution.removeLine(nodeA[0], nodeB[0]);
			} else if (cmd == CMD_CHECK) {
				nodeA[0] = sc.nextInt();
				nodeB[0] = sc.nextInt();
				ans = sc.nextInt();
				ret = usersolution.checkTime(nodeA[0], nodeB[0]);
				if (ans != ret) {
					ok = false;
				}
			}
			else ok = false;
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        sc = new Scanner(System.in);

        int T = sc.nextInt();
        int MARK = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        sc.close();
    }
}


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
	boolean[] visit;
	
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
		
//		Iterator<Node> iterator = nodes[mNodeA].iterator();
//		while (iterator.hasNext()) {
//		    if (iterator.next().NEXT == mNodeB) {
//		        iterator.remove();
//		        break;
//		    }
//		}
//		
//		iterator = nodes[mNodeB].iterator();
//		while (iterator.hasNext()) {
//		    if (iterator.next().NEXT == mNodeA) {
//		        iterator.remove();
//		        break;
//		    }
//		}
		
		
		
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
		
//		visit = new boolean[100 * N + 31];
//		visit[mNodeA] = true;

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(mNodeA, 0));
		while (!pq.isEmpty()) {
			Node CURR = pq.poll();
			
//			if (visit[CURR.NEXT]) continue;
//			visit[CURR.NEXT] = true;
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








































