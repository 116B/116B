import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];
        for (int r = 0; r < N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        boolean[][] visited = new boolean[N][1 << N];
        int[] start = new int[]{0, 0, 0}; // city, dist, visited
        pq.add(start);

        while (!pq.isEmpty()) {
            int[] arr = pq.poll();
            int city = arr[0];
            int dist = arr[1];
            int visit = arr[2];
            if (visit == (1 << N) - 1 && city == 0) {
                System.out.println(dist);
                return;
            }
            if (visited[city][visit]) { // 이미 방문한 점이면
                continue;
            }

            visited[city][visit] = true;

            if (visit == (1 << N) - 2) { // 0번을 제외한 모든 점을 방문했으면
                if (map[city][0] == 0)
                    continue;
                int[] newArr = new int[] {0, dist + map[city][0], arr[2] | 1};
                pq.add(newArr);
                continue;
            }

            for (int c = 1; c < N; c++) { // 1번부터 N번까지만 확인.
                if (map[city][c] == 0 || (arr[2] & (1 << c)) != 0) // 갈 수 없거나, 이미 간 곳이면 패스
                    continue;
                if (visited[c][arr[2]]) // visited에 찍혀있으면 패스
                    continue;
                int[] newArr = new int[]{c, dist + map[city][c], (arr[2] | (1 << c))};
                pq.add(newArr);
            }
        }
    }
}