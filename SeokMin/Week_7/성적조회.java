import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    private final static int CMD_INIT = 100;
    private final static int CMD_ADD = 200;
    private final static int CMD_REMOVE = 300;
    private final static int CMD_QUERY = 400;

    private final static UserSolution usersolution = new UserSolution();

    private static void String2Char(char[] buf, String str) {
        for (int k = 0; k < str.length(); ++k)
            buf[k] = str.charAt(k);
        buf[str.length()] = '\0';
    }
    private static boolean run(BufferedReader br) throws Exception {
        int q = Integer.parseInt(br.readLine());

        int id, grade, score;
        int cmd, ans, ret;
        boolean okay = false;

        for (int i = 0; i < q; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {
                case CMD_INIT:
                    usersolution.init();
                    okay = true;
                    break;
                case CMD_ADD:
                    char[] gender = new char[7];
                    id = Integer.parseInt(st.nextToken());
                    grade = Integer.parseInt(st.nextToken());
                    String2Char(gender, st.nextToken());
                    score = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.add(id, grade, gender, score);
                    if (ret != ans)
                        okay = false;
                    break;
                case CMD_REMOVE:
                    id = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.remove(id);
                    if (ret != ans)
                        okay = false;
                    break;
                case CMD_QUERY:
                    int gradeCnt, genderCnt;
                    int[] gradeArr = new int[3];
                    char[][] genderArr = new char[2][7];
                    gradeCnt = Integer.parseInt(st.nextToken());
                    for (int j = 0; j < gradeCnt; ++j) {
                        gradeArr[j] = Integer.parseInt(st.nextToken());
                    }
                    genderCnt = Integer.parseInt(st.nextToken());
                    for (int j = 0; j < genderCnt; ++j) {
                        String2Char(genderArr[j], st.nextToken());
                    }
                    score = Integer.parseInt(st.nextToken());
                    ans = Integer.parseInt(st.nextToken());
                    ret = usersolution.query(gradeCnt, gradeArr, genderCnt, genderArr, score);
                    if (ret != ans)
                        okay = false;
                    break;
                default:
                    okay = false;
                    break;
            }
        }
        return okay;
    }

    public static void main(String[] args) throws Exception {
        int TC, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        TC = Integer.parseInt(st.nextToken());
        MARK = Integer.parseInt(st.nextToken());

        for (int testcase = 1; testcase <= TC; ++testcase) {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        br.close();
    }
}


class UserSolution {

    static class Student implements Comparator<Student> {
        int id, grade, score;
        String gender;

        public Student(int id, int grade, int score, String gender) {
            this.id = id;
            this.grade = grade;
            this.score = score;
            this.gender = gender;
        }

        @Override
        public int compare(Student s1, Student s2) {
            return s1.score == s2.score ? s1.id - s2.id : s1.score - s2.score;
        }
    }

    // ID, 대응되는 학생
    static Map<Integer, Student> studentInfo;
    // 학년, 성별, 학생
    // Map<mGrade, Map<gender, Treeset<Student>>>
    static Map<Integer, Map<String, TreeSet<Student>>> sortByGra;

    public void init() {
        studentInfo = new HashMap<>();
        sortByGra = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            sortByGra.put(i, new HashMap<>());
            sortByGra.get(i).put("male", new TreeSet<Student>());
            sortByGra.get(i).put("female", new TreeSet<Student>());
        }
        return;
    }

    // 학생 ID, 학년, 성별, 점수
    public int add(int mId, int mGrade, char mGender[], int mScore) {
        String gender = new String(mGender).trim();
        Student student = new Student(mId, mGrade, mScore, gender);
        studentInfo.put(mId, student);
        TreeSet<Student> scores = sortByGra.get(mGrade).get(gender);
        scores.add(student);

        return scores.last().id;
    }

    // 학생 ID
    public int remove(int mId) {
        Student student = studentInfo.get(mId);
        if (student == null) {
            return 0;
        }
        studentInfo.remove(student.id);
        String gender = student.gender;
        int grade = student.grade;
        TreeSet<Student> scores = sortByGra.get(grade).get(gender);
        scores.remove(student);

        return scores.first() == null ? 0 : scores.first().id;
    }

    // 학년,
    public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        TreeSet<Student> students = new TreeSet<>();
        for (int i = 0; i < mGradeCnt; i++) {
            for (int j = 0; j < mGenderCnt; j++) {
                String gender = new String(mGender[j]).trim();
                TreeSet<Student> score = sortByGra.get(mGrade[i]).get(gender);
                while (score.last().score >= mScore) {
                    students.add(score.pollLast());
                }
            }
        }
        return students == null ? 0 : students.first().id;
    }
}