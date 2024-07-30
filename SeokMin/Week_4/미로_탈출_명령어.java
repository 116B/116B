class Solution {
	
	String answer;
    int n, m, r, c, k;
    char[] ch = {'d', 'l', 'r', 'u'};
    int[][] moves = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
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

        if ((k - height - width) % 2 != 0 || k < height + width) {
            return "impossible";
        }

        dfs(x, y, "");
        return answer;
    }

    private void dfs(int x, int y, String str) {
    	if (str.length() > k || !answer.equals("impossible")) {
            return;
        }
    	
        if (x == r && y == c && str.length() == k) {
        	answer = str;
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + moves[i][0];
            int ny = y + moves[i][1];
            
            if (nx > 0 && nx <= n && ny > 0 && ny <= m) {
                if (str.length() + Math.abs(nx - r) + Math.abs(ny - c) <= k) {
                    dfs(nx, ny, str + ch[i]);
                }
            }
        }
    }	// dfs
}