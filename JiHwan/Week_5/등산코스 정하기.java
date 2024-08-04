import java.util.*;

class Solution {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        int[] answer = new int[] {1000000000, 1000000000};
        int[] visited = new int[n+1];
        
        List<List<int[]>> arr = new ArrayList<List<int[]>>();   // paths의 원소는 [i, j, w] 형태
        for (int i = 0; i <= n; i++) {
            arr.add(new ArrayList<int[]>());
        }
        
        for (int[] path : paths) { // 저장할 때는 [end, d]
            arr.get(path[0]).add(new int[] {path[1], path[2]});
            arr.get(path[1]).add(new int[] {path[0], path[2]});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));
        for (int gate : gates) { // gate -> -1
            visited[gate] = -1;
            for (int[] start : arr.get(gate)) {
                pq.add(new int[] {start[1], start[0], start[1]});
            }
        }
        
        for (int summit : summits) { // gate -> -2
            visited[summit] = -2;
        }
        
        while (!pq.isEmpty()) {
            int[] tmp = pq.poll(); //  [시작 값과 이동거리의 최댓값, 도착 위치, 이동 거리]
            int sdMax = tmp[0];
            int end = tmp[1];
            int d = tmp[2];
            
            if (visited[end] == 0) { // 1. 아직 방문하지 않았을 경우
                visited[end] = Math.max(sdMax, d);
                for (int[] val : arr.get(end)) {
                    pq.add(new int[] {Math.max(visited[end], val[1]), val[0], val[1]});
                }
            } else if (visited[end] == -1) { // 2. 출발지인 경우
                continue;
            } else if (visited[end] == -2) { // 3. 도착지인 경우
                if (sdMax < answer[1]) {
                    answer[0] = end;
                    answer[1] = sdMax;   
                } else if (sdMax == answer[1] && answer[0] > end) {
                    answer[0] = end;
                }
            } else if (sdMax < visited[end]) { // 4. 더 짧은 길이 있을 경우
                visited[end] = Math.max(sdMax, d);
                for (int[] val : arr.get(end)) {
                    pq.add(new int[] {Math.max(visited[end], val[1]), val[0], val[1]});
                }
            }
        }
        
        return answer;
    }
}