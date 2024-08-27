import java.util.*;

class UserSolution {

    static TreeMap<Integer, TreeSet<Integer>>[][] students;
    static Map<Integer, int[]> ids;

    public void init() {
        students = new TreeMap[4][2];
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                students[i][j] = new TreeMap<>();
            }
        }
        ids = new HashMap<>();
    }

    public int add(int mId, int mGrade, char mGender[], int mScore) {
        int gender = (mGender[0] == 'm') ? 1 : 0;

        students[mGrade][gender].putIfAbsent(mScore, new TreeSet<>());
        students[mGrade][gender].get(mScore).add(mId);

        ids.put(mId, new int[]{mScore, mGrade, gender});

        int maxScore = students[mGrade][gender].lastKey();
        return students[mGrade][gender].get(maxScore).last();
    }

    public int remove(int mId) {
        int[] info = ids.get(mId);
        if (info == null) {
            return 0;
        }

        int score = info[0];
        int grade = info[1];
        int gender = info[2];

        students[grade][gender].get(score).remove(mId);

        ids.remove(mId);

        if (students[grade][gender].get(score).isEmpty()) {
            students[grade][gender].remove(score);
        }

        if (!students[grade][gender].isEmpty()) {
            int minScore = students[grade][gender].firstKey();
            if (!students[grade][gender].get(minScore).isEmpty()) {
                return students[grade][gender].get(minScore).first();
            }
        }
        return 0;
    }

    public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });

        for (int i = 0; i < mGradeCnt; i++) {
            for (int j = 0; j < mGenderCnt; j++) {
                int grade = mGrade[i];
                int gender = (mGender[j][0] == 'm') ? 1 : 0;

                if (students[grade][gender] == null) {
                    continue;
                }

                Integer key = students[grade][gender].ceilingKey(mScore);
                if (key != null && !students[grade][gender].get(key).isEmpty()) {
                    int id = students[grade][gender].get(key).first();
                    pq.add(new int[]{key, id});
                }
            }
        }

        if (pq.isEmpty()) {
            return 0;
        }

        return pq.poll()[1];
    }
}
