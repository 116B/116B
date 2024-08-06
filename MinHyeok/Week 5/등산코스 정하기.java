import java.util.*;

class Solution {
    int n;
    List<int[]>[] paths;
    int[] gates;
    Set<Integer> goals;
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        this.n = n;
        this.paths = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            this.paths[i] = new ArrayList<>();
        }
        for (int i = 0; i < paths.length; i++) {
            int point1 = paths[i][0];
            int point2 = paths[i][1];
            int dist = paths[i][2];
            this.paths[point1].add(new int[] {point2, dist});
            this.paths[point2].add(new int[] {point1, dist});
        }
        this.gates = gates;
        goals = new HashSet<>();
        for (int goal : summits) {
            goals.add(goal);
        }
        int[] answer = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        boolean[] visited = new boolean[n+1];
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[1] == arr2[1] ? arr1[0] - arr2[0] : arr1[1] - arr2[1];
            }
        });
        for (int i = 0; i < gates.length; i++) {
            int start = gates[i];
            visited[start] = true;
            for (int j = 0; j < this.paths[start].size(); j++) {
                int point = this.paths[start].get(j)[0];
                if (visited[point])
                    continue;
                pq.add(this.paths[start].get(j));
            }
        }
        while (!pq.isEmpty()) {
            int[] start = pq.poll();
            int sp = start[0];
            if (visited[sp])
                continue;
            visited[sp] = true;
            if (goals.contains(sp)) {
                if (answer[1] >= start[1]) {
                    if (answer[0] > sp)
                        answer = start;
                    continue;
                } else
                    break;
            }
            for (int i = 0; i < this.paths[sp].size(); i++) {
                int ep = this.paths[sp].get(i)[0];
                if (visited[ep])
                    continue;
                int intensity = Math.max(start[1], this.paths[sp].get(i)[1]);
                pq.add(new int[] {ep, intensity});
            }
        }
        return answer;
    }
}