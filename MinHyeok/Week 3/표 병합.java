import java.util.*;

class Union {
    String value = "EMPTY";
    Union parent = this;
    
    public Union find() {
        if (this.parent != this) {
            this.parent = this.parent.find();
        }
        return this.parent;
    }
    
}

class Solution {
    public String[] solution(String[] commands) {
        Union[][] table = new Union[51][51];
        for (int r = 1; r <= 50; r++) {
            for (int c = 1; c <= 50; c++) {
                table[r][c] = new Union();
            }
        }
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            StringTokenizer st = new StringTokenizer(commands[i]);
            String command = st.nextToken();
            switch (command) {
                case "UPDATE":
                    if (st.countTokens() == 3) { // r, c 가 주어짐
                        int r = Integer.parseInt(st.nextToken());
                        int c = Integer.parseInt(st.nextToken());
                        table[r][c].find().value = st.nextToken();
                    } else { // value 가 주어짐
                        String prevValue = st.nextToken();
                        String nextValue = st.nextToken();
                        for (int r = 1; r <= 50; r++) {
                            for (int c = 1; c <= 50; c++) {
                                Union currentUnion = table[r][c].find();
                                if (currentUnion.value.equals(prevValue)) {
                                    currentUnion.value = nextValue;
                                } 
                            }
                        }
                    }
                    break;
                case "MERGE":
                    int r1 = Integer.parseInt(st.nextToken());
                    int c1 = Integer.parseInt(st.nextToken());
                    int r2 = Integer.parseInt(st.nextToken());
                    int c2 = Integer.parseInt(st.nextToken());
                    String value = table[r2][c2].find().value;
                    table[r2][c2].find().parent = table[r1][c1].find();
                    if (table[r1][c1].find().value.equals("EMPTY")) {
                        table[r1][c1].find().value = value;
                    }
                    break;
                case "UNMERGE":
                    int r = Integer.parseInt(st.nextToken());
                    int c = Integer.parseInt(st.nextToken());
                    Union parent = table[r][c].find();
                    value = parent.value;
                    parent.value = "EMPTY";
                    List<int[]> list = new ArrayList<>();
                    for (int a = 1; a <= 50; a++) {
                        for (int b = 1; b <= 50; b++) {
                            if (table[a][b].find() == parent) {
                                list.add(new int[] {a, b});
                            }
                        }
                    }
                    for (int idx = 0; idx < list.size(); idx++) {
                        int[] arr = list.get(idx);
                        table[arr[0]][arr[1]].parent = table[arr[0]][arr[1]];
                        table[arr[0]][arr[1]].value = "EMPTY";
                    }
                    table[r][c].value = value;
                    break;
                case "PRINT":
                    r = Integer.parseInt(st.nextToken());
                    c = Integer.parseInt(st.nextToken());
                    tmp.add(table[r][c].find().value);
                    break;
            }
        }
        String[] answer = new String[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            answer[i] = tmp.get(i);
        }
        return answer;
    }
}