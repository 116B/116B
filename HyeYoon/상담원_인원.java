import java.util.*;
import java.io.*;
// N, K 모두 굉장히 작다. 이걸 가지고 반복문을 돌려도 시간 복잡도가 괜찮겠다.

class Req{
    int requestTime;
    int duration;
    int type;
    
    public Req(int requestTime, int duration, int type){
        this.requestTime=requestTime;
        this.duration=duration;
        this.type=type;
    }
}

class Solution {
    static int K; 
    static int[] mentorPerType;
    static List<Queue<Req>> requestQList;
    static int[][] requests;
    static int minWaitingTime;
    
    public int solution(int k, int n, int[][] reqs) {
        K =k; // 유형 개수
        mentorPerType = new int[K + 1]; // 1-based. 유형마다 배치된 멘토 수 
        requestQList = new ArrayList<>(K + 1); //k번 유형에 대한 상담 요청 누적 큐
        requests = reqs;
        minWaitingTime = Integer.MAX_VALUE;
        
        for(int i=0; i<=K; i++){ // 유형 별 요청 큐 생성만 해두기 (NullPointerException)
            requestQList.add(new LinkedList<>());
        }
        
        //1~k번 상담 유형마다 멘토를 1명 이상 배치해보자. 총 멘토 수 N명
        
        // 최소 한 명 배치해야하고, 멘토가 누가 누구인지도 중요하지 않으므로 
        // 일단 각 타입마다 한 명씩은 배치
        Arrays.fill(mentorPerType, 1);
        
        // 배치해야하는 잉여 멘토 수
        int remainingMentors  = n-K;
        // 잉여 멘토들을 배치하자.
        randomMentor(remainingMentors , 1);   

        return minWaitingTime;
    }

    
    // cnt : 아직 배치받지 못한 잉여 멘토 수. 0이 되면 이 함수 종료.
    // typeIdx : 현재 배치 대상인 유형의 idx
    static void randomMentor(int cnt, int typeIdx){
        if(typeIdx>K){ // 모든 유형에 대한 배치가 끝났을 때
            if (cnt == 0) {
                // 모든 잉여 멘토 배치가 완료되었을 때만 상담 요청 처리
                int waitingTime = processRequests();
                minWaitingTime = Math.min(minWaitingTime, waitingTime);
            }
            return;
        }
        
        if(cnt==0){ // 모든 유형 탐색하진 않았더라도 잉여 멘토가 0명이면 배치 조기 종료
             int waitingTime = processRequests();
             minWaitingTime = Math.min(minWaitingTime, waitingTime);
             return;
        }
        
        // 1) 이번 유형에 멘토를 배치하지 않는 경우 i=0
        // 2) 이번 유형에 멘토를 배치하는 경우 i=1 ~ i=cnt
        // 최소 0명 ~ 최대 cnt명 배치 가능
        for(int i=0; i<=cnt; i++){
            mentorPerType[typeIdx] += i;
            randomMentor(cnt-i, typeIdx+1);
            mentorPerType[typeIdx] -= i; // 원상복구
        }
    }
    
    public static int processRequests(){
        
        // 모든 신청자의 대기시간 누적
        int totalWaitingTime =0;
        
        // processRequests는 여러 번 호출되므로 이전 요청 큐 초기화. 
        for (int i = 0; i <= K; i++) {
            requestQList.get(i).clear(); 
        }
        
        // 각 요청을 객체로 반환하여 유형 별 요청누적 큐에 추가
        for(int[] req : requests){
            int requestTime = req[0];
            int duration = req[1];
            int type = req[2];
            requestQList.get(type).offer(new Req(requestTime, duration, type));
        }
        
        // 유형 별로 상담 신청 처리
        for(int k=1; k<=K; k++){
            Queue<Req> requestForTypeQ = requestQList.get(k); // 이번 유형 요청만 모아놓은 큐
            PriorityQueue<Integer> mentorPossibleTimeQ = new PriorityQueue<>();
            
            // 초기화 : 모든 멘토들이 새로운 상담까지 걸리는 시간을 0으로 설정
            // k번 유형에 배치된 멘토들 수 만큼 pq 원소로 삽입
            for(int m=0; m<mentorPerType[k]; m++){
                mentorPossibleTimeQ.add(0);
            }
            
            while(!requestForTypeQ.isEmpty()){
                Req curReq = requestForTypeQ.poll();
                int earliestAvailableTime = mentorPossibleTimeQ.poll(); // 가장 빨리 되는 멘토의 "시각"
                // 1) 가장 빨리되는 멘토의 시각이 내 요청 시각보다 빠르면 음수 결과가 나오므로 결국 대기 시간은 0
                // 2) 반대 경우라면 양수라서 해당 시간차만큼 waitingTime 변수에 대입됨
                int waitingTime = Math.max(0, earliestAvailableTime-curReq.requestTime);
                
                // 대기시간 누적 갱신
                totalWaitingTime += waitingTime;
                // 상담이 끝나는 시각 = (내가 요청한 시각, 가장 빨리 되는 멘토의 시각) 중 늦은 시각 + 상담에 걸리는 시간
                int finishTime = Math.max(curReq.requestTime, earliestAvailableTime) + curReq.duration;
                mentorPossibleTimeQ.offer(finishTime); // 이번에 상담해준 멘토의 가능시각 갱신 후 멘토큐에 삽입
            }
        }        
        return totalWaitingTime;
    }
    
}