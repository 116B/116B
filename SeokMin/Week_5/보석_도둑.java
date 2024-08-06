package 그리디;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1202_보석_도둑 {

    static int N, K;    // 보석의 개수, 가방의 개수
    static int[][] J;   // 보석의 무게, 가격 배열
    static int[] C; // 최대 무게
    static PriorityQueue<Integer> pq;
    static Long ans = 0L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        J = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            J[i][0] = Integer.parseInt(st.nextToken());
            J[i][1] = Integer.parseInt(st.nextToken());
        }

        C = new int[K];
        for (int i = 0; i < K; i++) {
            C[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(J, (A, B) -> A[0] - B[0]);
        Arrays.sort(C);
        pq = new PriorityQueue<Integer>(Collections.reverseOrder());
        int idx = 0;
//        int jdx = 0;
        for (int bag : C) {
        	while (idx < N && J[idx][0] <= bag) {
        		pq.offer(J[idx][1]);
        		idx++;
        	}
        	
        	if (!pq.isEmpty())
        		ans += pq.poll();
        }
//        for (int j = jdx; j < N; j++) {
//            if (idx == K) {
//                break;
//            }
//            if (C[idx] >= J[j][0]) {
//                ans += J[j][1];
//                jdx++;
//            }
//        }

        System.out.println(ans);
    }   // main
}
