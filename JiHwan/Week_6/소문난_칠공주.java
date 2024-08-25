import java.io.*;
import java.util.*;

public class Main {

    private static char[] arr;
    private static int cnt;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        arr = new char[26];
        for (int i = 0; i < 5; i++) {
            String str = br.readLine();
            for (int j = 0; j < 5; j++) {
                arr[i * 5 + j + 1] = str.charAt(j);
            }
        }
        int[] visited = new int[7];
        checked(visited, 1, 25, 0, 0);
        System.out.println(cnt);
    }


    public static void checked(int[] visited, int start, int n, int r, int s) {
        if (r == 7 && s >= 4) {
            boolean[] is_visited = new boolean[7];
            Queue<Integer> q = new LinkedList<>();
            q.add(0);
            is_visited[0] = true;

            while (!q.isEmpty()) {
                int t = q.poll();
                for (int j = 0; j < 7; j++) {
                    if (t != j && !is_visited[j]) {
                        if (((visited[t] - 1) == visited[j] && visited[j] % 5 != 0) || ((visited[t] + 1) == visited[j] && visited[t] % 5 != 0) || (visited[t] - 5) == visited[j] || (visited[t] + 5) == visited[j]) {
                            is_visited[j] = true;
                            q.add(j);
                        }
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                if (!is_visited[i]) return;
            }
            cnt ++;
            return;
        }else if (r >= 7) return;

        for (int i = start; i <= 25; i++) {
            if (arr[i] == 'S') {
                visited[r] = i;
                checked(visited, i + 1, n, r + 1,  s+1);
                visited[r] = 0;
            } else {
                visited[r] = i;
                checked(visited, i + 1, n, r + 1,  s);
                visited[r] = 0;
            }
        }
    }
}