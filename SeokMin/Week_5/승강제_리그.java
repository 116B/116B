package Pro;

import java.io.IOException;
import java.util.*;

class UserSolution {

	private class Player implements Comparable<Player> {
		int id, ability;
		
		Player(int id, int ability) {
			this.id = id;
			this.ability = ability;
		}
		
		@Override
		public int compareTo(Player o) {
			// 능력치가 다른 경우 능력치 순서대로 내림차순,
			// 능력치가 같은 경우 리그 순서대로 오름차순으로 정렬
			if (ability != o.ability) {
				return Integer.compare(o.ability, ability);
			}
			return Integer.compare(id, o.id);
		}
	}
	
	private int N, L;
	private List<List<Player>> leagues;
	private int sum;

	public void init(int N, int L, int mAbility[]) throws NumberFormatException, IOException {
		this.N = N;
		this.L = L;

		leagues = new ArrayList<>(L);
		for (int i = 0; i < L; i++) {
			leagues.add(new ArrayList<>());
		}

		for (int i = 0; i < N; i++) {
			leagues.get(i / (N / L)).add(new Player(i, mAbility[i]));
		}
	}

	public int move() {
	    sum = 0;
	    // 1. 상위, 하위 리그로 이동할 선수들을 담을 리스트 선언
	    List<Player> movingUp = new ArrayList<>();
	    List<Player> movingDown = new ArrayList<>();

	    // 2. 리그 내의 선수들을 능력치 순서대로 정렬한 후
	    // 상위 리그로 이동하는 선수가 없는 0번째 리그와
	    // 하위 리그로 이동하는 마지막 선수가 없는 (L - 1)리그는 따로 처리 후
	    // 리그 내 첫 번째 선수를 상위 리그로 이동할 선수 리스트에 추가
	    // 리그 내 마지막 선수를 하위 리그로 이동할 선수 리스트에 추가
	    for (int i = 0; i < L; i++) {
	        Collections.sort(leagues.get(i));
	        if (i == 0)
	        	movingDown.add(leagues.get(0).get((N / L) - 1));
	        else if (i == L - 1)
	        	movingUp.add(leagues.get(L - 1).get(0));
	        else {
	        	movingUp.add(leagues.get(i).get(0));
	        	movingDown.add(leagues.get(i).get(leagues.get(i).size() - 1));
	        }
	    }

	    // 3. 선수들을 이동
	    for (int i = 0; i < L - 1; i++) {
	        Player goingUp = movingUp.get(i);
	        Player goingDown = movingDown.get(i);

	        leagues.get(i).remove(goingDown);
	        leagues.get(i + 1).remove(goingUp);
	        leagues.get(i).add(goingUp);
	        leagues.get(i + 1).add(goingDown);

	        sum += goingUp.id + goingDown.id;
	    }

	    return sum;
	}

	public int trade() {
		sum = 0;
		// 1. 선수를 교환할 인접한 두 리그 내의 선수들을 능력치 순서대로 정렬
		for (int i = 0; i < L - 1; i++) {
			Collections.sort(leagues.get(i));
			Collections.sort(leagues.get(i + 1));

			Player bestLower = leagues.get(i + 1).get(0);
			int middleIndex = leagues.get(i).size() / 2;
			Player middleUpper = leagues.get(i).get(middleIndex);

			leagues.get(i).remove(middleUpper);
			leagues.get(i + 1).remove(bestLower);
			leagues.get(i).add(bestLower);
			leagues.get(i + 1).add(middleUpper);

			sum += bestLower.id + middleUpper.id;
		}
		return sum;
	}
}