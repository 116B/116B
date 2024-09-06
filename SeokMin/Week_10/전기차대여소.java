package Pro;

import java.util.*;

class Solution
{
    private static final int CMD_INIT				= 0;
    private static final int CMD_ADD				= 1;
    private static final int CMD_DISTANCE			= 2;
    private static final int MAX_N					= 350;

    private static int[][] region = new int[MAX_N][MAX_N];
    private static UserSolution usersolution = new UserSolution();
    private static Scanner sc;

    private static boolean run() throws Exception
    {
        int Q = sc.nextInt();

        boolean okay = false;

        for (int q = 0; q < Q; ++q)
        {
            int cmd = sc.nextInt();
            int ret, ans, N, range, id, r, c, id2;

            switch(cmd)
            {
                case CMD_INIT:
                    N = sc.nextInt();
                    range = sc.nextInt();
                    for (int i = 0; i < N; i++)
                    {
                        for (int j = 0; j < N; j++)
                        {
                            int in = sc.nextInt();
                            region[i][j] = in;
                        }
                    }
                    usersolution.init(N, range, region);
                    okay = true;
                    break;
                case CMD_ADD:
                    id = sc.nextInt();
                    r = sc.nextInt();
                    c = sc.nextInt();

                    usersolution.add(id, r, c);
                    break;
                case CMD_DISTANCE:
                    id = sc.nextInt();
                    id2 = sc.nextInt();
                    ret = usersolution.distance(id, id2);
                    ans = sc.nextInt();
                    if (ret != ans)
                        okay = false;
                    break;
                default:
                    okay = false;
                    break;
            }

        }

        return okay;
    }

    public static void main(String[] args) throws Exception
    {
        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        sc = new Scanner(System.in);

        int TC = sc.nextInt();
        int MARK = sc.nextInt();

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run() ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        sc.close();
    }
}

class UserSolution
{

    class Station {
        int ID, R, C;
        public Station(int ID, int R, int C) {
            this.ID = ID;
            this.R = R;
            this.C = C;
        }
    }	// Station

    class Node implements Comparable<Node> {
        int ID, len;
        public Node(int ID, int len) {
            this.ID = ID;
            this.len = len;
        }

        @Override
        public int compareTo(Node n) {
            return len - n.len;
        }
    }	// Node

    int N, mRange;				// 길이, 이동 가능 거리
    int[] dist;					// 거리
    int[][] mMap;				// 입력받는 맵
    Station[][] elecStation;	// 충전소 위치
    boolean[] visit;			// 방문
    Queue<int[]> queue;			// 4방 탐색에 쓸 Queue
    int[] dr = {-1, 1, 0, 0};
    int[] dc = {0, 0, -1, 1};
    PriorityQueue<Node>[] nodes;
    final int INF = 987654321;

    void init(int N, int mRange, int mMap[][])
    {
        this.N = N;
        this.mRange = mRange;
        this.mMap = mMap;
        elecStation = new Station[N][N];

        nodes = new PriorityQueue[200];
        for (int i = 0; i < 200; i++) {
            nodes[i] = new PriorityQueue<>();
        }

        return;
    }

    void add(int mID, int mRow, int mCol)
    {
        elecStation[mRow][mCol] = new Station(mID, mRow, mCol);
        visit = new boolean[200];
        search(mID, mRow, mCol);	// search 메소드는 한 번에 갈 수 있는 충전소들만 찾음

        return;
    }

    int distance(int mFrom, int mTo)
    {
        return dijkstra(mFrom, mTo);	// dijkstra 메소드로 중간에 다른 충전소를 거쳐서 건너갈 수 있는 경우 모두 찾음
    }

    int dijkstra(int mFrom, int mTo) {
        dist = new int[200];
        Arrays.fill(dist, INF);
        dist[mFrom] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visitNode = new boolean[200];
        pq.add(new Node(mFrom, 0));
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (visitNode[curr.ID]) {
                continue;
            }

            visitNode[curr.ID] = true;
            for (Node next : nodes[curr.ID]) {
                if (dist[next.ID] > curr.len + next.len) {
                    dist[next.ID] = curr.len + next.len;
                    pq.add(new Node(next.ID, dist[next.ID]));
                }
            }
        }	// while

        if (dist[mTo] == INF) {
            return -1;
        } else {
            return dist[mTo];
        }
    }	// dijkstra

    void search(int mID, int mRow, int mCol) {
        queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        queue.offer(new int[]{mRow, mCol, 0});
        visited[mRow][mCol] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int dist = curr[2];

            if (dist > mRange) continue;

            if (elecStation[r][c] != null && elecStation[r][c].ID != mID) {
                int targetID = elecStation[r][c].ID;
                nodes[mID].add(new Node(targetID, dist));
                nodes[targetID].add(new Node(mID, dist));
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc] && mMap[nr][nc] != 1) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }
    }	// search
}