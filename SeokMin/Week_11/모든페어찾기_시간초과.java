package Pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

class Solution {
    private static BufferedReader br;
    private static final UserSolution userSolution = new UserSolution();

    private final static int MAX_N = 50000;
    private final static int[] cards = new int[MAX_N * 2];
    private final static boolean[] found = new boolean[MAX_N + 1];

    private static int N;
    private static int foundCnt;
    private static boolean ok;

    public static boolean checkCards(int mIndexA, int mIndexB, int mDiff) {
        if (!ok || mIndexA < 0 || mIndexA >= N * 2 || mIndexB < 0 || mIndexB >= N * 2) {
            ok = false;
            return false;
        }

        if (abs(cards[mIndexA] - cards[mIndexB]) > mDiff) {
            return false;
        }

        int val = cards[mIndexA];
        if (mDiff == 0 && mIndexA != mIndexB && !found[val]) {
            foundCnt += 1;
            found[val] = true;
        }

        return true;
    }

    private static void init() {
        foundCnt = 0;
        ok = true;

        for (int i = 1; i <= N; i++) {
            found[i] = false;
        }
    }

    private static boolean run() throws IOException {
        N = Integer.parseInt(br.readLine());

        StringTokenizer stdin = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2 * N; i++) {
            cards[i] = Integer.parseInt(stdin.nextToken());
        }

        init();

        userSolution.playGame(N);

        return ok && foundCnt == N;
    }

    public static void main(String[] args) throws IOException {
        // System.setIn(new FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        int T, MARK;
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.printf("#%d %d\n", tc, score);
        }

        br.close();
    }
}

class UserSolution {

    static int found;
    static boolean[] foundCard;
    static List<Integer>[] diff;

    public void playGame(int N) {
        found = 0;
        foundCard = new boolean[2 * N];
        diff = new ArrayList[2 * N];
        for (int i = 0; i < 2 * N; i++) {
            diff[i] = new ArrayList<>(); // 1만큼 차이
        }

        for (int A = 0; A < (2 * N) - 1; A++) {
            if (foundCard[A]) continue;
            for (int B = A + 1; B < 2 * N; B++) {				// A와의 차이가 1 이하인 모든 숫자의 위치를 찾음
                if (foundCard[B]) continue;

                if (Solution.checkCards(A, B, 1)) {				// 차이가 1 이하인 경우
                    if (Solution.checkCards(A, B, 0)) {			// 같은 경우
                        foundCard[A] = foundCard[B] = true;
                        found++;
                    } else {									// 차이가 1인 경우
                        diff[A].add(B);
                    }
                }
            }   // B에 대한 for

            // 차이가 1인 숫자들끼리 짝지음
            if (diff[A].isEmpty()) {            // 빈 배열인 경우
                continue;
            } else if (diff[A].size() == 2) {   // 사이즈가 2인 경우 두 숫자는 같은 경우
                Solution.checkCards(diff[A].get(0), diff[A].get(1), 0);
                foundCard[diff[A].get(0)] = foundCard[diff[A].get(1)] = true;
                found++;
            } else {                            // 사이즈가 4인 경우 세 가지 경우의 수가 존재
                if (Solution.checkCards(diff[A].get(0), diff[A].get(1), 0)) {
                    Solution.checkCards(diff[A].get(2), diff[A].get(3), 0);
                    foundCard[diff[A].get(0)] = foundCard[diff[A].get(1)]
                            = foundCard[diff[A].get(2)]
                            = foundCard[diff[A].get(3)]
                            = true;
                    found += 2;
                } else if (Solution.checkCards(diff[A].get(0), diff[A].get(2), 0)) {
                    Solution.checkCards(diff[A].get(1), diff[A].get(3), 0);
                    foundCard[diff[A].get(0)] = foundCard[diff[A].get(1)]
                            = foundCard[diff[A].get(2)]
                            = foundCard[diff[A].get(3)]
                            = true;
                    found += 2;
                } else {
                    Solution.checkCards(diff[A].get(0), diff[A].get(3), 0);
                    Solution.checkCards(diff[A].get(1), diff[A].get(2), 0);
                    foundCard[diff[A].get(0)] = foundCard[diff[A].get(1)]
                            = foundCard[diff[A].get(2)]
                            = foundCard[diff[A].get(3)]
                            = true;
                    found += 2;
                }
            }

            if (found == N) break;
        }   // A에 대한 for
    }   // playGame
}