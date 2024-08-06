import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static StringTokenizer st;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		PriorityQueue<int[]> pq = new PriorityQueue<int[]>((e1, e2) -> {
			return e2[1] - e1[1];
		});
		int[][] jewels = new int[N][2];
		int[] bags = new int[K];

		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			jewels[n][0] = Integer.parseInt(st.nextToken());
			jewels[n][1] = Integer.parseInt(st.nextToken());
		}

		for (int k = 0; k < K; k++) {
			bags[k] = Integer.parseInt(br.readLine());
		}

		Arrays.sort(jewels, (e1, e2) -> {
			return e1[0] - e2[0];
		});
		Arrays.sort(bags);

		int index = 0;
		long result = 0;

		for (int k = 0; k < K; k++) {
			int nowBag = bags[k];

			while (index < N) {
				if (jewels[index][0] <= nowBag) {
					pq.add(jewels[index++]);
				} else {
					break;
				}
			}
			if(!pq.isEmpty()) {
				result += pq.poll()[1];
			}
		}

		System.out.println(result);
	}
}