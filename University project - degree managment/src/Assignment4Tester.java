public class Assignment4Tester {



    public static void main(String[] args) throws Exception{

        testPart1();
        testPart2();
        testPart3();

        System.out.println("All tests passed! This does not guarantee a 100!");

    }

    private static void testPart1() throws Exception {
        /* Test Course */
        Course algebra1 = new Course("Algebra 1", 100,4);
        Course algebra2 = new Course("Algebra 2", 101,4);
        algebra2.addPreliminaryCourse(algebra1);
        assertion(algebra2.getPreliminaryCourses().contains(algebra1), getLineNumber());
        Course algebra3 = new Course("Algebra 3", 102, 2);
        Course calculus1 = new Course("Calculus 1", 103, 2);
        List<Course> preCourses = new LinkedList<Course>();
        preCourses.add(algebra2);
        preCourses.add(calculus1);
        algebra3.addPreliminaryCourses(preCourses);
        assertion(algebra3.getAllPreliminaryCourses().contains(algebra1), getLineNumber());
        assertion(algebra3.getAllPreliminaryCourses().contains(calculus1), getLineNumber());
        algebra3.addPreliminaryCourse(algebra1);
        assertion(algebra1.isPreliminaryCourse(algebra3), getLineNumber());

        /* Test Degree */
        Degree cs = new BachelorDegree("Computer Science", 201);
        cs.addCourse(algebra3, true);
        assertion(cs.getDegreeName().equals("Computer Science"), getLineNumber());
        assertion(cs.getRequiredCredits() == 20, getLineNumber());
        assertion(cs.getMandatoryCourses().contains(algebra1),getLineNumber());
        Course theater = new Course("Theater", 901,1);
        cs.addCourse(theater, false);
        assertion(cs.getMandatoryCredits() == 12,getLineNumber());
        Degree masterCs = new MasterDegree("Computer Science", 203, true);
        assertion(masterCs instanceof Degree,getLineNumber());
        assertion((masterCs instanceof BachelorDegree) == false,getLineNumber());


        System.out.println("Part 1 tests passed");
    }

    private static void testPart2() throws Exception {
        Course algebra1 = new Course("Algebra 1", 100,4);
        /*Test Grade*/
        Grade algebraGrade = new Grade(algebra1, 100);
        assertion(algebraGrade.getGrade() == 100, getLineNumber());
        assertion(algebraGrade.getCourse().equals(algebra1), getLineNumber());
        /*Test Student Info */
        StudentInfo bobInfo = new StudentInfo("bob",
                "dob",
                123456789,
                "BobLand 1");
        assertion(bobInfo.getFirstName().equals("bob"), getLineNumber());
        assertion(bobInfo.getFamilyName().equals("dob"), getLineNumber());

        /* Test Student */
        Degree cs = new BachelorDegree("Computer Science", 201);
        cs.addCourse(algebra1, true);
        Student bob = new Student(bobInfo, cs);
        bob.increaseYear();
        assertion(bob.getCurrentYear() == 2, getLineNumber());
        bob.registerTo(algebra1);
        assertion(bob.getRegisteredCourses().contains(algebra1), getLineNumber());
        bob.addGrade(algebra1, 90);
        Course algebra2 = new Course("Algebra 2", 101,4);
        assertion(bob.isCompleted(algebra1,50), getLineNumber());
        assertion(!bob.getRegisteredCourses().contains(algebra1), getLineNumber());
        assertion(bob.averageGrade() == 90, getLineNumber());
        bob.registerTo(algebra2);
        bob.addGrade(algebra2, 100);
        assertion(bob.averageGrade() == 95, getLineNumber());
        assertion(bob.isCompleted(algebra1, 50), getLineNumber());

        /*Test Student Management System */
        StudentManagementSystem managementSystem = new StudentManagementSystem(56);
        Course algebra3 = new Course("Algebra 3", 102,4);

        managementSystem.addCourse(algebra1);
        managementSystem.addCourse(algebra2);
        managementSystem.addDegree(cs);
        managementSystem.addStudent(bob);
        cs.addCourse(algebra2, true);
        cs.addCourse(algebra3, true);
        algebra2.addPreliminaryCourse(algebra1);
        algebra3.addPreliminaryCourse(algebra2);
        managementSystem.addCourse(algebra3);
        assertion(managementSystem.register(123456789, algebra3), getLineNumber());
        List<Pair<Integer, Integer>> grades = new LinkedList<Pair<Integer, Integer>>();
        grades.add(new Pair(123456789, 90));
        assertion(managementSystem.closeCourse(algebra3,grades).size() == 0, getLineNumber());
        StudentInfo joeInfo = new StudentInfo("joe",
                "job",
                123456780,
                "JoeLand 1");
        Student joe = new Student(joeInfo, cs);
        managementSystem.addStudent(joe);
        assertion(!managementSystem.register(123456780, algebra3), getLineNumber());
        assertion(managementSystem.getMissingPreCourses(algebra3, 123456780).size() == 2, getLineNumber());
        Course gym = new Course("Gym", 1001, 6);
        Course tennis = new Course("Tennis", 1002, 6);
        cs.addCourse(gym, false);
        cs.addCourse(tennis, false);
        managementSystem.addCourse(algebra3);
        managementSystem.addCourse(gym);
        managementSystem.addCourse(tennis);
        assertion(managementSystem.register(123456789, gym), getLineNumber());
        assertion(managementSystem.register(123456789, tennis), getLineNumber());
        managementSystem.addGrade(gym, 123456789, 100);
        managementSystem.addGrade(tennis, 123456789, 100);
        managementSystem.addGrade(algebra3, 123456789, 100);
        assertion(managementSystem.closeDegree(123456789, 2020), getLineNumber());
        System.out.println("Part 2 tests passed");
    }

    private static void testPart3() throws Exception {
        Course algebra1 = new Course("Algebra 1", 100,4);
        /*Building the Student management System*/
        Grade algebraGrade = new Grade(algebra1, 100);
        assertion(algebraGrade.getGrade() == 100, getLineNumber());
        assertion(algebraGrade.getCourse().equals(algebra1), getLineNumber());
        StudentInfo bobInfo = new StudentInfo("bob",
                "dob",
                123456789,
                "BobLand 1");
        assertion(bobInfo.getFirstName().equals("bob"), getLineNumber());
        assertion(bobInfo.getFamilyName().equals("dob"), getLineNumber());
        Degree cs = new BachelorDegree("Computer Science", 201);
        cs.addCourse(algebra1, true);
        Student bob = new Student(bobInfo, cs);
        assertion(bob.getStudentInfo().getFirstName().equals("bob"), getLineNumber());
        assertion(bob.getDegree().equals(cs), getLineNumber());
        assertion(bob.getCurrentYear() == 1, getLineNumber());
        bob.increaseYear();
        assertion(bob.getCurrentYear() == 2, getLineNumber());
        bob.registerTo(algebra1);
        assertion(bob.getRegisteredCourses().contains(algebra1), getLineNumber());
        bob.addGrade(algebra1, 90);
        Course algebra2 = new Course("Algebra 2", 101,4);

        assertion(bob.isCompleted(algebra1,50), getLineNumber());
        assertion(bob.getRegisteredCourses().contains(algebra1) == false, getLineNumber());
        assertion(bob.averageGrade() == 90, getLineNumber());
        bob.registerTo(algebra2);
        bob.addGrade(algebra2, 100);
        assertion(bob.averageGrade() == 95, getLineNumber());
        assertion(bob.isCompleted(algebra1, 50), getLineNumber());
        StudentManagementSystem managementSystem = new StudentManagementSystem(56);
        Course algebra3 = new Course("Algebra 3", 102,4);
        managementSystem.addCourse(algebra1);
        managementSystem.addCourse(algebra2);
        managementSystem.addDegree(cs);
        managementSystem.addStudent(bob);
        cs.addCourse(algebra2, true);
        cs.addCourse(algebra3, true);
        algebra2.addPreliminaryCourse(algebra1);
        algebra3.addPreliminaryCourse(algebra2);
        managementSystem.addCourse(algebra3);
        assertion(managementSystem.register(123456789, algebra3), getLineNumber());
        List<Pair<Integer, Integer>> grades = new LinkedList<Pair<Integer, Integer>>();
        grades.add(new Pair(123456789, 90));
        assertion(managementSystem.closeCourse(algebra3,grades).size() == 0, getLineNumber());
        StudentInfo joeInfo = new StudentInfo("joe",
                "job",
                123456780,
                "JoeLand 1");
        Student joe = new Student(joeInfo, cs);
        managementSystem.addStudent(joe);
        assertion(!managementSystem.register(123456780, algebra3), getLineNumber());
        assertion(managementSystem.getMissingPreCourses(algebra3, 123456780).size() == 2, getLineNumber());
        Course gym = new Course("Gym", 1001, 6);
        Course tennis = new Course("Tennis", 1002, 6);
        cs.addCourse(gym, false);
        cs.addCourse(tennis, false);
        managementSystem.addCourse(algebra3);
        managementSystem.addCourse(gym);
        managementSystem.addCourse(tennis);
        assertion(managementSystem.register(123456789, gym), getLineNumber());
        assertion(managementSystem.register(123456789, tennis), getLineNumber());
        managementSystem.addGrade(gym, 123456789, 100);
        managementSystem.addGrade(tennis, 123456789, 100);
        managementSystem.addGrade(algebra3, 123456789, 100);
        assertion(managementSystem.closeDegree(123456789, 2020), getLineNumber());
        /* Tests for part 3 */
        assertion(managementSystem.getMissingCourses(123456780).size() == 3, getLineNumber());
        assertion(managementSystem.nextAvailableCourses(123456780).size() == 3, getLineNumber());
        assertion(managementSystem.register(123456780, gym), getLineNumber());
        assertion(managementSystem.getRegisteredStudents(gym).size() == 1, getLineNumber());
        assertion(managementSystem.getFirstKStudents(cs,2020,1).size() == 1, getLineNumber());
        managementSystem.addGrade(gym, 123456780, 20);
        assertion(managementSystem.getFailStudents(gym).size() == 1, getLineNumber());
        System.out.println("Part 3 tests passed");
    }


    public static void assertion(boolean value, int lineNumber) throws Exception {
        if(!value) throw new Exception("Failed test at line: " + lineNumber);
    }

    public static int getLineNumber() {
        return strictlyNamedMethodForGettingLineNumber();
    }

    private static int strictlyNamedMethodForGettingLineNumber() {
        boolean thisOne = false;
        int thisOneCountDown = 1;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(StackTraceElement element : elements) {
            String methodName = element.getMethodName();
            int lineNum = element.getLineNumber();
            if(thisOne && (thisOneCountDown == 0)) {
                return lineNum;
            } else if(thisOne) {
                thisOneCountDown--;
            }
            if(methodName.equals("strictlyNamedMethodForGettingLineNumber")) {
                thisOne = true;
            }
        }
        return -1;
    }

}
