import java.util.*;

class Solution {
    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;
        List<List<int[]>> lst = new ArrayList<>();

        for(int i = 0; i <= n; i++) {
            lst.add(new ArrayList<>());
        }
        
        for(int[] i : reqs) { // 정리하는 과정
            lst.get(i[2]).add(new int[]{i[0],i[1]});
        }
        
        int[][] waitTime = new int[k+1][n-k+2]; // 케이스 별로 나눈 다음
        for(int i = 1; i <= k; i++) {
            for(int j = 1; j <= n-k+1; j++) {
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                for(int[] arr : lst.get(i)) {
                    if(pq.size() < j) {
                        pq.add(arr[0] + arr[1]); // (시작 시간 + 상담 시간) = 끝나는 시간
                    }
                    else {
                        int minNum = pq.poll(); // 끝나는 시간
                        int wait = minNum - arr[0]; // 기다린 시간                        
                        if(wait > 0) {
                            waitTime[i][j] += wait;
                            pq.add(minNum + arr[1]);
                        }
                        else {
                            pq.add(arr[0] + arr[1]);
                        }
                    }
                }
            }
        }
        // r = (멘토의 수 - 상담 유형의 수 + 1) -> 가능한 상담원의 최대 숫자
        answer = dfs(n - k + 1, waitTime, 1, k);
        return answer;
    }
    
    // 가능한 상담원의 최대 숫자, 기다린 시간, 깊이, 상담 유형의 수
    static int dfs(int r, int[][] waitTime, int d, int k) {
        if(d > k) return 0;
        int sum = 100000000;
        for(int i = 1; i <= r; i++) {
            sum = Math.min(dfs(r - i + 1, waitTime, d + 1, k) + waitTime[d][i], sum);
        }
        return sum;
    }
}