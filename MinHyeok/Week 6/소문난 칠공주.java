import java.io.*;
import java.util.*;

public class Main {

    static boolean[][] map = new boolean[5][5];
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };
    static int answer = 0;
    static int[] start;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int r = 0; r < 5; r++) {
            String str = br.readLine();
            for (int c = 0; c < 5; c++) {
                map[r][c] = str.charAt(c) == 'S';
            }
        }
        for (int i = 0; i < 25; i++) {
            int r = i / 5;
            int c = i % 5;
            start = new int[] { r, c };
            int yCnt = map[r][c] ? 0 : 1;
            Set<Integer> visit = new HashSet<>();
            visit.add(i);
            dfs(i, 1, yCnt, visit);
        }
        System.out.println(answer);
    }

    private static void dfs(int prev, int cnt, int yCnt, Set<Integer> visit) {
        if (yCnt >= 4)
            return;
        if (cnt >= 7) {
            boolean[][] visited = new boolean[5][5];
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(start);
            int visitCnt = 1;
            visited[start[0]][start[1]] = true;
            while (!queue.isEmpty()) {
                int[] tmp = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int nr = tmp[0] + dr[i];
                    int nc = tmp[1] + dc[i];
                    int num = nr * 5 + nc;
                    if (nr >= 0 && nr < 5 && nc >= 0 && nc < 5 && !visited[nr][nc] && visit.contains(num)) {
                        queue.offer(new int[] { nr, nc });
                        visited[nr][nc] = true;
                        visitCnt++;
                    }
                }
            }
            if (visitCnt >= 7)
                answer++;
            return;
        }
        for (int i = prev + 1; i < 25; i++) {
            int r = i / 5;
            int c = i % 5;
            Set<Integer> set = new HashSet<>(visit);
            set.add(5 * r + c);
            if (map[r][c]) {
                dfs(i, cnt + 1, yCnt, set);
            } else {
                dfs(i, cnt + 1, yCnt + 1, set);
            }
        }

    }

}