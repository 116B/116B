class Solution {
    public String solution(int n, int m, int y, int x, int c, int r, int k) {
        String answer = "";
        int[] dlru = new int[5]; // d, l, r, u, rl
				x--; y--; r--; c--;
        
        // 1. 거리를 구해서 갈 수 있는 거리인지 판단!
        int mDis = Math.abs(x-r) + Math.abs(y-c);
        if (mDis > k || mDis % 2 != k % 2) answer = "impossible";
        
        // 2. 갈 수 있는 거리라면, case 3개로 나누어서 판별, 
        // 내려갔다가만 가기, 내려갔다가 왼쪽갔다가 가기, 내려갔다가 왼쪽갔다가 왔다갔다 하다가 가기
        else {
            if (k >= r + x + n-1 - c + n-1 - y) {  // case 3
                dlru[0] += n-1 - y;
                dlru[1] += x;
                dlru[2] += r;
                dlru[3] += n-1 - c;
                dlru[4] += (k - (r + x + n-1 - c + n-1 - y)) / 2;
            } else if (k <= Math.abs(r - x) + n-1 - c + n-1 - y) { // case 1
                dlru[0] += (k - mDis) / 2;
                dlru[1] += Math.max(0, x - r);
                dlru[2] += Math.max(0, r - x);
                dlru[3] += (k - mDis) / 2;
            } else { // case 2
                dlru[0] += n-1 - y;
                dlru[1] += Math.max(0, x - r) + (k - (Math.abs(r - x) + n-1 - c + n-1 - y)) / 2;
                dlru[2] += Math.max(0, r - x) + (k - (Math.abs(r - x) + n-1 - c + n-1 - y)) / 2;
                dlru[3] += n-1 - c;
            }
            
            for (int i = 0; i < dlru[0]; i++) answer += "d";
            for (int i = 0; i < dlru[1]; i++) answer += "l";
            for (int i = 0; i < dlru[4]; i++) answer += "rl";
            for (int i = 0; i < dlru[2]; i++) answer += "r";
            for (int i = 0; i < dlru[3]; i++) answer += "u";
        }

        return answer;
    }
}