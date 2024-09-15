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