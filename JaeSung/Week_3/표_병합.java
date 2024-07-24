import java.util.*;
import static java.lang.Integer.parseInt;

class Solution {
    public String[] solution(String[] commands) {
        Matrix matrix = new Matrix();
        return matrix.apply(commands);
    }
}

class Matrix {
    static final int RANGE = 2501;
    
    int[] parent = new int[RANGE]; // 인덱스에 부모 인덱스를 넣기위한 일차원 배열
    String[] values = new String[RANGE]; // 셀의 값을 넣기 위한 배열
    
    Matrix() { // 초기화
        for (int i = 1; i < RANGE; i++) {
            parent[i] = i;
            values[i] = "";
        }
    };
    
    int find(int a) { // 재귀로 루트 인덱스를 찾기 위한 과정
        if (parent[a] == a) return a; // 만약 루트 노드가 없다면 자기 자신 인덱스 이므로 리턴
        else return parent[a] = find(parent[a]); // 재귀 시작 후 ex a[1] = 3 -> a[3] = 4 -> a[4] = 4 라면 종료 후 4 반환
    }
    
    void union(int a, int b) {
       if (a != b) parent[b] = a; // parent[b] = a 로 설정하면서 union 실행
    }
    // parent[7] = 4 --> parent[13] 즉 리프 노드부터 parent[4] = 4 까지 연결됨
    
    int convertIndex(int row, int col) {
        return 50 * (row - 1) + col; // row = 1, y = 1 -> 1 // row = 2, y = 1 -> 51
    }
    
    void update(int r, int c, String value) {
        int idx = convertIndex(r, c);
        values[find(idx)] = value; // 루트 노드를 찾아서(find) 반환 후 루트 노드의 values를 value 변경
    }
    
    void update(String before, String after) {
        for (int i = 1; i < RANGE; i++) {
            if (before.equals(values[i])) values[i] = after; // 루트 노드가 기록되므로 루트 노드의 value를 변경
        }
    }
    
    void merge(int r1, int c1, int r2, int c2) {
        
        if (r1 == r2 && c1 == c2) return; // 병합할 위치가 동일하면 종료 
        
        int idx1 = convertIndex(r1, c1); // 병합할 대상
        int idx2 = convertIndex(r2, c2); // 병합될(할) 대상
        
        int root1 = find(idx1); // 병합할 대상의 루트 노드 번호
        int root2 = find(idx2); // 병합될(할) 대상의 루트 노드 번호
        
        if (root1 == root2) return; // 두 루트 노드가 같다면 이미 병합되어 있는 노드임 
        
        String root = values[root1].isBlank() ? values[root2] : values[root1]; // root1의 값이 "" 라면, root2를 기준으로 병합
        values[root2] = ""; // root2는 값이 있더라도, root1을 기준으로 병합될 예정이므로 ""
        
        union(root1, root2); // root1 으로 병합
        values[root1] = root; // root1의 value를 root1 혹은 root2가 values에 가지고 있던 값으로 업데이트
    }
    
    void unmerge(int r, int c) {
        int idx = convertIndex(r, c);
        int root = find(idx); // 인덱스가 연결된 루트 노드
        
        String rootValue = values[root];
        values[root] = ""; // 루트 노드에 값을 ""로 바꿈으로써 병합되어 있던 셀들이 값을 잃음
        values[idx] = rootValue; // 선택된 노드에 rootValue를 참조 (만약 값이 있다면 병합을 해제하면 해당 포인트가 값을 얻음)
        List<Integer> dels = new ArrayList<>();
        for (int i = 1; i < RANGE; i++) {
            if (find(i) == root) { // find(i)로 루트 노드로 연결된 노드를 전부 탐색
                dels.add(i); 
            }
        }
        for (Integer i : dels) {
            parent[i] = i; // 해당 인덱스를 자기 자신으로 바꿈으로써 연결 노드 전부 제거
        }
    }
    
    String print(int r, int c) {
        int idx = convertIndex(r, c); 
        int root = find(idx);
        
        if (values[root].isBlank()) return "EMPTY";
        return values[root]; // 루트 노드의 값을 출력 = 병합된 노드의 루트 노드의 값
    }
    
    String[] apply(String[] commands) {
        List<String> result = new ArrayList<>();
        
        for (String command : commands) {
            String[] cmds = command.split(" ");
            
            String cmd = cmds[0];
            String v1 = cmds[1];
            String v2 = cmds[2];
            
            if (cmds.length >= 4) {
                String v3 = cmds[3];
                if (cmds.length == 5) merge(parseInt(v1), parseInt(v2), parseInt(v3), parseInt(cmds[4])); // merge
                else update(parseInt(v1), parseInt(v2), v3); // update 오버로딩
            } else {
                if (cmd.equals("UPDATE")) update(v1, v2); // update 오버로딩
                else if (cmd.equals("UNMERGE")) unmerge(parseInt(v1), parseInt(v2)); // unmerge
                else result.add(print(parseInt(v1), parseInt(v2))); // print
            }
        }
        
        return result.toArray(new String[0]);
    }
    
}