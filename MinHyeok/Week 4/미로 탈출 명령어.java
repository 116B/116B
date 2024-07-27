import java.util.*;

class Solution {
	int[] dr = new int[] { 1, 0, 0, -1 }; // d l r u
	int[] dc = new int[] { 0, -1, 1, 0 }; // d l r u
	Map<Integer, String> strMap = new HashMap<>();

	public String solution(int n, int m, int x, int y, int r, int c, int k) {
		String[][] map = new String[n + 1][m + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				map[i][j] = "";
			}
		}
		strMap.put(0, "d");
		strMap.put(1, "l");
		strMap.put(2, "r");
		strMap.put(3, "u");

		map[x][y] = "";
		String answer = "impossible";
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] { x, y });
		int count = 0;
		while (count < k) {
			count++;
			int size = queue.size();
			String[][] copyMap = new String[n + 1][m + 1];
			for (int s = 0; s < size; s++) {
				int[] tmp = queue.poll();
				for (int i = 0; i < 4; i++) {
					int nr = tmp[0] + dr[i];
					int nc = tmp[1] + dc[i];
					String str = map[tmp[0]][tmp[1]] + strMap.get(i);
					if (nr >= 1 && nr <= n && nc >= 1 && nc <= m) {
						if (copyMap[nr][nc] == null) {
							copyMap[nr][nc] = str;
							queue.offer(new int[] { nr, nc });
						} else {
							if (str.compareTo(copyMap[nr][nc]) < 0) {
								copyMap[nr][nc] = str;
							}
						}
					}
				}
			}
			map = copyMap;
		}
		if (map[r][c] != null) {
			answer = map[r][c];
		}
		return answer;
	}
}