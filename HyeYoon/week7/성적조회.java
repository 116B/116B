import java.util.*;

class UserSolution {

    // HashMaps to store student records based on grade and gender
    private HashMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> maleRecords;
    private HashMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> femaleRecords;
    private HashMap<Integer, String> studentGender; // Maps student ID to their gender
    private HashMap<Integer, Integer> studentGrade; // Maps student ID to their grade

    public void init() {
        maleRecords = new HashMap<>();
        femaleRecords = new HashMap<>();
        studentGender = new HashMap<>();
        studentGrade = new HashMap<>();
    }

    public int add(int mId, int mGrade, char mGender[], int mScore) {
        String gender = new String(mGender).trim();
        TreeMap<Integer, TreeMap<Integer, Integer>> records = getRecords(mGrade, gender);

        if (!records.containsKey(mScore)) {
            records.put(mScore, new TreeMap<>());
        }
        records.get(mScore).put(mId, mGrade);

        studentGender.put(mId, gender);
        studentGrade.put(mId, mGrade);

        return getHighestId(records);
    }

    public int remove(int mId) {
        if (!studentGender.containsKey(mId)) {
            return 0;
        }

        int mGrade = studentGrade.get(mId);
        String gender = studentGender.get(mId);
        TreeMap<Integer, TreeMap<Integer, Integer>> records = getRecords(mGrade, gender);

        for (Map.Entry<Integer, TreeMap<Integer, Integer>> entry : records.entrySet()) {
            TreeMap<Integer, Integer> ids = entry.getValue();
            if (ids.containsKey(mId)) {
                ids.remove(mId);
                if (ids.isEmpty()) {
                    records.remove(entry.getKey());
                }
                break;
            }
        }

        studentGender.remove(mId);
        studentGrade.remove(mId);

        if (records.isEmpty()) {
            return 0;
        }
        return getLowestId(records);
    }

    public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        int minId = 0;
        Integer minScore = null;

        for (int i = 0; i < mGradeCnt; i++) {
            for (int j = 0; j < mGenderCnt; j++) {
                String gender = new String(mGender[j]).trim();
                TreeMap<Integer, TreeMap<Integer, Integer>> records = getRecords(mGrade[i], gender);

                SortedMap<Integer, TreeMap<Integer, Integer>> tailMap = records.tailMap(mScore);
                if (!tailMap.isEmpty()) {
                    Integer currentMinScore = tailMap.firstKey();
                    Integer currentMinId = tailMap.get(currentMinScore).firstKey();

                    if (minScore == null || currentMinScore < minScore ||
                            (currentMinScore.equals(minScore) && currentMinId < minId)) {
                        minScore = currentMinScore;
                        minId = currentMinId;
                    }
                }
            }
        }

        return minId;
    }

    private TreeMap<Integer, TreeMap<Integer, Integer>> getRecords(int grade, String gender) {
        HashMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> recordsMap = gender.equals("male") ? maleRecords : femaleRecords;

        if (!recordsMap.containsKey(grade)) {
            recordsMap.put(grade, new TreeMap<>());
        }
        return recordsMap.get(grade);
    }

    private int getHighestId(TreeMap<Integer, TreeMap<Integer, Integer>> records) {
        return records.lastEntry().getValue().lastKey();
    }

    private int getLowestId(TreeMap<Integer, TreeMap<Integer, Integer>> records) {
        return records.firstEntry().getValue().firstKey();
    }
}
