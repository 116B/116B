package Pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

class Solution2 {
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

	// mDiff가 두 카드의 상한선
	// 상한선이란 것을 이용해서 이분탐색??
	
	Map<Integer, List<Integer>> map;
	int N;
	
    public void playGame(int N) {
    	this.N = N;
    	
    	map = new HashMap<Integer, List<Integer>>();
    	for (int i = 0; i < 2 * N; i++) {
    		int diff = binarySearch(i, N);
    		if (map.get(diff) == null) {
    			map.put(diff, new ArrayList<Integer>());
    		}
    		map.get(diff).add(i);
    	}
    	
    	for (Integer diff : map.keySet()) {	// map을 순회하면서 페어를 찾는다.
    		int size = map.get(diff).size();
			List<Integer> lists = map.get(diff);
			
			if (size == 4) {
				if (Solution2.checkCards(lists.get(0), lists.get(1), 0)) {
					Solution2.checkCards(lists.get(2), lists.get(3), 0);
				} else if (Solution2.checkCards(lists.get(0), lists.get(2), 0) ) {
					Solution2.checkCards(lists.get(1), lists.get(3), 0);
				} else {
					Solution2.checkCards(lists.get(0), lists.get(3), 0);
					Solution2.checkCards(lists.get(1), lists.get(2), 0);
				}
			} else {					// 0번 카드가 1이거나 N인 경우 크기가 2인 리스트가 존재할 수 있다.
				Solution2.checkCards(lists.get(0), lists.get(1), 0);
			}
    		
    	}
    }   // playGame

    // 0번째 숫자와 index번째 숫자의 차이를 반환
	private int binarySearch(int index, int max) {
		int left = 0;
		int right = max;
		
		while (left < right) {
			int mid = (left + right) / 2;
			
			if (Solution2.checkCards(0, index, mid)) {	// 0번째 숫자와 index번째 숫자의 차이가 mid보다 작거나 같은 경우
				right = mid;
			} else {									// 0번째 숫자와 index번째 숫자의 차이가 mid보다 큰 경우
				left = mid + 1;
			}
		}
		
		return right;									// 0번째 숫자와 index번째 숫자의 차이 = right
	}
}