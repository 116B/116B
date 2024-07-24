import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
	
	static String[][] graph;
	static String[] comm;	// 명령어 집합
	static Parent[][] p;	// 대표자 집합
	
    public String[] solution(String[] commands) {
    	
        List<String> answer = new ArrayList<>(); 
        int idx = 0;
        
        graph = new String[51][51];
        p = new Parent[51][51];
        
        for (int r = 1; r < 51; r++) {
        	for (int c = 1; c < 51; c++) {
        		
        		Arrays.fill(graph[r], "EMPTY");
        		p[r][c] = new Parent(r, c);
        	}
        }	// 표의 기본 값을 "EMPTY"로 채우고 (r, c)의 대표자를 (r, c)로 설정
        
        int size = commands.length;
        for (int i = 0; i < size; i++) {
        
        	comm = commands[i].split(" ");        	
        		
			if (comm[0].equals("UPDATE")) { // UPDATE 명령어인 경우

				if (comm.length == 4) { // 좌표를 명시해준 경우

					update(Integer.parseInt(comm[1]), Integer.parseInt(comm[2]), comm[3]);
				} else { // 값을 명시해준 경우

					update(comm[1], comm[2]);
				}
			} else if (comm[0].equals("MERGE")) { // MERGE 명령어인 경우
				
				merge(new int[] { Integer.parseInt(comm[1]), Integer.parseInt(comm[2]) },
						new int[] { Integer.parseInt(comm[3]), Integer.parseInt(comm[4]) });
			} else if (comm[0].equals("UNMERGE")) { // UNMERGE 명령어인 경우
				
				unmerge(Integer.parseInt(comm[1]), Integer.parseInt(comm[2]));
			} else if (comm[0].equals("PRINT")) { // PRINT 명령어인 경우

				answer.add(print(Integer.parseInt(comm[1]), Integer.parseInt(comm[2])));
			}
        }	// i에 대한 for문
        
        return answer.toArray(new String[0]);
    }	// solution

    private String print(int r, int c) {

    	Parent par = findParent(p[r][c]);
    	return graph[par.r][par.c];
	}


	private void unmerge(int R, int C) {

    	Parent par = findParent(p[R][C]);
    	String str = graph[par.r][par.c];
    	
    	for (int r = 1; r < 51; r++) {
    		for (int c = 1; c < 51; c++) {
    			if (p[r][c].r == par.r && p[r][c].c == par.c) {
    				
    				p[r][c] = new Parent(r, c);
    				graph[r][c] = "EMPTY";
    			}
    		}
    	}	// r에 대한 for문
    	
    	graph[R][C] = str;
	}	// unmerge

    
	// 두 집합을 합치는 메소드
	private void merge(int[] xy1, int[] xy2) {
		
		Parent p1 = findParent(p[xy1[0]][xy1[1]]);
		Parent p2 = findParent(p[xy2[0]][xy2[1]]);
		
		if (p[p2.r][p2.c].r == p[p1.r][p1.c].r 
				&& p[p2.r][p2.c].c == p[p1.r][p1.c].c)	// 같은 집합이면 
			return;
		
		// 다른 집합이면
		if (!graph[p1.r][p1.c].equals("EMPTY")) {	// 표가 비어있지 않으면
			p[p2.r][p2.c] = p1;
			return;
		}
		
		graph[p1.r][p1.c] = graph[p2.r][p2.c];
	}	// merge
	
	
	// 대표자를 찾는 메소드
	private Parent findParent(Parent par) {
		
		if (p[par.r][par.c].r == par.r && p[par.r][par.c].c == par.c)
			return par;
		
		p[par.r][par.c] = findParent(p[par.r][par.c]);
		return p[par.r][par.c];
	}	// findParent

	
	private static void update(String str1, String str2) {

		for (int r = 1; r < 51; r++) {
			for (int c = 1; c < 51; c++) {
				if (graph[r][c].equals(str1))
					graph[r][c] = str2;
			}
		}
	} // 값을 명시해 준 UPDATE 명령어

	
	private void update(int R, int C, String str) {
		
		Parent par = findParent(p[R][C]);
		
		graph[par.r][par.c] = str;
	}	// 좌표를 명시해 준 UPDATE 명령어
	
	
	static class Parent {
		
		int r, c;
		
		public Parent(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}	// Parent class
}












