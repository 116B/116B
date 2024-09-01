import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int[][] grid;
    static boolean[][] visited;
    static int[][] destinations;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        visited = new boolean[n][n];
        destinations = new int[m][2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            destinations[i][0] = Integer.parseInt(st.nextToken()) - 1;
            destinations[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        dfs(destinations[0][0], destinations[0][1], 1);
        System.out.println(count);
    }

    static void dfs(int x, int y, int index) {
        if (index == m) {
            count++;
            return;
        }

        visited[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny] && grid[nx][ny] == 0) {
                if (nx == destinations[index][0] && ny == destinations[index][1]) {
                    dfs(nx, ny, index + 1);
                } else {
                    dfs(nx, ny, index);
                }
            }
        }

        visited[x][y] = false;
    }
}
