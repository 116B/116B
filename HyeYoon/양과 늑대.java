import java.util.*;

class Solution {
    int maxSheep = 0;

    public int solution(int[] info, int[][] edges) {
        // 노드 개수는 정해져 있고, 연결 개수는 정해져있지 않으므로 List<Integer>[] 사용
        List<Integer>[] graph = new ArrayList[info.length];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        // dfs 를 위한 초기화
        boolean[] visited = new boolean[info.length];
        visited[0] = true;
        // 경로 기록 (길이를 지정할 수 없으므로 List<Integer>)
        List<Integer> path = new ArrayList<>();
        path.add(0);
        
        dfs(graph, info, visited, 0, 1, 0, path);

        return maxSheep;
    }

    void dfs(List<Integer>[] graph, int[] info, boolean[] visited, int node, int sheep, int wolf, List<Integer> path) {
        maxSheep = Math.max(maxSheep, sheep);

        // 현재 경로의 복사본을 만듦
        List<Integer> currentPath = new ArrayList<>(path);
        
        // currentPath 내의 모든 원소에 대해 반복문을 돌리는 이유 :
        // 각 노드에서 방문 가능한 모든 경로를 완전히 탐색하기 위함
        // DFS 탐색에서 현재 경로에 있는 모든 노드를 기준으로 다음 방문할 노드를 결정해야 한다.
        for (int current : currentPath) {
            for (int next : graph[current]) {
                if (!visited[next]) {
                    // 방문처리
                    visited[next] = true;
                    path.add(next);

                    if (info[next] == 0) { // 1) 양
                        dfs(graph, info, visited, next, sheep + 1, wolf, path);
                    } else { // 늑대
                        if (sheep > wolf + 1) { // 양이 더 많을 때만 진행
                            dfs(graph, info, visited, next, sheep, wolf + 1, path);
                        }
                    }
                        
                    // 원상복구 (백트랙킹)
                    path.remove(path.size() - 1);
                    visited[next] = false;
                }
            }
        }
    }
}