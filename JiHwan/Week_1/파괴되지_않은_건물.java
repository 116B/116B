class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        
        int Y = board.length;
        int X = board[0].length;
        int[][] tmp = new int[Y + 1][X + 1]; // 임시 배열을 만들음
        
        for (int t = 0; t < skill.length; t++) {
            int type = skill[t][0];
            int y1 = skill[t][1];
            int x1 = skill[t][2];
            int y2 = skill[t][3] + 1;
            int x2 = skill[t][4] + 1;
            int degree = skill[t][5];
            
            if (type == 1) { // 공격
                tmp[y1][x1] -= degree;
                tmp[y2][x1] += degree;
                tmp[y1][x2] += degree;
                tmp[y2][x2] -= degree;
            } else { // 회복
                tmp[y1][x1] += degree;
                tmp[y2][x1] -= degree;
                tmp[y1][x2] -= degree;
                tmp[y2][x2] += degree;
            }
        }
        
        for (int i = 0; i < X + 1; i++) { // 세로로 누적합 더하기
            for (int j = 0; j < Y; j++) {
                tmp[j + 1][i] += tmp[j][i];
            }
        }
        
        for (int i = 0; i < Y + 1; i++) { // 가로로 누적합 더하기
            for (int j = 0; j < X; j++) {
                tmp[i][j + 1] += tmp[i][j];
            }
        }
        
        for (int i = 0; i < Y; i++) { // board + tmp 해서 answer 개수 구하기
            for (int j = 0; j < X; j++) {
                if (board[i][j] + tmp[i][j] >= 1) answer++;
            }
        }
        
        return answer;
    }
}