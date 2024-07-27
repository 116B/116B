import java.util.*;
import java.io.*;

class Node {
	Node parent = this;
	List<Node> children = new ArrayList<>();
	int color;
	int depth;

	Node() {
	}

	Node(int color, int depth) {
		this.color = color;
		this.depth = depth;
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int Q = Integer.parseInt(br.readLine());
		Map<Integer, Node> map = new HashMap<>();
		for (int c = 0; c < Q; c++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			switch (command) {
			case 100:
				int m = Integer.parseInt(st.nextToken());
				int parent = Integer.parseInt(st.nextToken());
				int color = Integer.parseInt(st.nextToken());
				int depth = Integer.parseInt(st.nextToken());
				if (parent == -1) { // 새로운 루트
					Node node = new Node(color, depth);
					map.put(m, node);
				} else { // 기존 루트에 합쳐야하므로 depth 먼저 검사
					Node pNode = map.get(parent);
					List<Node> parentList = new ArrayList<>();
					int rank = 2;
					boolean possible = true;
					while (pNode.parent != pNode) {
						if (rank > pNode.depth) {
							possible = false;
							break;
						}
						rank++;
						parentList.add(pNode);
						pNode = pNode.parent;
					}
					parentList.add(pNode);
					if (possible && rank <= pNode.depth) { // 루트 노드 확인
						Node node = new Node(color, depth);
						node.parent = map.get(parent);
						map.put(m, node);
						for (Node n : parentList) {
							n.children.add(node);
						}
					}
				}
				break;
			case 200:
				m = Integer.parseInt(st.nextToken());
				color = Integer.parseInt(st.nextToken());
				Node node = map.get(m);
				node.color = color;
				for (Node n : node.children) {
					n.color = color;
				}
				break;
			case 300:
				m = Integer.parseInt(st.nextToken());
				sb.append(map.get(m).color).append("\n");
				break;
			case 400:
				int result = 0;
				for (Node n : map.values()) {
					int colors = 1 << n.color;
					for (Node child : n.children) {
						colors = colors | (1 << child.color);
						if (colors >= 62) {
							break;
						}
					}
					int cnt = 0;
					while (colors > 0) {
						if ((colors & 1) == 1) {
							cnt++;
						}
						colors = colors >> 1;
					}
					result += cnt * cnt;
				}
				sb.append(result).append("\n");
				break;
			}
		}
		System.out.println(sb);
	}
}