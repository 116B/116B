import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        int answer = 0;
        int life = 0;
        int moreCoin = 0;

        List<Integer> save = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();

        for (int i = 0; i < cards.length / 3; i++) {
            save.add(cards[i]);
            for (int j = 0; j < save.size() - 1; j++) {
                if (save.get(j) == cards.length + 1 - save.get(save.size() - 1)) {
                    save.remove(save.size() - 1);
                    save.remove(j);
                    life++;
                    break;
                }
            }
        }
        
        for (int i = cards.length / 3; i < cards.length; i+= 2) {
            for (int k = 0; k < 2; k++) {
                boolean is_saved = false;
                for (int j = 0; j < save.size(); j++) {
                    if (save.get(j) == cards.length + 1 - cards[i + k] && coin > 0) {
                        save.remove(j);
                        life++;
                        coin--;
                        is_saved = true;
                        break;
                    }
                }

                if (!is_saved) {
                    tmp.add(cards[i + k]);
                    for (int j = 0; j < tmp.size() - 1; j++) {
                        if (tmp.get(j) == cards.length + 1 - tmp.get(tmp.size() - 1)) {
                            tmp.remove(tmp.size() - 1);
                            tmp.remove(tmp.get(j));
                            moreCoin++;
                            break;
                        }
                    }
                }
            }

            if (life == 0) {
                if (coin >= 2 && moreCoin > 0) {
                    coin -= 2;
                    moreCoin --;
                    answer++;
                } else {
                    break;
                }
            } else {
                life --;
                answer++;
            }
        }

        return answer + 1;
    }
}