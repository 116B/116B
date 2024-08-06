import java.util.*;

class UserSolution {
	int N;
	int L;
	int[] mAbility;
	int size;
	TreeSet<Integer>[] upSet; // 중간값보다 크거나 같은 값들
	TreeSet<Integer>[] downSet; // 중간값보다 작은 값들

	void init(int N, int L, int mAbility[]) {
		this.N = N;
		this.L = L;
		this.mAbility = mAbility;
		this.size = N / L;
		upSet = new TreeSet[L];
		downSet = new TreeSet[L];
		for (int i = 0; i < L; i++) {
			PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return mAbility[o1] == mAbility[o2] ? o2 - o1 : mAbility[o1] - mAbility[o2];
				}
			});
			for (int j = 0; j < size; j++) {
				pq.add(i * (N / L) + j);
			}
			downSet[i] = new TreeSet<>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return mAbility[o1] == mAbility[o2] ? o2 - o1 : mAbility[o1] - mAbility[o2];
				}
			});
			upSet[i] = new TreeSet<>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return mAbility[o1] == mAbility[o2] ? o2 - o1 : mAbility[o1] - mAbility[o2];
				}
			});

			for (int j = 0; j < size / 2; j++) {
				downSet[i].add(pq.poll());
			}
			while (!pq.isEmpty()) {
				upSet[i].add(pq.poll());
			}
		}
	}

	int move() {
		int answer = 0;
		int[] up = new int[L]; // 올라갈 놈
		int[] down = new int[L]; // 내려갈 놈
		down[0] = downSet[0].pollFirst();
		answer += down[0];
		for (int i = 1; i < L - 1; i++) {
			up[i] = upSet[i].pollLast();
			down[i] = downSet[i].pollFirst();
			answer += down[i] + up[i];
		}
		up[L - 1] = upSet[L - 1].pollLast();
		answer += up[L - 1];

		upSet[0].add(up[1]);
		downSet[0].add(upSet[0].pollFirst());
		for (int i = 1; i < L - 1; i++) {
			upSet[i].add(up[i + 1]);
			upSet[i].add(down[i - 1]);
			downSet[i].add(upSet[i].pollFirst());
		}
		downSet[L - 1].add(down[L - 2]);
		upSet[L - 1].add(downSet[L - 1].pollLast());
		return answer;
	}

	int trade() {
		int answer = 0;
		int[] up = new int[L]; // 올라갈 놈
		int[] mid = new int[L]; // 내려갈 놈
		mid[0] = upSet[0].pollFirst();
		answer += mid[0];
		for (int i = 1; i < L - 1; i++) {
			up[i] = upSet[i].pollLast();
			mid[i] = upSet[i].pollFirst();
			answer += up[i] + mid[i];
		}
		up[L - 1] = upSet[L - 1].pollLast();
		answer += up[L - 1];

		downSet[0].add(up[1]);
		upSet[0].add(downSet[0].pollLast());
		for (int i = 1; i < L - 1; i++) {
			downSet[i].add(up[i + 1]);
			downSet[i].add(mid[i - 1]);
			upSet[i].add(downSet[i].pollLast());
			upSet[i].add(downSet[i].pollLast());
		}
		downSet[L - 1].add(mid[L - 2]);
		upSet[L - 1].add(downSet[L - 1].pollLast());
		return answer;
	}

}