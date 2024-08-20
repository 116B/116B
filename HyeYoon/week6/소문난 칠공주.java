import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

class Node{
    int r, c;

    public Node(int r, int c){
        this.r = r;
        this.c =c;
    }
}



public class Main {

    static char[][] student= new char[5][5];
    static int result;
    static boolean[] isSelected = new boolean[25];
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};


    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

        for(int i=0; i<5; i++){
            String line = br.readLine();
            for(int j=0; j<5; j++){
                student[i][j]= line.charAt(j);
            }
        }

        search(0,0,0);
        System.out.println(result);
    }

    // 학생 조합 완전 탐색
    static void search(int studentIdx, int depth, int yCnt){
        // 1. 임도연파가 4명 이상이면 조기 종료
        if(yCnt >= 4){
            return;
        }

        // 2. 7명 완성 시 조건 만족 여부 확인 후 종료
        if(depth==7){
            int curIdx = studentIdx-1;
            if(bfs(curIdx/5, curIdx%5)){
                result++;
            }
            return;
        }

        // 3. 다음 학생 탐색
        for(int i=studentIdx; i<25; i++){
            isSelected[i] = true;

            // 3.1 임도연파
            if(student[i/5][i%5] == 'Y'){
                search(i+1, depth+1, yCnt+1);
            } else{ // 3.2 이다솜파
                search(i+1, depth+1, yCnt);
            }
            isSelected[i] = false;
        }
    }

    static boolean bfs(int r, int c){
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[5][5];
        visited[r][c] = true;
        queue.offer(new Node(r, c));
        // 인접한 학생의 수
        int cnt = 1;

        while(!queue.isEmpty()){

            Node cur = queue.poll();
            //인접한 상하좌우 탐색
            for(int i=0;i<4;i++){
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                int nxt = nr * 5 + nc;
                //행렬에 범위 내이며, 방문하지 않았으며, 조합 내 포함되어 있으면
                if(nr<0 || nc<0 || nr>=5  || nc>=5 ){
                    continue;
                }
                if(!visited[nr][nc] && isSelected[nxt]){
                    visited[nr][nc] = true;
                    queue.offer(new Node(nr, nc));
                    cnt++;
                }
            }
        }
        //인접한 여학생이 7명인 경우 true, 아니면 false
        return cnt == 7;
    }
}