import java.util.*;

class Node {
    int r;
    int c;
    String move;

    public Node(int r, int c, String move) {
        this.r = r;
        this.c = c;
        this.move = move;
    }
}

class Solution {

    static int N, M, X, Y, R, C, K;
    static int[] dr = {1, 0, 0, -1}; // 하 좌 우 상
    static int[] dc = {0, -1, 1, 0};
    static String[] dir = {"d", "l", "r", "u"}; // 사전 순으로 탐색하도록
    static PriorityQueue<String> pq = new PriorityQueue<>();
    static boolean[][][] visited;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        N = n;
        M = m;
        X = x;
        Y = y;
        R = r;
        C = c;
        K = k;

        visited = new boolean[N][M][K + 1];
        
        bfs(X - 1, Y - 1); // 0-based

        if (pq.size() == 0) {
            return "impossible";
        } else {
            return pq.poll();
        }
    }

    static void bfs(int r, int c) {

        Queue<Node> q = new LinkedList<>();
        q.add(new Node(r, c, ""));
        visited[r][c][0] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            // 현재 경로가 길이 K인 경우 체크
            if (cur.move.length() == K) {
                if (cur.r == R - 1 && cur.c == C - 1) {
                    pq.add(cur.move);
                }
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                int nlen = cur.move.length() + 1;

                if (nr < 0 || nc < 0 || nr >= N || nc >= M) continue;
                if (visited[nr][nc][nlen]) continue;

                // 남은 이동 횟수로 목표 지점까지 도달 가능한지 확인
                int remainingMove = K - cur.move.length() - 1;
                int dis = Math.abs(nr - (R - 1)) + Math.abs(nc - (C - 1));

                // 1) 남은 이동 횟수를 모두 사용하더라도 도착지점에 도달할 수 없을 때 
                if (remainingMove < dis) continue;

                // 2) 남은 이동횟수와 목표 지점까지의 거리의 홀/짝 여부가 다를 때
                if (remainingMove % 2 != dis % 2) continue;

                visited[nr][nc][nlen] = true;
                q.add(new Node(nr, nc, cur.move + dir[d]));
            }        
        }
    }
}