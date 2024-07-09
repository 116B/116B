import java.util.*;

class Solution {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int R = board.length;
        int C = board[0].length;
        int[][] mapOfSum = new int[R+1][C+1];
        for (int i = 0; i < skill.length; i++) {
            int type = skill[i][0]; // 1 - attack, 2 - repair
            int r1 = skill[i][1];
            int c1 = skill[i][2];
            int r2 = skill[i][3];
            int c2 = skill[i][4];
            int degree = type == 2 ? skill[i][5] : -skill[i][5];
            mapOfSum[r1][c1] += degree;
            mapOfSum[r1][c2+1] -= degree;
            mapOfSum[r2+1][c2+1] += degree; // 중복 제거
            mapOfSum[r2+1][c1] -= degree;
        }
        for (int r = 0; r < R; r++) {
            for (int c = 1; c < C; c++) {
                mapOfSum[r][c] += mapOfSum[r][c-1];
            }
        }
        
        for (int c = 0; c < C; c++) {
            for (int r = 1; r < R; r++) {
                mapOfSum[r][c] += mapOfSum[r-1][c];
            }
        }
        
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                board[r][c] += mapOfSum[r][c];
                if (board[r][c] > 0)
                    answer++;
            }
        }
        return answer;
    }
}