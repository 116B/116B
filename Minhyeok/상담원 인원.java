import java.util.*;

class Solution {
    int answer = Integer.MAX_VALUE;
    int[] types;
    int k;
    int[][] reqs;
    public int solution(int k, int n, int[][] reqs) {
        this.k = k;
        this.reqs = reqs;
        types = new int[k+1]; // 해당 업무에 배치된 사람의 수
        for (int i = 1; i <= k; i++)
            types[i] = 1;
        // 각 업무에 최소 1명은 있어야 하므로
        int counselors = n-k; // 남는 상담사의 수
        arrangeCounselor(counselors, 1);
        return answer;
    }

    public void arrangeCounselor(int num, int start) {
        if (start >= k) {
            types[k] += num;
            calc();
            types[k] -= num;
            return;
        }
        for (int i = 0; i <= num; i++) {
            types[start] += i;
            arrangeCounselor(num-i, start+1);
            types[start] -= i; // 뺄 필요 없이 1씩 더하기만 하면 연산 줄어들 거 같은데??
        }
    }

    public void calc() {
        int waitingTime = 0; // 기다린 시간

        PriorityQueue<Integer>[] restList = new PriorityQueue[k+1]; // 상담 중
        Deque<int[]>[] waitingList = new ArrayDeque[k+1]; // 대기 중
        for (int i = 0; i <= k; i++) {
            restList[i] = new PriorityQueue<Integer>();
            waitingList[i] = new ArrayDeque<int[]>();
        }

        for (int[] arr : reqs) {
            int type = arr[2];
            int startTime = arr[0];
            int spendTime = arr[1];
            if (restList[type].size() < types[type]) { // 여유가 있다면
                restList[type].offer(startTime + spendTime);
            } else { // 상담사가 풀일 경우
                int endTime = restList[type].peek();
                if (endTime <= startTime) {
                    // 이미 상담 시간이 끝났으면
                    if (waitingList[type].size() > 0) {
                        // 해당 타입 상담을 기다리고 있는 사람이 있으면
                        int[] tmp = waitingList[type].pollFirst();
                        int time = Math.max(endTime - tmp[0], 0);
                        restList[type].poll();
                        restList[type].offer(tmp[0] + tmp[1] + time);
                        waitingTime += time;
                        waitingList[type].offerLast(arr);
                    } else {
                        // 해당 타입 상담을 기다리고 있는 사람이 없으면
                        restList[type].poll();
                        restList[type].offer(startTime + spendTime);
                    }
                } else {
                    // 상담 시간이 끝나지 않았다면
                    waitingList[type].offerLast(arr);
                }
            }
        }
        for (int i = 1; i <= k; i++) {
            while (waitingList[i].size() > 0) {
                int[] tmp = waitingList[i].pollFirst();
                int endTime = restList[i].poll();
                int time = Math.max(0, endTime - tmp[0]);
                waitingTime += time;
                restList[i].offer(tmp[0] + time + tmp[1]);
            }
        }

        if (waitingTime < answer)
            answer = waitingTime;
    }
}