import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		char[][] map = new char[N][M];
		int[] start = new int[3];

		Set<Integer>[][] visited = new HashSet[N][M];
		for (int r = 0; r < N; r++) {
			String str = br.readLine();
			for (int c = 0; c < M; c++) {
				visited[r][c] = new HashSet<>();
				map[r][c] = str.charAt(c);
				if (map[r][c] == '0') {
					map[r][c] = '.';
					start[0] = r;
					start[1] = c;
					visited[r][c].add(0);
				}
			}
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(start);
		int[] dr = { -1, 1, 0, 0 };
		int[] dc = { 0, 0, -1, 1 };
		int answer = -1;
		int moving = 0;
		while (!queue.isEmpty()) {
			moving++;
			int size = queue.size();
			for (int n = 0; n < size; n++) {
				int[] tmp = queue.poll(); // r, c, keys
				int keys = tmp[2];
				for (int i = 0; i < 4; i++) {
					int nr = tmp[0] + dr[i];
					int nc = tmp[1] + dc[i];
					if (nr >= 0 && nr < N && nc >= 0 && nc < M && !visited[nr][nc].contains(keys)) {
						switch (map[nr][nc]) {
						case '.':
							visited[nr][nc].add(keys);
							queue.offer(new int[] { nr, nc, keys });
							break;
						case '#': // wall
							break;
						case '1': // goal
							System.out.println(moving);
							return;
						default: // key or door
							if (map[nr][nc] >= 'a' && map[nr][nc] <= 'f') {
								int num = map[nr][nc] - 'a';
								int key = tmp[2] | (1 << num);
								visited[nr][nc].add(key);
								queue.offer(new int[] { nr, nc, key });
							} else if (map[nr][nc] >= 'A' && map[nr][nc] <= 'F') {
								int num = map[nr][nc] - 'A';
								if ((keys & (1 << num)) != 0) {
									visited[nr][nc].add(keys);
									queue.offer(new int[] { nr, nc, keys });
								}
							}
							break;
						}
					}
				}
			}
		}
		System.out.println(answer);
	}
}