import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        Set<Integer> originalSet = new HashSet<>();
        Set<Integer> addSet = new HashSet<>();
        int result = 0;
        int n = cards.length;
        int goal = n + 1;
        int firstDraw = cards.length / 3;
        
        for(int i = 0; i < firstDraw; i++) {
            originalSet.add(cards[i]);
        }
        
        while(true) {
            // 한 턴이 시작됐다는 것은 라운드 하나 진행한다는 것
            result++;
            
            if(firstDraw >= n) break;
            
            // 일단 카드 두 장 드로우 하고 시작
            addSet.add(cards[firstDraw]);
            addSet.add(cards[firstDraw + 1]);
            firstDraw += 2;
            
            
            // 해결 됐는지 판단하는 flag
            boolean flag = false;
            
            // 내가 가진 카드로 해결이 가능할 때
            for(int i : originalSet) {
                if(originalSet.contains(goal - i)) {
                    originalSet.remove(i);
                    originalSet.remove(goal - i);
                    flag = true;
                    break;
                }
            }
            
            // 카드 한 장 드로우 해서 해결할 때
            if(!flag) {
                if(coin > 0) {
                    for(int i : originalSet) {
                        if(addSet.contains(goal - i)) {
                            originalSet.remove(i);
                            addSet.remove(goal - i);
                            coin--;
                            flag = true;
                            break;
                        }
                    }
                }
                
            }
            
            // 카드 두 장 드로우 해서 해결할 때
            if(!flag) {
                if(coin > 1) {
                    for(int i : addSet) {
                        if(addSet.contains(goal - i)) {
                            addSet.remove(i);
                            addSet.remove(goal - i);
                            coin -= 2;
                            flag = true;
                            break;
                        }
                    }
                }
            }
            
            // 어떻게 해도 해결이 안될 때
            if(!flag) break;
        }
        return result;
    }
}