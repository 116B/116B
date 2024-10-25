class Solution {
    // 실외 온도, 희망 온도 최저, 희망 온도 최고, 다를 때 전력, 같을 때 전력
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int answer = 10000000;
        
        int[] arr = new int[2000]; // -1000 ~ 999
        for (int i = 0; i < 2000; i++) arr[i] = -1;
        arr[temperature + 1000] = 0; 
        
        for (int h = 0; h < onboard.length; h++) {
            int[] newArr = new int[2000];
            for (int j = 0; j < 2000; j++) newArr[j] = -1;
            
            if (onboard[h] == 0) {
                for (int i = 1; i < 1999; i++) {
                    int num = arr[i];
                    
                    if (num != -1 && temperature > i - 1000) {
                        // 에어컨 켜기(온도 하락)
                        if (arr[i] + a < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i] + a;
                        } 
                        // 에어컨 켜기(온도 유지)
                        if (arr[i] + b < newArr[i] || newArr[i] == -1)  {
                            newArr[i] = arr[i] + b;
                        }
                        // 에어컨 끄기(온도 상승)    
                        if (arr[i] < newArr[i + 1] || newArr[i + 1] == -1)  {
                            newArr[i + 1] = arr[i];
                        }
                    } else if (num != -1 && temperature == i - 1000) {
                        // 에어컨 켜기(온도 하락)
                        if (arr[i] + a < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i] + a;
                        }
                        // 에어컨 끄기(온도 유지)  
                        if (arr[i] < newArr[i] || newArr[i] == -1)  {
                            newArr[i] = arr[i];
                        }
                        // 에어컨 켜기(올리기)
                        if (arr[i] + a < newArr[i + 1] || newArr[i + 1] == -1) {
                            newArr[i + 1] = arr[i] + a;
                        }
                    } else if (num != -1 && temperature < i - 1000) {
                        // 에어컨 끄기(온도 하락)  
                        if (arr[i] < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i];
                        }
                        // 에어컨 켜기(유지)
                        if (arr[i] + b < newArr[i] || newArr[i] == -1) {
                            newArr[i] = arr[i] + b;
                        }
                        // 에어컨 켜기(올리기)
                        if (arr[i] + a < newArr[i + 1] || newArr[i + 1] == -1) {
                            newArr[i + 1] = arr[i] + a;
                        }
                    }
                }
                for (int i = 0; i < 2000; i++) arr[i] = -1;

                for (int j = 0; j < 2000; j++) {
                    arr[j] = newArr[j];
                }
                
            } else if (onboard[h] == 1) {
                for (int i = 1; i < 1999; i++) {
                    int num = arr[i];
                    
                    if (num != -1 && temperature > i - 1000) {
                        // 에어컨 켜기(온도 하락)
                        if (arr[i] + a < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i] + a;
                        } 
                        // 에어컨 켜기(온도 유지)
                        if (arr[i] + b < newArr[i] || newArr[i] == -1)  {
                            newArr[i] = arr[i] + b;
                        }
                        // 에어컨 끄기(온도 상승)    
                        if (arr[i] < newArr[i + 1] || newArr[i + 1] == -1)  {
                            newArr[i + 1] = arr[i];
                        }
                    } else if (num != -1 && temperature == i - 1000) {
                        // 에어컨 켜기(온도 하락)
                        if (arr[i] + a < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i] + a;
                        }
                        // 에어컨 끄기(온도 유지)  
                        if (arr[i] < newArr[i] || newArr[i] == -1)  {
                            newArr[i] = arr[i];
                        }
                        // 에어컨 켜기(올리기)
                        if (arr[i] + a < newArr[i + 1] || newArr[i + 1] == -1) {
                            newArr[i + 1] = arr[i] + a;
                        }
                    } else if (num != -1 && temperature < i - 1000) {
                        // 에어컨 끄기(온도 하락)  
                        if (arr[i] < newArr[i - 1] || newArr[i - 1] == -1) {
                            newArr[i - 1] = arr[i];
                        }
                        // 에어컨 켜기(유지)
                        if (arr[i] + b < newArr[i] || newArr[i] == -1) {
                            newArr[i] = arr[i] + b;
                        }
                        // 에어컨 켜기(올리기)
                        if (arr[i] + a < newArr[i + 1] || newArr[i + 1] == -1) {
                            newArr[i + 1] = arr[i] + a;
                        }
                    }
                }
                for (int i = 0; i < 2000; i++) arr[i] = -1;
                
                for (int j = 0; j < 2000; j++) {
                    if (t1 <= j - 1000 && j - 1000 <= t2) {                        
                        arr[j] = newArr[j];
                    }
                }
            }
        }
        
        for (int j = 0; j < 2000; j++) {
            if (arr[j] != -1 && arr[j] < answer) {
                answer = arr[j];
            }
        }
                
        return answer;
    }
}