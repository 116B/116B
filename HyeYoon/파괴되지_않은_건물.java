class Solution {
    
    static int[][] diff;
    
    public int solution(int[][] board, int[][] skill) {
        
        // 1. 차분 배열 생성
        int n = board.length;
        int m = board[0].length;
        diff = new int[n + 1][m + 1];
        
        // 2. 차분 배열에 스킬 적용
        for (int[] sk : skill) {
            int type = sk[0];
            int r1 = sk[1], c1 = sk[2], r2 = sk[3], c2 = sk[4];
            int degree = (type == 1 ? -sk[5] : sk[5]);
            
            diff[r1][c1] += degree;
            diff[r1][c2 + 1] -= degree;
            diff[r2 + 1][c1] -= degree;
            diff[r2 + 1][c2 + 1] += degree;
        }
        
        // 3. 누적 합을 이용해 차분 배열을 원본 배열에 적용
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > 0) diff[i][j] += diff[i - 1][j];
                if (j > 0) diff[i][j] += diff[i][j - 1];
                if (i > 0 && j > 0) diff[i][j] -= diff[i - 1][j - 1];
                board[i][j] += diff[i][j];
            }
        }
        
        return countNotDestroyed(board);
    }
    
    // 파괴되지 않은 건물 개수 카운트
    public int countNotDestroyed(int[][] map){
        int cnt=0;
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[i].length; j++){
                if(map[i][j]>0) cnt++;
            }
        }
        return cnt;
    }
}