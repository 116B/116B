import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] map;
    static boolean[][][] visited;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M][64];

        int sy = 0, sx = 0; // 시작점
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == '0') {
                    sy = i;
                    sx = j;
                }
            }
        }
        System.out.println(bfs(sy, sx));
    }

    public static int bfs(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y, x, 0, 0}); // y좌표, x좌표, cnt, key
        visited[y][x][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cnt = cur[2];
            int key = cur[3];

            for (int i = 0; i < 4; i++) {
                int ny = cur[0] + dy[i];
                int nx = cur[1] + dx[i];

                if (ny < 0 || nx < 0 || ny >= N || nx >= M || visited[ny][nx][key] || map[ny][nx] == '#') continue;

                if (map[ny][nx] == '1') {
                    return cnt + 1;
                }

                if ('a' <= map[ny][nx] && map[ny][nx] <= 'f') { // 열쇠를 찾았을 경우
                    int newKey = key | (1 << (map[ny][nx] - 'a'));
                    visited[ny][nx][newKey] = true;
                    q.offer(new int[]{ny, nx, cnt + 1, newKey});
                } else if ('A' <= map[ny][nx] && map[ny][nx] <= 'F') { // 방을 찾았을 경우
                    if ((key & (1 << (map[ny][nx] - 'A'))) != 0) {
                        visited[ny][nx][key] = true;
                        q.offer(new int[]{ny, nx, cnt + 1, key});
                    }
                } else { // 빈 공간일 경우
                    visited[ny][nx][key] = true;
                    q.offer(new int[]{ny, nx, cnt + 1, key});
                }
            }
        }
        return -1;
    }
}