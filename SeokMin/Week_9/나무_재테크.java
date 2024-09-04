import java.io.*;
import java.util.*;

public class 나무_재테크 {

    static int N, M, K;
    static int[][] A, robot;  // 추가 양분, 로봇
    static int sumAge;
    static ArrayList<Integer>[][] treeAge; // 나무의 나이 저장
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};  // 12시 방향부터 반시계방향
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        A = new int[N + 1][N + 1];
        robot = new int[N + 1][N + 1];
        for (int r = 1; r <= N; r++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(A[r], 5);
            for (int c = 1; c < N + 1; c++) {
                robot[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        treeAge = new ArrayList[N + 1][N + 1];
        for (int r = 0; r <= N; r++) {
            for (int c = 0; c <= N; c++) {
                treeAge[r][c] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            treeAge[r][c].add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < K; i++) {
            spring();
            fall();
            winter();
        }

        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {
                if (!treeAge[r][c].isEmpty()) {
                    answer += treeAge[r][c].size();
                }
            }
        }

        System.out.println(answer);
    }   // main

    private static void winter() {
        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {
                A[r][c] += robot[r][c];
            }
        }
    }   // winter

    private static void fall() {
        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {
                for (Integer age : treeAge[r][c]) {
                    if (age % 5 == 0) {
                        for (int i = 0; i < 8; i++) {
                            int nr = r + dr[i];
                            int nc = c + dc[i];
                            if (isOk(nr, nc)) {
                                treeAge[nr][nc].add(1);
                            }
                        }
                    }
                }
            }
        }
    }   // fall

    private static void spring() {
         for (int r = 1; r <= N; r++) {
             for (int c = 1; c <= N; c++) {
                 Collections.sort(treeAge[r][c]);
                 int size = treeAge[r][c].size();
                 sumAge = 0;

                 for (int i = 0; i < size; i++) {
                     int age = treeAge[r][c].get(i);
                     if (A[r][c] >= age) {
                         A[r][c] -= age;
                         treeAge[r][c].set(i, age + 1);
                     } else {
                         sumAge += (age / 2);
                         treeAge[r][c].set(i, 0);
                     }
                 }

                 for (int i = size - 1; i >= 0; i--) {
                     if (treeAge[r][c].get(i) == 0) {
                         treeAge[r][c].remove(i);
                     }
                 }

                 A[r][c] += sumAge;
             }  // c
         }  // r
    }   // spring

    private static boolean isOk(int R, int C) {
        return !(R < 1 || R > N || C < 1 || C > N);
    }
}






























