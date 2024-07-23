import java.util.*;

class Solution {
    private String[][] graph;
    private int[][] parent;

    public Solution() {
        graph = new String[51][51];
        parent = new int[51][51];
        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                parent[i][j] = i * 51 + j;
            }
        }
    }

    private int find(int r, int c) {
        if (parent[r][c] != r * 51 + c) {
            int p = parent[r][c];
            parent[r][c] = find(p / 51, p % 51);
        }
        return parent[r][c];
    }

    private void union(int r1, int c1, int r2, int c2) {
        int root1 = find(r1, c1);
        int root2 = find(r2, c2);
        if (root1 != root2) {
            parent[root2 / 51][root2 % 51] = root1;
        }
    }

    public String[] solution(String[] commands) {
        List<String> answer = new ArrayList<>();
        
        for (String command : commands) {
            String[] tmp = command.split(" ");
            
            // 명령어 UPDATE, MERGE, UNMERGE, PRINT
            if (tmp[0].equals("UPDATE")) {
                if (tmp.length == 4) {
                    int r = Integer.parseInt(tmp[1]);
                    int c = Integer.parseInt(tmp[2]);
                    String value = tmp[3];
                    int root = find(r, c);
                    graph[root / 51][root % 51] = value;
                } else {
                    String value1 = tmp[1];
                    String value2 = tmp[2];
                    for (int r = 1; r <= 50; r++) {
                        for (int c = 1; c <= 50; c++) {
                            if (value1.equals(graph[r][c])) {
                                graph[r][c] = value2;
                            }
                        }
                    }
                }
            } else if (tmp[0].equals("MERGE")) {
                int r1 = Integer.parseInt(tmp[1]);
                int c1 = Integer.parseInt(tmp[2]);
                int r2 = Integer.parseInt(tmp[3]);
                int c2 = Integer.parseInt(tmp[4]);
                if (r1 == r2 && c1 == c2) continue;

                int root1 = find(r1, c1);
                int root2 = find(r2, c2);
                
                if (root1 != root2) {
                    String value = graph[root1 / 51][root1 % 51];
                    if (value == null) {
                        value = graph[root2 / 51][root2 % 51];
                    }
                    union(r1, c1, r2, c2);
                    int newRoot = find(r1, c1);
                    graph[newRoot / 51][newRoot % 51] = value;
                }
            } else if (tmp[0].equals("UNMERGE")) {
                int r = Integer.parseInt(tmp[1]);
                int c = Integer.parseInt(tmp[2]);
                int root = find(r, c);
                String value = graph[root / 51][root % 51];
                List<int[]> mergedCells = new ArrayList<>();
                
                for (int i = 1; i <= 50; i++) {
                    for (int j = 1; j <= 50; j++) {
                        if (find(i, j) == root) {
                            mergedCells.add(new int[]{i, j});
                        }
                    }
                }
                
                for (int[] cell : mergedCells) {
                    parent[cell[0]][cell[1]] = cell[0] * 51 + cell[1];
                    graph[cell[0]][cell[1]] = null;
                }
                
                graph[r][c] = value;
            } else if (tmp[0].equals("PRINT")) {
                int r = Integer.parseInt(tmp[1]);
                int c = Integer.parseInt(tmp[2]);
                int root = find(r, c);
                if (graph[root / 51][root % 51] == null) {
                    answer.add("EMPTY");
                } else {
                    answer.add(graph[root / 51][root % 51]);
                }
            }
        }
        
        return answer.toArray(new String[0]);
    }
}