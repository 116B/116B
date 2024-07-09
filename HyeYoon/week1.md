## 파괴되지 않은 건물

> 💡 원본 배열 입장에서 공격/회복의 모든 과정까지 알아야할까? 결과만 전달해주면 된다!
> **차분**을 활용해보자!

### 차분 배열 사용 전

**효율성 면에서 오답**

(`r1` to `r2`, `c1` to `c2`)에 대해 이중 for문을 돌리면서 값을 직접 갱신
시간 복잡도 : **O(N _ M _ K)**
(`K`는 스킬의 개수이고 , `N`과 `M`은 각각 보드의 행과 열의 크기)

```java
// 아래 내용을 main 함수 내의 skill.length(=K)에 대한 for문 안에서 수행하고 있었다..
public static void destroyOrRecover(int[] arr){
    int type = arr[0];
    int r1 = arr[1];
    int c1 = arr[2];
    int r2 = arr[3];
    int c2 = arr[4];
    int degree = arr[5];

    for(int i=r1; i<=r2; i++){
        for(int j=c1; j<=c2; j++){
            if(type==1) map[i][j] -= degree; // 1) 적군의 공격
            else map[i][j] += degree; // 2) 아군의 회복
        }
    }
}
```

### 차분 배열 사용 후

**효율성 정답**

```java
class Solution {

    static int[][] diff;

    public int solution(int[][] board, int[][] skill) {

        // 1. 차분 배열 생성
        int n = board.length;
        int m = board[0].length;
        diff = new int[n + 1][m + 1];

        **// 2. 차분 배열에 스킬 적용
        for (int[] sk : skill) {
            int type = sk[0];
            int r1 = sk[1], c1 = sk[2], r2 = sk[3], c2 = sk[4];
            int degree = (type == 1 ? -sk[5] : sk[5]);

            diff[r1][c1] += degree;
            diff[r1][c2 + 1] -= degree;
            diff[r2 + 1][c1] -= degree;
            diff[r2 + 1][c2 + 1] += degree;
        }

        // 3. 누적 합을 이용해 차분 배열을 원본 배열에 적용
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > 0) diff[i][j] += diff[i - 1][j];
                if (j > 0) diff[i][j] += diff[i][j - 1];
                if (i > 0 && j > 0) diff[i][j] -= diff[i - 1][j - 1];
                board[i][j] += diff[i][j];
            }
        }**

        return countNotDestroyed(board);
    }

    // 파괴되지 않은 건물 개수 카운트
    public int countNotDestroyed(int[][] map){
        int cnt=0;
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[i].length; j++){
                if(map[i][j]>0) cnt++;
            }
        }
        return cnt;
    }
}
```

> 💡 `diff[i][j]`는 (i, j) 위치에서 시작하는 값의 변화량!

**`diff[r1][c1] += degree`**

시작 지점인 `[r1, c1]`에 `degree`를 더한다. 이는 이 지점에서 변화가 시작된다는 의미.

**`diff[r1][c2 + 1] -= degree`**

범위의 끝 열의 다음 열에서 변화를 원래대로 돌릴거라고 표시. `[r1, c1]`에서 시작된 변화를 `[r1, c2]`까지만 적용하고, `[r1, c2 + 1]`부터는 적용하지 않도록 하기 위해!

**`diff[r2 + 1][c1] -= degree`**

범위의 끝 행의 다음 행에서 변화를 원래대로 돌릴거라고 표시. `[r1, c1]`에서 시작된 변화를 `[r2, c1]`까지만 적용하고, `[r2 + 1, c1]`부터는 적용하지 않도록 하기 위해!

**`diff[r2 + 1][c2 + 1] += degree`**

범위의 끝 지점의 다음 행과 다음 열에서 변화를 원래대로 돌릴거라고 표시. `diff[r2 + 1][c1]`와 `diff[r1][c2 + 1]`의 영향을 상쇄하여 `[r1, c1]`에서 시작된 변화를 `[r2, c2]`까지만 적용하기 위해!

**업데이트 과정**

초기 배열

```

0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
0 0 0 0 0
```

스킬이 `[1, 1, 3, 3, 5]` (즉, `[1, 1]`에서 `[3, 3]`까지 5를 더함)일 때, 차분 배열 `diff`는 다음과 같이 업데이트된다.

```
0 0  0  0  0  0
0 5  0  0 -5  0
0 0  0  0  0  0
0 0  0  0  0  0
0 -5 0  0  5  0
0 0  0  0  0  0
```

이제 누적 합을 계산하면:

```
0 0  0  0  0
0 5  5  5  0
0 5  5  5  0
0 5  5  5  0
0 0  0  0  0
```

따라서, `[1, 1]`에서 `[3, 3]`까지 5가 더해진 최종 배열을 얻을 수 있습니다. 이 방식은 각 업데이트를 O(1) 시간에 수행하고, 전체 배열의 누적 합을 계산하는 데 O(N \* M) 시간을 소요
