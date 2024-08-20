package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

//빈 칸: 언제나 이동할 수 있다. ('.')
//벽: 절대 이동할 수 없다. ('#')
//열쇠: 언제나 이동할 수 있다. 이 곳에 처음 들어가면 열쇠를 집는다. ('a', 'b', 'c', 'd', 'e', 'f')
//문: 대응하는 열쇠가 있을 때만 이동할 수 있다. ('A', 'B', 'C', 'D', 'E', 'F')
//민식이의 현재 위치: 빈 곳이고, 민식이가 현재 서 있는 곳이다. ('0')
//출구: 달이 차오르기 때문에, 민식이가 가야하는 곳이다. 이 곳에 오면 미로를 탈출한다. ('1')

class Node{
    int r;
    int c;
    int key; // 갖고 있는 열쇠 비트연산 값
    int move; // 이동 횟수

    public Node(int r, int c, int key, int move){
        this.r = r;
        this.c = c;
        this.key = key;
        this.move = move;
    }
}



public class 달이차오른다가자 {

    static int N, M;
    static int sr, sc;
    static char[][] arr;
    static int[] dr = {-1, 1, 0,0};
    static int[] dc ={0,0,-1,1};
    static int minMove = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];

        for(int n=0; n<N; n++){
            String str = br.readLine();
            for(int m=0; m<M; m++){
                arr[n][m] = str.charAt(m);
                if(arr[n][m]=='0'){
                    sr = n; sc = m;
                }
            }
        }

        bfs();
        if(minMove == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(minMove);
    }

    public static void bfs(){
        Queue<Node> q = new LinkedList<>();
        boolean[][][] visited = new boolean[N][M][64];
        q.offer(new Node(sr, sc, 0,0));
        visited[sr][sc][0] = true;

        while(!q.isEmpty()){
            Node cur = q.poll();
            if(arr[cur.r][cur.c]=='1'){
                minMove = Math.min(minMove, cur.move);
            }

            for(int d=0; d<4; d++){
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if(nr<0 || nc<0 || nr>=N || nc>=M) continue;
                if(visited[nr][nc][cur.key]) continue;
                if(arr[nr][nc]=='#') continue; // (1) 벽

                // (2) 열쇠
                if(arr[nr][nc] >= 'a' && arr[nr][nc] <= 'f'){
                    // 새로운 열쇠값 산출
                    int newKey = 1 << (arr[nr][nc] - 'a');
                    // 새로운 열쇠를 획득했으므로 추가 (OR연산)
                    newKey = cur.key | newKey;
                    // 방문 처리
                    visited[nr][nc][newKey]=true;
                    q.offer(new Node(nr, nc, newKey, cur.move+1));
                }

                // (3) 문
                else if(arr[nr][nc] >= 'A' && arr[nr][nc] <= 'F'){
                    // 문에 맞는 열쇠가 있다면 이동
                    if((1<< (arr[nr][nc] - 'A') & cur.key) != 0 ){ 
                        // 방문 처리
                        visited[nr][nc][cur.key]=true;
                        q.offer(new Node(nr, nc, cur.key, cur.move+1));
                    }

                }

                // (4) 빈 칸
                else{
                    // 방문 처리
                    visited[nr][nc][cur.key] = true;
                    q.offer(new Node(nr, nc, cur.key, cur.move+1));
                }

            }
        }

    }
}
