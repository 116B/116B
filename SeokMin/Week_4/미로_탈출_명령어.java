class Solution {
	
	String answer;
    int n, m, r, c, k;
    char[] ch = {'d', 'l', 'r', 'u'};
    int[] dx = {1, 0, 0, -1};
    int[] dy = {0, -1, 1, 0};
    int width, height;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
    	answer = "";
    	this.n = n;
    	this.m = m;
        this.r = r;
        this.c = c;
        this.k = k;
        
        height = Math.abs(x - r);
        width = Math.abs(y - c);

        // 탈출 지점에 도착할 수 없는 경우
        if ((k - height - width) % 2 != 0 || k < height + width) {
            return "impossible";
        }

        dfs(x, y, "");
        return answer;
    }

    private void dfs(int x, int y, String str) {
    	// 움직일 수 있는 횟수를 넘었거나 || 최적의 경로를 찾아서 answer이 다른 값으로 초기화 된 경우 dfs를 종료
    	if (str.length() > k || !answer.equals("")) {
            return;
        }
    	
    	// 최적의 경로를 찾은 경우 dfs 종료
        if (x == r && y == c && str.length() == k) {
        	answer = str;
            return;
        }

        // 4방 탐색
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            if (nx > 0 && nx <= n && ny > 0 && ny <= m) {
                if (str.length() + Math.abs(nx - r) + Math.abs(ny - c) <= k) {	// 움직인 거리 + 움직여야 하는 거리 <= k인 경우 계속 dfs
                    dfs(nx, ny, str + ch[i]);
                }
            }
        }
    }	// dfs
}
