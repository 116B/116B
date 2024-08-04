import java.io.*;
import java.util.*;

public class Main {
    static class Jewel {
        int value, weight;  // 가치 및 무게

        public Jewel(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    static int N, K;
    static long answer = 0;
    static int[] bag;  // 가방 무게 저장 배열
    static Jewel[] jewels;  // 보석 저장 배열

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        bag = new int[K];
        jewels = new Jewel[N];

        // 보석 입력값 저장	[(v, w), (v, w), ...]
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            jewels[i] = new Jewel(V, M);
        }

        // 가방 입력값 저장	[c, c, c, ...]
        for (int i = 0; i < K; i++) {
            int C = Integer.parseInt(br.readLine());
            bag[i] = C;
        }

        Arrays.sort(bag);  // 가방 오름차순 정렬(작은 것부터 꺼내기)
        Arrays.sort(jewels, Comparator.comparingInt((Jewel j) -> j.weight).thenComparingInt(j -> -j.value));  // 보석 정렬 무게-가치

        // PriorityQueue 생성 및 내림차순 정렬로 설정(큰 것부터 꺼내기)
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        // 가방 무게가 낮은 것부터 탐색.
        for (int i = 0, j = 0; i < K; i++) {
            while (j < N) {
                if (bag[i] < jewels[j].weight)  // 다음 보석부터 가방의 무게보다 클 때
                    break;
                pq.add(jewels[j++].value);  // 가방에 보석을 넣을 수 있을 때
            }
            // 넣을 수 있는 보석이 있을 때 가장 가치가 높은 것 넣기
            if (!pq.isEmpty())
                answer += pq.poll();
        }

        System.out.println(answer);  // 가치의 합 출력
    }
}
