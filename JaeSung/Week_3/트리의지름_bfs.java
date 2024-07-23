import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int to;
        int weight;

        Node(int to, int weight) {
            this.to = to; // 연결된 노드 번호
            this.weight = weight; // 해당 노드까지의 거리
        }
    }

    static List<Node>[] graph;
    static boolean[] visited;
    static int maxDist = 0;
    static int maxNode = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());

        graph = new ArrayList[V + 1]; // 노드 번호 1부터 시작하기 때문에 V + 1
        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        } // 노드에 연결된 다른 노드들을 저장하기 위한 ArrayList
        // 이 초기화 과정이 없을 때 NullPointerException 발생

        for (int i = 0; i < V; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            while (true) {
                int adj = Integer.parseInt(st.nextToken());
                if (adj == -1) break;
                int weight = Integer.parseInt(st.nextToken());
                graph[node].add(new Node(adj, weight));
            }
        } // 간선 정보 입력 끝

        // 첫 번째 BFS를 통해 임의의 노드에서 가장 먼 노드를 찾음
        visited = new boolean[V + 1];
        bfs(1);

        // 두 번째 BFS를 통해 첫 번째로 찾은 노드에서 가장 먼 노드를 찾음
        visited = new boolean[V + 1];
        maxDist = 0;
        bfs(maxNode);

        System.out.println(maxDist);
    }

    static void bfs(int start) {
        Queue<Node> queue = new LinkedList<>();
        visited[start] = true;
        queue.offer(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currNode = current.to;
            int currDist = current.weight;

            if (currDist > maxDist) {
                maxDist = currDist;
                maxNode = currNode;
            }

            for (Node neighbor : graph[currNode]) {
                if (!visited[neighbor.to]) {
                    visited[neighbor.to] = true;
                    queue.offer(new Node(neighbor.to, currDist + neighbor.weight));
                }
            }
        }
    }
}