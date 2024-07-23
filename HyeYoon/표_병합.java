import java.io.*;
import java.util.*;

// 한 칸 한 칸씩 관리하려고 하지 말고
// 각 칸이 어떤 그룹에 속해있는지,
// 그 그룹의 대표는 어떤 값을 갖고 있는지
// 를 통해 문제를 풀이해보자.

// switch 문 복습
// 문제 서술 상 2차원이어도 사이즈가 고정적이라면 1차원으로 관리할 생각 해보기

class Solution {
    
    static int N = 2500; // 50*50
    static int group[];  // 각 인덱스의 그룹 대표 인덱스를 저장
    static String values[]; // 각 인덱스에 저장된 값
    
    public String[] solution(String[] commands) {
        
        group = new int[N];
        for(int n=0; n<N; n++){
            group[n] = n; // 각 그룹 대표 자기 자신으로 초기화
        }
        values = new String[N];
        List<String> ans = new ArrayList<>();
        
        
        for(String command : commands){
            StringTokenizer st = new StringTokenizer(command);
            
            switch(st.nextToken()){    
                case "UPDATE":
                    String v1 = st.nextToken();
                    String v2 = st.nextToken();
                    
                    // "UPDATE r c value"
                    if(st.hasMoreTokens()){  
                        String val = st.nextToken();
                        int r = Integer.parseInt(v1)-1; // 0-based
                        int c = Integer.parseInt(v2)-1; // 0-based
                        values[find(r*50 + c)] = val;
                    }
                    // "UPDATE value1 value2"
                    else{
                        for(int i=0; i<N; i++){
                            if(values[find(i)]!=null && values[find(i)].equals(v1)){
                                values[find(i)] = v2;
                            }
                        }
                    }
                    break; // 다른 케이스 실행되지 않도록 break 꼭 걸기!! 중요!!
                //"MERGE r1 c1 r2 c2"
                case "MERGE":
                    int r1 = Integer.parseInt(st.nextToken())-1;
                    int c1 = Integer.parseInt(st.nextToken())-1;
                    int r2 = Integer.parseInt(st.nextToken())-1;
                    int c2 = Integer.parseInt(st.nextToken())-1;
                    
                    int num1 = r1*50 + c1;
                    int num2 = r2*50 + c2;
                    
                    // 첫 번째 값을 기준으로 통일시켜야하는데, 첫 번째 값이 null이면
                    if(values[find(num1)]==null  && values[find(num2)]!=null){
                        int tmp = num1;
                        num1 = num2;
                        num2 = tmp;
                    }
                    
                    union(num1, num2); // 첫 번째 값을 기준으로 병합
                    break;
                // "UNMERGE r c" -> 외부 풀이 참고
                case "UNMERGE":
                    int r = Integer.parseInt(st.nextToken()) -1;
                    int c = Integer.parseInt(st.nextToken())-1;
                    int groupNum = find(r*50+c); // 셀이 소속된 그룹
                    String groupVal = values[groupNum];
                    
                    // 이렇게 그룹 해제 전 모든 셀의 그룹의 find를 통해 업데이트 해주지 않으면,
                    // a -> b -> c 관계에서 b가 먼저 끊겨 생기는 문제 발생 
                    for(int n=0; n<N; n++){ // 모든 셀의 그룹 대표 갱신할 준비
                        find(n);
                    }
                    
                    // 표를 전체 순회하면서
                    for(int n=0; n<N; n++){
                        // 선택된 셀과 같은 그룹이면 그룹 초기화 후 값을 null로
                        if(find(n) == groupNum) {
                            group[n] = n;
                            
                            if(n == r*50 +c){ // 그룹 대표일 때
                                values[n] = groupVal;
                            } else{ // 그룹 대표는 아니고 그냥 초기화 대상일 때
                                values[n] = null;
                            }
                        }
                    }
                    break;
                case "PRINT":
                    r = Integer.parseInt(st.nextToken())-1;
                    c = Integer.parseInt(st.nextToken())-1;
                    
                    String v = values[find(r*50 +c)];
                    ans.add(v==null? "EMPTY": v);
                    break;
                }   
            }

            // String[] ansArray = new String[ans.size()];
            // for(int i=0; i<ans.size(); i++){
            //     ansArray[i] = ans.get(i);
            // }
            // return ansArray;
       
        return ans.toArray(new String[0]);
    }
    static int find(int idx){
        // 1) 자기 자신이 대표라면
        if(idx == group[idx] ){
            return idx;
        }
        // 2) 다른 인덱스가 본인 그룹의 대표라면, 그룹 대표 갱신
        group[idx] = find(group[idx]);
        return group[idx];
    }
    
    static void union(int g1, int g2){
        g1 = find(g1);
        g2 = find(g2);
        
        // 이미 같은 그룹일 경우 바로 리턴
        if(g1==g2){
            return;
        }
        // g1을 대표로 두 그룹을 병합
        values[g2] = null;
        group[g2] = g1;
    }
}